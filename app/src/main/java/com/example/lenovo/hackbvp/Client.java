package com.example.lenovo.hackbvp;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Lenovo on 07-08-2017.
 */

public class Client
{
    private static APIService service;

    public static APIService getService() {

        if (service == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(45, TimeUnit.SECONDS).retryOnConnectionFailure(true)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request r = chain.request();
                            r = r.newBuilder()
                                    .addHeader("Accept", "application/vnd.github.v3+json")
                                    .build();
                            return chain.proceed(r);
                        }
                    })
                    .build();
            Retrofit r = new Retrofit.Builder().baseUrl("http://172.16.8.174:9000").
                            addConverterFactory(GsonConverterFactory.create(
                            new GsonBuilder().create())).client(client)
                    .build();
            service = r.create(APIService.class);
        }
        return service;
    }
}