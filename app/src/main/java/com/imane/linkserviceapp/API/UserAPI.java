package com.imane.linkserviceapp.API;

import com.imane.linkserviceapp.Classes.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserAPI {
    @POST("/connection/user")
    Call<List<User>> connection(@Body User user);

    @GET("/user/{UserID}")
    Call<List<User>> getOne(@Path("UserID") int id);

    @PATCH("/user/{id_user}")
    Call<Void> updateUser(@Path ("id_user") int id, @Body User user);

    @PATCH("/user/delete/{id_user}")
    Call<Void> deleteUser(@Path ("id_user") int id, @Body User user);

    @POST("/user")
    Call<Void> create(@Body User user);

    @GET("/user/email/{email}")
    Call<List<User>> getByMail(@Path("email") String email);

}
