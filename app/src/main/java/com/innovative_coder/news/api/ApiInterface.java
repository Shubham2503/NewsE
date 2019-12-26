package com.innovative_coder.news.api;

import com.innovative_coder.news.models.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<News> getNews(

            @Query("country") String country ,
            @Query("apiKey") String apiKey

    );

    @GET("top-headlines")
    Call<News> getGlobal(

            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey

    );



    @GET("top-headlines")
    Call<News> getNewsByCategory(

            @Query("category") String category,
            @Query("language") String language,
            @Query("country") String country ,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey

    );

    @GET("everything")
    Call<News> getNewsSearch(

            @Query("q") String keyWord,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey
    );

    @GET("top-headlines")
    Call<News> getNewsBySourcs(
            @Query("sources")String sources,
            @Query("language")String language,
            @Query("apiKey")String apiKey
    );



}
