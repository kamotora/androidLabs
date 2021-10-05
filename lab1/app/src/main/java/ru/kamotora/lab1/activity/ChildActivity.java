package ru.kamotora.lab1.activity;

import static ru.kamotora.lab1.common.Constants.MESSAGE_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.kamotora.lab1.R;
import ru.kamotora.lab1.common.EditTextUtils;

public class ChildActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Child Activity");
        setContentView(R.layout.activity_child);
        // getting message
        String messageFromMaster = getIntent().getStringExtra(MESSAGE_KEY);
        TextView textView = findViewById(R.id.textViewChild);
        textView.setText(messageFromMaster);
        // setup
        Button sendButton = findViewById(R.id.sendButtonChild);
        EditText editText = findViewById(R.id.editTextChild);
        editText.addTextChangedListener(new DisableButtonTextWatcher(editText, sendButton));

        editText.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND && EditTextUtils.isEditTextNotBlank(editText)) {
                sendText(editText.getText().toString());
                handled = true;
            }
            return handled;
        });

        sendButton.setOnClickListener(self -> {
            sendText(EditTextUtils.getTextOrThrow(editText));
        });
    }

    private void sendText(String text) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MESSAGE_KEY, text);
        setResult(RESULT_OK, intent);
        finish();
    }
}
