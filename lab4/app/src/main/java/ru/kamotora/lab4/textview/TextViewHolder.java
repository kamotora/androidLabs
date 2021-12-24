package ru.kamotora.lab4.textview;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.kamotora.lab4.DogFragment;
import ru.kamotora.lab4.R;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Accessors(fluent = true, prefix = "_")
public class TextViewHolder extends RecyclerView.ViewHolder {
    TextView _view;

    public TextViewHolder(@NonNull View itemView, @NonNull final FragmentActivity activity) {
        super(itemView);
        _view = itemView.findViewById(R.id.textView);
        _view.setOnClickListener(self -> {
            Log.i(getClass().getSimpleName(), "Item has been clicked...");
            new DogFragment(_view.getText().toString()).show(activity.getSupportFragmentManager(), null);
        });
    }
}

