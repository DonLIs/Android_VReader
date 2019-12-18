package me.donlis.vreader.base;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.google.android.material.appbar.AppBarLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;
import me.donlis.vreader.R;

public abstract class AbstractToolbarLessActivity<VM extends AndroidViewModel,VB extends ViewDataBinding>
        extends AbstractBaseActivity<VM,VB> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        AppBarLayout layout = (AppBarLayout) findViewById(R.id.header);
        if(layout != null){
            layout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void setToolBar(Toolbar toolBar) {

    }
}
