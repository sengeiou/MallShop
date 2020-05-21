package com.mike.baselib.net;

import android.util.Log;

import com.mike.baselib.api.ApiService;
import com.mike.baselib.listener.DownloadCallBack;
import com.mike.baselib.utils.AppContext;
import com.mike.baselib.utils.Constants;
import com.mike.baselib.utils.SPDownloadUtil;
import com.mike.baselib.utils.StorageUtils;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 创建时间：2018/3/7
 * 编写人：czw
 * 功能描述 ：
 */
public class RetrofitHttp {

    private static final int DEFAULT_TIMEOUT = 10;
    private static final String TAG = "RetrofitClient";

    private ApiService apiService;

    private OkHttpClient okHttpClient;

    public static String baseUrl = Constants.BASE_FILE_DOWNLOAD_URL;

    private static RetrofitHttp sIsntance;

    public static RetrofitHttp getInstance() {
        if (sIsntance == null) {
            synchronized (RetrofitHttp.class) {
                if (sIsntance == null) {
                    sIsntance = new RetrofitHttp();
                }
            }
        }
        return sIsntance;
    }

    private RetrofitHttp() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        apiService = retrofit.create(ApiService.class);
    }


    private Disposable disposable;

    public void downloadFile(final long range, final String url, final String fileName, final DownloadCallBack downloadCallback) {
        //断点续传时请求的总长度
        File dir = StorageUtils.getCacheDirectory(AppContext.getInstance().getContext());
        File file = new File(dir, fileName);
        String totalLength = "-";
        if (file.exists()) {
            totalLength += file.length();
        }
        apiService.executeDownload("bytes=" + Long.toString(range) + totalLength, url).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                RandomAccessFile randomAccessFile = null;
                InputStream inputStream = null;
                long total = range;
                long responseLength = 0;
                try {
                    byte[] buf = new byte[2048];
                    int len = 0;
                    responseLength = responseBody.contentLength();
                    inputStream = responseBody.byteStream();
                    File dir = StorageUtils.getCacheDirectory(AppContext.getInstance().getContext());
                    File file = new File(dir, fileName);
                    randomAccessFile = new RandomAccessFile(file, "rwd");
                    if (range == 0) {
                        randomAccessFile.setLength(responseLength);
                    }
                    randomAccessFile.seek(range);

                    int progress = 0;
                    int lastProgress = 0;

                    while ((len = inputStream.read(buf)) != -1) {
                        randomAccessFile.write(buf, 0, len);
                        total += len;
                        lastProgress = progress;
                        progress = (int) (total * 100 / randomAccessFile.length());
                        if (progress > 0 && progress != lastProgress) {
                            downloadCallback.onProgress(progress);
                        }
                    }
                    downloadCallback.onCompleted();
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                    downloadCallback.onError(e.getMessage());
                    e.printStackTrace();
                } finally {
                    try {
                        SPDownloadUtil.getInstance().save(url, total);
                        if (randomAccessFile != null) {
                            randomAccessFile.close();
                        }

                        if (inputStream != null) {
                            inputStream.close();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onError(Throwable e) {
                downloadCallback.onError(e.toString());
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public void cancel() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}