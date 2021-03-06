package me.donlis.vreader.view.home.project;

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
import me.donlis.vreader.adapter.ProjectListAdapter;
import me.donlis.vreader.base.AbstractBaseFragment;
import me.donlis.vreader.bean.ArticlesBean;
import me.donlis.vreader.bean.BaseWanAndroidBean;
import me.donlis.vreader.bean.DataBean;
import me.donlis.vreader.databinding.FragmentProjectBinding;
import me.donlis.vreader.view.webview.WebViewFragment;
import me.donlis.vreader.viewmodel.ProjectViewModel;
import me.yokeyword.fragmentation.SupportFragment;

public class ProjectFragment extends AbstractBaseFragment<ProjectViewModel, FragmentProjectBinding>
        implements BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ProjectListAdapter projectListAdapter;

    public static ProjectFragment getInstance(){
        return new ProjectFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_project;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    private void initView(){
        projectListAdapter = new ProjectListAdapter(_mActivity.getApplicationContext());

        projectListAdapter.setEnableLoadMore(true);
        projectListAdapter.setOnLoadMoreListener(this,bindView.recycler);
        projectListAdapter.setOnItemClickListener(this);

        bindView.recycler.setLayoutManager(new LinearLayoutManager(_mActivity));
        bindView.recycler.addItemDecoration(new DividerItemDecoration(_mActivity,DividerItemDecoration.VERTICAL));
        bindView.recycler.setAdapter(projectListAdapter);

        bindView.swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    protected void loadData() {
        getProjectList();
    }

    @Override
    protected void onReLoad() {
        viewModel.reset();
        getProjectList();
    }

    @Override
    public void onLoadMoreRequested() {
        viewModel.nextPager();
        getProjectList();
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
    public void onRefresh() {
        bindView.swipeRefresh.setRefreshing(true);
        viewModel.reset();
        getProjectList();
    }

    private void getProjectList(){
        viewModel.getProjectList().observe(this, observer);
        viewModel.loadProjectList();
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
                    projectListAdapter.loadMoreEnd();
                }
            }else {
                List<ArticlesBean> datas = homeListBean.getData().getDatas();
                if(datas == null || datas.size() == 0){
                    projectListAdapter.loadMoreEnd();
                }else {
                    if (viewModel.getPager() == 0) {
                        showContentView();
                        projectListAdapter.setNewData(datas);
                        projectListAdapter.disableLoadMoreIfNotFullPage();
                    } else {
                        projectListAdapter.addData(datas);
                    }
                    projectListAdapter.loadMoreComplete();
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        if(viewModel.getProjectList() != null){
            viewModel.getProjectList().removeObserver(observer);
        }
        if(projectListAdapter != null){
            projectListAdapter.getData().clear();
            projectListAdapter = null;
        }
        super.onDestroy();
    }

}
