package ru.kamotora.lab3.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class SimpleServiceConnection implements ServiceConnection {
    private SimpleService simpleService;
    private boolean isMusicServiceBound;

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        SimpleServiceBinder musicBinder = (SimpleServiceBinder) service;
        simpleService = musicBinder.getSimpleService();
        isMusicServiceBound = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        isMusicServiceBound = false;
    }
}
