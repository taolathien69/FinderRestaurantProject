package com.example.nhom4_cuoiki;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesApiService {
    @GET("maps/api/place/nearbysearch/json")
    Call<PlacesApiResponse> getNearbyRestaurants(
            @Query("location") String location,
            @Query("radius") int radius,
            @Query("type") String type,
            @Query("key") String apiKey
    );
}

