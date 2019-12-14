package com.ninhhk.aoremote;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {
    private JSONObject jsonObject;

    public JSONParser(String jsonStr) {
        try {
            jsonObject = new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String parseCode() {
        return jsonObject.optString("code", "FFFF");
    }
}
