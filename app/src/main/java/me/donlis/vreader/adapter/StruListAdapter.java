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
import me.donlis.vreader.bean.TreeBean;
import me.donlis.vreader.databinding.FlowLayoutBinding;
import me.donlis.vreader.databinding.LayoutStruListItemBinding;

public class StruListAdapter extends BaseQuickAdapter<TreeBean, BaseViewHolder> {

    private Context mContext;

    private OnTagClickListener onTagClickListener;

    public StruListAdapter(Context context) {
        super(R.layout.layout_stru_list_item);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, TreeBean item) {
        LayoutStruListItemBinding binding = DataBindingUtil.bind(helper.itemView);

        if(binding != null){

            binding.flowLayout.setAdapter(new TagAdapter<TreeBean.ChildrenBean>(item.getChildren()) {

                @Override
                public View getView(FlowLayout parent, int position, TreeBean.ChildrenBean childrenBean) {
                    FlowLayoutBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.flow_layout, null, false);
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
