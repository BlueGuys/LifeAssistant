package com.hongyan.life.utils;

import com.hongyan.life.activity.bill.Category;
import com.hongyan.life.activity.bill.Record;

import java.util.ArrayList;

/**
 * 账单数据类
 */
public class BillUtils {


    public static ArrayList<Category> categoryList = new ArrayList<>();


    public static void init(){
        categoryList.add(new Category(Category.TYPE_0, "餐饮"));
        categoryList.add(new Category(Category.TYPE_0, "烟酒"));
    }

    /**
     * 添加一条记录
     */
    public static void addRecord(Record record){

    }

    public static boolean delRecord(String ID){
        return true;
    }

    /**
     * 列表 1.收入 2.支出
     */
    public static ArrayList<Record> getRecordList(int type){
        ArrayList<Record> records = new ArrayList<>();
        records.add(new Record());
        records.add(new Record());
        records.add(new Record());
        records.add(new Record());
        return records;
    }

}
