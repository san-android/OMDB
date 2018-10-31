package com.freshcodes.omdbapp.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freshcodes.omdbapp.R;
import com.freshcodes.omdbapp.adapter.ReviewAdapter;
import com.freshcodes.omdbapp.globle.AppConstant;
import com.freshcodes.omdbapp.globle.AppMethod;
import com.freshcodes.omdbapp.model.GetReviewResponseModel;
import com.freshcodes.omdbapp.model.MovieResponseModel;
import com.freshcodes.omdbapp.model.PostReviewResponseModel;
import com.freshcodes.omdbapp.model.ReviewModel;
import com.freshcodes.omdbapp.ws.HttpRequest;
import com.freshcodes.omdbapp.ws.WSConfig;
import com.freshcodes.omdbapp.ws.WSListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class AddReviewActivity extends AppCompatActivity implements WSListener {
    MovieResponseModel movieDetails;
    Toolbar mToolbar;

    LinearLayout llPostReview;

    EditText etName, etReview;
    AppCompatRatingBar ratingBar;
    Button btnAddReview;

    TextView txtOverallRating;

    RecyclerView rvReviews;
    ArrayList<ReviewModel> reviewModels;
    ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        initArguments();
        initActionBar();
        init();
        setUpEvents();
        getReview();
    }

    private void initArguments() {
        movieDetails = (MovieResponseModel) getIntent().getSerializableExtra("movieDetails");
    }

    private void initActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Post Review");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init() {
        llPostReview = (LinearLayout) findViewById(R.id.llPostReview);

        etName = (EditText) findViewById(R.id.etName);
        etName.setText(AppMethod.getStringPreference(AddReviewActivity.this, AppConstant.PREF_USER_NAME));
        etReview = (EditText) findViewById(R.id.etReview);
        ratingBar = (AppCompatRatingBar) findViewById(R.id.ratingBar);

        btnAddReview = (Button) findViewById(R.id.btnAddReview);

        txtOverallRating = (TextView) findViewById(R.id.txtOverallRating);

        rvReviews = (RecyclerView) findViewById(R.id.rvReviews);
        rvReviews.setLayoutManager(new LinearLayoutManager(AddReviewActivity.this));
        reviewModels = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(AddReviewActivity.this, reviewModels);
        rvReviews.setAdapter(reviewAdapter);
    }

    private void setUpEvents() {
        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                float rate = ratingBar.getRating();
                String review = etReview.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    etName.setError("Name should not be blank!");
                } else if (name.length() < 3) {
                    etName.setError("Invalid Name!");
                } else if (rate == 0) {
                    AppMethod.showAlertWithOk(AddReviewActivity.this, getString(R.string.app_name), "Rating should not be zero!");
                } else if (TextUtils.isEmpty(review)) {
                    etReview.setError("Review should not blank!");
                } else {
                    postReview(name, rate, review);
                }
            }
        });
    }

    private void postReview(String name, float rate, String review) {
        try {
            JSONObject reqBody = new JSONObject();
            reqBody.put("imdbID", movieDetails.getImdbID());
            reqBody.put("name", name);
            reqBody.put("rating", rate);
            reqBody.put("review", review);

            HttpRequest httpRequest = new HttpRequest(AddReviewActivity.this, HttpRequest.POST, reqBody, WSConfig.WS_POST_REVIEW, this);
            httpRequest.execute(WSConfig.URL_POST_REVIEW);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getReview() {
        try {
            JSONObject reqBody = new JSONObject();
            reqBody.put("imdbID", movieDetails.getImdbID());

            HttpRequest httpRequest = new HttpRequest(AddReviewActivity.this, HttpRequest.POST, reqBody, WSConfig.WS_GET_REVIEW, this);
            httpRequest.execute(WSConfig.URL_GET_REVIEW);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWSSuccess(String WSType, String response) {
        if (WSType.equals(WSConfig.WS_POST_REVIEW)) {
            try {
                PostReviewResponseModel postReviewResponseModel = new Gson().fromJson(response, PostReviewResponseModel.class);
                if (postReviewResponseModel.getStatus() == 1) {
                    AppMethod.showPositiveActionDialog(AddReviewActivity.this, getString(R.string.app_name), postReviewResponseModel.getMessage(), false, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            etReview.setText("");
                            ratingBar.setRating(0.0f);
                            getReview();
                        }
                    });
                } else {
                    AppMethod.showAlertWithOk(AddReviewActivity.this, getString(R.string.app_name), postReviewResponseModel.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (WSType.equals(WSConfig.WS_GET_REVIEW)) {
            try {
                GetReviewResponseModel getReviewResponseModel = new Gson().fromJson(response, GetReviewResponseModel.class);
                if (getReviewResponseModel.getStatus() == 1) {
                    float overAllRating = 0.0f;
                    try {
                        overAllRating = Float.parseFloat(getReviewResponseModel.getAvgrate());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (overAllRating > 0) {
                        txtOverallRating.setText(String.format("Average : %.02f/5", overAllRating));
                    } else {
                        txtOverallRating.setText("No reviews posted yet!");
                    }

                    reviewModels.clear();
                    reviewModels.addAll(getReviewResponseModel.getData());
                    reviewAdapter.notifyDataSetChanged();
                } else {
                    AppMethod.showAlertWithOk(AddReviewActivity.this, getString(R.string.app_name), getReviewResponseModel.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}