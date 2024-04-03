package com.example.mymoviehome.listeners;

import com.example.mymoviehome.models_tvshow.TVShow;

public interface WatchlistListener {

        void onTVShowClicked(TVShow tvShow);

        void removeTVShowFromWtachlist(TVShow tvShow,int position);


}
