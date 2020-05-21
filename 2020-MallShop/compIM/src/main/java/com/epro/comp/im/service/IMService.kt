package com.epro.comp.im.service

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import android.text.TextUtils
import com.epro.comp.im.CompIM
import com.epro.comp.im.listener.ChatMessageChangeEvent
import com.epro.comp.im.listener.ConnectSuccessEvent
import com.epro.comp.im.listener.IMServiceCloseEvent
import com.epro.comp.im.mvp.model.bean.*
import com.epro.comp.im.smack.SmackManager
import com.epro.comp.im.utils.IMBusManager
import com.epro.comp.im.utils.IMConst
import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.mike.baselib.utils.*
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.StanzaListener
import org.jivesoftware.smack.XMPPConnection
import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.chat2.IncomingChatMessageListener
import org.jivesoftware.smack.chat2.OutgoingChatMessageListener
import org.jivesoftware.smack.filter.AndFilter
import org.jivesoftware.smack.filter.StanzaTypeFilter
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.packet.Stanza
import org.jivesoftware.smackx.filetransfer.FileTransfer
import org.jivesoftware.smackx.filetransfer.FileTransferListener
import org.jivesoftware.smackx.filetransfer.FileTransferRequest
import org.jivesoftware.smackx.ping.PingFailedListener
import org.jivesoftware.smackx.ping.PingManager
import org.jxmpp.jid.EntityBareJid
import org.jxmpp.jid.impl.JidCreate
import java.io.File
import java.io.FileInputStream


/**
 * 即时通讯服务,长连接
 */
class IMService : Service() {
    companion object {
        fun startIMService(activity: Activity, shopId: String, csId: String) {
            activity.startService(Intent(activity, IMService::class.java).putExtra("shopId", shopId).putExtra("csId", csId))
        }

        fun stopIMService(activity: Activity) {
            activity.stopService(Intent(activity, IMService::class.java))
        }

        fun bindService(activity: Activity, shopId: String, csId: String, serviceConnection: ServiceConnection) {
            activity.bindService(Intent(activity, IMService::class.java).putExtra("shopId", shopId).putExtra("csId", csId), serviceConnection, Context.BIND_AUTO_CREATE)
        }

        fun unbindService(activity: Activity, serviceConnection: ServiceConnection) {
            activity.unbindService(serviceConnection)
        }
    }

