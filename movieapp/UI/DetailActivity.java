package com.example.android.movieapp.UI;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.movieapp.JsonUtils.MovieTrailerJson;
import com.example.android.movieapp.Repository.MovieRepository;
import com.example.android.movieapp.Repository.ViewModels.MovieViewModel;
import com.example.android.movieapp.model.Movie;
import com.example.android.movieapp.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity {
    String movieID;
    ImageView imageView;
    TextView movieTitle;
    TextView moviePlot;
    TextView ratings;
    TextView releaseDate;
    RatingBar rateStar;
    FloatingActionButton starButton;
    private  ImageView playTrailer;
    private TextView trailerTextView;
    private MovieViewModel movieViewModel;
    TextView contentReview;
    Movie content;
    static Movie trailerResult = null;
    LiveData<List<Movie>> temp ;
    private MovieRepository movieRepository;

    private final String BASE_URL = "http://image.tmdb.org/t/p/";
    StringBuilder imageUrl  = new StringBuilder(BASE_URL);
    static String movieTrailerYoutube = "https://www.youtube.com/watch?v=";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        playTrailer = findViewById(R.id.playTrailer);
        playTrailer.setImageResource(R.drawable.play_icon);
        starButton = findViewById(R.id.floatingActionButton);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        populateUI();
    }
    public void  populateUI(){
        imageView = findViewById(R.id.imageView3);
        movieTitle = findViewById(R.id.textView2);
        moviePlot = findViewById(R.id.moviePlot2);
        ratings = findViewById(R.id.ratings);
        releaseDate = findViewById(R.id.releaseDate2);
        rateStar = findViewById(R.id.ratingBar);
        contentReview = findViewById(R.id.reviewContent);
        final Movie movieData =  getIntent().getParcelableExtra("MOVIE_DATA");
        movieID = movieData.getMovieId();
        setTitle(movieData.getMovieTitle());
        movieTitle.setText(movieData.getMovieTitle());
        moviePlot.setText(movieData.getMoviePlot());
        ratings.setText(movieData.getMovieRating());
        releaseDate.setText(movieData.getReleaseDate());
        rateStar.setRating(Float.parseFloat(movieData.getMovieRating()));
        if(movieData.getBackDrop().isEmpty() || movieData.getBackDrop().equals("null")){
            imageUrl.append("original/").append(movieData.getMovieImage());
            Picasso.with(getApplicationContext()).
                    load(imageUrl.toString())
                    .error(R.drawable.image1)
                    .placeholder(R.drawable.image1)
                    .into(imageView);
            return;
        }
        imageUrl.append("original/").append(movieData.getBackDrop());
        Picasso.with(getApplicationContext()).load(imageUrl.toString()).into(imageView);
        String trailerUrl = "https://api.themoviedb.org/3/movie/" +
                movieID +"/videos?api_key=af6a7bb450028de8aefd99dec516ebb4&language=en-US";
        String reviewsUrl = "https://api.themoviedb.org/3/movie/" +
                movieID+"/reviews?api_key=af6a7bb450028de8aefd99dec516ebb4&language=en-US&page=1";
        TrailerAsyncTask asyncTask1 = new TrailerAsyncTask();
        ReviewAsyncTask asyncTask2  = new ReviewAsyncTask();
        asyncTask1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,trailerUrl);
        asyncTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, reviewsUrl);
        playTrailer = findViewById(R.id.playTrailer);
        trailerTextView = findViewById(R.id.playTrailer2);
        Log.v("change","the movie id before the click is: "+movieID);

        starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Movie temp2 = movieViewModel.getMovieID(movieID);
                if(temp2 == null){
                    Log.v("change", " movie is null");
                    movieViewModel.insert(movieData);
                } else if (temp2.getMovieId().equals(movieData.getMovieId())){
                    Log.v("change", ": "+movieData.getMovieTitle()+ " was deleted from database");
                    movieViewModel.delete(movieID);
                }

            }
        });
    }

    public class playTrailerListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(trailerResult != null){
                Intent openTrailer = new Intent(Intent.ACTION_VIEW, Uri.parse(movieTrailerYoutube + trailerResult.getMovieId()));
                startActivity(openTrailer);
            }
            else{
                Toast.makeText(DetailActivity.this, "SORRY THERE IS NO TRAILER FOR THIS MOVIE",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }


    private class TrailerAsyncTask extends AsyncTask<String, Void, Movie> {
        OkHttpClient client = new OkHttpClient();
        String dataHandler;
         Movie result =  null;
        @Override
        protected Movie doInBackground(String... strings) {
            Request request = new Request.Builder().url(strings[0]).build();
            try {
                Response response = client.newCall(request).execute();
                dataHandler = response.body().string();
                result = MovieTrailerJson.parseJasonTrailer(dataHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
        protected void onPostExecute(Movie movieTrailer) {
           trailerResult = movieTrailer;
            playTrailerListener playTrailerListener = new playTrailerListener();
            playTrailer.setOnClickListener(playTrailerListener);
            trailerTextView.setOnClickListener(playTrailerListener);
        }
    }
    private class ReviewAsyncTask extends AsyncTask<String, Void, Movie> {
        OkHttpClient client = new OkHttpClient();
        String dataHandler;
        Movie result =  null;
        @Override
        protected Movie doInBackground(String... strings) {
            Request request = new Request.Builder().url(strings[0]).build();
            try {
                Response response = client.newCall(request).execute();
                dataHandler = response.body().string();
                result = MovieTrailerJson.parseJasonReview(dataHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
        protected void onPostExecute(Movie movieTrailer) {
            content= movieTrailer;
            if(content != null){
                contentReview.setText(content.getMovieId());
            } else{
                Toast.makeText(DetailActivity.this, "SORRY THERE IS NO REVIEW FOR THIS MOVIE",
                        Toast.LENGTH_SHORT).show();
            }


        }
    }

}
