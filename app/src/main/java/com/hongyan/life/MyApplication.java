package com.hongyan.life;

import android.app.Application;

import com.hongyan.life.activity.bill.DaoMaster;
import com.hongyan.life.activity.bill.DaoSession;

import org.greenrobot.greendao.database.Database;

public class MyApplication extends Application {

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initDb();
    }

    private void initDb() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "life");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }


    public static DaoSession getDaoSession() {
        return daoSession;
    }


}
