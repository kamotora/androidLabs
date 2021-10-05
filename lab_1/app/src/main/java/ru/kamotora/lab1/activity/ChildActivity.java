package ru.kamotora.lab1.activity;

import static ru.kamotora.lab1.common.Constants.MESSAGE_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.kamotora.lab1.R;

public class ChildActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        // getting message
        String messageFromMaster = getIntent().getStringExtra(MESSAGE_KEY);
        TextView textView = findViewById(R.id.textViewChild);
        textView.setText(messageFromMaster);
        // setup
        Button sendButton = findViewById(R.id.sendButtonChild);
        EditText editText = findViewById(R.id.editTextChild);
        editText.addTextChangedListener(new DisableButtonTextWatcher(editText, sendButton));
        sendButton.setOnClickListener(self -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MESSAGE_KEY, editText.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
