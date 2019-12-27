package me.donlis.vreader.view.dynamics.news;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import me.donlis.vreader.R;
import me.donlis.vreader.adapter.NewsListAdapter;
import me.donlis.vreader.base.AbstractBaseFragment;
import me.donlis.vreader.bean.BaseMzBean;
import me.donlis.vreader.bean.NewsItem;
import me.donlis.vreader.databinding.FragmentNewsItemBinding;
import me.donlis.vreader.viewmodel.MzNewsViewModel;
import me.yokeyword.fragmentation.SupportFragment;

public class NewsItemFragment extends AbstractBaseFragment<MzNewsViewModel, FragmentNewsItemBinding>
        implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    private final static String TYPEID = "typeid";

    private NewsListAdapter adapter;

    private int typeId;

    public static NewsItemFragment getInstance(int typeId){
        NewsItemFragment fragment = new NewsItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPEID,typeId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_news_item;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArgs();
    }

    private void getArgs(){
        Bundle bundle = getArguments();
        if(bundle != null){
            typeId = bundle.getInt(TYPEID);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        if(savedInstanceState != null){
            getNewsList();
        }
    }

    private void initView(){
        bindView.swipeRefresh.setOnRefreshListener(this);

        adapter = new NewsListAdapter(_mActivity);
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(this,bindView.recycler);
        adapter.setOnItemClickListener(this);

        bindView.recycler.setLayoutManager(new LinearLayoutManager(_mActivity.getApplicationContext()));
        bindView.recycler.addItemDecoration(new DividerItemDecoration(_mActivity,DividerItemDecoration.VERTICAL));
        bindView.recycler.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        viewModel.reset();
        getNewsList();
    }

    private void getNewsList(){
        viewModel.getNewsList(typeId).observe(this, new Observer<BaseMzBean<List<NewsItem>>>() {
            @Override
            public void onChanged(BaseMzBean<List<NewsItem>> listBaseMzBean) {
                if(bindView.swipeRefresh.isRefreshing()){
                    bindView.swipeRefresh.setRefreshing(false);
                }

                if(listBaseMzBean == null){
                    if(viewModel.getPager() == 0){
                        showFailView();
                    }else{
                        adapter.loadMoreEnd();
                    }
                }else{
                    if(viewModel.getPager() == 0){
                        showContentView();
                        adapter.setNewData(listBaseMzBean.getData());
                        adapter.disableLoadMoreIfNotFullPage();
                    }else{
                        adapter.addData(listBaseMzBean.getData());
                    }
                    adapter.loadMoreComplete();
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        bindView.swipeRefresh.setRefreshing(true);
        viewModel.reset();
        getNewsList();
    }

    @Override
    public void onLoadMoreRequested() {
        viewModel.nextPager();
        getNewsList();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        NewsItem item = (NewsItem) adapter.getData().get(position);
        NewsDetailFragment fragment = NewsDetailFragment.getInstance(item.getNewsId());

        ((SupportFragment) ((SupportFragment) getParentFragment()).getParentFragment()).start(fragment);
    }

    @Override
    public void onDestroy() {
        if(adapter != null){
            adapter.getData().clear();
            adapter = null;
        }
        super.onDestroy();
    }
}