    private var compositeDisposable = CompositeDisposable()

    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun clearSubscription() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return IMBinder()
    }

    /**
     * 聊天接收监听
     */

    var incomingChatMessageListener: IncomingChatMessageListener = object : IncomingChatMessageListener {
        override fun newIncomingMessage(from: EntityBareJid?, message: Message?, chat: Chat?) {
            LogTools.debug("IMService", "from:" + from.toString() + "  message:" + message.toString() + " chat" + chat.toString())
            if (chat != null) {
                // LogTools.debug("IMService", message.toString())
                if (message != null && !TextUtils.isEmpty(message.body)) {
                    LogTools.debug("IMService", message.toString() + "###" + message.body)
                    val disposable = Observable.just(true).flatMap {
                        val listenners = ReflectionUtils.getFieldValue(smackManager.chatManager, "incomingListeners") as Set<*>
                        LogTools.debug("IMService", listenners.size)
                        val fromCsId = message.from.split("@")[0]
                        val cs = IMBusManager.getCustomerService(fromCsId)
                        if (cs != null) { //本地找shopId
                            LogTools.debug("IMService", cs.shopId)
                            shopId = cs.shopId
                            val msg = IMBusManager.saveRecvTextMsgToLocal<MsgBody>(message, cs.shopId)
                            IMBusManager.updateRecordByMessageAdd(msg!!)
                            return@flatMap Observable.just(msg)
                        } else { //网络找shopId
                            return@flatMap findShopId(fromCsId, message)
                        }
                    }.compose(SchedulerUtils.ioToMain()).subscribe({ bean ->
                        val event = ChatMessageChangeEvent<MsgBody>()
                        event.message = bean
                        EventBus.getDefault().post(event)
                    }, { throwable ->
                        //处理异常
                        ExceptionHandle.handleException(throwable)
                    })
                    addSubscription(disposable)
                }
            }
        }
    }

    var outgoingChatMessageListener: OutgoingChatMessageListener = object : OutgoingChatMessageListener {
        override fun newOutgoingMessage(to: EntityBareJid?, message: Message?, chat: Chat?) {
            LogTools.debug("IMService", "to:" + to.toString() + "  message:" + message.toString() + " chat" + chat.toString())
        }
    }
    var fileTransferListener: FileTransferListener = object : FileTransferListener {
        override fun fileTransferRequest(request: FileTransferRequest?) {
            LogTools.debug("IMService_file", request?.description)
            LogTools.debug("IMService_file", request?.fileName)
            LogTools.debug("IMService_file", request?.requestor)
            if (request == null) {
                return
            }
            recvFileMsg<ImageMsgBody>(request)
        }

    }
    /**
     * 连接监听
     */
    var connectListener = object : ConnectionListener {
        override fun connected(connection: XMPPConnection?) {
            LogTools.debug("IMService", "connected" + Thread.currentThread().name)
        }

        override fun connectionClosed() {
            LogTools.debug("IMService", "connectionClosed" + Thread.currentThread().name)
            startReconnect()
        }

        override fun connectionClosedOnError(e: Exception?) {
            LogTools.debug("IMService", "connectionClosedOnError" + Thread.currentThread().name)
            startReconnect()
        }

        override fun authenticated(connection: XMPPConnection?, resumed: Boolean) {

        }

    }

    var pingFailedListener = object : PingFailedListener {
        override fun pingFailed() {
            LogTools.debug("IMService", "pingFailed" + Thread.currentThread().name)
            startReconnect()
        }
    }

    var stanzaListener = object : StanzaListener {
        override fun processStanza(packet: Stanza?) {
            LogTools.debug("IMService", packet?.from.toString())
            LogTools.debug("IMService", Thread.currentThread().name)
            if (packet is Presence) {
                LogTools.debug("IMService", packet.toString())
                if (!TextUtils.isEmpty(csId) && packet.from.toString().contains(csId.toString()) && !packet.isAvailable) { //客服离线,断开连接
                    LogTools.debug("IMService", packet.from.toString() + "  " + packet.type)
                    //stopSelf()
                }
            }
        }
    }

    private fun findShopId(csId: String, message: Message): Observable<ChatMessage<MsgBody>> {
        return CompIM.getApiService().getCustomerService(csId).flatMap {
            if (it.code == ErrorStatus.SUCCESS) {
                LogTools.debug("IMService", it.result)
                if (it.result.list.isNotEmpty() && !TextUtils.isEmpty(it.result.list[0].shopId)) {
                    if (it.result.type == IMConst.CS_TYPE_MERCH) {
                        for (c in it.result.list) {
                            c.shopLogo=it.result.shopLogo
                            c.avatar=it.result.shopLogo
                            c.shopId=it.result.shopId
                            c.shopName=it.result.shopName
                        }
                        val shop = IMShop()
                        shop.shopId = it.result.list[0].shopId
                        shop.shopName = it.result.list[0].shopName
                        shop.logoUrl = it.result.list[0].shopLogo
                        IMBusManager.saveIMShop(shop)
                    }
                    IMBusManager.saveCustomerService(it.result.list)
                    val msg = IMBusManager.saveRecvTextMsgToLocal<MsgBody>(message, it.result.list[0].shopId)
                    IMBusManager.updateRecordByMessageAdd(msg!!)
                    return@flatMap Observable.just(msg)
                } else {
                    return@flatMap Observable.error<ChatMessage<MsgBody>>(ApiException("shopId is null", -10000))
                }
            } else {
                return@flatMap Observable.error<ChatMessage<MsgBody>>(ApiException(it.message, it.code))
            }
        }
    }

    //开始重连
    private fun startReconnect() {
        if (smackManager.connectionInstance != null && smackManager.connectionInstance.isConnected) {
            smackManager.connectionInstance.disconnect()
        }
        if (rxTimer.isDisposed) {
            rxTimer.interval(10 * 1000, RxTimer.IRxNext {
                login(csId)
            })
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (AppBusManager.isLogin() && smackManager.connectionInstance != null) {
            smackManager.connectionInstance.addConnectionListener(connectListener)
        }
    }

    var pingManager: PingManager? = null;
    var smackManager: SmackManager = SmackManager.getInstance()
    private fun login(csId: String?) {
        if (!AppBusManager.isLogin()) {
            stopSelf()
            return
        }
        var tag = 0
        LogTools.debug("IMService", "login doing")
        val disposable = Observable.just(true).flatMap {
            if (smackManager.isAuthenticated) {
                //告诉客服上线了
                setOnline()
                rxTimer.cancel()
                LogTools.debug("IMService", "login do1 Success")
                return@flatMap Observable.just(true)
            }
            smackManager.login(AppBusManager.getUsername(), AppBusManager.getUsername())
            val result = smackManager.isAuthenticated
            if (result) {
                tag = 2
                IMBusManager.setUser(User(AppBusManager.getUsername(), AppBusManager.getUsername(), AppBusManager.getUsername(), AppBusManager.getUserAvatar()))
                LogTools.debug("IMService", "login do2 Success")
                //设置监听器
                if (!isAdded) {
                    smackManager.addFileTransferListener(fileTransferListener)
                    smackManager.chatManager.addIncomingListener(incomingChatMessageListener)
                    smackManager.chatManager.addOutgoingListener(outgoingChatMessageListener)
                    val listenners = ReflectionUtils.getFieldValue(smackManager.chatManager, "incomingListeners") as Set<*>
                    LogTools.debug("IMService", listenners)
                    PingManager.setDefaultPingInterval(10)//Ping every 10 seconds
                    pingManager = PingManager.getInstanceFor(smackManager.connection)
                    //Set PingListener here to catch connect status
                    pingManager!!.registerPingFailedListener(pingFailedListener)
                    smackManager.connection.addAsyncStanzaListener(stanzaListener, AndFilter(StanzaTypeFilter(Presence::class.java)))
                    isAdded = true
                }

                //告诉客服上线了

                setOnline()
                rxTimer.cancel()

                if (TextUtils.isEmpty(csId)) {
                    return@flatMap Observable.just(true)
                } else {
                }
                //登录成功加好友
                //return@flatMap addFriend()
                return@flatMap Observable.just(true)
            } else {
                return@flatMap Observable.error<Boolean>(ApiException("登录失败", ErrorStatus.API_ERROR))
            }
        }.flatMap {
            if (it) { //登录成功
                addOfflineMessage()
            } else {
                return@flatMap Observable.error<Boolean>(ApiException("登录失败", ErrorStatus.API_ERROR))
            }
            return@flatMap Observable.just(true)
        }.compose(SchedulerUtils.ioToMain()).subscribe({ bean ->
            //toast("即时通讯登录成功"+tag)
            val event = ConnectSuccessEvent()
            event.user = User(AppBusManager.getUsername(), AppBusManager.getUsername(), AppBusManager.getUsername(), AppBusManager.getUserAvatar())
            event.tag = tag
            EventBus.getDefault().post(event)

        }, { throwable ->
            //处理异常
            stopSelf()
            ExceptionHandle.handleException(throwable)
            LogTools.debug("IMService", ExceptionHandle.errorMsg)
            //toast(ExceptionHandle.errorMsg)
        })
        addSubscription(disposable)
    }

    /**
     * 告诉客服上线
     */
    private fun setOnline() {
        LogTools.debug("IMService", "setOnline")
        val presence = Presence(Presence.Type.available)
        smackManager.connection.sendStanza(presence)
        if (!TextUtils.isEmpty(csId)) {
            val presenceTo = Presence(JidCreate.entityBareFrom(smackManager.getChatJid(csId)), Presence.Type.available)
            smackManager.connection.sendStanza(presenceTo)
            this@IMService.sendTextMsg("", csId!!)
        } else {
            val records = IMBusManager.getChatRecords()
            for (r in records) {
                if (!TextUtils.isEmpty(r.recentChatMessage.csId)) {
                    val presenceTo = Presence(JidCreate.entityBareFrom(smackManager.getChatJid(r.recentChatMessage.csId)), Presence.Type.available)
                    smackManager.connection.sendStanza(presenceTo)
                    this@IMService.sendTextMsg("", r.recentChatMessage.csId)
                }
            }
        }
    }

    private fun setOffline() {
        LogTools.debug("IMService", "setOffline")
        val presence = Presence(Presence.Type.unavailable)
        smackManager.connection.sendStanza(presence)
        if (!TextUtils.isEmpty(csId)) {
            val presenceTo = Presence(JidCreate.entityBareFrom(smackManager.getChatJid(csId)), Presence.Type.unavailable)
            smackManager.connection.sendStanza(presenceTo)
            //this@IMService.sendTextMsg("")
        }
    }

    /**
     * 添加客服朋友
     */
    private fun addFriend(): Observable<Boolean> {
        //登录成功加好友
        val friends = smackManager.allFriends
        var flag = false
        for (f in friends) {
            if (f.name != null && f.name.contains(csId!!)) {
                flag = true
                break
            }
        }
        for (f in friends) {
            LogTools.debug(f.name)
        }
        if (flag) {
            return Observable.just(true)
        } else {
            flag = smackManager.addFriend(csId, csId, null)
            if (flag) {
                val entry = smackManager.getFriend(csId)
                return Observable.just(true)
            } else {
                return Observable.error<Boolean>(ApiException("加好友失败", ErrorStatus.API_ERROR))
            }
        }
    }

    /**
     * 添加离线消息
     */
    private fun addOfflineMessage() {
        //接收离线消息
//                val offlineManager = OfflineMessageManager(smackManager.connection)
//                val messages = offlineManager.messages
//                for (m in messages) {
//                    if (!TextUtils.isEmpty(m.body)) {
//                        val msg = IMBusManager.saveRecvTextMsgToLocal<MsgBody>(m, true)
//                        IMBusManager.updateRecordByMessageAdd(msg!!)
//                    }
//                }
//                //删除离线消息
//                offlineManager.deleteMessages()
        //将状态设置成在线
//                val presence = Presence(Presence.Type.available)
//                presence.mode = Presence.Mode.available
//                smackManager.connection.sendStanza(presence)
    }


    var rxTimer: RxTimer = RxTimer()
    var isAdded = false
    var csId: String = ""
    var shopId: String = ""
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogTools.debug("IMService", "onStartCommand")
        csId = intent?.getStringExtra("csId")!!
        shopId = intent?.getStringExtra("shopId")!!
        login(csId)
        LogTools.debug("IMService", csId == "")
        LogTools.debug("IMService", shopId == "")
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 发送文本消息
     */
    private fun sendTextMsg(text: String, csId: String) {
        val disposable = Observable.just(true).flatMap {
            val chat = smackManager.createChat(csId)
            val message = IMBusManager.getBaseSendMessage<TextMsgBody>(MsgType.TEXT)
            if (chat == null) {
                return@flatMap Observable.just(message)
            }
            chat.send(text)
            if (TextUtils.isEmpty(text)) {
                return@flatMap Observable.just(message)
            }
            val textMsgBody = TextMsgBody()
            textMsgBody.message = text
            message.body = textMsgBody
            message.sentStatus = MsgSendStatus.SENT
            message.csId = csId
            message.customerId = IMBusManager.getUser()!!.userId
            message.shopId = shopId
            message.isRead = true
            IMBusManager.saveMessage(message)
            IMBusManager.updateRecordByMessageAdd(message)
            return@flatMap Observable.just(message)
        }.compose(SchedulerUtils.ioToMain()).subscribe({ bean ->
            //发送文字信息成功
            if (TextUtils.isEmpty(text)) {
                return@subscribe
            }
            val event = ChatMessageChangeEvent<TextMsgBody>()
            event.message = bean
            EventBus.getDefault().post(event)

        }, { throwable ->
            //处理异常
            ExceptionHandle.handleException(throwable)
            toast(ExceptionHandle.errorMsg)
        })
        addSubscription(disposable)
    }

    /**
     * 接收文件
     */
    private fun <T : MsgBody> recvFileMsg(request: FileTransferRequest) {
        val disposable = Observable.just(true).flatMap {
            // Accept it
            val transfer = request.accept()
            val dir = File(AppContext.getInstance().context.externalCacheDir!!.absolutePath, "chat")
            if (!dir.exists()) {
                dir.mkdir()
            }
            val file = File(dir, request.fileName)
            transfer.receiveFile(file)

            var msgType = MsgType.IMAGE
            val message = IMBusManager.getBaseSendMessage<T>(msgType)
            when (msgType) {
                MsgType.IMAGE -> {
                    val imageMsgBody = ImageMsgBody()
                    imageMsgBody.originalPath = file.absolutePath
                    message.body = imageMsgBody as T
                }
                else -> {
                }
            }

            message.csId = request.requestor.split("@")[0]
            message.customerId = IMBusManager.getUser()!!.userId
            message.isSend = false
            LogTools.debug("IMService_file", transfer.isDone)
            LogTools.debug("IMService_file", transfer.status.toString())
            while (!transfer.isDone) {
                Thread.sleep(200)
            }
            if (transfer.status == FileTransfer.Status.complete) {
                message.sentStatus = MsgSendStatus.SENT
                IMBusManager.saveMessage(message)
                IMBusManager.updateRecordByMessageAdd(message)
            } else {
                message.sentStatus = MsgSendStatus.FAILED
                return@flatMap Observable.error<ChatMessage<T>>(ApiException(transfer.exception.message.toString(), ErrorStatus.API_ERROR))
            }
            LogTools.debug("IMService_file", transfer.isDone)
            LogTools.debug("IMService_file", transfer.status.toString())
            return@flatMap Observable.just(message)
        }.compose(SchedulerUtils.ioToMain()).subscribe({ bean ->
            val event = ChatMessageChangeEvent<T>()
            event.message = bean
            EventBus.getDefault().post(event)
        }, { throwable ->
            //处理异常
            ExceptionHandle.handleException(throwable)
            LogTools.debug("IMService", ExceptionHandle.errorMsg)
            //toast(ExceptionHandle.errorMsg)
        })
        addSubscription(disposable)
    }

    /**
     * 发送大文件
     */

    private fun <T : MsgBody> sendFileMsg(file: File, msgType: MsgType) {
        val disposable = Observable.just(true).flatMap {
            LogTools.debug("IMService_file", file.exists())
            val transfer = smackManager.getSendFileTransfer(smackManager.getFileTransferJid(csId))
            transfer.sendFile(file, file.name)
            LogTools.debug("IMService_file", transfer.isDone)
            LogTools.debug("IMService_file", transfer.status.toString())
            val message = IMBusManager.getBaseSendMessage<T>(MsgType.IMAGE)
            when (msgType) {
                MsgType.IMAGE -> {
                    val imageMsgBody = ImageMsgBody()
                    imageMsgBody.originalPath = file.absolutePath
                    message.body = imageMsgBody as T
                }
                else -> {
                }
            }
            message.csId = csId
            message.customerId = IMBusManager.getUser()!!.userId
            message.shopId = shopId
            message.isRead=true
            IMBusManager.saveMessage(message)
            IMBusManager.updateRecordByMessageAdd(message)
            while (!transfer.isDone) {
                Thread.sleep(200)
            }
            if (transfer.status == FileTransfer.Status.complete) {
                message.sentStatus = MsgSendStatus.SENT
            } else {
                message.sentStatus = MsgSendStatus.FAILED
            }
            LogTools.debug("IMService_file", transfer.isDone)
            LogTools.debug("IMService_file", transfer.status.toString())
            return@flatMap Observable.just(message)
        }.compose(SchedulerUtils.ioToMain()).subscribe({ bean ->
            val event = ChatMessageChangeEvent<T>()
            event.message = bean
            EventBus.getDefault().post(event)
        }, { throwable ->
            //处理异常
            ExceptionHandle.handleException(throwable)
            LogTools.debug("IMService", ExceptionHandle.errorMsg)
            //toast(ExceptionHandle.errorMsg)
        })
        addSubscription(disposable)
    }

    /**
     * 发送小文件
     */
    private fun <T : MsgBody> sendBase64MsgFile(file: File, msgType: MsgType) {
        val disposable = Observable.just(true).flatMap {
            val chat = smackManager.createChat(csId)
            //文件转base64字符串
            val inputFile = FileInputStream(file)
            val buffer = ByteArray(file.length().toInt())
            inputFile.read(buffer)
            inputFile.close()
            val base64 = android.util.Base64.encodeToString(buffer, android.util.Base64.NO_WRAP)
            val message = IMBusManager.getBaseSendMessage<T>(MsgType.IMAGE)
            when (msgType) {
                MsgType.IMAGE -> {
                    val imageMsgBody = ImageMsgBody()
                    imageMsgBody.originalPath = file.absolutePath
                    message.body = imageMsgBody as T
                    chat.send(IMConst.IMAGE_TYPE + base64)
                }
                else -> {
                }
            }
            message.csId = csId
            message.customerId = IMBusManager.getUser()!!.userId
            message.shopId = shopId
            message.sentStatus = MsgSendStatus.SENT
            message.isRead = true
            IMBusManager.saveMessage(message)
            IMBusManager.updateRecordByMessageAdd(message)
            return@flatMap Observable.just(message)
        }.compose(SchedulerUtils.ioToMain()).subscribe({ bean ->
            //发送文文件成功
            val event = ChatMessageChangeEvent<T>()
            event.message = bean
            EventBus.getDefault().post(event)

        }, { throwable ->
            //处理异常
            ExceptionHandle.handleException(throwable)
            toast(ExceptionHandle.errorMsg)
        })
        addSubscription(disposable)
    }

    inner class IMBinder : Binder() {
        fun sendTextMsg(text: String) {
            this@IMService.sendTextMsg(text, this@IMService.csId!!)
        }

        fun <T : MsgBody> sendFileMsg(file: File, msgType: MsgType) {
            if (file.length() <= 50 * 1024) {
                this@IMService.sendBase64MsgFile<T>(file, msgType)
            } else {
                ToastUtil.showToast("文件过大")
                this@IMService.sendFileMsg<T>(file, msgType)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        LogTools.debug("IMService", "onDestroy")
        isAdded = false
        rxTimer.cancel()
        EventBus.getDefault().post(IMServiceCloseEvent())
        val disposable = Observable.just(true).flatMap {
            pingManager?.unregisterPingFailedListener(pingFailedListener)
            smackManager.fileTransferManager.removeFileTransferListener(fileTransferListener)
            smackManager.chatManager.removeIncomingListener(incomingChatMessageListener)
            smackManager.chatManager.removeOutgoingListener(outgoingChatMessageListener)
            val listenners = ReflectionUtils.getFieldValue(smackManager.chatManager, "incomingListeners") as Set<*>
            LogTools.debug("IMService", listenners.size)
            smackManager.connection.removeAsyncStanzaListener(stanzaListener)
            setOffline()
            //smackManager.logout()
            if (smackManager.connectionInstance != null) {
                smackManager.connectionInstance.removeConnectionListener(connectListener)
            }
            smackManager.disconnect()
            LogTools.debug("IMService", "disconnect")
            return@flatMap Observable.just(true)
        }.compose(SchedulerUtils.ioToMain()).subscribe({ bean ->

        }, { throwable ->
            //处理异常
            ExceptionHandle.handleException(throwable)
            toast(ExceptionHandle.errorMsg)
        })
        // addSubscription(disposable)
        clearSubscription()
    }
}