package com.example.trabalhofinal.requests;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;

public class CustomCookieStore {

    private static final String TAG = "MyCookieStore";
    private final Map<String, List<Cookie>> cookiesMap = new HashMap<>();

    public void saveCookies(String host, List<Cookie> cookies) {
        cookiesMap.put(host, cookies);
        Log.d(TAG, "Cookies saved for host: " + host);
    }

    public List<Cookie> loadCookies(String host) {
        List<Cookie> cookies = cookiesMap.get(host);
        Log.d(TAG, "Cookies loaded for host: " + host);
        return cookies != null ? new ArrayList<>(cookies) : new ArrayList<>();
    }

    public void clearCookies() {
        cookiesMap.clear();
        Log.d(TAG, "Cookies cleared");
    }
}

