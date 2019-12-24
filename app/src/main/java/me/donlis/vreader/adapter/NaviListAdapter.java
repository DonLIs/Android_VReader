package me.donlis.vreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import androidx.databinding.DataBindingUtil;
import me.donlis.vreader.R;
import me.donlis.vreader.bean.ArticlesBean;
import me.donlis.vreader.bean.NavBean;
import me.donlis.vreader.databinding.FlowNavLayoutBinding;
import me.donlis.vreader.databinding.LayoutNaviListItemBinding;

public class NaviListAdapter extends BaseQuickAdapter<NavBean, BaseViewHolder> {

    private Context mContext;

    private OnTagClickListener onTagClickListener;

    public NaviListAdapter(Context context) {
        super(R.layout.layout_navi_list_item);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, NavBean item) {
        LayoutNaviListItemBinding binding = DataBindingUtil.bind(helper.itemView);

        if(binding != null){

            binding.flowLayout.setAdapter(new TagAdapter<ArticlesBean>(item.getArticles()) {

                @Override
                public View getView(FlowLayout parent, int position, ArticlesBean childrenBean) {
                    FlowNavLayoutBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.flow_nav_layout, null, false);
                    inflate.setBean(childrenBean);
                    return inflate.getRoot();
                }
            });

            binding.flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    if(onTagClickListener != null){
                        onTagClickListener.onClick(helper.getAdapterPosition(),position);
                    }
                    return false;
                }
            });

            binding.setBean(item);
            binding.executePendingBindings();
        }
    }

    public void setOnTagClickListener(OnTagClickListener listener){
        this.onTagClickListener = listener;
    }

    public interface OnTagClickListener{
        void onClick(int position,int childPos);
    }

}
