package com.mike.baselib.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;


import com.mike.baselib.ISocketService;
import com.mike.baselib.net.socket.Packet;
import com.mike.baselib.net.socket.PacketHelper;
import com.mike.baselib.utils.AppBusManager;
import com.mike.baselib.utils.LogTools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 *  由于移动设备的网络的复杂性，经常会出现网络断开，如果没有心跳包的检测，
 *  客户端只会在需要发送数据的时候才知道自己已经断线，会延误，甚至丢失服务器发送过来的数据。
 */

public class SocketConnService extends Service {
    private LogTools logTools=new LogTools(this.getClass().getSimpleName());
    /**心跳频率*/
    private static final long HEART_BEAT_RATE = 30*1000;
    /**服务器ip地址*/
    public static final String HOST = "47.106.219.7";
    //47.106.219.7:15680
    /**服务器端口号*/
   //public static final int PORT = 15678;
    public static final int PORT = 15680;
    /**服务器消息回复广播*/
    public static final String MESSAGE_ACTION="message_action";
    /**服务器心跳回复广播*/
    public static final String HEART_BEAT_ACTION="heart_beat_action";
    /**读线程*/
    private ReadThread mReadThread;

    private LocalBroadcastManager mLocalBroadcastManager;
    /***/
    private WeakReference<Socket> mSocket;

    // For heart Beat
    private Handler mHandler = new Handler();
    /**心跳任务，不断重复调用自己*/
    private Runnable heartBeatRunnable = new Runnable() {

        @Override
        public void run() {
            if (System.currentTimeMillis() - sendTime >= HEART_BEAT_RATE) {
                Packet packet=new Packet();
                packet.setProjectId(PacketHelper.fillZero(2,Integer.toHexString(3)));//项目
                packet.setSerialNumber(PacketHelper.fillZero(4,Integer.toHexString(1)));//流水号
                packet.setFuntionId(PacketHelper.fillZero(4,Integer.toHexString(2)));//登录
                packet.setDeviceId(PacketHelper.byteToHexString("13481111401".getBytes()));
                packet.setData(PacketHelper.byteToHexString("123456".getBytes()));
                byte [] msg= PacketHelper.sendPacket(packet);
                logTools.d(PacketHelper.byteToHexString(msg));
                logTools.toJson(msg);
                boolean isSuccess = sendMsg(msg);//就发送一个数据过去 如果发送失败，就重新初始化一个socket
                if (!isSuccess) {
                    mHandler.removeCallbacks(heartBeatRunnable);
                    mReadThread.release();
                    releaseLastSocket(mSocket);
                    new InitSocketThread().start();
                }
            }
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    private long sendTime = 0L;
    /**
     * aidl通讯回调
     */
    private ISocketService.Stub iBackService = new ISocketService.Stub() {

        /**
         * 收到内容发送消息
         * @param message 需要发送到服务器的消息
         * @return
         * @throws RemoteException
         */
        @Override
        public boolean sendMessage(String message) throws RemoteException {
            return sendMsg(message);
        }
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return iBackService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new InitSocketThread().start();
        mLocalBroadcastManager=LocalBroadcastManager.getInstance(this);

    }
    public boolean sendMsg(final String msg) {
       return sendMsg(msg.getBytes());
    }
    public boolean sendMsg(final byte [] msg) {
        if (null == mSocket || null == mSocket.get()) {
            return false;
        }
        final Socket soc = mSocket.get();
            if (!soc.isClosed() && !soc.isOutputShutdown()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OutputStream os = soc.getOutputStream();
                            os.write(msg);
                            os.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                sendTime = System.currentTimeMillis();//每次发送成数据，就改一下最后成功发送的时间，节省心跳间隔时间
            } else {
                return false;
            }
        return true;
    }

    private void initSocket() {//初始化Socket
        try {
            Socket so = new Socket(HOST, PORT);
            mSocket = new WeakReference<>(so);
            mReadThread = new ReadThread(so);
            mReadThread.start();
            mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//初始化成功后，就准备发送心跳包
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 心跳机制判断出socket已经断开后，就销毁连接方便重新创建连接
     * @param socket
     */
    private void releaseLastSocket(WeakReference<Socket> socket) {
        try {
            if (null != socket) {
                Socket sk = socket.get();
                if (!sk.isClosed()) {
                    sk.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class InitSocketThread extends Thread {
        @Override
        public void run() {
            super.run();
            initSocket();
        }
    }

    // Thread to read content from Socket
    class ReadThread extends Thread {
        private WeakReference<Socket> mWeakSocket;
        private boolean isStart = true;

        private ReadThread(Socket socket) {
            mWeakSocket = new WeakReference<>(socket);
        }

        public void release() {
            isStart = false;
            releaseLastSocket(mWeakSocket);
        }

        @Override
        public void run() {
            super.run();
            Socket socket = mWeakSocket.get();
            if (null != socket) {
                try {
                    InputStream is = socket.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int length = 0;
                    while (!socket.isClosed() && !socket.isInputShutdown()
                            && isStart && ((length = is.read(buffer)) != -1)) {
                        if (length > 0) {
//                            String message = new String(Arrays.copyOf(buffer,
//                                    length)).trim();
                            String message =PacketHelper.byteToHexString(Arrays.copyOf(buffer,
                                    length)).trim();
                            logTools.d(message);
                            Packet packet=PacketHelper.parsePacket(Arrays.copyOf(buffer,
                                    length));
                            logTools.toJson(packet);
                            //收到服务器过来的消息，就通过Broadcast发送出去
                            if(packet.getData().equals("00")){//处理心跳回复
                                Intent intent=new Intent(HEART_BEAT_ACTION);
                                mLocalBroadcastManager.sendBroadcast(intent);
                            }else{
                                //其他消息回复
                                Intent intent=new Intent(MESSAGE_ACTION);
                                intent.putExtra("message", AppBusManager.Companion.toJson(packet));
                                mLocalBroadcastManager.sendBroadcast(intent);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(heartBeatRunnable);
        mReadThread.release();
        releaseLastSocket(mSocket);
    }
}
