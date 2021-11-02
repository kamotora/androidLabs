package ru.kamotora.lab3.service;

import static android.app.Activity.RESULT_OK;

import static ru.kamotora.lab3.Const.RESULT_ARGUMENTS_ERROR;
import static ru.kamotora.lab3.Const.RESULT_ALREADY_STARTED;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import lombok.SneakyThrows;
import ru.kamotora.lab3.Const;
import ru.kamotora.lab3.SimpleNumbersFounder;

public class SimpleService extends Service {

    final String LOG_TAG = getClass().getSimpleName();
    
    private CompletableFuture<Void> future;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public SimpleService() {
    }

    public static Intent getIntent(Context packageContext) {
        return new Intent(packageContext, SimpleService.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        work(intent);
        return Service.START_NOT_STICKY;
    }

    @SneakyThrows
    private void work(Intent intent) {
        int countDigits = intent.getIntExtra(Const.COUNT_DIGITS_PARAM, -1);
        int digitForSearch = intent.getIntExtra(Const.DIGIT_FOR_SEARCH_PARAM, -1);
        PendingIntent pendingIntent = intent.getParcelableExtra(Const.PENDING_INTENT_PARAM);
        if(future != null && !future.isDone()){
            Log.w(LOG_TAG, "Already started");
            pendingIntent.send(RESULT_ALREADY_STARTED);
            return;
        }
        if (countDigits == -1 || digitForSearch == -1 ) {
            pendingIntent.send(RESULT_ARGUMENTS_ERROR);
            return;
        }
        future = CompletableFuture
                .supplyAsync(SimpleNumbersFounder.init(countDigits, digitForSearch), executor)
                .thenAccept(result -> {
                    Log.d(LOG_TAG, "Callback work start");
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(Const.RESULT_PARAM, result);
                    try {
                        pendingIntent.send(SimpleService.this, RESULT_OK, resultIntent);
                        Log.d(LOG_TAG, "Callback sended result successfully");
                    } catch (PendingIntent.CanceledException e) {
                        Log.e("error", "Error to send result while pending intent", e);
                        throw new RuntimeException("SimpleService error");
                    }
                });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("debug", "SimpleService has been bound...");
//        work(intent, 2);
        return new SimpleServiceBinder(this);
    }

    public boolean onUnbind(Intent intent) {
        Log.d(LOG_TAG, "SimpleService onUnbind");
        return super.onUnbind(intent);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "SimpleService onDestroy");
    }
}