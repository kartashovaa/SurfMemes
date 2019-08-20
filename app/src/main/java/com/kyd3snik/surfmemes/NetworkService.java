package com.kyd3snik.surfmemes;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class NetworkService {
    private static NetworkService mInstance;
    private final static String BASE_URL = "http://demo3161256.mockable.io/";
    private Retrofit mRetrofit;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if(mInstance==null)
            mInstance = new NetworkService();
        return mInstance;


    }
    authAPI getAPI() {
        return mRetrofit.create(authAPI.class);
    }
}

interface authAPI {

    @POST("/auth/login")
    Call<UserResponse> login(@Body authRequest auth);

}