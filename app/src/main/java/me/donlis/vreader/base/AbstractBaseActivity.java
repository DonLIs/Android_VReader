package me.donlis.vreader.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProviders;
import me.donlis.vreader.R;
import me.donlis.vreader.databinding.ActivityBaseBinding;
import me.donlis.vreader.util.ClassUtil;
import me.yokeyword.fragmentation.SupportActivity;

public abstract class AbstractBaseActivity<VM extends AndroidViewModel,VB extends ViewDataBinding> extends SupportActivity {

    protected VM viewModel;

    protected VB bindView;

    protected View failView;

    protected View loadingView;

    protected ActivityBaseBinding mBaseBining;

    private Animation loadAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSystemBarTint();
    }

    /**
     * 初始化窗口和状态栏，沉浸式窗口
     */
    private void initSystemBarTint() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void setContentView(int layoutResID) {

        mBaseBining = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_base, null, false);

        bindView = DataBindingUtil.inflate(getLayoutInflater(),layoutResID,null,false);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        //bindView.getRoot().setLayoutParams(lp);

        mBaseBining.container.addView(bindView.getRoot(),lp);

        getWindow().setContentView(mBaseBining.getRoot());

        bindView.getRoot().setVisibility(View.GONE);

        loadingView = ((ViewStub) findViewById(R.id.loading_view)).inflate();
        loadAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        loadAnimation.setInterpolator(new LinearInterpolator());
        loadingView.startAnimation(loadAnimation);

        setToolBar(mBaseBining.header.toolbar);

        initViewModel();
    }

    /**
     * 初始化ViewModel
     */
    private void initViewModel() {
        Class<VM> viewModelClass = ClassUtil.getViewModel(this);
        if (viewModelClass != null) {
            this.viewModel = ViewModelProviders.of(this).get(viewModelClass);
        }
    }

    protected void setToolBar(Toolbar toolBar){
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
            }
        });
    }

    protected void showLoading(){
        if(loadingView != null && loadingView.getAnimation() == null){
            loadingView.startAnimation(loadAnimation);
        }
        if(loadingView != null && loadingView.getVisibility() == View.GONE){
            loadingView.setVisibility(View.VISIBLE);
        }
        if(bindView.getRoot().getVisibility() == View.VISIBLE){
            bindView.getRoot().setVisibility(View.GONE);
        }
        if(failView != null){
            failView.setVisibility(View.GONE);
        }
    }

    protected void showFailView(){
        if(loadingView != null && loadingView.getAnimation() != null){
            loadingView.clearAnimation();
        }
        if(loadingView != null && loadingView.getVisibility() == View.VISIBLE){
            loadingView.setVisibility(View.GONE);
        }
        if(bindView.getRoot().getVisibility() == View.VISIBLE){
            bindView.getRoot().setVisibility(View.GONE);
        }
        if(failView == null){
            failView = ((ViewStub) findViewById(R.id.fail_view)).inflate();
        }
        if(failView.getVisibility() == View.GONE) {
            failView.setVisibility(View.VISIBLE);
        }
    }

    protected void showContentView(){
        if(loadingView != null && loadingView.getAnimation() != null){
            loadingView.clearAnimation();
        }
        if(loadingView != null && loadingView.getVisibility() == View.VISIBLE){
            loadingView.setVisibility(View.GONE);
        }
        if(failView != null && failView.getVisibility() == View.VISIBLE){
            failView.setVisibility(View.GONE);
        }
        if(bindView.getRoot().getVisibility() == View.GONE){
            bindView.getRoot().setVisibility(View.VISIBLE);
        }
    }

    /**
     * 禁止改变字体大小
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
