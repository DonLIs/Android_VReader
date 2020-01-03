package me.donlis.vreader.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.donlis.vreader.bean.BaseMzBean;
import me.donlis.vreader.bean.NewsDetail;
import me.donlis.vreader.bean.NewsItem;
import me.donlis.vreader.bean.NewsType;
import me.donlis.vreader.http.HttpClient;

public class MzNewsViewModel extends BaseViewModel {

    private final int DEFAULT_PAGER = 0;

    private int pager = DEFAULT_PAGER;

    private MutableLiveData<BaseMzBean<List<NewsType>>> newsTypeListData;

    private MutableLiveData<BaseMzBean<List<NewsItem>>> newsListData;

    private MutableLiveData<BaseMzBean<NewsDetail>> newsInfo;

    public MzNewsViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<BaseMzBean<List<NewsType>>> getNewsType(){
        if(newsTypeListData == null) {
            newsTypeListData = new MutableLiveData<>();
        }
        return newsTypeListData;
    }

    public void loadNewsType(){
        if(newsTypeListData == null) {
            newsTypeListData = new MutableLiveData<>();
        }
        HttpClient.getMxnzpServer().getType()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseMzBean<List<NewsType>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BaseMzBean<List<NewsType>> newsTypeBaseMzBean) {
                        newsTypeListData.setValue(newsTypeBaseMzBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        newsTypeListData.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public MutableLiveData<BaseMzBean<List<NewsItem>>> getNewsList(){
        if(newsListData == null) {
            newsListData = new MutableLiveData<>();
        }
        return newsListData;
    }

    public void loadNewsList(int typeId){
        if(newsListData == null) {
            newsListData = new MutableLiveData<>();
        }
        HttpClient.getMxnzpServer().getNews(typeId,pager)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseMzBean<List<NewsItem>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BaseMzBean<List<NewsItem>> newsItemBaseMzBean) {
                        newsListData.setValue(newsItemBaseMzBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        newsListData.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public MutableLiveData<BaseMzBean<NewsDetail>> getNewsInfo(){
        if(newsInfo == null) {
            newsInfo = new MutableLiveData<>();
        }
        return newsInfo;
    }

    public void loadNewsInfo(String newId){
        if(newsInfo == null) {
            newsInfo = new MutableLiveData<>();
        }
        HttpClient.getMxnzpServer().getNewsDetail(newId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseMzBean<NewsDetail>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BaseMzBean<NewsDetail> newsDetailBaseMzBean) {
                        newsInfo.setValue(newsDetailBaseMzBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        newsInfo.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public int getPager() {
        return pager;
    }

    public void setPager(int pager) {
        this.pager = pager;
    }

    public void reset(){
        this.pager = DEFAULT_PAGER;
    }

    public void nextPager(){
        this.pager += 1;
    }

}
