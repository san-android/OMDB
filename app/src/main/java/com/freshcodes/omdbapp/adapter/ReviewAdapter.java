package com.freshcodes.omdbapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.freshcodes.omdbapp.R;
import com.freshcodes.omdbapp.globle.AppMethod;
import com.freshcodes.omdbapp.model.ReviewModel;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    Context context;
    ArrayList<ReviewModel> reviewModels;
    LayoutInflater inflater;

    public ReviewAdapter(Context context, ArrayList<ReviewModel> reviewModels) {
        this.context = context;
        this.reviewModels = reviewModels;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_review, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.txtName.setText(AppMethod.fromHtml(AppMethod.getString(reviewModels.get(i).getName(), "")));

        float rating = 0.0f;
        try {
            rating = Float.parseFloat(reviewModels.get(i).getRating());
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder.ratingBar.setRating(rating);

        viewHolder.txtReview.setText(AppMethod.fromHtml(AppMethod.getString(reviewModels.get(i).getReview(), "")));
    }

    @Override
    public int getItemCount() {
        return reviewModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtReview;
        AppCompatRatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtReview = (TextView) itemView.findViewById(R.id.txtReview);
            ratingBar = (AppCompatRatingBar) itemView.findViewById(R.id.ratingBar);
        }
    }
}