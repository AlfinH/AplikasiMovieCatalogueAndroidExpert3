package com.alfin.aplikasimoviecatalogueandroidexpert3.adapter;

import android.app.Activity;
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

public class MovieTvShowAdapter extends RecyclerView.Adapter<MovieTvShowAdapter.MovieTvShowViewHolder> {
    private final ArrayList<MovieTvShow> listMovieTvShows = new ArrayList<>();
    private final Activity activity;

    private MovieTvShowAdapter.OnItemClickCallback onItemClickCallback;
    public void setOnItemClickCallback(MovieTvShowAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public MovieTvShowAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<MovieTvShow> getListMovieTvShows() {
        return listMovieTvShows;
    }

    public void setListNotes(ArrayList<MovieTvShow> listMovieTvShows) {

        if (listMovieTvShows.size() > 0) {
            this.listMovieTvShows.clear();
        }
        this.listMovieTvShows.addAll(listMovieTvShows);

        notifyDataSetChanged();
    }

    public void addItem(MovieTvShow movieTvShow) {
        this.listMovieTvShows.add(movieTvShow);
        notifyItemInserted(listMovieTvShows.size() - 1);
    }

    public void updateItem(int position, MovieTvShow movieTvShow) {
        this.listMovieTvShows.set(position, movieTvShow);
        notifyItemChanged(position, movieTvShow);
    }

    public void removeItem(int position) {
        this.listMovieTvShows.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listMovieTvShows.size());
    }

    @NonNull
    @Override
    public MovieTvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_tvshow, parent, false);
        return new MovieTvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieTvShowViewHolder movieTvShowViewHolder, int position) {
        MovieTvShow movieTvShow = listMovieTvShows.get(position);
        movieTvShowViewHolder.txtJudul.setText(movieTvShow.getJudul());
        movieTvShowViewHolder.txtTanggal.setText(movieTvShow.getTanggal_rilis());
        movieTvShowViewHolder.txtDeskripsi.setText(movieTvShow.getDeskripsi());
        Glide.with(movieTvShowViewHolder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w342" + movieTvShow.getGambar())
                .apply(new RequestOptions().override(100, 150))
                .into(movieTvShowViewHolder.imgGambar);

        movieTvShowViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listMovieTvShows.get(movieTvShowViewHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovieTvShows.size();
    }

    class MovieTvShowViewHolder extends RecyclerView.ViewHolder {
        private TextView txtJudul;
        private TextView txtTanggal;
        private TextView txtDeskripsi;
        private ImageView imgGambar;

        MovieTvShowViewHolder(View itemView) {
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
