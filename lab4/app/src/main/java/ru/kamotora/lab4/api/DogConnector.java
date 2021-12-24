package ru.kamotora.lab4.api;

import android.util.Log;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

import lombok.val;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ru.kamotora.lab4.core.PropertiesLoader;

public class DogConnector {
    static String PROPERTIES_FILE = "application.properties";
    private static DogConnector mInstance;
    private final Retrofit mRetrofit;

    private DogConnector() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);
        Properties properties = new Properties();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(PropertiesLoader.getProperty("base.url"))
                .client(client.build())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public static DogConnector getInstance() {
        if (mInstance == null) {
            mInstance = new DogConnector();
        }
        return mInstance;
    }

    private DogApi getDogApi() {
        return mRetrofit.create(DogApi.class);
    }

    public List<String> getBreeds() {
        return wrap(dogApi -> dogApi.getBreeds()).getBreeds();
    }

    public String getRandomImageLinkByBreed(String breed) {
        return wrap(dogApi -> dogApi.getBreedRandomImageLink(breed)).getLink();
    }

    private <T> T wrap(Function<DogApi, Call<T>> requestFunc) {
        val request = requestFunc.apply(getDogApi());
        try {
            val response = request.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                Log.e(getClass().getSimpleName(), "Error response: " +
                        ReflectionToStringBuilder.toString(response.errorBody()));
                throw new RuntimeException("Error response");
            }
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), "Error send request: ");
            throw new RuntimeException("Error send request", e);
        }
    }
}