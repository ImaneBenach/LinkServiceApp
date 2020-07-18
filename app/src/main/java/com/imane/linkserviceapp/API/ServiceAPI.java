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
    @POST("/services")
    Call<Void> setService(@Body Service service);

    @PATCH("/service")
    Call<Void> updateService(@Body Service service);

    @GET("/services/statut/{statutService}")
    Call<List<Service>> getServicesByStatut(@Path("statutService") int statut);

    @GET("/services/typeService/{statutService}")
    Call<List<Service>> getServicesByType(@Path("statutService") int statut);

    @GET("/service/executor/{id_service}")
    Call<List<Apply>> getExecutor(@Path("id_service") int id_service);

}
