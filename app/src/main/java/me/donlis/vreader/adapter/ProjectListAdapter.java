package me.donlis.vreader.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import androidx.databinding.DataBindingUtil;
import me.donlis.vreader.R;
import me.donlis.vreader.bean.ArticlesBean;
import me.donlis.vreader.databinding.LayoutProjectListItemBinding;

public class ProjectListAdapter extends BaseQuickAdapter<ArticlesBean,BaseViewHolder> {

    private Context mContext;

    public ProjectListAdapter(Context context) {
        super(R.layout.layout_project_list_item);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticlesBean item) {
        LayoutProjectListItemBinding binding = DataBindingUtil.bind(helper.itemView);
        if(binding != null){
            binding.setBean(item);
            binding.executePendingBindings();
        }
    }
}
