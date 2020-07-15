package com.imane.linkserviceapp.API;

import com.imane.linkserviceapp.Classes.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserAPI {
    @POST("/connection/user")
    Call<List<User>> connection(@Body User user);
}
