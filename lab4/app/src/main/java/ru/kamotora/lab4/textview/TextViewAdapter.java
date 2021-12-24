package ru.kamotora.lab4.textview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lombok.AllArgsConstructor;
import ru.kamotora.lab4.R;

@AllArgsConstructor
public class TextViewAdapter extends RecyclerView.Adapter<TextViewHolder> {
    private final List<String> _dataset;
    private final FragmentActivity _activity;

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_view_item, parent, false);
        return new TextViewHolder(view, _activity);
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewHolder holder, int i) {
        holder.view().setText(_dataset.get(i));
    }

    @Override
    public int getItemCount() {
        return _dataset.size();
    }
}
