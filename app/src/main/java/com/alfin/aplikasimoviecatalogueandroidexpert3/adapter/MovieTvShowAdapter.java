package com.alfin.aplikasimoviecatalogueandroidexpert3.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alfin.aplikasimoviecatalogueandroidexpert3.BuildConfig;
import com.alfin.aplikasimoviecatalogueandroidexpert3.CustomOnItemClickListener;
import com.alfin.aplikasimoviecatalogueandroidexpert3.R;
import com.alfin.aplikasimoviecatalogueandroidexpert3.activity.DetailMovieTvShowActivity;
import com.alfin.aplikasimoviecatalogueandroidexpert3.model.MovieTvShow;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MovieTvShowAdapter extends RecyclerView.Adapter<MovieTvShowAdapter.MovieTvShowViewHolder> {
    private final ArrayList<MovieTvShow> listMovieTvShows = new ArrayList<>();
    private final Activity activity;

    public MovieTvShowAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<MovieTvShow> getListMovieTvShows() {
        return listMovieTvShows;
    }

    public void setListMovieTvShows(ArrayList<MovieTvShow> listMovieTvShows) {

        if (listMovieTvShows.size() >= 0) {
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
        final MovieTvShow movieTvShow = listMovieTvShows.get(position);
        movieTvShowViewHolder.txtJudul.setText(movieTvShow.getJudul());
        movieTvShowViewHolder.txtTanggal.setText(movieTvShow.getTanggal_rilis());
        movieTvShowViewHolder.txtDeskripsi.setText(movieTvShow.getDeskripsi());
        Glide.with(movieTvShowViewHolder.itemView.getContext())
                .load(BuildConfig.BASE_URL_W342 + movieTvShow.getGambar())
                .apply(new RequestOptions().placeholder(R.drawable.ic_error).error(R.drawable.ic_error).override(100, 150))
                .into(movieTvShowViewHolder.imgGambar);
        movieTvShowViewHolder.rlMovieTvShow.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent detailMovie = new Intent(activity, DetailMovieTvShowActivity.class);

                movieTvShow.setId(movieTvShow.getId());
                movieTvShow.setGambar(movieTvShow.getGambar());
                movieTvShow.setJudul(movieTvShow.getJudul());
                movieTvShow.setTanggal_rilis(movieTvShow.getJudul());
                movieTvShow.setGenre(movieTvShow.getGenre());
                movieTvShow.setDeskripsi(movieTvShow.getDeskripsi());

                detailMovie.putExtra(DetailMovieTvShowActivity.EXTRA_MOVIE_TVSHOW, movieTvShow);
                activity.startActivityForResult(detailMovie, DetailMovieTvShowActivity.REQUEST_UPDATE);
            }
        }));
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
        private RelativeLayout rlMovieTvShow;

        MovieTvShowViewHolder(View itemView) {
            super(itemView);
            imgGambar = itemView.findViewById(R.id.iv_gambar);
            txtJudul = itemView.findViewById(R.id.tv_judul);
            txtTanggal = itemView.findViewById(R.id.tv_tgl_rilis);
            txtDeskripsi = itemView.findViewById(R.id.tv_deskripsi);
            rlMovieTvShow = itemView.findViewById(R.id.rl_item_movie_tvshow);
        }
    }
}
