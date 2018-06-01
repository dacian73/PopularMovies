package com.example.thephilosopherwanderer.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by alex on 04.03.2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {

    final private MyOnItemClickListener myOnItemClickListener;
    private LayoutInflater inflater;
    private Context context;
    private List<TrailerObject> trailersList;
    private String BASE_YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/";
    private String YOUTUBE_THUMBNAIL_END = "/0.jpg";

    TrailerAdapter(List<TrailerObject> trailersList, MyOnItemClickListener listener) {
        // initialize the click listener
        myOnItemClickListener = listener;
        this.trailersList = trailersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return trailersList.size();
    }

    // Interface for clicking
    public interface MyOnItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public ImageView thumbnail;

        MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            title = view.findViewById(R.id.trailer_name);
            thumbnail = view.findViewById(R.id.trailer_thumbnail);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            myOnItemClickListener.onListItemClick(clickedPosition);
        }

        void bind(int position) {
            TrailerObject currentTrailer = trailersList.get(position);
            title.setText(currentTrailer.getmName());
            String thumbnailURL = BASE_YOUTUBE_THUMBNAIL_URL + currentTrailer.getmKey() + YOUTUBE_THUMBNAIL_END;
            Picasso.with(context).load(thumbnailURL).into(thumbnail);
        }
    }
}
