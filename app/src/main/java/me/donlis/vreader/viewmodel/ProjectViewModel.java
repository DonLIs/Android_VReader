package me.donlis.vreader.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.donlis.vreader.bean.HomeListBean;
import me.donlis.vreader.http.HttpClient;

public class ProjectViewModel extends AndroidViewModel {

    private final int DEFAULT_PAGER = 0;

    private int pager = DEFAULT_PAGER;

    public ProjectViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<HomeListBean> getProjectList(){
        final MutableLiveData<HomeListBean> listData = new MutableLiveData<>();
        HttpClient.getWanAndroidServer().getProjectList(pager)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeListBean homeListBean) {
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
        return listData;
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
