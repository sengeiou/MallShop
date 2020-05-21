package com.epro.mall.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.epro.mall.R;
import com.epro.mall.listener.WeixinPayResultEvent;
import com.mike.baselib.utils.AppBusManager;
import com.mike.baselib.utils.LogTools;
import com.mike.baselib.utils.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "WXPayEntryActivity_";
	
    private IWXAPI api;
    private String orderType;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
    	api = WXAPIFactory.createWXAPI(this, AppBusManager.Companion.getAppId());
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
		if(req instanceof PayReq){
			PayReq req1= (PayReq) req;
			orderType=req1.extData;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(TAG, requestCode+"");
		Log.d(TAG, resultCode+"");
		LogTools.debug(requestCode);
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			switch (resp.errCode){
				case 0:
					EventBus.getDefault().post(new WeixinPayResultEvent(true));
					ToastUtil.Companion.showToast(getString(R.string.wechat_pay_success));
					finish();
					break;
				case -1:
					EventBus.getDefault().post(new WeixinPayResultEvent(false));
					ToastUtil.Companion.showToast(resp.errStr);
					finish();
					break;
				case -2:
					EventBus.getDefault().post(new WeixinPayResultEvent(false));
					ToastUtil.Companion.showToast(getString(R.string.wechat_pay_cancle));
					finish();
					break;
			}

		}
	}
}