package com.example.android.movieapp.JsonUtils;

import android.arch.lifecycle.LiveData;

import com.example.android.movieapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieJsonUtil {

    private static final String TITLE = "title";
    private  static final String RELEASE_DATE = "release_date";
    private static  final String MOVIE_POSTER = "poster_path";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String PLOT_SYNOPSIS = "overview";
    private static final String RESULT = "results";
    private static final String BACK_DROP = "backdrop_path";
    private static final String MOVIE_ID = "id";

    // static method to retrieve the Json data from the array

    public  static List<Movie> parseMovieJson (String json){
       List<Movie> movieList = new ArrayList<>();
        Movie movieData = null;
        try{
            JSONObject dataJson = new JSONObject(json);
            JSONArray resultJson = dataJson.getJSONArray(RESULT);
            for(int i = 0; i < resultJson.length(); i++){
                JSONObject movie = resultJson.getJSONObject(i);
                String title = movie.optString(TITLE);
                String releaseDate = movie.optString(RELEASE_DATE);
                String moviePoster = movie.optString(MOVIE_POSTER);
                String voteAverage = movie.optString(VOTE_AVERAGE);
                String plotSynopsis = movie.optString(PLOT_SYNOPSIS);
                String backPoster = movie.optString(BACK_DROP);
                String mMovieId = movie.optString(MOVIE_ID);
                movieData = new Movie(title,plotSynopsis,releaseDate,moviePoster,voteAverage,backPoster,mMovieId);
                movieList.add(movieData);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return movieList;
    }
}


