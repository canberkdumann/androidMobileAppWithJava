package com.example.mymoviehome.response_tvshow;

import com.example.mymoviehome.models_tvshow.TVShowDetails;
import com.google.gson.annotations.SerializedName;

public class TVShowDetailResponse {

    @SerializedName("tvShow")
    private TVShowDetails tvShowDetails;

    public TVShowDetails getTvShowDetails() {
        return tvShowDetails;
    }
}
