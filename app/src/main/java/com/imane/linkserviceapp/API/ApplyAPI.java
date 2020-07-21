package com.imane.linkserviceapp.API;

import com.imane.linkserviceapp.Classes.Apply;
import com.imane.linkserviceapp.Classes.Note;
import com.imane.linkserviceapp.Classes.Service;

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

    @GET("/apply/{id_user}")
    Call<List<Service>> getUserAppliedServices(@Path("id_user") int idUser);

    @GET("/apply/service/user/{id_service}&{id_user}")
    Call<List<Apply>> existApply(@Path("id_service") int idService, @Path("id_user") int idUser);

    @GET("/apply/note/{id_user}&{id_type}")
    Call<List<Note>> getNoteUserByService(
            @Path("id_user") int id_user,
            @Path("id_type") int id_type
    );
}
