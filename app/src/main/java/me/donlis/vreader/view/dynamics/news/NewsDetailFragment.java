package me.donlis.vreader.view.dynamics.news;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import me.donlis.vreader.R;
import me.donlis.vreader.adapter.NewsDetailAdapter;
import me.donlis.vreader.base.AbstractSupportFragment;
import me.donlis.vreader.bean.BaseMzBean;
import me.donlis.vreader.bean.CompItem;
import me.donlis.vreader.bean.NewsDetail;
import me.donlis.vreader.databinding.FragmentNewsDetailBinding;
import me.donlis.vreader.databinding.LayoutImplHeaderBinding;
import me.donlis.vreader.viewmodel.MzNewsViewModel;

public class NewsDetailFragment extends AbstractSupportFragment<MzNewsViewModel, FragmentNewsDetailBinding> {

    private final static String NEWID = "newId";

    private String newId;

    private NewsDetailAdapter adapter;

    public static NewsDetailFragment getInstance(String newId){
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NEWID,newId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_news_detail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getArgs();
    }

    private void getArgs(){
        Bundle bundle = getArguments();
        if(bundle != null){
            newId = bundle.getString(NEWID,"");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setToolbarTitel("详情");

        initView();
    }

    private void initView(){
        adapter = new NewsDetailAdapter(_mActivity);

        bindView.recycler.setLayoutManager(new LinearLayoutManager(_mActivity));

        bindView.recycler.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        getNewsInfo();
    }

    private void getNewsInfo(){
        viewModel.getNewsInfo().observe(this, observer);
        viewModel.loadNewsInfo(newId);
    }

    private Observer<BaseMzBean<NewsDetail>> observer = new Observer<BaseMzBean<NewsDetail>>() {
        @Override
        public void onChanged(BaseMzBean<NewsDetail> newsDetailBaseMzBean) {
            if(newsDetailBaseMzBean == null){
                showFailView();
            }else{
                showContentView();
                createView(newsDetailBaseMzBean.getData());
            }
        }
    };

    private void createView(NewsDetail info){
        if(info == null){
            return;
        }

        setNewTitle(info);

        setContent(info);
    }

    private void setNewTitle(NewsDetail info){
        LayoutImplHeaderBinding headerBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.layout_impl_header,null,false);
        headerBinding.setBean(info);

        adapter.addHeaderView(headerBinding.getRoot());

        adapter.notifyItemChanged(0);
    }

    private void setContent(NewsDetail info){
        Pattern pt = Pattern.compile("\\<p\\>.*?\\<\\/p\\>");

        Pattern imgPt = Pattern.compile("\\<!--.*?--\\>");

        String content = info.getContent();
        content = content.trim();

        List<NewsDetail.ImageItem> images = info.getImages();
        if(images != null && images.size() > 0){
            for(NewsDetail.ImageItem img : images){
                content = content.replace(img.getPosition(),"<!--" + img.getImgSrc() + "-->");
            }
        }

        List<CompItem> strings = new ArrayList<>();

        while (content.length() > 0){
            if(content.startsWith("<p>")){
                Matcher matcher = pt.matcher(content);
                if(matcher.find()){
                    String s = content.substring(0,matcher.end());
                    content = content.replace(s,"");
                    strings.add(new CompItem(s,CompItem.TEXT));
                }else{
                    break;
                }
            }else if(content.startsWith("<!--")){
                Matcher matcher = imgPt.matcher(content);
                if(matcher.find()){
                    String s = content.substring(0,matcher.end());
                    content = content.replace(s,"");
                    s = s.replace("<!--","");
                    s = s.replace("-->","");
                    strings.add(new CompItem(s,CompItem.IMG));
                }else{
                    break;
                }
            }else {
                break;
            }

            content = content.trim();
        }

        adapter.setNewData(strings);
    }

    @Override
    public void onDestroy() {
        if(viewModel.getNewsInfo() != null){
            viewModel.getNewsInfo().removeObserver(observer);
        }
        if(adapter != null){
            adapter.getData().clear();
            adapter = null;
        }
        super.onDestroy();
    }
}
