package com.imane.linkserviceapp.API;

import com.imane.linkserviceapp.Classes.Apply;
import com.imane.linkserviceapp.Classes.MessageChat;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MessageAPI {
    @POST("/messages")
    Call<Void> setMessage(@Body MessageChat message);
}
