package me.donlis.vreader.app;

import org.greenrobot.greendao.database.Database;

import me.donlis.common.app.BaseApplication;
import me.donlis.common.util.ShareUtils;
import me.donlis.vreader.database.greenDao.db.DaoMaster;
import me.donlis.vreader.database.greenDao.db.DaoSession;

public class App extends BaseApplication {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        ShareUtils.init(this);
        initDataBase();
    }

    /**
     * 初始化数据库
     */
    private void initDataBase(){
        //初始化DaoMaster，name 为数据库的名字带后缀名
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "vreader.db");
        //使用数据库加密，参数：数据库密码
        //不使用数据加密可以使用getWritableDb()方法
        Database db = openHelper.getEncryptedWritableDb("123456");
        DaoMaster daoMaster = new DaoMaster(db);
        //获取daoSession
        daoSession = daoMaster.newSession();
    }

    /**
     * 提供获取DaoSession的方法
     * @return
     */
    public DaoSession getDaoSession() {
        if(daoSession == null){
            initDataBase();
        }
        return daoSession;
    }
}
