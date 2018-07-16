package com.example.android.movieapp.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.movieapp.model.Movie;

import java.util.List;
@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("SELECT * from movie_database ORDER BY title ASC")
   LiveData<List<Movie>> getAllMovie();

    @Query("SELECT * FROM MOVIE_DATABASE WHERE movieID = :movieID")
    Movie getMovie(String movieID);

    @Query("DELETE FROM MOVIE_DATABASE WHERE movieID = :movieID")
    void deleteMovie(String movieID);

}
