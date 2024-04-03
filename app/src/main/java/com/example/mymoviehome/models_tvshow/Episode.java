package com.example.mymoviehome.models_tvshow;

import com.google.gson.annotations.SerializedName;

public class Episode {

    @SerializedName("season")
    private String season;

    @SerializedName("episode")
    private String episode;

    @SerializedName("name")
    private String namee;

    @SerializedName("air_date")
    private String airDate;

    public String getSeason() {
        return season;
    }

    public String getEpisode() {
        return episode;
    }

    public String getNamee() {
        return namee;
    }

    public String getAirDate() {
        return airDate;
    }
}
