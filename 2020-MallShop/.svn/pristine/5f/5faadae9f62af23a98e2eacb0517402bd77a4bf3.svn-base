package com.epro.mall.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.classic.common.MultipleStatusView;
import com.epro.mall.R;
import com.mike.baselib.utils.AppContext;
import com.mike.baselib.utils.DisplayManager;
import com.mike.baselib.utils.ImageLoader;
import com.mike.baselib.utils.LogTools;
import com.mike.baselib.utils.SuperUtilsKt;


/**
 * Created by lp on 2017/2/15.
 */
@SuppressLint("SetJavaScriptEnabled")
public class WebViewWrapper extends RelativeLayout {

    private WebView webView;
    private ProgressBar progressBar;
    private MultipleStatusView webMultipleStatusView;

    private LogTools logTools = new LogTools(this);

    private boolean loadFinish = false;// onPageFinished是否执行
    private int loadProgress = 0;
    private int webViewHeight = 0;

    private String mUrl;

    public WebViewWrapper(Context context) {
        this(context, null);
    }

    public WebViewWrapper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebViewWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initWebViewSettings();
        initListener();
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View view = inflater.inflate(R.layout.layout_webview, this);
        webView = (WebView) view.findViewById(R.id.webView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        webMultipleStatusView = (MultipleStatusView) view.findViewById(R.id.webMultipleStatusView);
    }

    private void initWebViewSettings() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true); // 默认false，设置true后我们才能在WebView里与我们的JS代码进行交互
        settings.setJavaScriptCanOpenWindowsAutomatically(true); // 设置JS是否可以打开WebView新窗口

        settings.setSupportZoom(true); // 支持缩放
        settings.setBuiltInZoomControls(true); // 支持手势缩放
        settings.setDisplayZoomControls(false); // 不显示缩放按钮

        settings.setDatabaseEnabled(true);//数据库存储API是否可用，默认值false。
        settings.setSaveFormData(true);//WebView是否保存表单数据，默认值true。
        settings.setDomStorageEnabled(true);//DOM存储API是否可用，默认false。
        settings.setGeolocationEnabled(true);//定位是否可用，默认为true。
        settings.setAppCacheEnabled(true);//应用缓存API是否可用，默认值false, 结合setAppCachePath(String)使用。


        //设置可以访问文件
        settings.setAllowFileAccess(true);

        //settings.setUseWideViewPort(true); // 将图片调整到适合WebView的大小
        //settings.setLoadWithOverviewMode(true); // 自适应屏幕

        webView.setHorizontalScrollBarEnabled(false);//去掉webview的滚动条,水平不显示
        webView.setScrollbarFadingEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER); // 取消WebView中滚动或拖动到顶部、底部时的阴影
    }

    public void showLoading() {
        if (webMultipleStatusView == null) {
            return;
        }
        if (webMultipleStatusView.getViewStatus() != MultipleStatusView.STATUS_LOADING) {
            webMultipleStatusView.showLoading();

            ImageLoader.loadGif(webMultipleStatusView.getContext(), R.mipmap.gif_loading, R.color.transparent,((ImageView) webMultipleStatusView.findViewById(R.id.ivLoading)));
            View loadingView = webMultipleStatusView.findViewById(R.id.loading_view);
            loadingView.setClickable(false);
            loadingView.setFocusable(false);
        }
    }

    private void showProgress() {
        if (progressBar == null) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
    }

    private void finishLoad() {
        if (progressBar == null || webMultipleStatusView == null || webView == null) {
            return;
        }
        loadProgress = 0;
        progressBar.setVisibility(View.GONE);
        webMultipleStatusView.showContent();
        logTools.d("finish");
        //防止测量有误,重新测量一遍
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.measure(0, 0);
                logTools.d(webView.getMeasuredHeight());
                logTools.d(webView.getHeight());
                if (onWebViewHeightChangeListener != null && webView.getMeasuredHeight() > webViewHeight) {
                    onWebViewHeightChangeListener.onWebViewHeightChange(webView.getMeasuredHeight(), 101);
                    logTools.d("reset height");
                }
            }
        });
    }

    private void showError() {
        if (progressBar == null || webMultipleStatusView == null) {
            return;
        }
        progressBar.setVisibility(View.GONE);
        // view.loadUrl("about:blank");// 避免出现默认的错误界面
        webMultipleStatusView.showError();
    }

    private void initListener() {
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showProgress();
                logTools.d("onPageStarted() ");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadFinish = true;
                finishLoad();
                logTools.d("onPageFinished() ");
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                showError();
                logTools.d("onReceivedHttpError: " + errorResponse.getStatusCode());
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                logTools.d("onReceivedError: " + error.getErrorCode());
                showError();
            }
        });
        /**
         * 解决onPageFinished()偶尔不执行的bug
         */
        webView.setPictureListener(new WebView.PictureListener() {
            @Override
            public void onNewPicture(WebView view, @Nullable Picture picture) {
                if (!loadFinish && loadProgress >= 100) {
                    finishLoad();
                    logTools.d("onNewPicture");
                }
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (progressBar == null || webMultipleStatusView == null) {
                    return;
                }
                logTools.d(newProgress);
                logTools.d(webView.getContentHeight());
                logTools.d(DisplayManager.INSTANCE.dip2px(500F));
                if (newProgress > 0) {
                    view.measure(0, 0);
                    logTools.d(webView.getMeasuredHeight());
                    logTools.d(webView.getHeight());
                    webViewHeight = webView.getMeasuredHeight();
                    if (onWebViewHeightChangeListener != null) {
                        onWebViewHeightChangeListener.onWebViewHeightChange(webView.getMeasuredHeight(), newProgress);
                    }
                    progressBar.setProgress(newProgress);
                    loadProgress = newProgress;
                    if (newProgress == 100) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                //判断标题 title 中是否包含有“error”字段，如果包含“error”字段，则设置加载失败，显示加载失败的视图
                if (!TextUtils.isEmpty(title) && (title.toLowerCase().contains("error") || title.contains(AppContext.getInstance().getString(R.string.web_page_cannot_be_opened)))) {
                    showError();
                    logTools.d("do error");
                }
                logTools.d("onReceivedTitle: " + title);
            }


        });
    }


    public interface OnWebViewHeightChangeListener {
        void onWebViewHeightChange(int height, int progress);
    }

    OnWebViewHeightChangeListener onWebViewHeightChangeListener;

    public void setOnWebViewHeightChangeListener(OnWebViewHeightChangeListener onWebViewHeightChangeListener) {
        this.onWebViewHeightChangeListener = onWebViewHeightChangeListener;
    }

    public void loadUrl(String url) {
        mUrl = url;
        webView.loadUrl(url);
    }

    public void setProgressDrawable(@DrawableRes int id) {
        progressBar.setProgressDrawable(progressBar.getContext().getResources().getDrawable(id));
    }

    public WebView getWebView() {
        return webView;
    }

    public MultipleStatusView getMultipleStatusView() {
        return webMultipleStatusView;
    }

    public String getUrl() {
        return mUrl;
    }

    public boolean goBack() {
        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }

    public void onResume() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.onResume();
    }

    public void onPause() {
        webView.getSettings().setJavaScriptEnabled(false);
        webView.onPause();
    }

    public void onDestroy() {
        webView.setVisibility(GONE);
        webView.destroy();
    }

}