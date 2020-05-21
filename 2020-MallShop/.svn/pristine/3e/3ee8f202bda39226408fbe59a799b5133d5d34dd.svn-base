package com.mike.baselib.listener;

import com.mike.baselib.net.exception.ApiException;
import com.mike.baselib.net.exception.ErrorStatus;
import com.mike.baselib.utils.AppBusManager;
import com.mike.baselib.utils.LogTools;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class RetryWithDelay implements
        Function<Observable<? extends Throwable>, Observable<?>> {
    private LogTools logTools = new LogTools("RetryWithDelay");

    private int maxRetries = 1;//最大出错重试次数
    private int retryDelayMillis = 1000;//重试间隔时间
    private int retryCount = 0;//当前出错重试次数

    public RetryWithDelay() {
    }

    public RetryWithDelay(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
        return observable
                .flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        logTools.toJson(throwable);
                        logTools.d(throwable);
                        if (throwable instanceof IOException) {
                            return retry(throwable);
                        } else if (throwable instanceof ApiException) {
                            ApiException exception = (ApiException) throwable;
                            if (ErrorStatus.TOKEN_EXPIERD == exception.getCode()||ErrorStatus.MULTI_DEVICE_ERROR == exception.getCode()||ErrorStatus.ACCOUNT_FROZEN==exception.getCode()) {
                                synchronized (RetryWithDelay.class) {
                                    if (AppBusManager.Companion.isLogin()&&ErrorStatus.TOKEN_EXPIERD == exception.getCode()) {
                                        AppBusManager.Companion.setToken("");
                                        EventBus.getDefault().post(new TokenExpiredEvent());
                                    }
                                    if (AppBusManager.Companion.isLogin()&&ErrorStatus.MULTI_DEVICE_ERROR == exception.getCode()) {
                                        AppBusManager.Companion.setToken("");
                                        EventBus.getDefault().post(new MultiDeviceLoginEvent());
                                    }
                                    if (AppBusManager.Companion.isLogin()&&ErrorStatus.ACCOUNT_FROZEN == exception.getCode()) {
                                        AppBusManager.Companion.setToken("");
                                        EventBus.getDefault().post(new AccountFrozenEvent());
                                    }
                                }
                            }
                        }
                        return Observable.error(throwable);
                    }
                });
    }

    private ObservableSource<?> retry(Throwable throwable) {
        if (++retryCount <= maxRetries) {
            logTools.d("get error, it will try after " + retryDelayMillis * retryCount
                    + " millisecond, retry count " + retryCount);
            // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
            return Observable.timer(retryDelayMillis * retryCount,
                    TimeUnit.MILLISECONDS);
        }
        return Observable.error(throwable);
    }
}
