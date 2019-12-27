package me.donlis.vreader.view.main;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import me.donlis.ui.navigate.NavBar;
import me.donlis.ui.navigate.NavBarTab;
import me.donlis.vreader.R;
import me.donlis.vreader.adapter.MainViewPagerAdapter;
import me.donlis.vreader.base.AbstractBaseFragment;
import me.donlis.vreader.base.AbstractSupportFragment;
import me.donlis.vreader.databinding.FragmentMainBinding;
import me.donlis.vreader.view.about.AboutFragment;
import me.donlis.vreader.view.dynamics.DynamicsFragment;
import me.donlis.vreader.view.home.HomeFragment;
import me.donlis.vreader.view.section.SectionFragment;
import me.donlis.vreader.viewmodel.MainViewModel;

public class MainFragment extends AbstractBaseFragment<MainViewModel, FragmentMainBinding> {

    private ViewPager mViewPager;

    private MainViewPagerAdapter pagerAdapter;

    private NavBar mNavBar;

    public static MainFragment getInstance(){
        return new MainFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewPager = bindView.viewpager;
        mNavBar = bindView.navbar;

        showContentView();
        initView();
    }

    private void initView(){
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.getInstance());
        fragmentList.add(SectionFragment.getInstance());
        fragmentList.add(DynamicsFragment.getInstance());
        fragmentList.add(AboutFragment.getInstance());

        pagerAdapter = new MainViewPagerAdapter(getChildFragmentManager(), fragmentList);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mNavBar.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //选中颜色
        int primary_color = getResources().getColor(R.color.colorPrimary);
        //默认颜色
        int unselect_color = getResources().getColor(R.color.dark);

        NavBarTab nav_home = new NavBarTab(_mActivity,R.drawable.ic_home,"Home",primary_color,unselect_color);
        NavBarTab nav_section = new NavBarTab(_mActivity,R.drawable.ic_section,"Section",primary_color,unselect_color);
        NavBarTab nav_dynamics = new NavBarTab(_mActivity,R.drawable.ic_dynamics,"Dynamice",primary_color,unselect_color);
        NavBarTab nav_about = new NavBarTab(_mActivity,R.drawable.ic_account,"About",primary_color,unselect_color);

        mNavBar.addItem(nav_home).addItem(nav_section).addItem(nav_dynamics).addItem(nav_about);
        mNavBar.setOnTabSelectedListener(new NavBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                mViewPager.setCurrentItem(position,false);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
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
