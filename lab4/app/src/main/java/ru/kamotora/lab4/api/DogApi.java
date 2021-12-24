package ru.kamotora.lab4.api;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.kamotora.lab4.dto.BreedImagesResponse;
import ru.kamotora.lab4.dto.BreedRandomImageeResponse;
import ru.kamotora.lab4.dto.BreedsResponse;

public interface DogApi {

    @GET("breeds/list/all")
    Call<BreedsResponse> getBreeds();

    @GET("breed/{breed}/images")
    Call<BreedImagesResponse> getBreedImagesLinks(@Path("breed") String breed);


    @GET("breed/{breed}/images/random")
    Call<BreedRandomImageeResponse> getBreedRandomImageLink(@Path("breed") String breed);

}