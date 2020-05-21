package com.epro.mall.mvp.model
import com.mike.baselib.listener.RetryWithDelay
import com.epro.mall.mvp.model.bean.AccountAssociationBean
import com.epro.mall.mvp.model.bean.AssociationAccountListBean
import com.epro.mall.mvp.model.bean.UnbindAccountBean
import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

class AccountAssociationModel:BaseModel() {

    fun associationAccount(providerId:String,providerType:String,name:String):Observable<AccountAssociationBean>{

        return  getApiSevice().associationAccount(AccountAssociationBean.Send(providerId,providerType,name))
                .flatMap {
            if (it.code == ErrorStatus.SUCCESS){
                return@flatMap Observable.just(it)
            }else{
                return@flatMap Observable.error<AccountAssociationBean>(ApiException(it.message,it.code))
            }
        }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun unBindAccount(id:String):Observable<UnbindAccountBean>{
        return getApiSevice().unBindAccount(id)
                .flatMap {
            if (it.code == ErrorStatus.SUCCESS){
                return@flatMap Observable.just(it)
            }else{
                return@flatMap  Observable.error<UnbindAccountBean>(ApiException(it.message,it.code))
            }
        }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun associationAccountList():Observable<AssociationAccountListBean>{
        return getApiSevice().associationAccountList()
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS){
                        return@flatMap Observable.just(it)
                    }else{
                        return@flatMap  Observable.error<AssociationAccountListBean>(ApiException(it.message,it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }
}