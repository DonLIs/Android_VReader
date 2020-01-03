package me.donlis.vreader.view.dynamics;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import me.donlis.vreader.R;
import me.donlis.vreader.adapter.MainViewPagerAdapter;
import me.donlis.vreader.base.AbstractSupportFragment;
import me.donlis.vreader.bean.BaseMzBean;
import me.donlis.vreader.bean.NewsType;
import me.donlis.vreader.databinding.FragmentDynamicsBinding;
import me.donlis.vreader.view.dynamics.news.NewsItemFragment;
import me.donlis.vreader.viewmodel.MzNewsViewModel;

public class DynamicsFragment extends AbstractSupportFragment<MzNewsViewModel, FragmentDynamicsBinding> {

    private MainViewPagerAdapter pagerAdapter;

    private MutableLiveData<List<NewsType>> newsTypeList = new MutableLiveData<>();

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
        setToolbarTitel("新闻");

        initView();
    }

    private void initView(){
        pagerAdapter = new MainViewPagerAdapter(getChildFragmentManager());

        bindView.viewPager.setAdapter(pagerAdapter);
        bindView.tab.setupWithViewPager(bindView.viewPager);

        bindView.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    }

    @Override
    protected void loadData() {
        getNewTypeList();
    }

    private void getNewTypeList(){
        viewModel.getNewsType().observe(this, observer);
        viewModel.loadNewsType();
    }

    private Observer<BaseMzBean<List<NewsType>>> observer = new Observer<BaseMzBean<List<NewsType>>>() {
        @Override
        public void onChanged(BaseMzBean<List<NewsType>> newsTypeBaseMzBean) {
            if(newsTypeBaseMzBean == null){
                showFailView();
            }else{
                showContentView();
                newsTypeList.setValue(newsTypeBaseMzBean.getData());
                createView();
            }
        }
    };

    private void createView(){
        List<NewsType> types = newsTypeList.getValue();
        if(types == null){
            return;
        }
        for(NewsType item : types){
            pagerAdapter.addTitle(item.getTypeName());
            pagerAdapter.addFragment(NewsItemFragment.getInstance(item.getTypeId()));
        }
        //bindView.viewPager.setOffscreenPageLimit(types.size() -1);
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        if(viewModel.getNewsType() != null){
            viewModel.getNewsType().removeObserver(observer);
        }
        newsTypeList = null;
        if(pagerAdapter != null){
            pagerAdapter.clear();
            pagerAdapter = null;
        }
        super.onDestroy();
    }
}
