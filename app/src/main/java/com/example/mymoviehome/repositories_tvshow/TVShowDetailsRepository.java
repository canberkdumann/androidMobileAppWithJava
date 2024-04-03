package com.example.mymoviehome.repositories_tvshow;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mymoviehome.network.ApiClient;
import com.example.mymoviehome.network.ApiService;
import com.example.mymoviehome.response_tvshow.TVShowDetailResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowDetailsRepository {

    private ApiService apiService;

    public TVShowDetailsRepository(){

        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TVShowDetailResponse> getTVShowDetails(String tvShowId){
        MutableLiveData<TVShowDetailResponse> data = new MutableLiveData<>();
        apiService.getTVShowDetails(tvShowId).enqueue(new Callback<TVShowDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowDetailResponse> call,@NonNull Response<TVShowDetailResponse> response) {
                data.setValue(response.body());

            }

            @Override
            public void onFailure(@NonNull Call<TVShowDetailResponse> call,@NonNull Throwable t) {
                data.setValue(null);

            }
        });
        return data;

    }
}
