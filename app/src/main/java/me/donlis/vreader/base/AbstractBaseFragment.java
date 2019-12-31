package me.donlis.vreader.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;
import me.donlis.vreader.R;
import me.donlis.vreader.util.ClassUtil;
import me.donlis.vreader.viewmodel.BaseViewModel;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

public abstract class AbstractBaseFragment<VM extends BaseViewModel,VB extends ViewDataBinding> extends SwipeBackFragment {

    protected VM viewModel;

    protected VB bindView;

    protected View failView;

    protected View loadingView;

    protected View emptyView;

    protected boolean isInit = false;

    protected boolean isVisible = false;

    protected boolean isLoaded = false;

    private boolean isLateLoad = false;

    private Animation loadAnimation;

    public abstract int getLayoutResId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSwipeBackEnable(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base,null);

        bindView = DataBindingUtil.inflate(_mActivity.getLayoutInflater(),getLayoutResId(),null,false);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        bindView.getRoot().setLayoutParams(lp);

        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.container);
        layout.addView(bindView.getRoot());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bindView.getRoot().setVisibility(View.GONE);

        loadingView = ((ViewStub) _mActivity.findViewById(R.id.loading_view)).inflate();
        loadAnimation = AnimationUtils.loadAnimation(_mActivity.getApplicationContext(), R.anim.rotate);
        loadAnimation.setInterpolator(new LinearInterpolator());
        loadingView.startAnimation(loadAnimation);

        initViewModel();

        this.isInit = true;
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisible = isVisibleToUser;
        if(isVisible){
            prepareLoadData();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.isVisible = !hidden;
        if(isVisible){
            prepareLoadData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.isVisible = true;
        prepareLoadData();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.isVisible = false;
    }

    protected void prepareLoadData(){
        if(isInit && isVisible && !isLoaded){
            loadData();
            this.isLoaded = true;
        }else if(!isLoaded){
            this.isLateLoad = true;
        }
    }

    protected void loadData(){

    }

    protected void onReLoad(){

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
        if(emptyView != null){
            emptyView.setVisibility(View.GONE);
        }
    }

    protected void showFailView(){
        if(loadingView != null && loadingView.getAnimation() != null){
            loadingView.clearAnimation();
        }
        if(loadingView != null && loadingView.getVisibility() == View.VISIBLE){
            loadingView.setVisibility(View.GONE);
        }
        if(bindView.getRoot().getVisibility() != View.GONE){
            bindView.getRoot().setVisibility(View.GONE);
        }
        if(failView == null){
            failView = ((ViewStub) getView().findViewById(R.id.fail_view)).inflate();
        }
        if(failView != null) {
            TextView ref = failView.findViewById(R.id.refresh_btn);
            ref.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoading();
                    onReLoad();
                }
            });
            failView.setVisibility(View.VISIBLE);
        }
        if(emptyView != null){
            emptyView.setVisibility(View.GONE);
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
        if(emptyView != null){
            emptyView.setVisibility(View.GONE);
        }
    }

    protected void showEmptyView(){
        if(loadingView != null && loadingView.getAnimation() != null){
            loadingView.clearAnimation();
        }
        if(loadingView != null && loadingView.getVisibility() == View.VISIBLE){
            loadingView.setVisibility(View.GONE);
        }
        if(failView != null && failView.getVisibility() == View.VISIBLE){
            failView.setVisibility(View.GONE);
        }
        if(bindView.getRoot().getVisibility() == View.VISIBLE){
            bindView.getRoot().setVisibility(View.GONE);
        }
        if(emptyView == null){
            emptyView = ((ViewStub) getView().findViewById(R.id.empty_view)).inflate();
        }
        if(emptyView != null && emptyView.getVisibility() == View.GONE){
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        if(viewModel != null){
            viewModel.dispose();
        }
        super.onDestroy();
    }
}
