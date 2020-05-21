package com.mike.baselib.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mike.baselib.ISocketService;
import com.mike.baselib.R;
import com.mike.baselib.service.SocketConnService;
import com.mike.baselib.utils.AppContext;
import com.mike.baselib.utils.LogTools;

import java.lang.ref.WeakReference;

public class DemoHeartSocketActivity extends BaseSimpleActivity {

    private TextView mResultText;
    private EditText mEditText;

    private LogTools logTools = new LogTools(this.getClass().getSimpleName());

    private ISocketService iSocketService;
    private Intent mServiceIntent;
    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iSocketService = null;

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iSocketService = ISocketService.Stub.asInterface(service);
        }
    };

    @Override
    public int layoutContentId() {
        return R.layout.activity_heart_socket;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        super.initView();
        mResultText = findViewById(R.id.resule_text);
        mEditText = findViewById(R.id.content_edit);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mReciver = new MessageBackReciver(mResultText);
        mServiceIntent = new Intent(this, SocketConnService.class);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(SocketConnService.HEART_BEAT_ACTION);
        mIntentFilter.addAction(SocketConnService.MESSAGE_ACTION);
    }

    @Override
    public void initListener() {

    }

    class MessageBackReciver extends BroadcastReceiver {
        private WeakReference<TextView> textView;

        public MessageBackReciver(TextView tv) {
            textView = new WeakReference<TextView>(tv);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            TextView tv = textView.get();
            if (action.equals(SocketConnService.HEART_BEAT_ACTION)) {
                if (null != tv) {
                    logTools.d("Get a heart heat");
                    tv.setText("Get a heart heat");
                }
            } else {
                String message = intent.getStringExtra("message");
                logTools.d(message);
                tv.setText(AppContext.getInstance().getString(R.string.service_msg) + message);
            }
        }
    }

    private MessageBackReciver mReciver;

    private IntentFilter mIntentFilter;

    private LocalBroadcastManager mLocalBroadcastManager;

    @Override
    protected void onStart() {
        super.onStart();
        mLocalBroadcastManager.registerReceiver(mReciver, mIntentFilter);
        bindService(mServiceIntent, conn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(conn);
        mLocalBroadcastManager.unregisterReceiver(mReciver);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.send) {
            String content = mEditText.getText().toString();
            try {
                boolean isSend = iSocketService.sendMessage(content);//Send Content by socket
                Toast.makeText(this, isSend ? "success" : "fail",
                        Toast.LENGTH_SHORT).show();
                mEditText.setText("");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
