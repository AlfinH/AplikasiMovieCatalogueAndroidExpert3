package com.alfin.aplikasimoviecatalogueandroidexpert3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alfin.aplikasimoviecatalogueandroidexpert3.model.MovieTvShow;
import com.alfin.aplikasimoviecatalogueandroidexpert3.R;

import java.util.ArrayList;

public class MovieTvShowAdapter extends RecyclerView.Adapter<MovieTvShowAdapter.ViewHolder> {
    private ArrayList<MovieTvShow> listMovieTvShow;

    public MovieTvShowAdapter(ArrayList<MovieTvShow> list) {
        this.listMovieTvShow = list;
    }

    private OnItemClickCallback onItemClickCallback;
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_tvshow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.imgGambar.setImageResource(listMovieTvShow.get(position).getGambar());
        holder.txtJudul.setText(listMovieTvShow.get(position).getJudul());
        holder.txtTanggal.setText(listMovieTvShow.get(position).getTanggal_rilis());
        holder.txtDeskripsi.setText(listMovieTvShow.get(position).getDeskripsi());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listMovieTvShow.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovieTvShow.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtJudul;
        private TextView txtTanggal;
        private TextView txtDeskripsi;
        private ImageView imgGambar;
        public ViewHolder(@NonNull View itemView) {
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
