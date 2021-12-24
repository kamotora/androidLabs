package ru.kamotora.lab4;

import android.app.Application;
import android.content.Context;

import ru.kamotora.lab4.core.PropertiesLoader;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PropertiesLoader.init(getApplicationContext());
    }
}