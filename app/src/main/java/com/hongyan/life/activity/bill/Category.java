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
        incomeCategoryList.add(new Category(1, "工资", R.drawable.category_i_wage_normal));
        incomeCategoryList.add(new Category(2, "兼职", R.drawable.category_i_parttimework_normal));
        incomeCategoryList.add(new Category(3, "理财", R.drawable.category_i_finance_normal));
        incomeCategoryList.add(new Category(4, "礼金", R.drawable.category_e_gift_normal));
        incomeCategoryList.add(new Category(5, "其他", R.drawable.category_i_other_normal));

        expandCategoryList.add(new Category(6, "餐饮", R.drawable.category_e_catering_normal));
        expandCategoryList.add(new Category(7, "购物", R.drawable.category_e_shopping_normal));
        expandCategoryList.add(new Category(8, "交通", R.drawable.category_e_traffic_normal));
        expandCategoryList.add(new Category(9, "运动", R.drawable.category_e_sport_normal));
        expandCategoryList.add(new Category(10, "服饰", R.drawable.category_e_dress_normal));
        expandCategoryList.add(new Category(11, "美容", R.drawable.category_e_beauty_normal));
        expandCategoryList.add(new Category(12, "住房", R.drawable.category_e_house_normal));
        expandCategoryList.add(new Category(13, "汽车", R.drawable.category_e_car_normal));
        expandCategoryList.add(new Category(14, "烟酒", R.drawable.category_e_smoke_normal));
        expandCategoryList.add(new Category(15, "数码", R.drawable.category_e_digital_normal));
        expandCategoryList.add(new Category(16, "学习", R.drawable.category_e_study_normal));
        expandCategoryList.add(new Category(17, "娱乐", R.drawable.category_e_entertainmente_normal));
        expandCategoryList.add(new Category(18, "礼金", R.drawable.category_e_gift_normal));
        expandCategoryList.add(new Category(19, "孩子", R.drawable.category_e_child_normal));
        expandCategoryList.add(new Category(20, "通讯", R.drawable.category_e_communicate_normal));
        expandCategoryList.add(new Category(21, "维修", R.drawable.category_e_repair_normal));
        expandCategoryList.add(new Category(22, "维修", R.drawable.category_i_other_normal));
    }

    public static ArrayList<Category> getIncomeCategoryList() {
        return incomeCategoryList;
    }

    public static ArrayList<Category> getExpandCategoryList() {
        return expandCategoryList;
    }

    public static int getIconById(int id) {
        for (Category c1 : incomeCategoryList) {
            if (c1.type == id) {
                return c1.icon;
            }
        }
        for (Category c2 : incomeCategoryList) {
            if (c2.type == id) {
                return c2.icon;
            }
        }
        return R.drawable.category_i_other_normal;
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
