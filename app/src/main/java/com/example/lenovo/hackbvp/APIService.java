package com.example.lenovo.hackbvp;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Lenovo on 07-08-2017.
 */

public interface APIService {

    @POST("/hello")
    Call<ResponseBody> postData(@Body ArrayList<TrainingData> arrayList);

    @POST("/ml")
    Call<ResponseAzure> postAzure(@Body AzureBody body);


}