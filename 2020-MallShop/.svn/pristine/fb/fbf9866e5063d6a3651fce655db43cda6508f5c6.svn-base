package com.mike.baselib.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Instruction:Rxjava2.x实现定时器
 * <p>
 * Author:pei
 * Date: 2017/6/29
 * Description:
 */

public class RxTimer {

    private  Disposable mDisposable;

    private long recyCount=0;//循环次数

    /** milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public void timer(long milliseconds,final IRxNext next) {
        Observable.timer(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable=disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if(next!=null){
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //取消订阅
                        cancel();
                    }

                    @Override
                    public void onComplete() {
                        //取消订阅
                        cancel();
                    }
                });
    }


    /** 每隔milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public void interval(long milliseconds,final IRxNext next){
        Observable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable=disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if(next!=null){
                            recyCount=number;
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //取消订阅
                        cancel();
                    }

                    @Override
                    public void onComplete() {
                        //取消订阅
                        cancel();
                    }
                });
    }


    /**
     * 取消订阅
     */
    public  void cancel(){
        if(mDisposable!=null&&!mDisposable.isDisposed()){
            recyCount=0;
            mDisposable.dispose();
        }
    }

    public long getRecyCount(){
        return recyCount;
    }

    public boolean isDisposed(){
        return mDisposable == null || mDisposable.isDisposed();
    }

    public interface IRxNext{
        void doNext(long number);
    }
}