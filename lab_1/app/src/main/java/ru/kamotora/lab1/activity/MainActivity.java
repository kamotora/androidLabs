package ru.kamotora.lab1.activity;

import static ru.kamotora.lab1.common.Constants.MESSAGE_KEY;
import static ru.kamotora.lab1.common.Constants.MESSAGE_REQUEST_CODE;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Optional;

import ru.kamotora.lab1.R;
import ru.kamotora.lab1.common.Utils;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textViewMain);
        Button sendButton = findViewById(R.id.sendButtonMain);
        EditText editText = findViewById(R.id.editTextMain);

        editText.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND && Utils.isEditTextNotBlank(editText)) {
                sendText(editText.getText().toString());
                handled = true;
            }
            return handled;
        });

        editText.addTextChangedListener(new DisableButtonTextWatcher(editText, sendButton));

        sendButton.setOnClickListener(self -> {
            sendText(editText.getText().toString());
        });
    }

    private void sendText(String text) {
        Intent intent = new Intent(this, ChildActivity.class);
        intent.putExtra(MESSAGE_KEY, text);
        startActivityForResult(intent, MESSAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MESSAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String message = Optional.ofNullable(data)
                        .map(self -> self.getStringExtra(MESSAGE_KEY))
                        .orElse(getString(R.string.error_message_main, "child"));
                textView.setText(message);
            }
        }
    }
}
