package ru.kamotora.lab3;

import static ru.kamotora.lab3.Const.COUNT_DIGITS_PARAM;
import static ru.kamotora.lab3.Const.DIGIT_FOR_SEARCH_PARAM;
import static ru.kamotora.lab3.Const.PENDING_INTENT_PARAM;
import static ru.kamotora.lab3.Const.RESULT_ALREADY_STARTED;
import static ru.kamotora.lab3.Const.RESULT_ARGUMENTS_ERROR;
import static ru.kamotora.lab3.Const.RESULT_PARAM;
import static ru.kamotora.lab3.Const.SIMPLE_INTENT_REQUEST_CODE;
import static ru.kamotora.lab3.Const.SIMPLE_SERVICE_CONNECTION_REQUEST_CODE;
import static ru.kamotora.lab3.Const.SIMPLE_SERVICE_REQUEST_CODE;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Optional;

import ru.kamotora.lab3.service.SimpleIntentService;
import ru.kamotora.lab3.service.SimpleService;
import ru.kamotora.lab3.service.SimpleServiceConnection;

public class MainActivity extends AppCompatActivity {

    private final SimpleServiceConnection serviceConnection = new SimpleServiceConnection();
    private Intent bindServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindServiceIntent = new Intent(this, SimpleService.class);
        findViewById(R.id.searchOnesButton)
                .setOnClickListener((l) -> {
                    PendingIntent pendingResult = createPendingResult(SIMPLE_SERVICE_REQUEST_CODE, new Intent(), 0);

                    startService(new Intent(this, SimpleService.class)
                            .putExtra(COUNT_DIGITS_PARAM, parseParam())
                            .putExtra(DIGIT_FOR_SEARCH_PARAM, 1)
                            .putExtra(PENDING_INTENT_PARAM, pendingResult));

                    ((TextView) findViewById(R.id.onesServiceResult))
                            .setText(R.string.startWorking);
                });
        findViewById(R.id.searchTwosButton)
                .setOnClickListener((l) -> {
                    PendingIntent pendingResult = createPendingResult(SIMPLE_SERVICE_CONNECTION_REQUEST_CODE, new Intent(), 0);

                    startService(bindServiceIntent
                            .putExtra(COUNT_DIGITS_PARAM, parseParam())
                            .putExtra(DIGIT_FOR_SEARCH_PARAM, 2)
                            .putExtra(PENDING_INTENT_PARAM, pendingResult));

                    ((TextView) findViewById(R.id.twoServiceResult))
                            .setText(R.string.startWorking);
                });
        findViewById(R.id.searchThreesButton)
                .setOnClickListener((l) -> {
                    PendingIntent pendingResult = createPendingResult(SIMPLE_INTENT_REQUEST_CODE, new Intent(), 0);

                    startService(new Intent(this, SimpleIntentService.class)
                            .putExtra(COUNT_DIGITS_PARAM, parseParam())
                            .putExtra(DIGIT_FOR_SEARCH_PARAM, 3)
                            .putExtra(PENDING_INTENT_PARAM, pendingResult));

                    ((TextView) findViewById(R.id.threeServiceResult))
                            .setText(R.string.startWorking);
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(bindServiceIntent, serviceConnection, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
    }

    private int parseParam() {
        EditText editText = findViewById(R.id.editTextNumber);
        Editable text = editText.getText();
        try {
            int result = Integer.parseInt(text.toString());
            if (result < 0)
                throw new RuntimeException("error"); //todo show error
            return result;
        } catch (Exception e) {
            throw new RuntimeException("error");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIMPLE_SERVICE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                setResult(data, findViewById(R.id.onesServiceResult));
            }
            if (resultCode == RESULT_ALREADY_STARTED) {
                ((TextView) findViewById(R.id.onesServiceResult))
                        .setText(R.string.alreadyStarted);
            }
            if (resultCode == RESULT_ARGUMENTS_ERROR) {
                ((TextView) findViewById(R.id.onesServiceResult))
                        .setText(R.string.argumentError);
            }
        }
        if (requestCode == SIMPLE_SERVICE_CONNECTION_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                setResult(data, findViewById(R.id.twoServiceResult));
            }
            if (resultCode == RESULT_ALREADY_STARTED) {
                ((TextView) findViewById(R.id.twoServiceResult))
                        .setText(R.string.alreadyStarted);
            }
            if (resultCode == RESULT_ARGUMENTS_ERROR) {
                ((TextView) findViewById(R.id.twoServiceResult))
                        .setText(R.string.argumentError);
            }
        }
        if (requestCode == SIMPLE_INTENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                setResult(data, findViewById(R.id.threeServiceResult));
            }
            if (resultCode == RESULT_ALREADY_STARTED) {
                ((TextView) findViewById(R.id.threeServiceResult))
                        .setText(R.string.alreadyStarted);
            }
            if (resultCode == RESULT_ARGUMENTS_ERROR) {
                ((TextView) findViewById(R.id.threeServiceResult))
                        .setText(R.string.argumentError);
            }
        }
    }

    private void setResult(Intent data, TextView textView) {
        String result = Optional.ofNullable(data)
                .map(self -> self.getLongExtra(RESULT_PARAM, -2))
                .filter(num -> num != -2)
                .map(num -> Long.toString(num))
                .orElse(getString(R.string.defaultResult));
        Log.d(getClass().getSimpleName(), "Activity received " + result);
        textView.setText(result);
    }
}