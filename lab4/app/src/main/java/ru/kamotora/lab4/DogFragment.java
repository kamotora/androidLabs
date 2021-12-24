package ru.kamotora.lab4;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ru.kamotora.lab4.core.ImageLoader;
import ru.kamotora.lab4.core.TaskRunner;

public class DogFragment extends DialogFragment {
    private final String breedName;
    View _root;

    public DogFragment(String breedName) {
        this.breedName = breedName;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _root = inflater.inflate(R.layout.image_dialog, null);
        new TaskRunner().executeAsync(new ImageLoader(breedName),
                (image) -> requireActivity().runOnUiThread(() -> onImageLoaded(image)));
        return _root;
    }

    private void onImageLoaded(Bitmap image) {
        ImageView imageView = _root.findViewById(R.id.imageView);
        imageView.setImageBitmap(image);
        _root.findViewById(R.id.breedsLoadingProgressBar).setVisibility(View.GONE);
    }
}
