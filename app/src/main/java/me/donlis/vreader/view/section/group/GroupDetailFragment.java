package me.donlis.vreader.view.section.group;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import androidx.annotation.Nullable;
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
import me.donlis.vreader.databinding.FragmentGroupDetailBinding;
import me.donlis.vreader.view.webview.WebViewFragment;
import me.donlis.vreader.viewmodel.GroupDetailViewModel;
import me.yokeyword.fragmentation.SupportFragment;

public class GroupDetailFragment extends AbstractBaseFragment<GroupDetailViewModel, FragmentGroupDetailBinding>
        implements BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private final static String GROUPID = "groupid";

    private final static String GROUPNAME = "groupname";

    private ArticleListAdapter adapter;

    private int groudId;

    private String groudName;

    public static GroupDetailFragment getInstance(int groupId,String groupName){
        GroupDetailFragment fragment = new GroupDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(GROUPID,groupId);
        bundle.putString(GROUPNAME,groupName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_group_detail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArgu();
    }

    private void getArgu(){
        Bundle bundle = getArguments();
        if(bundle != null){
            groudId = bundle.getInt(GROUPID,-1);
            groudName = bundle.getString(GROUPNAME,"");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    private void initView(){
        bindView.swipeRefresh.setOnRefreshListener(this);

        adapter = new ArticleListAdapter(_mActivity);
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
        getList();
    }

    private void getList(){
        viewModel.getData(groudId).observe(this, new Observer<BaseWanAndroidBean<DataBean>>() {
            @Override
            public void onChanged(BaseWanAndroidBean<DataBean> dataBeanBaseWanAndroidBean) {
                if(bindView.swipeRefresh.isRefreshing()){
                    bindView.swipeRefresh.setRefreshing(false);
                }

                if(dataBeanBaseWanAndroidBean == null){
                    if(viewModel.getPager() == 0){
                        showFailView();
                    }else{
                        adapter.loadMoreEnd();
                    }
                }else{
                    List<ArticlesBean> datas = dataBeanBaseWanAndroidBean.getData().getDatas();
                    if(datas == null || datas.size() ==0){
                        adapter.loadMoreEnd();
                    }else {
                        if (viewModel.getPager() == 0) {
                            showContentView();
                            adapter.setNewData(datas);
                            adapter.disableLoadMoreIfNotFullPage();
                        } else {
                            adapter.addData(datas);
                        }
                        adapter.loadMoreComplete();
                    }
                }
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ArticlesBean bean = (ArticlesBean) adapter.getItem(position);

        if(bean == null){
            Toast.makeText(_mActivity,"数据异常",Toast.LENGTH_LONG).show();
        }else {
            WebViewFragment webViewFragment = WebViewFragment.getInstance(bean.getTitle(),bean.getLink());

            try{
                ((SupportFragment) getParentFragment()).start(webViewFragment);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoadMoreRequested() {
        viewModel.nextPager();
        getList();
    }

    @Override
    public void onRefresh() {
        bindView.swipeRefresh.setRefreshing(true);
        viewModel.reset();
        getList();
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
