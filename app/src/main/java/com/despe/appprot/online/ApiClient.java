package com.despe.appprot.online;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    //mettre son ip address
    public static final String BASE_URL_PATIENT = "http://192.168.1.72/prothese/"; //ip home
   // public static final String BASE_URL_PATIENT = "http://172.20.10.2/prothese/"; //ip eduroam
    private static Retrofit retrofit = null;

    public static Retrofit getClient2( ) {
        if (retrofit==null) {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL_PATIENT).addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        Log.d("MSG_DEBUG", "[ApiClient] return retrofit");
        return retrofit;
    }
}