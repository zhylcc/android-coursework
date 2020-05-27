package com.example.apppro.utils;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyService {
    @GET("api/invoke/video/invoke/video")
    Call<List<MyData>> getDataList();
}
