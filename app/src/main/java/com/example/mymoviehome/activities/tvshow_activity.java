package com.example.mymoviehome.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviehome.R;
import com.example.mymoviehome.adapters_tvshow.TVShowsAdapter;
import com.example.mymoviehome.databinding.TvShowsFragmentBinding;
import com.example.mymoviehome.listeners.TVShowsListener;
import com.example.mymoviehome.models_tvshow.TVShow;
import com.example.mymoviehome.viewmodels_tvshow.MostPopularTVShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class tvshow_activity extends Fragment implements TVShowsListener {

    private TvShowsFragmentBinding tvShowsFragmentBinding;
    private MostPopularTVShowsViewModel viewModel;
    private List<TVShow> tvShows = new ArrayList<>();
    private TVShowsAdapter tvShowsAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tvShowsFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.tv_shows_fragment,container,false);
        doInitialization();

        return tvShowsFragmentBinding.getRoot();
    }

    private void doInitialization() {

        tvShowsFragmentBinding.tvShowsRecylerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
        tvShowsAdapter = new TVShowsAdapter(tvShows,this);
        tvShowsFragmentBinding.tvShowsRecylerView.setAdapter(tvShowsAdapter);
        tvShowsFragmentBinding.tvShowsRecylerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!tvShowsFragmentBinding.tvShowsRecylerView.canScrollVertically(1)){
                    if(currentPage <= totalAvailablePages){
                        currentPage += 1;
                        getMostPopularTVShows();
                    }
                }
            }
        });

        tvShowsFragmentBinding.imageWatchList.setOnClickListener(v -> startActivity(new Intent(getActivity(),WatchListActivity.class)));
        tvShowsFragmentBinding.imageSearch.setOnClickListener(v -> startActivity(new Intent(getActivity(),SearchActivity.class)));
        getMostPopularTVShows();
    }


    private void getMostPopularTVShows() {

        toggleLoading();
        viewModel.getMostPopularTVShows(currentPage).observe(getActivity(), mostPopularTVShowsResponse -> {
            toggleLoading();
            if(mostPopularTVShowsResponse != null){

                totalAvailablePages = mostPopularTVShowsResponse.getTotalPages();
                if(mostPopularTVShowsResponse.getTvShows() != null){
                    int oldCount = tvShows.size();
                    tvShows.addAll(mostPopularTVShowsResponse.getTvShows());
                    tvShowsAdapter.notifyItemRangeInserted(oldCount,tvShows.size());
                }
            }

        }

        );
    }

    private void toggleLoading(){

        if(currentPage == 1){
            if(tvShowsFragmentBinding.getIsLoading() != null && tvShowsFragmentBinding.getIsLoading()){

                tvShowsFragmentBinding.setIsLoading(false);
            }else {
                tvShowsFragmentBinding.setIsLoading(true);
            }
        }else {
            if(tvShowsFragmentBinding.getIsLoadingMore() != null && tvShowsFragmentBinding.getIsLoadingMore()){

                tvShowsFragmentBinding.setIsLoading(false);
            }else {
                tvShowsFragmentBinding.setIsLoadingMore(true);
            }

        }
    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getActivity(),TVShowDetailsActivity.class);
        intent.putExtra("tvShow",tvShow);

       /* intent.putExtra("id",tvShow.getId());
        intent.putExtra("name",tvShow.getName());
        intent.putExtra("startDate",tvShow.getStartDate());
        intent.putExtra("country",tvShow.getCountry());
        intent.putExtra("network",tvShow.getNetwork());
        intent.putExtra("status",tvShow.getStatus());*/
        startActivity(intent);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}
