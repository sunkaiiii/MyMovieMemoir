package com.example.mymoviememoir.network.request.base;

import com.example.mymoviememoir.network.RestfulGetModel;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseGoogleRequest implements RestfulGetModel {
    private String api_key = "";

    @Override
    public Map<String, String> getQueryGetParameter() {
        Map<String, String> result = new HashMap<>();
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(this);
                if (value != null) {
                    result.put(field.getName(), value.toString());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("key", api_key);
        return result;
    }

}
