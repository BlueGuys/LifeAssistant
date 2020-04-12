package com.hongyan.life.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.hongyan.life.MyApplication;
import com.hongyan.life.activity.bill.Category;
import com.hongyan.life.activity.bill.DaoSession;
import com.hongyan.life.activity.bill.Record;
import com.hongyan.life.activity.bill.RecordDao;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * 账单数据类
 */
public class BillUtils {

    private static final String TAG = "BillUtils";

    public static SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyy-MM");

    public static ArrayList<Category> categoryList = new ArrayList<>();


    public static void init() {
//        categoryList.add(new Category(Category.TYPE_0, "餐饮"));
//        categoryList.add(new Category(Category.TYPE_0, "烟酒"));
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
     * @return
     */
    public static List<Record> getRecordListMonth(int type) {

        long startTime = DateUtil.getBeginDayOfMonth().getTime();
        long endTime = DateUtil.getEndDayOfMonth().getTime();
        Log.d(TAG, startTime + "--" + endTime);
        RecordDao recordDao = MyApplication.getDaoSession().getRecordDao();
        List<Record> list = recordDao.queryBuilder().
                where(RecordDao.Properties.Type.eq(type), RecordDao.Properties.TimeStap.between(startTime, endTime))
                .list();
        return list;
    }

    /**
     * @return
     */
    public static List<Record> getRecordListMonth(int type, String month) throws ParseException {

        Date parse = yyyyMM.parse(month);
        long startTime = DateUtil.getDayStartTime(parse).getTime();
        Date nextMonthDate = DateUtil.getNextMonthDate(parse);
        long endTime = DateUtil.getDayStartTime(nextMonthDate).getTime();
        RecordDao recordDao = MyApplication.getDaoSession().getRecordDao();
        List<Record> list = recordDao.queryBuilder().
                where(RecordDao.Properties.Type.eq(type), RecordDao.Properties.TimeStap.between(startTime, endTime))
                .list();
        return list;
    }

    public static float getMonthRecord(int type) {
        float sum = 0;
        List<Record> recordListMonth = getRecordListMonth(type);
        for (Record r : recordListMonth) {
            sum += r.getAmount();
        }
        return sum;
    }



    public static float getMonthRecord(int type,String month) throws ParseException {
        float sum = 0;
        List<Record> recordListMonth = getRecordListMonth(type,month);
        for (Record r : recordListMonth) {
            sum += r.getAmount();
        }
        return sum;
    }

    public static List<Record> getRecordListWeek(int type) {
        long startTime = DateUtil.getBeginDayOfWeek().getTime();
        long endTime = DateUtil.getEndDayOfWeek().getTime();
        Log.d(TAG, startTime + "--" + endTime);

        RecordDao recordDao = MyApplication.getDaoSession().getRecordDao();
        List<Record> list = recordDao.queryBuilder().
                where(RecordDao.Properties.Type.eq(type), RecordDao.Properties.TimeStap.between(startTime, endTime))
                .list();
        return list;
    }

    public static float getWeekRecord(int type) {
        float sum = 0;
        List<Record> recordListMonth = getRecordListWeek(type);
        for (Record r : recordListMonth) {
            sum += r.getAmount();
        }
        return sum;
    }


    public static float getBalance() {
        RecordDao recordDao = MyApplication.getDaoSession().getRecordDao();
        List<Record> records = recordDao.loadAll();
        float sum = 0;
        for (Record r : records) {
            if (r.getType() > 0) {
                if (r.getType() == 1) {
                    sum += r.amount;
                } else {
                    sum -= r.amount;
                }
            }
        }
        return sum;
    }


    public static List<Record> getAll() {
        List<Record> records = MyApplication.getDaoSession().getRecordDao().loadAll();
        return records;
    }

    public static LinkedHashMap<Integer, Float> getMonthInfo(int type, String month) throws ParseException {
        Date parse = yyyyMM.parse(month);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cal.setTime(parse);
        int maximum = cal.getMaximum(Calendar.DAY_OF_MONTH);
        LinkedHashMap<Integer, Float> map = new LinkedHashMap<>();
        for (int i = 1; i < maximum + 1; i++) {
            map.put(i, 0f);
        }
        List<Record> recordListMonth = getRecordListMonth(type, month);
        for (Record r : recordListMonth) {
            int dayofMonth = r.getDayofMonth();
            map.put(dayofMonth , map.get(dayofMonth)+r.getAmount() );
        }
        return map;
    }

}
