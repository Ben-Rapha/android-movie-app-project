package com.example.android.movieapp;

import com.example.android.movieapp.model.Movie;

public class MovieKeys {
    private  static final String POPULAR_MOVIES= "https://api.themoviedb.org/3/" +
            "discover/movie?api_key=af6a7bb450028de8aefd99dec516ebb4&language" +
            "=en-US&sort_by=popularity.desc&include_adult" +
            "=false&include_video=false&page=1";
    private static final String TOP_RATED =   "https://api.themoviedb.org/3/movie/" +
            "top_rated?api_key=af6a7bb450028de8aefd99dec516ebb4&language=en-US&page=1";


    private static final String TRAILER_ULR = "https://api.themoviedb.org/3/movie/" +
            "{movie_id}/videos?api_key=af6a7bb450028de8aefd99dec516ebb4&language=en-US";

    public static String getPopularMovies(){
        return POPULAR_MOVIES;
    }

    public static String getTopRated(){
        return TOP_RATED;
    }

    public static String getTrailerUlr(){
        return TRAILER_ULR;
    }
}
