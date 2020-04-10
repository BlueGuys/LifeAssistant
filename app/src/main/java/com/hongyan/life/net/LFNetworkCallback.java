package com.hongyan.life.net;


/**
 * Copyright (c) 2017-2019 LINKFACE Corporation. All rights reserved.
 */

public abstract class LFNetworkCallback {
    private String TAG = LFNetworkCallback.class.getSimpleName();

    public abstract void completed(String response);

    /**
     * @param httpStatusCode
     * @param error
     */
    public void failed(int httpStatusCode, String error) {
        String result = "Code" + httpStatusCode + ";error:" + error;
    }
}