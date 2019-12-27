package me.donlis.vreader.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
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

public class MzNewsViewModel extends AndroidViewModel {

    private final int DEFAULT_PAGER = 0;

    private int pager = DEFAULT_PAGER;

    public MzNewsViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<BaseMzBean<List<NewsType>>> getNewsType(){
        final MutableLiveData<BaseMzBean<List<NewsType>>> listData = new MutableLiveData<>();
        HttpClient.getMxnzpServer().getType()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseMzBean<List<NewsType>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseMzBean<List<NewsType>> newsTypeBaseMzBean) {
                        listData.setValue(newsTypeBaseMzBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listData.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return listData;
    }

    public MutableLiveData<BaseMzBean<List<NewsItem>>> getNewsList(int typeId){
        final MutableLiveData<BaseMzBean<List<NewsItem>>> listData = new MutableLiveData<>();
        HttpClient.getMxnzpServer().getNews(typeId,pager)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseMzBean<List<NewsItem>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseMzBean<List<NewsItem>> newsItemBaseMzBean) {
                        listData.setValue(newsItemBaseMzBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listData.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return listData;
    }

    public MutableLiveData<BaseMzBean<NewsDetail>> getNewsInfo(String newId){
        final MutableLiveData<BaseMzBean<NewsDetail>> data = new MutableLiveData<>();
        HttpClient.getMxnzpServer().getNewsDetail(newId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseMzBean<NewsDetail>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseMzBean<NewsDetail> newsDetailBaseMzBean) {
                        data.setValue(newsDetailBaseMzBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        data.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return data;
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
