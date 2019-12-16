package com.alfin.aplikasimoviecatalogueandroidexpert3.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class TvShow {
    private int id;
    private String judul,tanggal_rilis,deskripsi,genre,gambar;

    public TvShow() {
    }

    public TvShow(JSONObject object) {
        try {
            int id = object.getInt("id");
            String gambar = object.getString("poster_path");
            String judul = object.getString("original_name");
            String tanggal_rilis = object.getString("first_air_date");
            String genre = "";
            JSONArray genreArr = object.getJSONArray("genre_ids");
            for(int i = 0;i<genreArr.length();i++){
                if(i == 0){
                    genre += toGenre(genreArr.getInt(i));
                }else{
                    genre += ", " + toGenre(genreArr.getInt(i));
                }
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
        switch (id){
            case 10759:
                genre = "Action & Adventure";
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
            case 10762:
                genre = "Kids";
                break;
            case 9648:
                genre = "Mystery";
                break;
            case 10763:
                genre = "News";
                break;
            case 10764:
                genre = "Reality";
                break;
            case 10765:
                genre = "Sci-Fi & Fantasy";
                break;
            case 10766:
                genre = "Soap";
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
