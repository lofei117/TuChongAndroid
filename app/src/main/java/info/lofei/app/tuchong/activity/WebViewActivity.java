package info.lofei.app.tuchong.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import info.lofei.app.tuchong.R;

public class WebViewActivity extends BaseActivity {

    private static final String EXTRA_DATA_WEB_URL = "web_url";

    private static final String EXTRA_DATA_WEB_NAME = "web_name";

    private static final String EXTRA_DATA_NET_TITLE_ENABLE = "net_title_enable";

    private static final String EXTRA_DATA_WEB_USER_AGENT = "android:web_user_agent";

    private static final String DEFAULT_WEBSITE = "http://www.tuchong.com";

    @Bind(R.id.the_web_view)
    WebView mWebView;

    @Bind(R.id.progressBar)
    ProgressBar pb;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        if (getIntent() == null) {
            return;
        }

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            bundle = new Bundle();
            bundle.putString(EXTRA_DATA_WEB_URL, DEFAULT_WEBSITE);
            bundle.putString(EXTRA_DATA_WEB_NAME, getString(R.string.app_name));
        }

        mNetTitleEnable = bundle.getBoolean(EXTRA_DATA_NET_TITLE_ENABLE, false);

        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initProgressBar();

        setWebViewData(bundle);

        setDataAndListener(bundle);
    }

    private void initProgressBar() {
        pb.setVisibility(View.GONE);
    }

    private void setDataAndListener(Bundle bundle) {
        String title = bundle.getString(EXTRA_DATA_WEB_NAME);
        mToolbar.setTitle(TextUtils.isEmpty(title) ? getString(R.string.app_name) : title);
        mToolbar.setNavigationOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                if(mWebView.canGoBack()){
                    mWebView.goBack();
                }else{
                    finish();
                }
            }
        });
    }

    private void setWebViewData(Bundle bundle) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        mWebView.loadUrl(bundle.getString(EXTRA_DATA_WEB_URL));

        //设置Web视图
        mWebView.setWebViewClient(new DefaultWebViewClient());

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                pb.setProgress(newProgress);
            }
        });

        //设置WebView属性，能够执行Javascript脚本
        WebSettings settings = mWebView.getSettings();

        //settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //settings.setDefaultFontSize(28);

        settings.setJavaScriptEnabled(true);

        String userAgent = bundle.getString(EXTRA_DATA_WEB_USER_AGENT);
        //TODO can set web view user agent at here.
        //            userAgent =  "abc360/" + "/android/" +
        //            Build.VERSION.RELEASE + "/" + Build.MODEL;
        if (!TextUtils.isEmpty(userAgent)) {
            settings.setUserAgentString(userAgent);
        }

    }

    public static Intent getLaunchIntent(Context c, String url) {
        Intent intent = new Intent(c, WebViewActivity.class);
        Bundle b = new Bundle();
        WebLaunchConfig  config = new WebLaunchConfig(url,"");
        config.setNetTitleEnable(true);
        b.putString(EXTRA_DATA_WEB_URL, config.getUrl());
        b.putString(EXTRA_DATA_WEB_NAME, config.getTitle());
        b.putString(EXTRA_DATA_WEB_USER_AGENT, config.getUserAgent());
        b.putBoolean(EXTRA_DATA_NET_TITLE_ENABLE, config.isNetTitleEnable());

        intent.putExtras(b);
        return intent;
    }


    /**
     * 启动WebView界面展示url。
     * @param c   上下文
     * @param url 需要展示的url地址
     */
    public static void launch(Context c, String url) {
        Intent intent = new Intent(c, WebViewActivity.class);
        Bundle b = new Bundle();
        WebLaunchConfig  config = new WebLaunchConfig(url,"");
        config.setNetTitleEnable(true);
        b.putString(EXTRA_DATA_WEB_URL, config.getUrl());
        b.putString(EXTRA_DATA_WEB_NAME, config.getTitle());
        b.putString(EXTRA_DATA_WEB_USER_AGENT, config.getUserAgent());
        b.putBoolean(EXTRA_DATA_NET_TITLE_ENABLE, config.isNetTitleEnable());

        intent.putExtras(b);
        c.startActivity(intent);
    }

    /**
     * 启动WebView界面展示url。
     * @param c      上下文
     * @param config 需要展示的web页面信息，以及加载网络名称、设置userAgent的配置信息。
     */
    public static void launch(Context c, WebLaunchConfig config) {
        Intent intent = new Intent(c, WebViewActivity.class);
        Bundle b = new Bundle();
        if (config != null) {
            b.putString(EXTRA_DATA_WEB_URL, config.getUrl());
            b.putString(EXTRA_DATA_WEB_NAME, config.getTitle());
            b.putString(EXTRA_DATA_WEB_USER_AGENT, config.getUserAgent());
            b.putBoolean(EXTRA_DATA_NET_TITLE_ENABLE, config.isNetTitleEnable());
        }

        intent.putExtras(b);
        c.startActivity(intent);
    }

    private boolean mNetTitleEnable;

    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    //Web视图
    private class DefaultWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            pb.setVisibility(View.VISIBLE);

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            pb.setVisibility(View.GONE);

            String webTitle = view.getTitle();

            if(mNetTitleEnable || !TextUtils.isEmpty(webTitle)){
//                mActionTitle.setText(view.getTitle());
            }
            super.onPageFinished(view, url);
        }


        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //新浪微博自定义协议，过滤。
            if ("sinaweibo://splash".equals(url)) {
                return false;
            }

            view.loadUrl(url);
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            ViewGroup parent = (ViewGroup) mWebView.getParent();
            parent.removeView(mWebView);
            mWebView.removeAllViews();
            mWebView.destroy();
        }
    }

    /**
     * 打开网页需要的配置项。
     */
    public static class WebLaunchConfig {

        private String url;

        private String title;

        private String UserAgent;

        /**
         * 是否启用网络页面加载后的标题
         */
        private boolean netTitleEnable;

        /**
         * 默认构造器，传递url 和标题 title
         *
         * @param url   需要打开的网页地址
         * @param title 需要打开的网页标题
         */
        public WebLaunchConfig(String url, String title) {
            this.url = url;
            this.title = title;
        }

        public boolean isNetTitleEnable() {
            return netTitleEnable;
        }

        public void setNetTitleEnable(boolean netTitleEnable) {
            this.netTitleEnable = netTitleEnable;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUserAgent() {
            return UserAgent;
        }

        public void setUserAgent(String userAgent) {
            UserAgent = userAgent;
        }
    }

}