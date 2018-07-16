package com.example.android.movieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.movieapp.R;
import com.example.android.movieapp.UI.DetailActivity;
import com.example.android.movieapp.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    private List<Movie> movieContainer;
    private Context mcontext;
    private final String BASEURL = "http://image.tmdb.org/t/p/";

    public MovieAdapter(Context context, List<Movie> movieHolder){
        this.mcontext = context;
        movieContainer = movieHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        ViewHolder(View dataView){
            super(dataView);
            imageView = dataView.findViewById(R.id.movieImageView);
        }
    }
    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
        return new ViewHolder(convertView);
    }
    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, final int position) {
        final Movie movieApp = movieContainer.get(position);
        StringBuilder imageUrl  = new StringBuilder(BASEURL);
        imageUrl.append("original/").append(movieApp.getMovieImage());
        Picasso.with(mcontext).load(imageUrl.
                toString()).
                fit().error(R.drawable.image1).
                placeholder(R.drawable.image1).
                into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("MOVIE_DATA", movieApp);
                view.getContext().startActivities(new Intent[]{intent});
//                Log.v("movie ID is", movieApp.getmMovieId());
            }
        });


    }
    @Override
    public int getItemCount() {
        return movieContainer.size();
    }

    public void updateMovieAdapter(List<Movie> update){
        this.movieContainer = update;
        notifyDataSetChanged();
    }



}

