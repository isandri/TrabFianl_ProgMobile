package com.example.trabalhofinal.requests;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class PersistentCookieJar implements CookieJar {
    private static final String PREF_NAME = "CookiePrefs";
    private static final String PREF_KEY_COOKIES = "cookies";

    private final SharedPreferences sharedPreferences;
    private final Set<String> cookieCache = new HashSet<>();

    public PersistentCookieJar(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        cookieCache.addAll(sharedPreferences.getStringSet(PREF_KEY_COOKIES, new HashSet<>()));
    }

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            cookieCache.add(cookie.toString());
        }

        sharedPreferences.edit().putStringSet(PREF_KEY_COOKIES, cookieCache).apply();
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> validCookies = new ArrayList<>();

        for (String cookieString : cookieCache) {
            Cookie cookie = Cookie.parse(url, cookieString);
            if (cookie != null && cookie.matches(url)) {
                validCookies.add(cookie);
            }
        }

        return validCookies;
    }

    public synchronized void clearCookies() {
        sharedPreferences.edit().remove(PREF_KEY_COOKIES).apply();
    }

    private String cookieToString(Cookie cookie) {
        return cookie.toString();
    }

    private Cookie stringToCookie(String cookieString) {
        return Cookie.parse(HttpUrl.parse("https://dummyurl.com/"), cookieString);
    }
}
