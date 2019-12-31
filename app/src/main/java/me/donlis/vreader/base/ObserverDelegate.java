package me.donlis.vreader.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ObserverDelegate {

    private CompositeDisposable mCompositeDisposable;

    public void addDisposable(Disposable disposable){
        if(disposable == null){
            return;
        }
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void detachObserver(){
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }

}
