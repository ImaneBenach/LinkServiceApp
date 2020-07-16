package com.imane.linkserviceapp.API;

import com.imane.linkserviceapp.Classes.Apply;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApplyAPI {
    @POST("/apply")
    Call<Void> setApply(@Body Apply apply);

    @PATCH("/apply")
    Call<Void> updateApply(@Body Apply apply);

    @GET("/apply/service/user/{id_service}&{id_user}")
    Call<List<Apply>> existApply(@Path("id_service") int idService, @Path("id_user") int idUser);
}
