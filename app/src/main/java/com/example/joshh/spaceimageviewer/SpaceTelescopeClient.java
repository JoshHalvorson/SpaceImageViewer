package com.example.joshh.spaceimageviewer;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface SpaceTelescopeClient {

    @GET("api/v3/image/{id}")
    Call<SpaceTelescopeImage> getImageData(@Path("id") int id);
}
