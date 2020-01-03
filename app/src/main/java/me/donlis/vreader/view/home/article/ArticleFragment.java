package me.donlis.vreader.view.home.article;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import me.donlis.vreader.R;
import me.donlis.vreader.adapter.ArticleListAdapter;
import me.donlis.vreader.base.AbstractBaseFragment;
import me.donlis.vreader.bean.ArticlesBean;
import me.donlis.vreader.bean.BaseWanAndroidBean;
import me.donlis.vreader.bean.DataBean;
import me.donlis.vreader.databinding.FragmentArticleBinding;
import me.donlis.vreader.view.webview.WebViewFragment;
import me.donlis.vreader.viewmodel.ArticleViewModel;
import me.yokeyword.fragmentation.SupportFragment;

public class ArticleFragment extends AbstractBaseFragment<ArticleViewModel, FragmentArticleBinding>
        implements BaseQuickAdapter.RequestLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.OnItemClickListener{

    private ArticleListAdapter articleListAdapter;

    public static ArticleFragment getInstance(){
        return new ArticleFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_article;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    private void initView(){
        articleListAdapter = new ArticleListAdapter(_mActivity.getApplicationContext());

        articleListAdapter.setEnableLoadMore(true);
        articleListAdapter.setOnLoadMoreListener(this, bindView.recycler);
        articleListAdapter.setOnItemClickListener(this);
        bindView.recycler.setLayoutManager(new LinearLayoutManager(_mActivity));
        bindView.recycler.addItemDecoration(new DividerItemDecoration(_mActivity,DividerItemDecoration.VERTICAL));
        bindView.recycler.setAdapter(articleListAdapter);

        bindView.swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    protected void loadData() {
        getHomeList();
    }

    @Override
    protected void onReLoad() {
        viewModel.reset();
        getHomeList();
    }

    @Override
    public void onRefresh() {
        bindView.swipeRefresh.setRefreshing(true);
        viewModel.reset();
        getHomeList();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ArticlesBean bean = (ArticlesBean) adapter.getItem(position);

        if(bean == null){
            Toast.makeText(_mActivity,"数据异常",Toast.LENGTH_LONG).show();
        }else {
            WebViewFragment webViewFragment = WebViewFragment.getInstance(bean.getTitle(),bean.getLink());

            try{
                ((SupportFragment) ((SupportFragment) getParentFragment()).getParentFragment()).start(webViewFragment);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoadMoreRequested() {
        viewModel.nextPager();
        getHomeList();
    }

    private void getHomeList(){
        viewModel.getHomeList().observe(this, observer);
        viewModel.loadHomeList();
    }

    private Observer<BaseWanAndroidBean<DataBean>> observer = new Observer<BaseWanAndroidBean<DataBean>>() {
        @Override
        public void onChanged(BaseWanAndroidBean<DataBean> homeListBean) {
            if(bindView.swipeRefresh.isRefreshing()){
                bindView.swipeRefresh.setRefreshing(false);
            }

            if(homeListBean == null){
                if(viewModel.getPager() == 0) {
                    showFailView();
                }else{
                    articleListAdapter.loadMoreEnd();
                }
            }else {
                List<ArticlesBean> datas = homeListBean.getData().getDatas();
                if(datas == null || datas.size() == 0){
                    articleListAdapter.loadMoreEnd();
                }else {
                    if (viewModel.getPager() == 0) {
                        showContentView();
                        articleListAdapter.setNewData(datas);
                        articleListAdapter.disableLoadMoreIfNotFullPage();
                    } else {
                        articleListAdapter.addData(datas);
                    }
                    articleListAdapter.loadMoreComplete();
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        if(viewModel.getHomeList() != null){
            viewModel.getHomeList().removeObserver(observer);
        }
        if(articleListAdapter != null){
            articleListAdapter.getData().clear();
            articleListAdapter = null;
        }
        super.onDestroy();
    }

}
