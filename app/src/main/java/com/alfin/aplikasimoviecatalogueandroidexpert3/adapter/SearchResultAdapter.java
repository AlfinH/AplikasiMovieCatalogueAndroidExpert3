package com.alfin.aplikasimoviecatalogueandroidexpert3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alfin.aplikasimoviecatalogueandroidexpert3.R;
import com.alfin.aplikasimoviecatalogueandroidexpert3.model.MovieTvShow;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.MovieTvShowViewHolder> {

    private ArrayList<MovieTvShow> mData = new ArrayList<>();

    public ArrayList<MovieTvShow> getData() {
        return mData;
    }

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(SearchResultAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public SearchResultAdapter() {
    }

    public void setData(ArrayList<MovieTvShow> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(final MovieTvShow item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
    }

    @NonNull
    @Override
    public MovieTvShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_tvshow, viewGroup, false);
        return new MovieTvShowViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieTvShowViewHolder movieViewHolder, int position) {
        MovieTvShow movieTvShow = mData.get(position);
        movieViewHolder.txtJudul.setText(movieTvShow.getJudul());
        movieViewHolder.txtTanggal.setText(movieTvShow.getTanggal_rilis());
        movieViewHolder.txtDeskripsi.setText(movieTvShow.getDeskripsi());
        Glide.with(movieViewHolder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w342" + movieTvShow.getGambar())
                .apply(new RequestOptions().placeholder(R.drawable.ic_error).error(R.drawable.ic_error).override(100, 150))
                .into(movieViewHolder.imgGambar);

        movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(mData.get(movieViewHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MovieTvShowViewHolder extends RecyclerView.ViewHolder {
        private TextView txtJudul;
        private TextView txtTanggal;
        private TextView txtDeskripsi;
        private ImageView imgGambar;

        public MovieTvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGambar = itemView.findViewById(R.id.iv_gambar);
            txtJudul = itemView.findViewById(R.id.tv_judul);
            txtTanggal = itemView.findViewById(R.id.tv_tgl_rilis);
            txtDeskripsi = itemView.findViewById(R.id.tv_deskripsi);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(MovieTvShow data);
    }
}
