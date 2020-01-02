package com.alfin.aplikasimoviecatalogueandroidexpert3.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alfin.aplikasimoviecatalogueandroidexpert3.R;
import com.alfin.aplikasimoviecatalogueandroidexpert3.activity.DetailMovieTvShowActivity;
import com.alfin.aplikasimoviecatalogueandroidexpert3.adapter.SearchResultAdapter;
import com.alfin.aplikasimoviecatalogueandroidexpert3.loader.SearchViewModel;
import com.alfin.aplikasimoviecatalogueandroidexpert3.model.MovieTvShow;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {

    private SearchResultAdapter adapter;
    private SearchViewModel searchViewModel;

    private SearchView searchColumn;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private static final String EXTRA_STATE = "EXTRA_STATE";

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchColumn = view.findViewById(R.id.search_column);
        searchColumn.setOnQueryTextListener(this);

        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.rv_search_result);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SearchResultAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickCallback(new SearchResultAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MovieTvShow data) {
                showSelectedMovieTvShow(data);
            }
        });

        if (savedInstanceState != null) {
            ArrayList<MovieTvShow> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setData(list);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getData());
    }

    private void showSelectedMovieTvShow(MovieTvShow data) {
        Intent detailMovieTvShow = new Intent(getContext(), DetailMovieTvShowActivity.class);
        detailMovieTvShow.putExtra(DetailMovieTvShowActivity.EXTRA_MOVIE_TVSHOW, data);
        startActivity(detailMovieTvShow);
    }

    private void searchResult() {
        String input_movie = searchColumn.getQuery().toString();

        searchViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SearchViewModel.class);

        searchViewModel.setSearchResults(getResources().getString(R.string.language), input_movie);
        showLoading(true);

        searchViewModel.getMovieTvShows().observe(this, new Observer<ArrayList<MovieTvShow>>() {
            @Override
            public void onChanged(ArrayList<MovieTvShow> movieTvShowsItems) {
                if (movieTvShowsItems != null) {
                    adapter.setData(movieTvShowsItems);
                    showLoading(false);
                }
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchResult();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
