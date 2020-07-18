package com.imane.linkserviceapp.API;

import com.imane.linkserviceapp.Classes.Win;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WinAPI {
    @POST("/win")
    Call<Void> create(@Body Win win);

}
