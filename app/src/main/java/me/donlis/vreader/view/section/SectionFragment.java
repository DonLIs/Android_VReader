package me.donlis.vreader.view.section;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import me.donlis.vreader.R;
import me.donlis.vreader.adapter.MainViewPagerAdapter;
import me.donlis.vreader.base.AbstractSupportFragment;
import me.donlis.vreader.databinding.FragmentSectionBinding;
import me.donlis.vreader.databinding.ToolbarSectionBinding;
import me.donlis.vreader.view.section.navigate.NavigateFragment;
import me.donlis.vreader.view.section.structure.StructureFragment;
import me.donlis.vreader.viewmodel.BaseViewModel;

public class SectionFragment extends AbstractSupportFragment<BaseViewModel, FragmentSectionBinding> {

    private ToolbarSectionBinding toolbarBinding;

    private MainViewPagerAdapter pagerAdapter;

    public static SectionFragment getInstance(){
        return new SectionFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_section;
    }

    @Override
    protected boolean isDisplayHomeAsUp() {
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showContentView();

        initView();
    }

    @Override
    protected ViewDataBinding setToolbar() {
        toolbarBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.toolbar_section,null,false);

        return toolbarBinding;
    }

    private void initView(){

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(StructureFragment.getInstance());
        fragmentList.add(NavigateFragment.getInstance());

        List<String> titles = new ArrayList<>();
        titles.add("体系");
        titles.add("导航");

        pagerAdapter = new MainViewPagerAdapter(getChildFragmentManager(), fragmentList,titles);

        bindView.viewPager.setAdapter(pagerAdapter);
        bindView.viewPager.setOffscreenPageLimit(1);

        toolbarBinding.tab.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView tv = new TextView(_mActivity);
                tv.setTextSize(18);
                tv.setTextColor(Color.WHITE);
                tv.setText(tab.getText());

                tab.setCustomView(tv);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setCustomView(null);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        toolbarBinding.tab.setupWithViewPager(bindView.viewPager);
    }

    @Override
    public void onDestroy() {
        if(pagerAdapter != null){
            pagerAdapter.clear();
            pagerAdapter = null;
        }
        super.onDestroy();
    }
}
