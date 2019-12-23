package me.donlis.vreader.view.home;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import me.donlis.vreader.R;
import me.donlis.vreader.adapter.MainViewPagerAdapter;
import me.donlis.vreader.base.AbstractBaseFragment;
import me.donlis.vreader.base.AbstractSupportFragment;
import me.donlis.vreader.databinding.FragmentHomeBinding;
import me.donlis.vreader.view.home.article.ArticleFragment;
import me.donlis.vreader.view.home.project.ProjectFragment;
import me.donlis.vreader.viewmodel.HomeViewModel;

public class HomeFragment extends AbstractSupportFragment<HomeViewModel, FragmentHomeBinding> {

    private MainViewPagerAdapter pagerAdapter;

    private TabLayout tabLayout;

    private ViewPager viewPager;

    public static HomeFragment getInstance(){
        return new HomeFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected boolean isDisplayHomeAsUp() {
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showLoading();

        initView();

        showContentView();
    }

    private void initView(){
        tabLayout = bindView.tab;
        viewPager = bindView.viewPager;

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ArticleFragment.getInstance());
        fragmentList.add(ProjectFragment.getInstance());

        List<String> titles = new ArrayList<>();
        titles.add("最新博文");
        titles.add("项目博文");

        pagerAdapter = new MainViewPagerAdapter(getChildFragmentManager(), fragmentList,titles);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(1);

        tabLayout.setupWithViewPager(viewPager);
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
