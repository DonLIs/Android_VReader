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
import androidx.lifecycle.ViewModelProviders;
import me.donlis.vreader.R;
import me.donlis.vreader.databinding.ActivityBaseBinding;
import me.donlis.vreader.util.ClassUtil;
import me.donlis.vreader.util.StatusBarUtil;
import me.donlis.vreader.viewmodel.BaseViewModel;
import me.yokeyword.fragmentation.SupportActivity;

public abstract class AbstractBaseActivity<VM extends BaseViewModel,VB extends ViewDataBinding> extends SupportActivity {

    protected VM viewModel;

    protected VB bindView;

    protected View failView;

    protected View loadingView;

    protected ActivityBaseBinding mBaseBining;

    private Animation loadAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setStatusBarTransparent();
        super.onCreate(savedInstanceState);
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

        StatusBarUtil.setBarColor(this,Color.TRANSPARENT);
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

    private void setStatusBarTransparent() {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams winParams = window.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            window.setAttributes(winParams);
        }
    }

    @Override
    protected void onDestroy() {
        if(viewModel != null){
            viewModel.dispose();
        }
        super.onDestroy();
    }
}
