package com.alfin.aplikasimoviecatalogueandroidexpert3.model;

import org.json.JSONObject;

public class Movie {
    private int id;
    private String judul,tanggal_rilis,deskripsi,gambar;

    public Movie() {
    }

    public Movie(JSONObject object) {
        try {
            int id = object.getInt("id");
            String gambar = "https://image.tmdb.org/t/p/w185" + object.getString("poster_path");
            String judul = object.getString("title");
            String tanggal_rilis = object.getString("release_date");
            String deskripsi = object.getString("overview");
            this.id = id;
            this.gambar = gambar;
            this.judul = judul;
            this.tanggal_rilis = tanggal_rilis;
            this.deskripsi = deskripsi;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
