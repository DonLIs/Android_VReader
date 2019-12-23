package me.donlis.vreader.view.section.structure;

import android.os.Bundle;

import androidx.annotation.Nullable;
import me.donlis.vreader.R;
import me.donlis.vreader.base.AbstractBaseFragment;

public class StructureFragment extends AbstractBaseFragment {

    public static StructureFragment getInstance(){
        return new StructureFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_structure;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

    }

    private void initView(){

    }

}
