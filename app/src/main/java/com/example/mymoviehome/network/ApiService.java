package com.example.mymoviehome.network;

import com.example.mymoviehome.response_tvshow.TVShowDetailResponse;
import com.example.mymoviehome.response_tvshow.TVShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<TVShowResponse> getMostPopularTVShows(@Query("page") int page);

    @GET("show-details")
    Call<TVShowDetailResponse> getTVShowDetails(@Query("q") String tvShowId);


    @GET("search")
    Call<TVShowResponse> searchTVShow(@Query("q") String query, @Query("page") int page);
}
