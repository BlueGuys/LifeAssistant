package com.hongyan.life.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Memo {

    @Id(autoincrement = true)
    private Long id;

    private long timestamp;

    private String content;

    @Generated(hash = 1987990789)
    public Memo(Long id, long timestamp, String content) {
        this.id = id;
        this.timestamp = timestamp;
        this.content = content;
    }

    @Generated(hash = 1901232184)
    public Memo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
