package com.alfin.aplikasimoviecatalogueandroidexpert3.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

public class MovieTvShow implements Parcelable {
    private int id;
    private String gambar, judul, tanggal_rilis, genre, deskripsi;

    public MovieTvShow() {
    }

    public MovieTvShow(int id, String gambar, String judul, String tanggal_rilis, String genre, String deskripsi) {
        this.id = id;
        this.gambar = gambar;
        this.judul = judul;
        this.tanggal_rilis = tanggal_rilis;
        this.genre = genre;
        this.deskripsi = deskripsi;
    }

    public MovieTvShow(JSONObject object) {
        if (object.has("release_date")) {
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
        } else if (object.has("first_air_date")) {
            try {
                int id = object.getInt("id");
                String gambar = object.getString("poster_path");
                String judul = object.getString("original_name");
                String tanggal_rilis = object.getString("first_air_date");
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
            case 10759:
                genre = "Action & Adventure";
                break;
            case 10762:
                genre = "Kids";
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
            case 10767:
                genre = "Talk";
                break;
            case 10768:
                genre = "War & Politics";
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

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
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
        id = in.readInt();
        gambar = in.readString();
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
        dest.writeInt(id);
        dest.writeString(gambar);
        dest.writeString(judul);
        dest.writeString(tanggal_rilis);
        dest.writeString(genre);
        dest.writeString(deskripsi);
    }
}
