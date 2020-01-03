package me.donlis.vreader.view.section.structure;

import android.os.Bundle;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import me.donlis.common.util.Utils;
import me.donlis.vreader.R;
import me.donlis.vreader.adapter.StruListAdapter;
import me.donlis.vreader.base.AbstractBaseFragment;
import me.donlis.vreader.bean.BaseWanAndroidBean;
import me.donlis.vreader.bean.TreeBean;
import me.donlis.vreader.databinding.FragmentStructureBinding;
import me.donlis.vreader.view.section.group.GroupsFragment;
import me.donlis.vreader.viewmodel.StruViewModel;
import me.donlis.vreader.widget.SpaceItemDecoration;
import me.yokeyword.fragmentation.SupportFragment;

public class StructureFragment extends AbstractBaseFragment<StruViewModel, FragmentStructureBinding>
        implements SwipeRefreshLayout.OnRefreshListener, StruListAdapter.OnTagClickListener {

    private StruListAdapter struListAdapter;

    public static StructureFragment getInstance(){
        return new StructureFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_structure;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    private void initView(){
        struListAdapter = new StruListAdapter(_mActivity.getApplicationContext());
        struListAdapter.setOnTagClickListener(this);
        bindView.recycler.setLayoutManager(new LinearLayoutManager(_mActivity));
        bindView.recycler.addItemDecoration(new SpaceItemDecoration(0, Utils.dip2px(10)));
        bindView.recycler.setAdapter(struListAdapter);

        bindView.swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    protected void loadData() {
        getStruList();
    }

    @Override
    public void onRefresh() {
        bindView.swipeRefresh.setRefreshing(true);
        getStruList();
    }

    private void getStruList(){
        viewModel.getTree().observe(this, observer);
        viewModel.loadTree();
    }

    private Observer<BaseWanAndroidBean<List<TreeBean>>> observer = new Observer<BaseWanAndroidBean<List<TreeBean>>>() {
        @Override
        public void onChanged(BaseWanAndroidBean<List<TreeBean>> listBaseWanAndroidBean) {
            if(bindView.swipeRefresh.isRefreshing()){
                bindView.swipeRefresh.setRefreshing(false);
            }

            if(listBaseWanAndroidBean == null){
                List<TreeBean> data = struListAdapter.getData();
                if(data == null || data.size() == 0){
                    showFailView();
                }
            }else{
                showContentView();
                struListAdapter.setNewData(listBaseWanAndroidBean.getData());
            }
        }
    };

    @Override
    public void onClick(int position, int childPos) {
        TreeBean.ChildrenBean bean = null;

        TreeBean item = struListAdapter.getItem(position);
        if(item != null){
            List<TreeBean.ChildrenBean> children = item.getChildren();
            if(children != null){
                bean = children.get(childPos);
            }
        }

        if(bean == null){
            return;
        }

        GroupsFragment groupsFragment = GroupsFragment.getInstance(bean.getId(), item);
        try{
            ((SupportFragment) ((SupportFragment) getParentFragment()).getParentFragment()).start(groupsFragment);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        if(viewModel.getTree() != null){
            viewModel.getTree().removeObserver(observer);
        }
        if(struListAdapter != null){
            struListAdapter.getData().clear();
            struListAdapter = null;
        }
        super.onDestroy();
    }

}
