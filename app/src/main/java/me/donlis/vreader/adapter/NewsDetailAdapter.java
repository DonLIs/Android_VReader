package me.donlis.vreader.adapter;

import android.content.Context;
import android.text.Html;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import androidx.annotation.NonNull;
import me.donlis.vreader.R;
import me.donlis.vreader.bean.CompItem;
import me.donlis.vreader.util.GlideUtil;

public class NewsDetailAdapter extends BaseMultiItemQuickAdapter<CompItem,BaseViewHolder> {

    private Context mContext;

    public NewsDetailAdapter(Context context) {
        super(null);
        this.mContext = context;
        addItemType(CompItem.TEXT, R.layout.layout_impl_textview);
        addItemType(CompItem.IMG,R.layout.layout_impl_image);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CompItem item) {
        switch (helper.getItemViewType()){
            case CompItem.TEXT:
                helper.setText(R.id.text, Html.fromHtml(item.getContent()));
                break;
            case CompItem.IMG:
                ImageView imageView = helper.getView(R.id.img);
                GlideUtil.displayImg(imageView,item.getContent());
                break;
        }

    }

}
