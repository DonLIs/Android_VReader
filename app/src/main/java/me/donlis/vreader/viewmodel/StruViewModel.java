package me.donlis.vreader.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.donlis.vreader.bean.BaseWanAndroidBean;
import me.donlis.vreader.bean.TreeBean;
import me.donlis.vreader.http.HttpClient;

public class StruViewModel extends BaseViewModel {

    private MutableLiveData<BaseWanAndroidBean<List<TreeBean>>> listData;

    public StruViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<BaseWanAndroidBean<List<TreeBean>>> getTree(){
        if(listData == null) {
            listData = new MutableLiveData<>();
        }
        return listData;
    }

    public void loadTree(){
        if(listData == null) {
            listData = new MutableLiveData<>();
        }
        HttpClient.getWanAndroidServer().getTree()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseWanAndroidBean<List<TreeBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BaseWanAndroidBean<List<TreeBean>> listBaseWanAndroidBean) {
                        listData.setValue(listBaseWanAndroidBean);
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

}
