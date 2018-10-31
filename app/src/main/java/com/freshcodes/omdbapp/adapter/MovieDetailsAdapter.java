package com.freshcodes.omdbapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.freshcodes.omdbapp.R;
import com.freshcodes.omdbapp.globle.AppMethod;

import java.util.ArrayList;

public class MovieDetailsAdapter extends RecyclerView.Adapter<MovieDetailsAdapter.ViewHolder> {
    Context context;
    ArrayList<Pair<String, String>> movieDetailList;
    LayoutInflater inflater;

    public MovieDetailsAdapter(Context context, ArrayList<Pair<String, String>> movieDetailList) {
        this.context = context;
        this.movieDetailList = movieDetailList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_movie_details, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.txtTitle.setText(AppMethod.fromHtml(AppMethod.getString(movieDetailList.get(i).first, "")));
        viewHolder.txtDescription.setText(AppMethod.fromHtml(AppMethod.getString(movieDetailList.get(i).second, "")));
    }

    @Override
    public int getItemCount() {
        return movieDetailList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
        }
    }
}