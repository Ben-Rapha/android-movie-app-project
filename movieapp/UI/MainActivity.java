package com.example.android.movieapp.UI;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.movieapp.Repository.ViewModels.MovieViewModel;
import com.example.android.movieapp.model.Movie;
import com.example.android.movieapp.adapter.MovieAdapter;
import com.example.android.movieapp.JsonUtils.MovieJsonUtil;
import com.example.android.movieapp.MovieKeys;
import com.example.android.movieapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private MovieViewModel movieViewModel;
    RecyclerView mRecyclerView;
    List<Movie> result = new ArrayList<>();
    private MovieAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView =  findViewById(R.id.movieRecycler);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager movieGrid = new GridLayoutManager(this, getSpanCount());
        mRecyclerView.setLayoutManager(movieGrid);
        myAdapter = new MovieAdapter(this,result);
        mRecyclerView.setAdapter(myAdapter);
        Log.v("change", "on create has been called ");

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getPopularMovies();
//        setTitle("Popular Movies");
        notifyObserver();
        getSpanCount();
    }

    //menu items for the user to choose between top rated or popular movies
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_query, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popularMovies:
                movieViewModel.getPopularMovies();
//                setTitle("Popular Movies");
                notifyObserver();
                return true;
            case R.id.topRated:
               movieViewModel.getHighRatedMovies();
//                setTitle("High Rated Movies");
               notifyObserver();
//                Log.v("change","high rated movies is called");
                return true;

            case R.id.favorite:
                movieViewModel.getFavoritesFromDatabase();
//                setTitle("Favorite Movies");
                notifyObserver();
//                Log.v("change","favorite movies from database is called");
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void notifyObserver(){
        movieViewModel.getDataFromNetwork().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable  List<Movie> movies) {
                myAdapter.updateMovieAdapter(movies);
            }
        });
    }
    //calculate with of divide by 250 to fill width equally
    public int getSpanCount(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels/250;
    }

    public void onResume() {
        super.onResume();
        notifyObserver();

    }

    public void onPause() {
        super.onPause();
        notifyObserver();
    }


}
