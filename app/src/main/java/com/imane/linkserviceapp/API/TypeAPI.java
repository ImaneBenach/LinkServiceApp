package com.imane.linkserviceapp.API;

import com.imane.linkserviceapp.Classes.TypeService;
import com.imane.linkserviceapp.Classes.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface TypeAPI {
    @GET("/typeService/active")
    Call<List<TypeService>> getTypesActives();

}
