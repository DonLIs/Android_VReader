package me.donlis.vreader.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import me.donlis.common.util.Utils;
import me.donlis.vreader.R;
import me.donlis.vreader.bean.NewsItem;
import me.donlis.vreader.databinding.LayoutNewsList1ItemBinding;
import me.donlis.vreader.databinding.LayoutNewsList2ItemBinding;
import me.donlis.vreader.util.GlideUtil;

public class NewsListAdapter extends BaseMultiItemQuickAdapter<NewsItem, NewsListAdapter.ViewHolder> {

    private Context mContext;

    public NewsListAdapter(Context context) {
        super(null);
        this.mContext = context;
        addItemType(1, R.layout.layout_news_list1_item);
        addItemType(2, R.layout.layout_news_list2_item);
    }

    @Override
    protected void convert(ViewHolder helper, NewsItem item) {
        LayoutNewsList1ItemBinding binding1 = null;
        LayoutNewsList2ItemBinding binding2 = null;
        switch (helper.getItemViewType()){
            case 1:
                binding1 = DataBindingUtil.bind(helper.itemView);
                if(binding1 != null){
                    setImageView(helper,binding1.imgList,item);
                    binding1.setBean(item);
                    binding1.executePendingBindings();
                }
                break;
            default:
                binding2 = DataBindingUtil.bind(helper.itemView);
                if(binding2 != null){
                    setImageView(helper,binding2.imgList,item);
                    binding2.setBean(item);
                    binding2.executePendingBindings();
                }
                break;
        }
    }

    private void setImageView(ViewHolder helper,LinearLayout llayout,NewsItem item){
        if(helper.getImageViewList() == null){
            List<String> imgList = item.getImgList();
            if(imgList != null){
                for(String url : imgList){
                    ImageView imageView = new ImageView(mContext);
                    GlideUtil.displayImg(imageView,url);
                    helper.setImageView(imageView);
                    llayout.addView(imageView,new LinearLayout.LayoutParams(Utils.dip2px(100),Utils.dip2px(80)));
                }
            }
        }else{
            llayout.removeAllViews();
            List<ImageView> imageViewList = helper.getImageViewList();
            for(ImageView imageView : imageViewList){
                if(imageView.getParent() != null){
                    ((ViewGroup) imageView.getParent()).removeView(imageView);
                }
                llayout.addView(imageView,new LinearLayout.LayoutParams(Utils.dip2px(100),Utils.dip2px(80)));
            }
        }
    }

    class ViewHolder extends BaseViewHolder{

        private List<ImageView> imageViewList;

        public ViewHolder(View view) {
            super(view);
        }

        public List<ImageView> getImageViewList(){
            return imageViewList;
        }

        public void setImageView(ImageView view){
            if(imageViewList == null){
                imageViewList = new ArrayList<>();
            }
            imageViewList.add(view);
        }

    }

}
