package com.alfin.aplikasimoviecatalogueandroidexpert3.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieTvShow implements Parcelable {
    private int gambar;
    private String judul,tanggal_rilis,genre,deskripsi;

    public MovieTvShow() {
    }

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getTanggal_rilis() {
        return tanggal_rilis;
    }

    public void setTanggal_rilis(String tanggal_rilis) {
        this.tanggal_rilis = tanggal_rilis;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    protected MovieTvShow(Parcel in) {
        gambar = in.readInt();
        judul = in.readString();
        tanggal_rilis = in.readString();
        genre = in.readString();
        deskripsi = in.readString();
    }

    public static final Creator<MovieTvShow> CREATOR = new Creator<MovieTvShow>() {
        @Override
        public MovieTvShow createFromParcel(Parcel in) {
            return new MovieTvShow(in);
        }

        @Override
        public MovieTvShow[] newArray(int size) {
            return new MovieTvShow[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(gambar);
        dest.writeString(judul);
        dest.writeString(tanggal_rilis);
        dest.writeString(genre);
        dest.writeString(deskripsi);
    }
}
