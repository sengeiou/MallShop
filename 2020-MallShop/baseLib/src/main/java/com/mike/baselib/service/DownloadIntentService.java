package com.mike.baselib.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.RemoteViews;

import com.mike.baselib.R;
import com.mike.baselib.listener.DownloadCallBack;
import com.mike.baselib.net.RetrofitHttp;
import com.mike.baselib.utils.AppContext;
import com.mike.baselib.utils.SPDownloadUtil;
import com.mike.baselib.utils.StorageUtils;

import java.io.File;


/**
 * 创建时间：2018/3/7
 * 编写人：damon
 * 功能描述 ：
 */

public class DownloadIntentService extends IntentService {

    private static final String TAG = "DownloadIntentService";
    private NotificationManager mNotifyManager;
    private String mDownloadFileName;
    private Notification mNotification;
    private NotificationCompat.Builder builder;
    private int  notificationId;
    private NotificationChannel channel;
    public DownloadIntentService() {
        super("InitializeService");
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String downloadUrl = intent.getExtras().getString("download_url");
        final int downloadId = intent.getExtras().getInt("download_id");
        mDownloadFileName = intent.getExtras().getString("download_file");

        File dir = StorageUtils.getCacheDirectory(AppContext.getInstance().getContext());

        Log.d(TAG, "download_url --" + downloadUrl);
        Log.d(TAG, "download_file --" + mDownloadFileName);

        final File file = new File(dir, mDownloadFileName);
        long range = 0;
        int progress = 0;
        if (file.exists()) {
            range = SPDownloadUtil.getInstance().get(downloadUrl, 0);
            if (file.length()!=0){
                progress = (int) (range * 100 / file.length());
            }
            Log.d(TAG, "progress  --" + progress);
            if (range == file.length()) {
                Log.d(TAG, "range  --" + range);
                installApp(file);
                return;
            }
        }
        Log.d(TAG, "range = " + range);

      /*  final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notify_download);
        remoteViews.setProgressBar(R.id.pb_progress, 100, progress, false);
        remoteViews.setTextViewText(R.id.tv_progress, "已下载" + progress + "%");

        final NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, AppContext.getInstance().getContext().getPackageName())
                        .setContent(remoteViews)
                        .setTicker("正在下载")
                        .setSmallIcon(R.mipmap.app_icon);
        mNotification = builder.build();

        mNotifyManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyManager.notify(downloadId, mNotification);*/

      /*  Intent mIntent = new Intent(this,ProgressActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mIntent);*/

        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //创建 通知通道  channelid和channelname是必须的（自己命名就好）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("1",
                    "Channel1", NotificationManager.IMPORTANCE_LOW);
            mNotifyManager.createNotificationChannel(channel);
        }
        final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notify_download);
        remoteViews.setProgressBar(R.id.pb_progress, 100, progress, false);
        remoteViews.setTextViewText(R.id.tv_progress, progress + "%");
        notificationId = 0x1234;
        builder = new NotificationCompat.Builder(this,"1");
        builder.setSmallIcon(R.mipmap.notify_update)
                .setContent(remoteViews)
                .setWhen(System.currentTimeMillis())
                .setTicker(AppContext.getInstance().getString(R.string.downloading));
        mNotification = builder.build();//构建通知对象
        mNotifyManager.notify(notificationId,mNotification);

        RetrofitHttp.getInstance().downloadFile(range, downloadUrl, mDownloadFileName, new DownloadCallBack() {
            @Override
            public void onProgress(int progress) {
                Log.d(TAG, "下载完成 progress ：" + progress);
                remoteViews.setProgressBar(R.id.pb_progress, 100, progress, false);
                remoteViews.setTextViewText(R.id.tv_progress, progress + "%");
                mNotification = builder.build();
                mNotifyManager.notify(notificationId, mNotification);
              //  sendBroadcast(new Intent().setAction("update_progress").putExtra("progress", progress));
            }

            @Override
            public void onCompleted() {
                Log.d(TAG, "下载完成" + file.getAbsolutePath());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //关闭通知通道
                    mNotifyManager.deleteNotificationChannel("1");
                }else {
                    mNotifyManager.cancel(notificationId);
                }
                installApp(file);
            }

            @Override
            public void onError(String msg) {
                mNotifyManager.cancel(notificationId);
                Log.d(TAG, "下载发生错误--" + msg);
            }
        });
    }

    private void installApp(File apkfile) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        Log.d(TAG, "installApp --");
        if (Build.VERSION.SDK_INT >= 24) { //适配安卓7.0
            Log.d(TAG, "installApp SDK_INT > 24--");
            i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri apkFileUri = FileProvider.getUriForFile(this,
                    this.getPackageName() + ".fileProvider", apkfile);
            i.setDataAndType(apkFileUri, "application/vnd.android.package-archive");
        } else {
            Log.d(TAG, "installApp SDK_INT < 24--");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                    "application/vnd.android.package-archive");// File.toString()会返回路径信息
        }
        startActivity(i);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //关闭通知通道
            if (mNotifyManager!=null){
                mNotifyManager.deleteNotificationChannel("1");
            }
        }else {
            if (mNotifyManager!=null){
                mNotifyManager.cancel(notificationId);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RetrofitHttp.getInstance().cancel();
    }

}
