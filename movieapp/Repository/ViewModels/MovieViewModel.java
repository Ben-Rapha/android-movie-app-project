package com.example.android.movieapp.Repository.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.movieapp.Repository.MovieRepository;
//import com.example.android.movieapp.Repository.MovieRepositoryNetwork;
import com.example.android.movieapp.Repository.MovieRepositoryNetwork;
import com.example.android.movieapp.model.Movie;

import java.util.List;

public class MovieViewModel extends AndroidViewModel{
    private MovieRepository movieRepository;
    private MovieRepositoryNetwork movieRepositoryNetwork;
    private LiveData<List<Movie>> mAllMovies;
    private LiveData<List<Movie>> dataFromNetwork;

    public MovieViewModel(@NonNull Application application) {
        super(application);
//        Log.v("change"," view model constructor is called");
        movieRepository = new MovieRepository(application);
         movieRepositoryNetwork = new MovieRepositoryNetwork();
    }



    public LiveData<List<Movie>> getFavoritesFromDatabase(){
//        Log.v("change","favorite movies is called from view model");
        dataFromNetwork = movieRepository.getmAllMovies();
        return  dataFromNetwork;
    }

    public void getPopularMovies(){
//        Log.v("change","popular movies is called from view model");
        dataFromNetwork = movieRepositoryNetwork.getPopularMoviesFromNetwork();
    }

    public void getHighRatedMovies(){
//        Log.v("change","hih rated movies is called from view model");
        dataFromNetwork = movieRepositoryNetwork.getHighRatedFromNetwork();
    }

    public LiveData<List<Movie>> getDataFromNetwork(){
        return dataFromNetwork;
    }

    public void insert(Movie movie){
        movieRepository.insert(movie);
    }

    public void delete(String movie){
        movieRepository.delete(movie);
    }
    public Movie getMovieID(String movieID){
        return movieRepository.getMovie(movieID);
    }
}


