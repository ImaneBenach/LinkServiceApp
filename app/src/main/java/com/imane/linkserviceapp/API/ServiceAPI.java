package com.imane.linkserviceapp.API;

import com.imane.linkserviceapp.Classes.Apply;
import com.imane.linkserviceapp.Classes.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceAPI {
    @POST("/service")
    Call<Void> setService(@Body Service apply);

    @PATCH("/service")
    Call<Void> updateService(@Body Service apply);

    @GET("/services/statut/{statutService}")
    Call<List<Service>> getServicesByStatut(@Path("statutService") int statut);
}
