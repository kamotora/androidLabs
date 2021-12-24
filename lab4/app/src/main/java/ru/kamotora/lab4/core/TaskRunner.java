package ru.kamotora.lab4.core;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskRunner {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public interface Callback<R> {
        void onComplete(R result);
    }

    public interface ExceptionHandler {
        void handle(Exception e);
    }

    public <R> void executeAsync(Callable<R> callable, Callback<R> callback) {
        executeAsync(callable, callback, Throwable::printStackTrace);
    }

    public <R> void executeAsync(Callable<R> callable, Callback<R> callback, ExceptionHandler exceptionHandler) {
        executor.execute(() -> {
            try {
                final R result = callable.call();
                handler.post(() -> callback.onComplete(result));
            } catch (Exception e) {
                exceptionHandler.handle(e);
            }
        });
    }
}
