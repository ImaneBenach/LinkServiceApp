package com.imane.linkserviceapp.API;

import com.imane.linkserviceapp.Classes.Apply;
import com.imane.linkserviceapp.Classes.Service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface ServiceAPI {
    @POST("/service")
    Call<Void> setService(@Body Service apply);

    @PATCH("/service")
    Call<Void> updateService(@Body Service apply);
}
