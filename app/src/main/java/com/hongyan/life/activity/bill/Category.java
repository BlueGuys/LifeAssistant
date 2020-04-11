package com.hongyan.life.activity.bill;

import com.hongyan.life.R;

import java.util.ArrayList;

public class Category {

    /**
     * 餐饮
     */
    public static final int TYPE_0 = 0;
    /**
     * 餐饮
     */
    public static final int TYPE_1 = 1;
    /**
     * 餐饮
     */
    public static final int TYPE_2 = 2;
    /**
     * 餐饮
     */
    public static final int TYPE_3 = 3;
    /**
     * 餐饮
     */
    public static final int TYPE_4 = 4;
    /**
     * 餐饮
     */
    public static final int TYPE_5 = 5;
    /**
     * 餐饮
     */
    public static final int TYPE_6 = 6;

    public int type;
    public String desc;
    public int icon;

    public Category(int type, String desc, int icon) {
        this.type = type;
        this.desc = desc;
        this.icon = icon;
    }


    public static ArrayList<Category> getIncomeCategoryList() {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "工资", R.drawable.ic_launcher));
        categories.add(new Category(2, "股票", R.drawable.ic_launcher));
        categories.add(new Category(2, "股票", R.drawable.ic_launcher));
        categories.add(new Category(2, "股票", R.drawable.ic_launcher));
        categories.add(new Category(2, "股票", R.drawable.ic_launcher));
        categories.add(new Category(2, "股票", R.drawable.ic_launcher));
        categories.add(new Category(2, "股票", R.drawable.ic_launcher));
        return categories;
    }

    public static ArrayList<Category> getExpandCategoryList() {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "哈哈哈", R.drawable.ic_launcher));
        return categories;
    }

}
