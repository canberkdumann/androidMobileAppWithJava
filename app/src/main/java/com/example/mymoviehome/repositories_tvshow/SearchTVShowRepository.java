package com.example.mymoviehome.repositories_tvshow;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mymoviehome.network.ApiClient;
import com.example.mymoviehome.network.ApiService;
import com.example.mymoviehome.response_tvshow.TVShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTVShowRepository {

    private ApiService apiService;

    public SearchTVShowRepository(){

        apiService = ApiClient.getRetrofit().create(ApiService.class);


    }

    public LiveData<TVShowResponse> searchTVShow(String query, int page){

        MutableLiveData<TVShowResponse> data = new MutableLiveData<>();
        apiService.searchTVShow(query, page).enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowResponse> call,@NonNull Response<TVShowResponse> response) {
                  data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVShowResponse> call,@NonNull Throwable t) {
                data.setValue(null);

            }
        });
        return data;
    }
}
