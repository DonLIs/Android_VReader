package me.donlis.vreader.view.dynamics;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import me.donlis.vreader.R;
import me.donlis.vreader.base.AbstractSupportFragment;
import me.donlis.vreader.databinding.FragmentDynamicsBinding;

public class DynamicsFragment extends AbstractSupportFragment<AndroidViewModel, FragmentDynamicsBinding> {

    public static DynamicsFragment getInstance(){
        return new DynamicsFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_dynamics;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected boolean isDisplayHomeAsUp() {
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
    }


}
