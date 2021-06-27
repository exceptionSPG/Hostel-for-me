package com.hfad.hostel.Helper;

import com.hfad.hostel.Interface.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://192.168.43.56/HF_Online/public/";//"https://mynotesappwebsite.000webhostapp.com/MyApi/public/";//"http://192.168.43.56/MyApi/public/"; //https://mynotesappwebsite.000webhostapp.com/MyApi/MyApi/public/
    private static RetrofitClient mInstance;
    private Retrofit hostelRetrofit;

    public RetrofitClient() {
        hostelRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitClient getInstance(){
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }
    public Api getApi(){
        return hostelRetrofit.create(Api.class);
    }
}
