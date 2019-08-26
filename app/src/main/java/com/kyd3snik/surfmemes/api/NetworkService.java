package com.kyd3snik.surfmemes.api;

import com.kyd3snik.surfmemes.storages.UserStorage;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//TODO: add token in headers
public class NetworkService {
    private final static String BASE_URL = "https://demo3161256.mockable.io/";
    private static NetworkService mInstance;
    private Retrofit mRetrofit;

    private NetworkService() {
        String accessToken = UserStorage.getToken();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new TokenInterceptor(accessToken))
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null)
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

