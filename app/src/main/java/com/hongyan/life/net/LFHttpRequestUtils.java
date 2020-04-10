package com.hongyan.life.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Copyright (c) 2017-2019 LINKFACE Corporation. All rights reserved.
 */

public class LFHttpRequestUtils {

    private static String charset = "UTF-8";

    private static String HOST = "https://cloudapi.linkface.cn";

    public static final String TAG = LFHttpRequestUtils.class.getSimpleName();
    public static final ExecutorService executorService = Executors
            .newFixedThreadPool(5);

    public static void postDecodeData(String sBaseUrl, String appId, String appSecret, byte[] file,
                                      LFNetworkCallback callback) {

        Map<String, Object> parameterList = new LinkedHashMap<>();
        parameterList.put("api_id", appId);
        parameterList.put("api_secret", appSecret);
        parameterList.put("file", file);

        postSyn(sBaseUrl, parameterList, callback);
    }

    public static void postSyn(String url, Map<String, Object> parameterList, LFNetworkCallback callback) {
        postDecodeSyn(url, parameterList, callback);
    }

    private static void postDecodeSyn(final String urlPath, final Map<String, Object> parameterMap, final LFNetworkCallback callback) {
        if (urlPath == null || "".equals(urlPath)) {
            sendFailResult(callback, 404, "URL无效");
            return;
        }

        final String BOUNDARY = java.util.UUID.randomUUID().toString();
        final String PREFIX = "--", LINEND = "\r\n";
        final String MULTIPART_FROM_DATA = "multipart/form-data";
        final String CHARSET = "UTF-8";
        final byte[][] fileByte = {null};
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlPath);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();


                    StringBuffer sb = new StringBuffer();
                    if (parameterMap != null && !parameterMap.isEmpty()) {
                        for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                            sb.append(PREFIX);
                            sb.append(BOUNDARY);
                            sb.append(LINEND);

                            if (null == entry.getValue()) {
                                sb.append(URLEncoder.encode("", charset));
                            } else {
                                if (entry.getValue() instanceof String || (entry.getValue() instanceof Integer)) {
                                    sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
                                    sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
                                    sb.append("Content-Length: " + entry.getValue().toString().length() + LINEND);
                                    sb.append(LINEND);
                                    sb.append(entry.getValue());
                                    sb.append(LINEND);
                                } else if (entry.getValue() instanceof byte[]) {
                                    fileByte[0] = (byte[]) entry.getValue();
                                    sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + entry.getKey() + "\"" + LINEND);
                                    sb.append("Content-Type: application/octet-stream" + LINEND);
                                    sb.append("Content-Length: " + fileByte[0].length + LINEND);
                                    sb.append(LINEND);
                                }
                            }
                        }
                    }
                    final byte[] data = sb.toString().getBytes();

                    conn.setConnectTimeout(10000);
                    conn.setReadTimeout(10000);
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

                    OutputStream outStream = conn.getOutputStream();
                    outStream.write(data);
                    outStream.write(fileByte[0]);
                    outStream.write(LINEND.getBytes());
                    // 请求结束标志
                    byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
                    outStream.write(end_data);
                    outStream.flush();
                    dealRequestResponse(conn, callback);
                } catch (Exception e) {
                    if (e instanceof SocketTimeoutException) {
                        sendFailResult(callback, -1, "request timeout");
                    } else {
                        sendFailResult(callback, 404, "request error");
                    }
                }
            }
        });

    }


    public static void getRequest(String getUrl, LFNetworkCallback callback) {
        getSyn(getUrl, callback);
    }

    public static void getSyn(final String urlPath, final LFNetworkCallback callback) {
        if (urlPath == null || "".equals(urlPath)) {
            sendFailResult(callback, 404, "URL invalid ");
            return;
        }

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlPath);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    dealRequestResponse(conn, callback);
                } catch (Exception e) {
                    sendFailResult(callback, 404, "request error");
                }
            }
        });

    }


    private static void dealRequestResponse(HttpURLConnection conn, final LFNetworkCallback callback) throws IOException {
        InputStream is = null;
        BufferedReader reader = null;
        try {
            if (conn != null) {
                int code = conn.getResponseCode();
                if (code == 200) {
                    is = conn.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(is));
                    StringBuffer sb = new StringBuffer();
                    char[] buf = new char[1024];
                    int len = 0;
                    while ((len = reader.read(buf)) != -1) {
                        sb.append(new String(buf, 0, len));
                    }
                    String result = sb.toString();
                    if (result != null) {
                        sendSuccessResult(callback, result);
                    } else {
                        sendFailResult(callback, 0, "");
                    }
                } else {
                    String result = conn.getResponseMessage();
                    sendFailResult(callback, code, result);
                }
            } else {
                sendFailResult(callback, 0, "");
            }
        } finally {
            if (is != null) {
                is.close();
            }

            if (reader != null) {
                reader.close();
            }
        }
    }

    public static <T> void sendFailResult(final LFNetworkCallback callback, final int errorCode, final String errorString) {
        if (callback != null) {
            callback.failed(errorCode, errorString);
        }
    }

    public static void sendSuccessResult(final LFNetworkCallback callback, final String response) {

        if (callback != null) {
            callback.completed(response);
        }
    }

    public static String getHost() {
        return HOST;
    }

    public static void setHost(String host){
        HOST = host;
    }
}
