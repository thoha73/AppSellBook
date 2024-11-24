package com.example.appsellbook.graphql;

import android.content.Context;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String url = "http://192.168.1.7/appsellbook/graphql/";
    private static Retrofit retrofit;

    public static Retrofit getClient(Context context) {  // Phương thức yêu cầu tham số context
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}