package me.donlis.common.app;

import androidx.multidex.MultiDexApplication;
import me.donlis.common.exception.ExceptionCatch;
import me.donlis.common.util.Utils;

public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);

        ExceptionCatch.getInstance().init(this);
        ExceptionCatch.getInstance().setCatchInfo();
    }

}
