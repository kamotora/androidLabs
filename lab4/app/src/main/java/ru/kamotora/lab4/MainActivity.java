package ru.kamotora.lab4;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.kamotora.lab4.api.DogConnector;
import ru.kamotora.lab4.core.TaskRunner;
import ru.kamotora.lab4.textview.TextViewAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.breedsLoadingProgressBar).setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onStart() {
        super.onStart();
        new TaskRunner().executeAsync(() -> DogConnector.getInstance().getBreeds(),
                (breeds) -> runOnUiThread(() -> {
                    recyclerView.setAdapter(new TextViewAdapter(breeds, this));
                    findViewById(R.id.breedsLoadingProgressBar).setVisibility(View.GONE);
                }));
    }
}