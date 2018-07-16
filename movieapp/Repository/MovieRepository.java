package com.example.android.movieapp.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.movieapp.model.Movie;

import java.util.List;

public class MovieRepository {
    private MovieDao mMovieDao;
    private LiveData<List<Movie>> mAllMovies;
    private static Movie temp;

    public MovieRepository(Application application){
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        mMovieDao = db.movieDao();
        mAllMovies = mMovieDao.getAllMovie();
        Log.v("change", "database constructor called");
    }

    public Movie getMovie(String movie){
        new getMovieIDAsyncTask(mMovieDao).execute(movie);
        return temp;
    }

    public LiveData<List<Movie>> getmAllMovies(){
        return mAllMovies;
    }


    public void insert(Movie movie){
        new insertMovieAsyncTask(mMovieDao).execute(movie);
    }

    public void delete(String movie){
        new deleteMovieAsyncTask(mMovieDao).execute(movie);
    }
    public static class getMovieIDAsyncTask extends AsyncTask<String,Void, Movie>{

        private MovieDao mMovieIDAsyncTask;

        getMovieIDAsyncTask(MovieDao dao){
            mMovieIDAsyncTask = dao;
        }
        @Override
        protected Movie doInBackground(String... movies) {
            temp = mMovieIDAsyncTask.getMovie(movies[0]);
            return temp;
        }
    }
    public static class insertMovieAsyncTask extends AsyncTask<Movie,Void,Void>{
        private MovieDao mMovieAsyncTaskDao;

        insertMovieAsyncTask(MovieDao dao){
            mMovieAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final Movie... movies) {
           mMovieAsyncTaskDao.insert(movies[0]);
            return null;
        }
    }

    public static class deleteMovieAsyncTask extends  AsyncTask<String,Void,Void>{
        private MovieDao mMovieAsyncTaskDao;
        deleteMovieAsyncTask(MovieDao dao){
            mMovieAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(String... movies) {
            mMovieAsyncTaskDao.deleteMovie(movies[0]);
            return null;
        }
    }


}

