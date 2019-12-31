package me.donlis.vreader.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import io.reactivex.disposables.Disposable;
import me.donlis.vreader.base.ObserverDelegate;

public class BaseViewModel extends AndroidViewModel {

    private ObserverDelegate delegate;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        delegate = new ObserverDelegate();
    }

    public void addDisposable(Disposable d){
        if(delegate == null){
            delegate = new ObserverDelegate();
        }
        delegate.addDisposable(d);
    }

    public void dispose(){
        if(delegate != null){
            delegate.detachObserver();
        }
    }

}
