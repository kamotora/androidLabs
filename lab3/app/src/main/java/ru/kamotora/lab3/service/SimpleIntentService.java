package ru.kamotora.lab3.service;

import static android.app.Activity.RESULT_OK;
import static ru.kamotora.lab3.Const.RESULT_ARGUMENTS_ERROR;
import static ru.kamotora.lab3.Const.RESULT_ALREADY_STARTED;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import lombok.SneakyThrows;
import ru.kamotora.lab3.Const;
import ru.kamotora.lab3.SimpleNumbersFounder;


public class SimpleIntentService extends IntentService {

    final String LOG_TAG = getClass().getSimpleName();
    private boolean isWorking = false;

    public SimpleIntentService() {
        super("intent Service");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SimpleIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        PendingIntent pendingIntent = intent.getParcelableExtra(Const.PENDING_INTENT_PARAM);
        wrapper(pendingIntent, () -> {
            int countDigits = intent.getIntExtra(Const.COUNT_DIGITS_PARAM, -1);
            int digitForSearch = intent.getIntExtra(Const.DIGIT_FOR_SEARCH_PARAM, -1);
            if (countDigits == -1 || digitForSearch == -1) {
                try {
                    pendingIntent.send(RESULT_ARGUMENTS_ERROR);
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
                return;
            }
            Long result = SimpleNumbersFounder.init(countDigits, digitForSearch).get();
            Log.d(LOG_TAG, "Intent service ended work");
            Intent resultIntent = new Intent();
            resultIntent.putExtra(Const.RESULT_PARAM, result);
            try {
                pendingIntent.send(this, RESULT_OK, resultIntent);
                Log.d(LOG_TAG, "Intent service send result successfully");
            } catch (PendingIntent.CanceledException e) {
                Log.e("error", "Error to send result while pending intent", e);
                throw new RuntimeException("SimpleIntentService error");
            }
        });
    }


    @SneakyThrows
    private void wrapper(PendingIntent intent, Runnable runnable) {
        if (isWorking)
            intent.send(RESULT_ALREADY_STARTED);
        try {
            isWorking = true;
            runnable.run();
            isWorking = false;
        } catch (Exception e) {
            isWorking = false;
            Log.e("error", "Error to run work in intent service", e);
        }
    }
}