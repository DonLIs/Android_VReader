package me.donlis.vreader.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.donlis.vreader.bean.BaseWanAndroidBean;
import me.donlis.vreader.bean.DataBean;
import me.donlis.vreader.http.HttpClient;

public class ProjectViewModel extends BaseViewModel {

    private final int DEFAULT_PAGER = 0;

    private int pager = DEFAULT_PAGER;

    private MutableLiveData<BaseWanAndroidBean<DataBean>> listData;

    public ProjectViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<BaseWanAndroidBean<DataBean>> getProjectList(){
        if(listData == null) {
            listData = new MutableLiveData<>();
        }
        return listData;
    }

    public void loadProjectList(){
        if(listData == null) {
            listData = new MutableLiveData<>();
        }
        HttpClient.getWanAndroidServer().getProjectList(pager)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseWanAndroidBean<DataBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BaseWanAndroidBean<DataBean> homeListBean) {
                        listData.setValue(homeListBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listData.setValue(null);
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
