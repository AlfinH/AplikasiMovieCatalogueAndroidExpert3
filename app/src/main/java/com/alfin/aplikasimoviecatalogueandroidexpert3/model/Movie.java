package com.alfin.aplikasimoviecatalogueandroidexpert3.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class Movie {
    private int id;
    private String judul, tanggal_rilis, deskripsi, genre, gambar;

    public Movie() {
    }

    public Movie(JSONObject object) {
        try {
            int id = object.getInt("id");
            String gambar = object.getString("poster_path");
            String judul = object.getString("title");
            String tanggal_rilis = object.getString("release_date");
            String genre = "";
            JSONArray genreArr = object.getJSONArray("genre_ids");
            for (int i = 0; i < genreArr.length(); i++) {
                if (i != 0) {
                    genre += ", ";
                }
                genre += toGenre(genreArr.getInt(i));
            }

            String deskripsi = object.getString("overview");
            this.id = id;
            this.gambar = gambar;
            this.judul = judul;
            this.tanggal_rilis = tanggal_rilis;
            this.genre = genre;
            this.deskripsi = deskripsi;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String toGenre(int id) {
        String genre = "";
        switch (id) {
            case 28:
                genre = "Action";
                break;
            case 12:
                genre = "Adventure";
                break;
            case 16:
                genre = "Animation";
                break;
            case 35:
                genre = "Comedy";
                break;
            case 80:
                genre = "Crime";
                break;
            case 99:
                genre = "Documentary";
                break;
            case 18:
                genre = "Drama";
                break;
            case 10751:
                genre = "Family";
                break;
            case 14:
                genre = "Fantasy";
                break;
            case 36:
                genre = "History";
                break;
            case 27:
                genre = "Horror";
                break;
            case 10402:
                genre = "Music";
                break;
            case 9648:
                genre = "Mystery";
                break;
            case 10749:
                genre = "Romance";
                break;
            case 878:
                genre = "Science Fiction";
                break;
            case 10770:
                genre = "TV Movie";
                break;
            case 53:
                genre = "Thriller";
                break;
            case 10752:
                genre = "War";
                break;
            case 37:
                genre = "Western";
                break;

        }
        return genre;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
