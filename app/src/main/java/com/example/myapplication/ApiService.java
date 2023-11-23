package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("pincode/{pincode}")
    Call<List<PostOfficeResponse>> getPincodeData(@Path("pincode") String pincode);
}
