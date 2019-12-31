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
import me.donlis.vreader.bean.NavBean;
import me.donlis.vreader.http.HttpClient;

public class NavigateViewModel extends BaseViewModel {

    public NavigateViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<BaseWanAndroidBean<List<NavBean>>> getNav(){
        final MutableLiveData<BaseWanAndroidBean<List<NavBean>>> listData = new MutableLiveData<>();

        HttpClient.getWanAndroidServer().getNavi()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseWanAndroidBean<List<NavBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BaseWanAndroidBean<List<NavBean>> listBaseWanAndroidBean) {
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
        return listData;
    }

}
