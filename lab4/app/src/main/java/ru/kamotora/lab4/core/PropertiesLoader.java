package ru.kamotora.lab4.core;


import android.content.Context;

import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

import lombok.SneakyThrows;
import lombok.val;

public class PropertiesLoader {
    private static Properties properties;

    @SneakyThrows
    public static void init(Context context) {
        if (properties == null) {
            properties = new Properties();
            properties.load(context.getAssets().open("application.properties"));
        }
    }

    public static String getProperty(String key) {
        if (properties == null)
            throw new NullPointerException(PropertiesLoader.class.getSimpleName() + " is not initialized");
        val value = properties.getProperty(key);
        if (StringUtils.isBlank(value))
            throw new NullPointerException("Not found property " + value);
        return value;
    }
}
