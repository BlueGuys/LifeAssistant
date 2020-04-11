package com.hongyan.life.activity.bill;

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

    public Category(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
