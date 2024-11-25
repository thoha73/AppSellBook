package com.example.appsellbook.GraphQL;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GraphQLApiService {
    @Headers("Content-Type: application/json")
    @POST("http://192.168.1.10/appsellbook/graphql/")
    Call<GraphQLResponse<Object>> executeQuery(@Body GraphQLRequest request);
}
