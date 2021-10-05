package ru.kamotora.lab1.common;

import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {
    public boolean isEditTextNotBlank(EditText editText) {
        return StringUtils.isNotBlank(getTextOrNull(editText));
    }

    @Nullable
    public String getTextOrNull(EditText editText){
        return Optional.ofNullable(editText.getText())
                .map(Object::toString)
                .orElse(null);
    }

}
