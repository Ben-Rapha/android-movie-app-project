package com.example.android.movieapp.Repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.movieapp.model.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class  MovieRoomDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    private static MovieRoomDatabase INSTANCE;

    static MovieRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (MovieRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDatabase.class,"movie_database").build();
                }
            }
        }
        return INSTANCE;
    }

}

