package me.donlis.vreader.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import me.donlis.common.util.Utils;
import me.donlis.vreader.R;
import me.donlis.vreader.databinding.ToolbarBinding;
import me.donlis.vreader.util.StatusBarUtil;
import me.donlis.vreader.viewmodel.BaseViewModel;
import me.donlis.vreader.widget.StatusView;

public abstract class AbstractSupportFragment<VM extends BaseViewModel,VB extends ViewDataBinding> extends AbstractBaseFragment<VM,VB> {

    private ToolbarBinding toolbarBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if(view == null){
            return null;
        }

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        ViewGroup group = (ViewGroup) view.getRootView();

        ViewDataBinding toolBinding = setToolbar();

        group.addView(toolBinding.getRoot(),0,lp);

        return view;
    }

    protected ViewDataBinding setToolbar(){
        toolbarBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.toolbar, null, false);

        toolbarBinding.appBarLayout.setPadding(0,Utils.dip2px(24),0,0);

        _mActivity.setSupportActionBar(toolbarBinding.toolbar);

        if(isDisplayHomeAsUp()) {
            ActionBar actionBar = _mActivity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
                setHasOptionsMenu(true);

                toolbarBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop();
                    }
                });
            }
        }

        return toolbarBinding;
    }

    protected boolean isDisplayHomeAsUp(){
        return true;
    }

    protected Toolbar getToolbar(){
        if(toolbarBinding == null){
            return null;
        }
        return toolbarBinding.toolbar;
    }

    protected void setToolbarTitel(CharSequence title){
        if(getToolbar() != null){
            getToolbar().setTitle(title);
        }
    }

}
