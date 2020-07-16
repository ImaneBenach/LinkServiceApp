package com.imane.linkserviceapp.API;

import com.imane.linkserviceapp.Classes.TypeService;
import com.imane.linkserviceapp.Classes.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TypeAPI {
    @GET("/typeService/active")
    Call<List<TypeService>> getTypesActives();

    @GET("/typeService/{idType}")
    Call<List<TypeService>> getTypesActives(@Path("idType") int id);

}
