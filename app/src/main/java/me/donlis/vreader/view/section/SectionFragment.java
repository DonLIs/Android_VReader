package me.donlis.vreader.view.section;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import me.donlis.vreader.R;
import me.donlis.vreader.base.AbstractBaseFragment;

public class SectionFragment extends AbstractBaseFragment {

    private Handler handler = new Handler();

    public static SectionFragment getInstance(){
        return new SectionFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_section;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    protected void loadData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showContentView();
            }
        }, 1500);
    }
}
