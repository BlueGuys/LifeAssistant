package com.hongyan.life.activity.translate;

import java.util.ArrayList;

public class TranslateBean {

    public int errorCode;
    public String query;
    public String[] translation;
    public Basic basic;
    public ArrayList<Web> web;

    class Basic {
        public String us_phonetic;
        public String phonetic;
        public String uk_phonetic;
        public String[] explains;
    }

    class Web {
        public String[] value;
        public String key;
    }
}
