package com.example.android.movieapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "movie_database",indices = {@Index(value = {"movieID"}, unique = true)})
public class Movie implements Parcelable{

//    @PrimaryKey(autoGenerate = true)
//    private int id;
    @ColumnInfo(name = "title")
    private String movieTitle;
    @ColumnInfo(name = "plot")
    private String moviePlot;
    @ColumnInfo(name ="date")
    private String releaseDate;
    @ColumnInfo(name = "image")
    private String  movieImage;
    @ColumnInfo(name ="rate")
    private String movieRating;
    @ColumnInfo(name = "backdrop")
    private String backDrop;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "movieID")
    private String mMovieId;

    public Movie(String movieTitle, String moviePlot, String releaseDate,
                 String movieImage,String movieRating, String backDrop,String movieId ){
        this.movieTitle = movieTitle;
        this.moviePlot = moviePlot;
        this.releaseDate = releaseDate;
        this.movieImage = movieImage;
        this.movieRating = movieRating;
        this.backDrop = backDrop;
        mMovieId = movieId;

    }

    @Ignore
    public Movie(String movieId){
        this.mMovieId = movieId;

    }

    public String getBackDrop() {
        return backDrop;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMoviePlot() {
        return moviePlot;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
//    public int getId(){
//        return id;
//    }
//
//    public void setId(int id){
//        this.id = id;
//    }

    public String getMovieImage() {
        return movieImage;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public String getMovieId(){ return mMovieId; }

    public void setMoviePlot(String moviePlot) {
        this.moviePlot = moviePlot;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.movieTitle);
        parcel.writeString(this.movieImage);
        parcel.writeString(this.moviePlot);
        parcel.writeString(this.movieRating);
        parcel.writeString(this.releaseDate);
        parcel.writeString(this.backDrop);
        parcel.writeString(this.mMovieId);
    }

    private Movie(Parcel in){
        this.movieTitle = in.readString();
        this.movieImage = in.readString();
        this.moviePlot = in.readString();
        this.movieRating = in.readString();
        this.releaseDate = in.readString();
        this.backDrop = in.readString();
        this.mMovieId = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return  new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

}

