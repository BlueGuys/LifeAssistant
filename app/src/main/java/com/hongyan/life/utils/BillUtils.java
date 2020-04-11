package com.hongyan.life.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.hongyan.life.MyApplication;
import com.hongyan.life.activity.bill.Category;
import com.hongyan.life.activity.bill.DaoSession;
import com.hongyan.life.activity.bill.Record;
import com.hongyan.life.activity.bill.RecordDao;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * 账单数据类
 */
public class BillUtils {


    public static ArrayList<Category> categoryList = new ArrayList<>();


    public static void init() {
        categoryList.add(new Category(Category.TYPE_0, "餐饮"));
        categoryList.add(new Category(Category.TYPE_0, "烟酒"));
    }


    /**
     * 添加一条记录
     */
    public static long addRecord(Record record) {
        DaoSession daoSession = MyApplication.getDaoSession();
        RecordDao recordDao = daoSession.getRecordDao();
        long l = recordDao.insert(record);
        return l;
    }


    /**
     * 添加或者修改
     */
    public static long insertOrReplace(Record record) {
        DaoSession daoSession = MyApplication.getDaoSession();
        RecordDao recordDao = daoSession.getRecordDao();
        long l = recordDao.insertOrReplace(record);
        return l;
    }


    public static boolean delRecord(long ID) {

        try {
            DaoSession daoSession = MyApplication.getDaoSession();
            RecordDao recordDao = daoSession.getRecordDao();
            recordDao.deleteByKey(ID);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 列表 1.收入 2.支出
     */
    public static List<Record> getRecordList(int type) {
        RecordDao recordDao = MyApplication.getDaoSession().getRecordDao();
        List<Record> list = recordDao.queryBuilder().where(RecordDao.Properties.Type.eq(type)).list();
        return list;
    }

    /**
     * TODO 本月
     * @return
     */
    public static List<Record> getRecordListMonth(int type){
        RecordDao recordDao = MyApplication.getDaoSession().getRecordDao();
        List<Record> list = recordDao.queryBuilder().where(RecordDao.Properties.Type.eq(type)).list();
        return null;
    }


    public static float getBalance() {
        RecordDao recordDao = MyApplication.getDaoSession().getRecordDao();
        List<Record> records = recordDao.loadAll();
        float sum = 0;
        for (Record r : records) {
            if (r.getType()>0){
                if (r.getType()== 1){
                    sum+=r.amount;
                }else {
                    sum-=r.amount;
                }
            }
        }
        return sum;
    }






}
