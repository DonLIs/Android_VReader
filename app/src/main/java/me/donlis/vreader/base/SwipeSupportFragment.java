package me.donlis.vreader.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import me.donlis.vreader.viewmodel.BaseViewModel;

public abstract class SwipeSupportFragment<VM extends BaseViewModel,VB extends ViewDataBinding> extends AbstractSupportFragment<VM,VB> {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setSwipeBackEnable(true);
        return attachToSwipeBack(view);
    }

}
