package com.example.todolist;


import com.example.todolist.services.ApiService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

   private static Retrofit getRetrofit(){

       HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
       httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

       OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

       Retrofit retrofit = new Retrofit.Builder()
               .baseUrl("https://628d95f0368687f3e705a926.mockapi.io/api/")
               .addConverterFactory(GsonConverterFactory.create())
               .client(okHttpClient)
               .build();

       return retrofit;
   }

   public static ApiService getTaskService(){
       ApiService taskService = getRetrofit().create(ApiService.class);

       return taskService;
   }
}
