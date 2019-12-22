package com.alfin.aplikasimoviecatalogueandroidexpert3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alfin.aplikasimoviecatalogueandroidexpert3.R;

import com.alfin.aplikasimoviecatalogueandroidexpert3.model.TvShow;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowHolder> {

    private ArrayList<TvShow> mData = new ArrayList<>();

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public TvShowAdapter() {
    }

    public void setData(ArrayList<TvShow> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(final TvShow item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
    }

    @NonNull
    @Override
    public TvShowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_tvshow, viewGroup, false);
        return new TvShowHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvShowHolder tvShowViewHolder, int position) {
        TvShow tvShow = mData.get(position);
        tvShowViewHolder.txtJudul.setText(tvShow.getJudul());
        tvShowViewHolder.txtTanggal.setText(tvShow.getTanggal_rilis());
        tvShowViewHolder.txtDeskripsi.setText(tvShow.getDeskripsi());
        Glide.with(tvShowViewHolder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w342" + tvShow.getGambar())
                .apply(new RequestOptions().placeholder(R.drawable.ic_error).error(R.drawable.ic_error).override(100, 150))
                .into(tvShowViewHolder.imgGambar);

        tvShowViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(mData.get(tvShowViewHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class TvShowHolder extends RecyclerView.ViewHolder {
        private TextView txtJudul;
        private TextView txtTanggal;
        private TextView txtDeskripsi;
        private ImageView imgGambar;

        public TvShowHolder(@NonNull View itemView) {
            super(itemView);
            imgGambar = itemView.findViewById(R.id.iv_gambar);
            txtJudul = itemView.findViewById(R.id.tv_judul);
            txtTanggal = itemView.findViewById(R.id.tv_tgl_rilis);
            txtDeskripsi = itemView.findViewById(R.id.tv_deskripsi);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(TvShow data);
    }
}