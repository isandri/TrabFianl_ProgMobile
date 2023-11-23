package com.example.trabalhofinal.interfaces;

import org.json.JSONObject;

public interface OnJsonResponseListener {
    void onSuccess(JSONObject jsonObject);
    void onFailure(String errorMessage);
}
