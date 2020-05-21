package com.epro.mall.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.epro.mall.R;
import com.mike.baselib.utils.LogTools;


public class ClearableEditText extends EditText {

    private Drawable imgDrawable;
    private Context mContext;
    private int clearIcon;
    private LogTools logTools=new LogTools(this);

    //构造函数
    public ClearableEditText(Context context) {
        super(context);
        mContext = context;
        init(null);
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    //监听输入框的输入值
    private void init(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.ClearableEditText);
        clearIcon = t.getResourceId(R.styleable.ClearableEditText_clearIcon, 0);
        t.recycle();
        if (clearIcon == 0) {
            imgDrawable = mContext.getResources().getDrawable(R.mipmap.icon_delete_black);
        } else {
            imgDrawable = mContext.getResources().getDrawable(clearIcon);
        }
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });
        setDrawable();
    }

    //设置删除图片
    private void setDrawable() {
        if (length() < 1)
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        else
            setCompoundDrawablesWithIntrinsicBounds(null, null, imgDrawable, null); //这里设置删除图片
    }

    //处理删除事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        logTools.d("do here");
        //如果图片不为空  手指离开屏幕
        //手指的初次触摸(ACTION_DOWN操作)，滑动(ACTION_MOVE操作)和抬起(ACTION_UP)
        if (imgDrawable != null && event.getAction() == MotionEvent.ACTION_UP) {
            logTools.d("do here1");
            //得到手指离开EditText时的X Y坐标
            int x = (int) event.getX();
            int y = (int) event.getY();
            int rawX = (int) event.getRawX();
            int rawY = (int) event.getRawY();
            //创建一个长方形
            Rect rect = new Rect();
            //让长方形的宽等于edittext的宽，让长方形的高等于edittext的高
            getGlobalVisibleRect(rect);

            //把长方形缩短至右边30dp，约等于（padding+图标分辨率）
            rect.left = rect.right - dp2px(mContext, 30);
            //如果x和y坐标在长方形当中，说明你点击了右边的xx图片,清空输入框
            logTools.d(rect.contains(rawX, rawY));
//            logTools.d("x:"+x);
//            logTools.d("y:"+y);
//            logTools.d("getLeft:"+getLeft());
//            logTools.d("getRight:"+getRight());
//            logTools.d("getTop:"+getTop());
//            logTools.d("getBottom:"+getBottom());
//            logTools.d(getRight()-dp2px(mContext, 30));
            if ((getRight()-dp2px(mContext, 30)-getLeft())<=x&&x<=getRight()&&getTop()<=y&&getBottom()>=y) { //相对坐标
                setText("");
            }else if (rect.contains(rawX,rawY)) { //绝对坐标
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale);
    }
}

