package me.donlis.vreader.view.section.navigate;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import me.donlis.common.util.Utils;
import me.donlis.vreader.R;
import me.donlis.vreader.adapter.NaviListAdapter;
import me.donlis.vreader.base.AbstractBaseFragment;
import me.donlis.vreader.bean.ArticlesBean;
import me.donlis.vreader.bean.BaseWanAndroidBean;
import me.donlis.vreader.bean.NavBean;
import me.donlis.vreader.databinding.FragmentNavigateBinding;
import me.donlis.vreader.view.webview.WebViewFragment;
import me.donlis.vreader.viewmodel.NavigateViewModel;
import me.donlis.vreader.widget.SpaceItemDecoration;
import me.yokeyword.fragmentation.SupportFragment;

public class NavigateFragment extends AbstractBaseFragment<NavigateViewModel, FragmentNavigateBinding>
        implements NaviListAdapter.OnTagClickListener, SwipeRefreshLayout.OnRefreshListener {

    private NaviListAdapter naviListAdapter;

    public static NavigateFragment getInstance(){
        return new NavigateFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_navigate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    private void initView(){
        naviListAdapter = new NaviListAdapter(_mActivity.getApplicationContext());
        naviListAdapter.setOnTagClickListener(this);
        bindView.recycler.setLayoutManager(new LinearLayoutManager(_mActivity));
        bindView.recycler.addItemDecoration(new SpaceItemDecoration(0, Utils.dip2px(10)));
        bindView.recycler.setAdapter(naviListAdapter);

        bindView.swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    protected void loadData() {
        getNavList();
    }

    @Override
    public void onRefresh() {
        bindView.swipeRefresh.setRefreshing(true);
        getNavList();
    }

    private void getNavList(){
        viewModel.getNav().observe(this, new Observer<BaseWanAndroidBean<List<NavBean>>>() {
            @Override
            public void onChanged(BaseWanAndroidBean<List<NavBean>> listBaseWanAndroidBean) {
                if(bindView.swipeRefresh.isRefreshing()){
                    bindView.swipeRefresh.setRefreshing(false);
                }

                if(listBaseWanAndroidBean == null){
                    List<NavBean> data = naviListAdapter.getData();
                    if(data == null || data.size() == 0){
                        showFailView();
                    }
                }else{
                    showContentView();
                    naviListAdapter.setNewData(listBaseWanAndroidBean.getData());
                }
            }
        });
    }

    @Override
    public void onClick(int position, int childPos) {
        ArticlesBean bean = null;

        NavBean item = naviListAdapter.getItem(position);
        if(item != null){
            List<ArticlesBean> children = item.getArticles();
            if(children != null){
                bean = children.get(childPos);
            }
        }

        if(bean == null){
            Toast.makeText(_mActivity,"数据异常",Toast.LENGTH_LONG).show();
        }else {
            Bundle bundle = new Bundle();

            bundle.putString("url", bean.getLink());
            bundle.putString("title", bean.getTitle());

            WebViewFragment webViewFragment = WebViewFragment.getInstance();
            webViewFragment.setArguments(bundle);
            try{
                ((SupportFragment) ((SupportFragment) getParentFragment()).getParentFragment()).start(webViewFragment);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        if(naviListAdapter != null){
            naviListAdapter.getData().clear();
            naviListAdapter = null;
        }
        super.onDestroy();
    }

}
