package com.kyd3snik.surfmemes.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//TODO: add token in headers
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
    public MemeApi getMemeApi() {
        return mRetrofit.create(MemeApi.class);
    }
    public AuthApi getAuthApi() {
        return mRetrofit.create(AuthApi.class);
    }

}

