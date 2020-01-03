package me.donlis.vreader.view.about;

import android.os.Bundle;

import androidx.annotation.Nullable;
import me.donlis.vreader.R;
import me.donlis.vreader.base.AbstractBaseFragment;
import me.donlis.vreader.databinding.ToolbarAboutBinding;

public class AboutFragment extends AbstractBaseFragment {

    private ToolbarAboutBinding toolbarBinding;

    public static AboutFragment getInstance(){
        return new AboutFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_about;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    protected void loadData() {
        showContentView();
    }

}
