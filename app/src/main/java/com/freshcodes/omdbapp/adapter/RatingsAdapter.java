package com.freshcodes.omdbapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.freshcodes.omdbapp.R;
import com.freshcodes.omdbapp.globle.AppMethod;
import com.freshcodes.omdbapp.model.RatingsModel;

import java.util.ArrayList;

public class RatingsAdapter extends RecyclerView.Adapter<RatingsAdapter.ViewHolder> {
    Context context;
    ArrayList<RatingsModel> ratingsModels;
    LayoutInflater inflater;

    public RatingsAdapter(Context context, ArrayList<RatingsModel> ratingsModels) {
        this.context = context;
        this.ratingsModels = ratingsModels;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.adapter_ratings, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.txtSource.setText(AppMethod.fromHtml(AppMethod.getString(ratingsModels.get(i).getSource(), "")));
        viewHolder.txtValue.setText(AppMethod.fromHtml(AppMethod.getString(ratingsModels.get(i).getValue(), "")));
    }

    @Override
    public int getItemCount() {
        return ratingsModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtSource, txtValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSource = (TextView) itemView.findViewById(R.id.txtSource);
            txtValue = (TextView) itemView.findViewById(R.id.txtValue);
        }
    }
}