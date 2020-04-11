package com.hongyan.life.activity.bill;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Record {

    @Id(autoincrement = true)
    private Long recordId;
    /**
     * 1.支出 2收入
     */
    public int type;

    /**
     * 金额
     */
    public float amount;

    /**
     * 时间
     */
    public long timeStap;

    /**
     * 种类(烟，酒等)
     */
    public int category;

    /**
     * 备注
     */
    public String remark;





    @Generated(hash = 1436135312)
    public Record(Long recordId, int type, float amount, long timeStap,
            int category, String remark) {
        this.recordId = recordId;
        this.type = type;
        this.amount = amount;
        this.timeStap = timeStap;
        this.category = category;
        this.remark = remark;
    }

    @Generated(hash = 477726293)
    public Record() {
    }


    public Long getRecordId() {
        return recordId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public long getTimeStap() {
        return timeStap;
    }

    public void setTimeStap(long timeStap) {
        this.timeStap = timeStap;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }
}
