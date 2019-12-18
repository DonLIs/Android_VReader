package me.donlis.vreader.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;
import me.donlis.vreader.R;
import me.donlis.vreader.databinding.ToolbarBinding;

public abstract class AbstractSupportFragment<VM extends AndroidViewModel,VB extends ViewDataBinding> extends AbstractBaseFragment<VM,VB> {

    protected ToolbarBinding toolbarBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if(view == null){
            return null;
        }
        toolbarBinding = DataBindingUtil.inflate(_mActivity.getLayoutInflater(), R.layout.toolbar, null, false);

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        ViewGroup group = (ViewGroup) view.getRootView();
        group.addView(toolbarBinding.getRoot(),0,lp);

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

        return view;
    }

    protected boolean isDisplayHomeAsUp(){
        return true;
    }

    protected void setToolbarTitel(CharSequence title){
        toolbarBinding.toolbar.setTitle(title);
    }

}
