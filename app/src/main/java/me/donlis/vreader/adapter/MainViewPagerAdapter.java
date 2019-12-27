package me.donlis.vreader.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;

    private List<String> titles;

    public MainViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, MainViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    public MainViewPagerAdapter(@NonNull FragmentManager fm,List<Fragment> fragments) {
        super(fm, MainViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
    }

    public MainViewPagerAdapter(@NonNull FragmentManager fm,List<Fragment> fragments,List<String> titles) {
        super(fm, MainViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
        this.titles = titles;
    }

    public void addTitle(String... args){
        if(titles == null){
            titles = new ArrayList<>();
        }
        titles.addAll(Arrays.asList(args));
    }

    public void addFragment(Fragment... args){
        if(fragments == null){
            fragments = new ArrayList<>();
        }
        fragments.addAll(Arrays.asList(args));
    }

    public void clear(){
        if(fragments != null){
            fragments.clear();
        }
        if(titles != null){
            titles.clear();
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(titles == null){
            return "";
        }else{
            return titles.get(position);
        }
    }
}
