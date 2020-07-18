package com.imane.linkserviceapp.API;

import com.imane.linkserviceapp.Classes.Badge;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BadgeAPI {

    @GET("/badge/own/{id_user}&{id_type}")
    Call<List<Badge>> getBestBadge(
            @Path("id_user") int id_user,
            @Path("id_type") int id_type
    );

    @GET("/badge/{id_user}&{id_type_service}")
    Call<List<Badge>> getBagdeByUserAndService(
            @Path("id_user") int id_user,
            @Path("id_type_service") int id_type_service
    );

    @GET("/badge/{id_type_service}")
    Call<List<Badge>> getAllBadgeByService(
            @Path("id_type_service") int id_type_service
    );

}
