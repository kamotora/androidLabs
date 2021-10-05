package ru.kamotora.lab1.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import lombok.RequiredArgsConstructor;
import ru.kamotora.lab1.common.Utils;

@RequiredArgsConstructor
public class DisableButtonTextWatcher implements TextWatcher {

    private final EditText editText;
    private final Button button;

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        button.setEnabled(Utils.isEditTextNotBlank(editText));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Auto-generated method stub
    }

    @Override
    public void afterTextChanged(Editable s) {
        // Auto-generated method stub
    }
}
