package com.example.android.movieapp.Repository;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.example.android.movieapp.JsonUtils.MovieJsonUtil;
import com.example.android.movieapp.MovieKeys;
import com.example.android.movieapp.model.Movie;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieRepositoryNetwork {
    static List<Movie> result;
    private static MutableLiveData<List<Movie>> dataFromNetwork = new MutableLiveData<>();

    public MovieRepositoryNetwork(){
    }

    public MutableLiveData<List<Movie>> getPopularMoviesFromNetwork() {
            new MovieAsyncTask().execute(MovieKeys.getPopularMovies());
        return dataFromNetwork;
    }



    public MutableLiveData<List<Movie>> getHighRatedFromNetwork() {
        new MovieAsyncTask().execute(MovieKeys.getTopRated());
        return dataFromNetwork;
    }

    private static class MovieAsyncTask extends AsyncTask<String, Void, LiveData<List<Movie>>> {
        OkHttpClient client = new OkHttpClient();
        String dataHandler;
        @Override
        protected LiveData<List<Movie>> doInBackground(String... strings) {
            Request request = new Request.Builder().url(strings[0]).build();
            try {
                Response response = client.newCall(request).execute();
                dataHandler = response.body().string();
                 result = MovieJsonUtil.parseMovieJson(dataHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            dataFromNetwork.postValue(result);
            return  dataFromNetwork;
        }


    }


}
