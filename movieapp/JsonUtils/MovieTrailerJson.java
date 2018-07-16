package com.example.android.movieapp.JsonUtils;

import com.example.android.movieapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieTrailerJson {
    private static final String RESULT = "results";
    private static final String KEY = "key";
    private static final String REVIEW_CONTENT = "content";


    public static Movie parseJasonTrailer(String json){
        Movie movieData = null;
        try {
            JSONObject dataJson = new JSONObject(json);
            JSONArray resultJson = dataJson.getJSONArray(RESULT);
            JSONObject movie = resultJson.getJSONObject(0);
            String trailerKey = movie.optString(KEY);
            movieData = new Movie(trailerKey);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieData;
    }

    public  static Movie parseJasonReview(String json){
        Movie movieData = null;
        try {
            JSONObject dataJson = new JSONObject(json);
            JSONArray resultJson = dataJson.getJSONArray(RESULT);
            JSONObject movie = resultJson.getJSONObject(0);
            String reviewContent = movie.optString(REVIEW_CONTENT);
            movieData = new Movie(reviewContent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieData;
    }
}
