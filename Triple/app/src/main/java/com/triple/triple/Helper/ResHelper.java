package com.triple.triple.Helper;

import java.lang.reflect.Field;

public class ResHelper {
    public static int getId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
