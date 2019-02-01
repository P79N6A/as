package com.yaoguang.driver.util;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.base.BaseBackFragment;
import com.yaoguang.common.base.BaseSwipeRefresh;
import com.yaoguang.common.base.interfaces.BasePresenter;
import com.yaoguang.common.common.debounceclick.NoDoubleClickListener;

/**
 * html
 * Created by wly on 2017/5/26.
 */
public class HtmlSwipeFragment extends BaseBackFragment implements Toolbar.OnMenuItemClickListener {
    WebView webView;
    String mTitle;
    String mUrl;
    private BaseSwipeRefresh mSwipeRefresh;

    /**
     * @param title "船位查询"
     * @param url   "http://weixin.shipxy.com/shipxy/map/?sid=972325687EAC4B2C909F74CF9811A8B4"
     * @return
     */
    public static HtmlSwipeFragment newInstance(String title, String url) {
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("url", url);
        HtmlSwipeFragment fragment = new HtmlSwipeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString("title");
            mUrl = bundle.getString("url");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title", mTitle);
        outState.putString("url", mUrl);
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swipe_html, container, false);
        if (savedInstanceState != null) {
            mTitle = savedInstanceState.getString("title");
            mUrl = savedInstanceState.getString("url");
        }

        mSwipeRefresh = new BaseSwipeRefresh(view);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        initToolbarNav(toolbar, mTitle, R.menu.html, HtmlSwipeFragment.this);

        webView = (WebView) view.findViewById(R.id.webview);
        // 开启 localStorage
        webView.getSettings().setDomStorageEnabled(true);
        // 设置支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // 启动缓存
        webView.getSettings().setAppCacheEnabled(true);
        // 设置缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        //使用自定义的WebViewClient
        webView.setWebViewClient(new WebViewClient() {
            //覆盖shouldOverrideUrlLoading 方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mSwipeRefresh.startRefresh();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mSwipeRefresh.finishRefreshing();
            }

        });

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                    webView.goBack();// 返回前一个页面
                    return true;
                }
                return false;
            }
        });

        view.findViewById(R.id.imgReturn).setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();// 返回前一个页面
                } else {
                    pop();
                }
            }
        });
        mSwipeRefresh.setOnRefreshData(new BaseSwipeRefresh.OnRefreshData() {
            @Override
            public void refreshData2() {
                webView.loadUrl(mUrl);
            }
        });
        webView.loadUrl(mUrl);
        return view;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        webView.loadUrl(mUrl);
        return false;
    }
}
