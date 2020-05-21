package com.epro.mall.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;

import com.epro.mall.R;
import com.mike.baselib.utils.AppContext;

@SuppressLint("AppCompatCustomView")
public class CountDownButton extends Button {
    private CountDownTimer timer;

    private int disableTextColor;
    private int enableTextColor;
    private int disableBackground;
    private int enableBackground;
    private boolean isFinish = true;

    OnFinishListener onFinishListener;
    OnTickListener onTickListener;

    public interface OnFinishListener {
        void onFinish(CountDownButton button);
    }

    public interface OnTickListener {
        void onTick(long l, CountDownButton button);
    }

    public void setOnFinishListener(OnFinishListener listener) {
        this.onFinishListener = listener;
    }

    public void setOnTickListener(OnTickListener listener) {
        this.onTickListener = listener;
    }

    //构造函数
    public CountDownButton(Context context) {
        super(context);
        init(null);
    }

    public CountDownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CountDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancel();
    }

    public void cancel() {
        isFinish = true;
        timer.cancel();
        setText(AppContext.getInstance().getString(R.string.my_info_pull_confirm_code));
    }

    public void start() {
        isFinish = false;
        timer.start();
    }

    private void init(AttributeSet attrs) {
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long l) {
                isFinish = false;
                setText("" + l / 1000 + AppContext.getInstance().getString(R.string.resend));
                disableUI();
                if (onTickListener != null) {
                    onTickListener.onTick(l, CountDownButton.this);
                }
            }

            @Override
            public void onFinish() {
                isFinish = true;
                setText(AppContext.getInstance().getString(R.string.my_info_pull_confirm_code));
                enableUI();
                if (onFinishListener != null) {
                    onFinishListener.onFinish(CountDownButton.this);
                }
            }
        };
        if (attrs == null) {
            return;
        }

        TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.CountDownButton);
        disableTextColor = t.getColor(R.styleable.CountDownButton_disableTextColor, 0);
        enableTextColor = t.getColor(R.styleable.CountDownButton_enableTextColor, 0);
        disableBackground = t.getResourceId(R.styleable.CountDownButton_disableBackground, 0);
        enableBackground = t.getResourceId(R.styleable.CountDownButton_enableBackground, 0);
        t.recycle();
        if (isEnabled()) {
            enableUI();
        } else {
            disableUI();
        }
    }

    @SuppressLint("ResourceType")
    public void enableUI() {
        if (!isFinish) {
            return;
        }
        setEnabled(true);
        setTextColor(getResources().getColor(R.color.mainTextColor));
        if (enableBackground != 0) {
            setBackgroundResource(getResources().getColor(R.color.white));
        }
        if (enableTextColor != 0) {
            setTextColor(enableTextColor);
        }
    }

    public void disableUI() {
        setEnabled(false);
        setTextColor(getResources().getColor(R.color.secondaryTextColor));
        if (disableBackground != 0) {
            setBackgroundResource(disableBackground);
        }
        if (disableTextColor != 0) {
            setTextColor(disableTextColor);
        }
    }

}

