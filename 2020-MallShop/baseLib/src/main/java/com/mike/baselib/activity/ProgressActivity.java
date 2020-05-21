package com.mike.baselib.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mike.baselib.R;
import com.mike.baselib.listener.CancelInstallEvent;

import org.greenrobot.eventbus.EventBus;


public class ProgressActivity extends BaseSimpleActivity {
    ProgressBroadCastReceiver mReceiver;
    ProgressBar progressBar;
    TextView tvProgress;
    TextView tvProgressRight;

    @Override
    public void initView() {
        super.initView();
        progressBar = findViewById(R.id.proBar);
        tvProgress = findViewById(R.id.tvProgress);
        tvProgressRight = findViewById(R.id.tvProgressRight);
        IntentFilter filter = new IntentFilter();
        filter.addAction("update_progress");
        mReceiver = new ProgressBroadCastReceiver();
        this.registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public int layoutContentId() {
        return R.layout.activity_progress;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    public class ProgressBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int progress = intent.getIntExtra("progress", 0);
            if (progress == 100) {
                finish();
            }
            progressBar.setProgress(progress);
            tvProgress.setText(progress + "%");
            tvProgressRight.setText(progress + "/100");
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, getString(R.string.cancel_install), Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new CancelInstallEvent());
        super.onBackPressed();
    }
}
