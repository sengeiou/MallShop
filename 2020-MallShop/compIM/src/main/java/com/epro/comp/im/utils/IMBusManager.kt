package com.epro.comp.im.utils

import android.app.ActivityManager
import android.content.Context
import android.text.TextUtils
import com.epro.comp.im.mvp.model.bean.*
import com.google.gson.reflect.TypeToken
import com.mike.baselib.utils.*
import org.jivesoftware.smack.packet.Message
import org.litepal.LitePal
import org.litepal.extension.count
import org.litepal.extension.find
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class IMBusManager {

    companion object {
        fun setUser(user: User) {
            SPUtils.put(AppContext.getInstance().context, SPConstant.USER_JSON, AppBusManager.toJson(user)!!)
        }

        fun getUser(): User? {
            val user_json: String = SPUtils.get(AppContext.getInstance().context, SPConstant.USER_JSON, "") as String
            if (TextUtils.isEmpty(user_json)) {
                return null
            }
            return AppBusManager.fromJson(user_json, User::class.java)
        }

        /**
         * 根据用户查询聊天记录
         */
        fun getChatRecords(): ArrayList<ChatRecord> {
            val whereClause = "customerId = ?"
            val whereArgs = arrayOf<String>(getUser()!!.userId)
            val list = LitePal.where(whereClause, whereArgs[0]).find<ChatRecord>() as ArrayList<ChatRecord>
            LogTools.debug("IMBusManager_" + list.size)
            for (c in list) {
                c.recentChatMessage = AppBusManager.fromJson(c.msgJson, RecentChatMessage::class.java)
                c.unReadCount = getUnReadMessagesCount(c.shopId)
            }
            list.reverse()
            return list
        }

        /**
         * 添加信息时,聊天记录存储更新
         */
        fun <T : MsgBody> updateRecordByMessageAdd(message: ChatMessage<T>) {
            LogTools.debug("IMBusManager_" + message.shopId + "*" + message.customerId)
            val whereClause = "shopId = ?"
            val whereArgs = arrayOf<String>(message.shopId)
            var recordList = LitePal.where(whereClause, whereArgs[0]).find(ChatRecord::class.java)
            LogTools.debug("IMBusManager_" + recordList)
            var record = ChatRecord()
            if (recordList.isNotEmpty()) {
                record = recordList[0]
                LogTools.debug("IMBusManager_" + recordList)
                if (!message.isRead) {
                    record.unReadCount += 1
                }
            } else {
                record.shopId = message.shopId
                val shop = getIMShop(record.shopId)
                record.shopName = shop?.shopName
                record.shopLogo = shop?.logoUrl
                record.customerId = message.customerId
                record.unReadCount = getUnReadMessagesCount(record.shopId)
            }

            //更新最近一条消息的数据
            val recentChatMessage = RecentChatMessage()
            if (message.msgType == MsgType.TEXT) {
                recentChatMessage.msgText = (message.body as TextMsgBody).message
            } else if (message.msgType == MsgType.IMAGE) {
                recentChatMessage.msgText = "[图片]"
            }
            recentChatMessage.msgType = message.msgType.name
            recentChatMessage.csId = message.csId
            recentChatMessage.customerId = message.customerId
            recentChatMessage.sentTime = message.sentTime
            record.msgJson = AppBusManager.toJson(recentChatMessage)

            //去除重复
            val list = LitePal.findAll(ChatRecord::class.java)
            LogTools.debug("IMBusManager_" + list)
            if (list.isEmpty()) {
                record.save()
            } else {
                val cList = CopyOnWriteArrayList<ChatRecord>()
                cList.addAll(list)
                for (r in cList) {
                    if (r.shopId == record.shopId) {
                        LogTools.debug("IMBusManager_" + record.shopId)
                        cList.remove(r)
                        break
                    }
                }
                cList.add(record)
                LogTools.debug("IMBusManager_" + cList)
                LogTools.debug("IMBusManager_" + record.shopId)
                LitePal.saveAll(cList)
            }
        }

        fun <T : MsgBody> updateRecordByMessageDelete(message: ChatMessage<T>) {
            LogTools.debug("IMBusManager_" + message.shopId + "*" + message.customerId)
            val whereClause = "shopId = ?"
            val whereArgs = arrayOf<String>(message.shopId)
            val recordList = LitePal.where(whereClause, whereArgs[0]).find(ChatRecord::class.java)
            LogTools.debug("IMBusManager_" + recordList)
            var record = ChatRecord()
            if (recordList.isNotEmpty()) {
                record = recordList[0]
                LogTools.debug("IMBusManager_" + recordList)
                //找最后一条记录
                val whereClause1 = "customerId = ? and shopId = ?"
                val whereArgs1 = arrayOf<String>(getUser()!!.userId, message.shopId)
                val dbMessage = LitePal.where(whereClause1, whereArgs1[0], whereArgs1[1]).findLast(DbChatMessage::class.java)
                if (dbMessage.uuid != message.uuid) { //要删除的记录和最后一条记录不一样,不用管
                    return
                }
                dbMessage.delete()
                val count = LitePal.where(whereClause1, whereArgs1[0], whereArgs1[1]).count<DbChatMessage>()
                if (count == 0) {
                    record.delete()
                    return
                }
                val lastDbMessage = LitePal.where(whereClause1, whereArgs1[0], whereArgs1[1]).findLast(DbChatMessage::class.java)
                val lastMessage = restoreMessage<T>(lastDbMessage)!!
                //更新最近一条消息的数据
                val recentChatMessage = RecentChatMessage()
                if (lastMessage.msgType == MsgType.TEXT) {
                    recentChatMessage.msgText = (lastMessage.body as TextMsgBody).message
                } else if (lastMessage.msgType == MsgType.IMAGE) {
                    recentChatMessage.msgText = "[图片]"
                }
                recentChatMessage.msgType = lastMessage.msgType.name
                recentChatMessage.csId = lastMessage.csId
                recentChatMessage.customerId = lastMessage.customerId
                recentChatMessage.sentTime = lastMessage.sentTime
                record.msgJson = AppBusManager.toJson(recentChatMessage)
                record.update(record.objectId)
            }
        }


        fun <T : MsgBody> saveMessage(message: ChatMessage<T>) {
            message.sentTime = System.currentTimeMillis()
            val dbMessage = DbChatMessage()
            dbMessage.uuid = message.uuid
            dbMessage.msgJson = AppBusManager.toJson(message)
            dbMessage.msgType = message.msgType.name
            dbMessage.csId = message.csId
            dbMessage.customerId = message.customerId
            dbMessage.shopId = message.shopId
            dbMessage.isRead = message.isRead
            dbMessage.sentTime = message.sentTime
            dbMessage.save()
        }

        fun clearAllRecord() {
            LitePal.deleteAll(ChatRecord::class.java, "customerId = ?", getUser()!!.userId)
            LitePal.deleteAll(DbChatMessage::class.java, "customerId = ?", getUser()!!.userId)
        }

        fun deleteRecord(shopId: String) {
            LitePal.deleteAll(ChatRecord::class.java, "shopId = ? and customerId = ?", shopId, getUser()!!.userId)
            LitePal.deleteAll(DbChatMessage::class.java, "shopId = ? and customerId = ?", shopId, getUser()!!.userId)
        }

        fun deleteChatMessage(chatMessage: ChatMessage<out MsgBody>) {
            LitePal.deleteAll(DbChatMessage::class.java, "uuid = ?", chatMessage.uuid)
        }

        private fun <T : MsgBody> restoreMessage(d: DbChatMessage): ChatMessage<T>? {
            if (d.msgType == MsgType.TEXT.name) {
                val type = object : TypeToken<ChatMessage<TextMsgBody>>() {}.type
                return AppBusManager.fromJson<ChatMessage<TextMsgBody>>(d.msgJson, type)!! as ChatMessage<T>
            } else if (d.msgType == MsgType.IMAGE.name) {
                val type = object : TypeToken<ChatMessage<ImageMsgBody>>() {}.type
                return AppBusManager.fromJson<ChatMessage<ImageMsgBody>>(d.msgJson, type)!! as ChatMessage<T>
            }
            return null
        }


        /**
         * 获取聊天界面消息
         */

        fun <T : MsgBody> getMessageList(shopId: String): ArrayList<ChatMessage<T>> {
            val messages = ArrayList<ChatMessage<T>>()
            val whereClause = "customerId = ? and shopId = ?"
            val whereArgs = arrayOf<String>(getUser()!!.userId, shopId)
            val dbMessageList = LitePal.where(whereClause, whereArgs[0], whereArgs[1]).find(DbChatMessage::class.java)
            if (dbMessageList == null || dbMessageList.isEmpty()) {
                return messages
            } else {
                for (d in dbMessageList) {
                    messages.add(restoreMessage(d)!!)
                }
                return messages
            }
        }

        /**
         * 将消息设为已读
         */
        fun updateMessageRead(shopId: String) {
            val whereClause = "customerId = ? and shopId = ? and isRead = ?"
            val whereArgs = arrayOf<String>(getUser()!!.userId, shopId, "0")
            val dbChatMessage = DbChatMessage()
            dbChatMessage.isRead = true
            dbChatMessage.updateAll(whereClause, whereArgs[0], whereArgs[1], whereArgs[2])

        }

        fun updateOneMessageRead(uuid: String) {
            val whereClause = "uuid = ?"
            val whereArgs = arrayOf<String>(uuid)
            val dbChatMessage = DbChatMessage()
            dbChatMessage.isRead = true
            dbChatMessage.updateAll(whereClause, whereArgs[0])
        }

        /**
         * 未读消息数量
         */
        fun getUnReadMessagesCount(shopId: String): Long {
            val whereClause = "customerId = ? and shopId = ? and isRead = ?"
            val whereArgs = arrayOf<String>(getUser()!!.userId, shopId, "0")
            return LitePal.where(whereClause, whereArgs[0], whereArgs[1], whereArgs[2]).count(DbChatMessage::class.java).toLong()

        }

        fun <T : MsgBody> getBaseSendMessage(msgType: MsgType): ChatMessage<T> {
            val messgae = ChatMessage<T>()
            messgae.uuid = UUID.randomUUID().toString() + ""
            messgae.csId = ""
            messgae.customerId = ""
            messgae.shopId = ""
            messgae.sentTime = System.currentTimeMillis()
            messgae.sentStatus = MsgSendStatus.SENDING
            messgae.msgType = msgType
            messgae.isSend = true
            messgae.isRead = false
            messgae.isOffline = false
            return messgae
        }

        fun getProcessName(context: Context): String {
            val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val processList = manager.runningAppProcesses
            var result = ""
            val pid = android.os.Process.myPid()
            processList.forEach {
                if (it.pid == pid) {
                    result = it.processName
                }
            }

            return result
        }

        /**
         * 将接收到消息保存本地
         */
        fun <T : MsgBody> saveRecvTextMsgToLocal(message: Message, shopId: String, isOffline: Boolean = false): ChatMessage<T>? {
            if (!TextUtils.isEmpty(message.body)) {
                if (message.body.startsWith(IMConst.IMAGE_TYPE)) {//图片等其他文件
                    val dir = File(AppContext.getInstance().context.externalCacheDir!!.absolutePath, "chat")
                    if (!dir.exists()) {
                        dir.mkdir()
                    }
                    val msg = getBaseSendMessage<T>(MsgType.IMAGE)
                    if (message.body.startsWith(IMConst.IMAGE_TYPE)) {
                        val file = File(dir, System.currentTimeMillis().toString() + ".jpg")
                        val body = ImageMsgBody()
                        val base64 = message.body.replace(IMConst.IMAGE_TYPE, "")
                        val buff = android.util.Base64.decode(base64, android.util.Base64.DEFAULT)
                        val out = FileOutputStream(file)
                        out.write(buff)
                        out.close()
                        body.originalPath = file.absolutePath
                        msg.setBody(body as T)
                    } else {


                    }
                    msg.csId = message.from.split("@")[0]
                    msg.customerId = message.to.split("@")[0]
                    msg.shopId = shopId
                    msg.sentStatus = MsgSendStatus.SENT
                    msg.isSend = false
                    msg.isOffline = isOffline
                    saveMessage(msg)
                    return msg
                } else {//文本
                    val msg = getBaseSendMessage<T>(MsgType.TEXT)
                    val body = TextMsgBody()
                    body.message = message.body
                    msg.body = body as T
                    msg.csId = message.from.split("@")[0]
                    msg.customerId = message.to.split("@")[0]
                    msg.shopId = shopId
                    msg.sentStatus = MsgSendStatus.SENT
                    msg.isSend = false
                    msg.isOffline = isOffline
                    saveMessage(msg)
                    return msg
                }
            }
            return null
        }

        /**
         * 本地客服存储
         */
        fun saveCustomerService(csList: ArrayList<CustomerService>) {
            val list = LitePal.findAll(CustomerService::class.java)
            val set = HashSet<CustomerService>()
            set.addAll(list)
            set.addAll(csList)
            LitePal.saveAll(set)
        }

        fun getCustomerService(csId: String): CustomerService? {
            val whereClause = "account = ?"
            val whereArgs = arrayOf<String>(csId)
            val list = LitePal.where(whereClause, whereArgs[0]).find(CustomerService::class.java)
            if (list.isNotEmpty()) {
                return list[0]
            }
            return null
        }

        /**
         * 本地店铺存储
         */
        fun saveIMShop(shop: IMShop) {
            val list = LitePal.findAll(IMShop::class.java)
            val set = HashSet<IMShop>()
            set.addAll(list)
            set.add(shop)
            LitePal.saveAll(set)
        }

        fun getIMShop(shopId: String): IMShop? {
            val whereClause = "shopId = ?"
            val whereArgs = arrayOf<String>(shopId)
            val list = LitePal.where(whereClause, whereArgs[0]).find(IMShop::class.java)
            if (list.isNotEmpty()) {
                return list[0]
            }
            return null
        }
    }

}