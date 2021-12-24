package ru.kamotora.lab4.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.Callable;

import lombok.Getter;
import ru.kamotora.lab4.api.DogApi;
import ru.kamotora.lab4.api.DogConnector;

@Getter
public class ImageLoader implements Callable<Bitmap> {
    private final String breed;

    public ImageLoader(String breed) {
        this.breed = breed;
    }

    private String getImageLink() {
        try {
            return DogConnector.getInstance().getRandomImageLinkByBreed(breed);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error to receive link for breed  " + breed + ". Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private URL getUrl(String imageUrl) {
        try {
            return new URI(imageUrl).toURL();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error to parse url " + imageUrl + ". Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Bitmap call() throws Exception {
        try (InputStream imageStream = getUrl(getImageLink()).openStream()) {
            return BitmapFactory.decodeStream(imageStream);
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), "Exception occurred " + e.getMessage());
            throw e;
        }
    }
}
