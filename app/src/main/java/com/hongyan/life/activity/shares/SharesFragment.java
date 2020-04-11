package com.hongyan.life.activity.shares;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hongyan.life.R;
import com.hongyan.life.activity.BaseFragment;
import com.hongyan.life.activity.webview.WebViewProgressView;

public class SharesFragment extends BaseFragment {

    private View view;
    protected WebView mWebView;
//    protected String mUrl = "https://m.baidu.com/sf?resource_id=5352&word=000001&ext=%7B%22sf_tab_name%22%3A%22%5Cu6982%5Cu89c8%22%7D&title=上证指数&ma_ver=2&pd=new_stock&openapi=1&dspName=iphone&from_sf=1&code=000001&name=上证指数&market=ab&lid=8726082989051610392&referlid=8726082989051610392&ms=1&frsrcid=5351&frorder=2";
    protected String mUrl = "https://m.baidu.com/sf?resource_id=5352&word=000003&ext=%7B%22sf_tab_name%22%3A%22%5Cu6982%5Cu89c8%22%7D&title=上证指数&ma_ver=2&pd=new_stock&openapi=1&dspName=iphone&from_sf=1&code=000001&name=深证成指&market=ab&lid=8726082989051610392&referlid=8726082989051610392&ms=1&frsrcid=5351&frorder=2";
    protected WebViewProgressView progressView;
    protected RelativeLayout imageBack;
    protected RelativeLayout rightLayout;
    protected TextView tvTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view==null){
            view = inflater.inflate(R.layout.fragment_shares, container, false);
            initView();
            mWebView.loadUrl(mUrl);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != view) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initView() {
        mWebView = view.findViewById(R.id.webView);
        tvTitle = view.findViewById(R.id.webView_title);
        progressView = view.findViewById(R.id.progress);
        imageBack = view.findViewById(R.id.rl_left);
        rightLayout = view.findViewById(R.id.rl_right);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();//返回上一页面
                }
            }
        });
        try {
            mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
            mWebView.getSettings().setAllowFileAccess(true);
            mWebView.getSettings().setDomStorageEnabled(true);
            mWebView.getSettings().setGeolocationEnabled(true);
            mWebView.getSettings().setSupportZoom(true);
            mWebView.getSettings().setSupportMultipleWindows(true);
            mWebView.getSettings().setUseWideViewPort(true);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            mWebView.getSettings().setTextZoom(100);

            if (Build.VERSION.SDK_INT >= 19) {
                mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            }
            mWebView.requestFocus(View.FOCUS_DOWN);

            mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            mWebView.setWebViewClient(mWebViewClient);
            mWebView.setWebChromeClient(new MyWebChromeClient());
        } catch (Exception e) {
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                //加载完毕进度条消失
                progressView.setVisibility(View.GONE);
            } else {
                //更新进度
                progressView.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return true;
        }
    }


    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    };


}
