package me.donlis.vreader.view.webview;

import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import me.donlis.vreader.R;
import me.donlis.vreader.base.SwipeSupportFragment;
import me.donlis.vreader.databinding.FragmentWebviewBinding;
import me.donlis.vreader.viewmodel.BaseViewModel;

public class WebViewFragment extends SwipeSupportFragment<BaseViewModel, FragmentWebviewBinding> {

    private final static String TITLE = "title";

    private final static String URL = "url";

    private String mUrl;

    private String mTitle;

    private WebView webView;

    public static WebViewFragment getInstance(String title,String url){
        WebViewFragment fragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE,title);
        bundle.putString(URL,url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_webview;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArgs();
    }

    private void getArgs(){
        Bundle bundle = getArguments();
        if(bundle != null){
            mUrl = bundle.getString(URL,"");
            mTitle = bundle.getString(TITLE,getString(R.string.app_name));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView(){
        setToolbarTitel(mTitle);

        initWebView();
    }

    private void initWebView(){
        webView = bindView.webView;

        WebSettings settings = webView.getSettings();
        // 网页内容的宽度是否可大于WebView控件的宽度
        settings.setLoadWithOverviewMode(false);
        // 保存表单数据
        settings.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        // 启动应用缓存
        settings.setAppCacheEnabled(true);
        // 设置缓存模式
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        settings.setUseWideViewPort(true);
        // 不缩放
        webView.setInitialScale(100);
        // 告诉WebView启用JavaScript执行。默认的是false。
        settings.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片
        settings.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        settings.setDomStorageEnabled(true);
        // 排版适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)
        settings.setTextZoom(100);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                showContentView();
            }
        });
    }

    @Override
    protected void loadData() {
        //showContentView();
        webView.loadUrl(mUrl);
//        Uri data = _mActivity.getIntent().getData();
//        if (data != null) {
//            try {
//                String scheme = data.getScheme();
//                String host = data.getHost();
//                String path = data.getPath();
//                String url = scheme + "://" + host + path;
//                webView.loadUrl(url);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.stopLoading();
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
