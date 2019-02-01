package com.yaoguang.appcommon.html;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yaoguang.appcommon.R;
import com.yaoguang.appcommon.common.base.BaseBackFragment;
import com.yaoguang.lib.base.interfaces.BasePresenter;
import com.yaoguang.lib.common.debounceclick.NoDoubleClickListener;
import com.yaoguang.lib.appcommon.widget.head.PullHead;

/**
 * html
 * 适用于所有存放html的窗体
 * Created by zhongjh on 2017/5/26.
 */
public class HtmlFragment extends BaseBackFragment implements Toolbar.OnMenuItemClickListener {

    public WebView webView;
    public String mTitle;
    public String mUrl;

    TwinklingRefreshLayout refreshLayout;

    boolean isLoad = false;

    /**
     * @param title "船位查询"
     * @param url   "http://weixin.shipxy.com/shipxy/map/?sid=972325687EAC4B2C909F74CF9811A8B4"
     * @return
     */
    public static HtmlFragment newInstance(String title, String url) {
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("url", url);
        HtmlFragment fragment = new HtmlFragment();
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
    public BasePresenter getPresenter() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_html, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        refreshLayout = (TwinklingRefreshLayout) view.findViewById(R.id.refresh);
        setRefreshLayout(refreshLayout);
        initToolbarNav(toolbar, mTitle, R.menu.html, HtmlFragment.this);


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
//                refreshLayout.refreshDataAnimation();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                refreshLayout.finishRefreshing();
                isLoad = true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    refreshLayout.finishRefreshing();
                    isLoad = true;
                }
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

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                if (!isLoad) {
                    webView.loadUrl(mUrl);
                } else {
                    webView.reload();
                }
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
            }
        });
        refreshLayout.startRefresh();

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

        return view;
    }

    /**
     * 设置顶部可以滑动
     */
    void setRefreshLayout(TwinklingRefreshLayout refreshLayout) {
        PullHead headerView = new PullHead(HtmlFragment.this.getContext());
        refreshLayout.setHeaderView(headerView);
//        refreshLayout.setMaxHeadHeight(110);
        refreshLayout.setHeaderHeight(110);
//        refreshLayout.setAutoLoadMore(false);

//        SinaRefreshView headerView = new SinaRefreshView(HtmlFragment.this.getContext());
//        headerView.setArrowResource(R.drawable.arrow);
//        headerView.setTextColor(0xff745D5C);
//        refreshLayout.setHeaderView(headerView);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        WebBackForwardList mWebBackForwardList = webView
                .copyBackForwardList();
        if (webView.canGoBackOrForward(-(mWebBackForwardList.getSize() - 1)))
            webView.goBackOrForward(-(mWebBackForwardList.getSize() - 1));
        return false;
    }
}
