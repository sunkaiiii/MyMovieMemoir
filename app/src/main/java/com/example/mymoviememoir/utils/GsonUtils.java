package com.example.mymoviememoir.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public final class GsonUtils {
    public static final Gson Instance = new Gson();

    public static <T> T fromJsonToObject(String s, Class<T> clazz) {
        return Instance.fromJson(s, clazz);
    }

    public static <T> List<T> fromJsonToList(String s, Class<T> clazz) {
        return Instance.fromJson(s, new ParameterTypeImplement(clazz));
    }

    public static String toJson(Object object) {
        return Instance.toJson(object);
    }

    private static class ParameterTypeImplement implements ParameterizedType {
        private Class clazz;

        public ParameterTypeImplement(Class clazz) {
            this.clazz = clazz;
        }

        @NonNull
        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @NonNull
        @Override
        public Type getRawType() {
            return List.class;
        }

        @Nullable
        @Override
        public Type getOwnerType() {
            return clazz.getClass();
        }
    }
}
