package com.example.appsellbook.graphql;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String url = "http://192.168.1.8/appsellbook/graphql/";
    private static Retrofit retrofit;

    public static Retrofit getClient(Context context) {  // Phương thức yêu cầu tham số context
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS) // Timeout kết nối
                    .readTimeout(120, TimeUnit.SECONDS)    // Timeout đọc dữ liệu
                    .writeTimeout(120, TimeUnit.SECONDS)   // Timeout ghi dữ liệu
                    .build();
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}