package me.donlis.vreader.view.section.navigate;

import android.os.Bundle;

import androidx.annotation.Nullable;
import me.donlis.vreader.R;
import me.donlis.vreader.base.AbstractBaseFragment;

public class NavigateFragment extends AbstractBaseFragment {

    public static NavigateFragment getInstance(){
        return new NavigateFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_navigate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
