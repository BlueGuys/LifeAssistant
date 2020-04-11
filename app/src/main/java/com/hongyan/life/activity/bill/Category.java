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

    private static ArrayList<Category> incomeCategoryList = new ArrayList<>();
    private static ArrayList<Category> expandCategoryList = new ArrayList<>();

    public static void init() {
        incomeCategoryList.add(new Category(1, "工资", R.drawable.ic_launcher));
        incomeCategoryList.add(new Category(2, "股票", R.drawable.ic_launcher));
        incomeCategoryList.add(new Category(2, "股票", R.drawable.ic_launcher));
        expandCategoryList.add(new Category(2, "股票", R.drawable.ic_launcher));
        expandCategoryList.add(new Category(2, "股票", R.drawable.ic_launcher));
        expandCategoryList.add(new Category(2, "股票", R.drawable.ic_launcher));
    }

    public static ArrayList<Category> getIncomeCategoryList() {
        return incomeCategoryList;
    }

    public static ArrayList<Category> getExpandCategoryList() {
        return expandCategoryList;
    }

    public static String getDescById(int id) {
        for (Category c1 : incomeCategoryList) {
            if (c1.type == id) {
                return c1.desc;
            }
        }
        for (Category c2 : incomeCategoryList) {
            if (c2.type == id) {
                return c2.desc;
            }
        }
        return "";
    }

}
