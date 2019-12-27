package me.donlis.vreader.view.section.group;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import me.donlis.vreader.R;
import me.donlis.vreader.adapter.MainViewPagerAdapter;
import me.donlis.vreader.base.AbstractSupportFragment;
import me.donlis.vreader.bean.TreeBean;
import me.donlis.vreader.databinding.FragmentGroupsBinding;
import me.donlis.vreader.databinding.ToolbarGroupsBinding;

public class GroupsFragment extends AbstractSupportFragment<AndroidViewModel, FragmentGroupsBinding> {

    private final static String SELECTID = "selectid";

    private final static String BEAN = "bean";

    private ToolbarGroupsBinding toolbarBinding;

    private MainViewPagerAdapter pagerAdapter;

    private TreeBean mTreeBean;

    private int selectId = -1;

    public static GroupsFragment getInstance(int selectId,TreeBean bean){
        GroupsFragment fragment = new GroupsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SELECTID,selectId);
        bundle.putSerializable(BEAN,bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_groups;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArgu();
    }

    private void getArgu(){
        Bundle bundle = getArguments();
        if(bundle == null){
            return;
        }
        selectId = bundle.getInt(SELECTID,-1);
        mTreeBean = (TreeBean) bundle.getSerializable(BEAN);
    }

    @Override
    protected ViewDataBinding setToolbar() {
        toolbarBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.toolbar_groups,null,false);
        _mActivity.setSupportActionBar(toolbarBinding.toolbar);
        ActionBar actionBar = _mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            setHasOptionsMenu(true);

            toolbarBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop();
                }
            });
        }
        return toolbarBinding;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        initView();
    }

    private void initView(){
        if(mTreeBean == null){
            return;
        }

        List<String> titles = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();

        toolbarBinding.toolbar.setTitle(mTreeBean.getName());

        int index = 0;
        List<TreeBean.ChildrenBean> children = mTreeBean.getChildren();
        if(children != null){
            for(int i = 0;i < children.size();i++){
                TreeBean.ChildrenBean child = children.get(i);
                titles.add(child.getName());
                int id = child.getId();
                if(selectId == id){
                    index = i;
                }else{

                }
                fragmentList.add(GroupDetailFragment.getInstance(id,child.getName()));
            }
        }

        pagerAdapter = new MainViewPagerAdapter(getChildFragmentManager(),fragmentList,titles);

        bindView.viewPager.setOffscreenPageLimit(fragmentList.size() -1);
        bindView.viewPager.setAdapter(pagerAdapter);
        toolbarBinding.tab.setupWithViewPager(bindView.viewPager);
        toolbarBinding.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                bindView.viewPager.setCurrentItem(tab.getPosition(),false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        bindView.viewPager.setCurrentItem(index,false);
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
