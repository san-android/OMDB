package com.freshcodes.omdbapp.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.freshcodes.omdbapp.R;
import com.freshcodes.omdbapp.adapter.MovieDetailsAdapter;
import com.freshcodes.omdbapp.adapter.RatingsAdapter;
import com.freshcodes.omdbapp.globle.AppConstant;
import com.freshcodes.omdbapp.globle.AppMethod;
import com.freshcodes.omdbapp.imageloader.DisplayImageOption;
import com.freshcodes.omdbapp.model.MovieResponseModel;
import com.freshcodes.omdbapp.model.RatingsModel;
import com.freshcodes.omdbapp.ws.HttpRequest;
import com.freshcodes.omdbapp.ws.WSConfig;
import com.freshcodes.omdbapp.ws.WSListener;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.net.URLEncoder;
import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class MainActivity extends AppCompatActivity implements WSListener {
    Toolbar mToolbar;
    SearchView svSearch;

    LinearLayout layoutSearchMovie, llMain;

    ImageView imgPoster;
    TextView txtTitle;

    RecyclerView rvDetails;
    ArrayList<Pair<String, String>> movieDetailList;
    MovieDetailsAdapter movieDetailsAdapter;

    RecyclerView rvRatings;
    ArrayList<RatingsModel> ratingsModels;
    RatingsAdapter ratingsAdapter;

    Button btnAddReview;

    MovieResponseModel movieResponseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionBar();
        init();
        setUpEvents();
    }

    private void initActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init() {
        svSearch = (SearchView) findViewById(R.id.svSearch);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        svSearch.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        svSearch.setFocusable(false);

        layoutSearchMovie = (LinearLayout) findViewById(R.id.layoutSearchMovie);
        layoutSearchMovie.setVisibility(View.VISIBLE);

        llMain = (LinearLayout) findViewById(R.id.llMain);
        llMain.setVisibility(View.GONE);

        imgPoster = (ImageView) findViewById(R.id.imgPoster);
        txtTitle = (TextView) findViewById(R.id.txtTitle);

        rvDetails = (RecyclerView) findViewById(R.id.rvDetails);
        rvDetails.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvDetails.setItemAnimator(new SlideInLeftAnimator());
        movieDetailList = new ArrayList<>();
        movieDetailsAdapter = new MovieDetailsAdapter(MainActivity.this, movieDetailList);
        rvDetails.setAdapter(movieDetailsAdapter);

        rvRatings = (RecyclerView) findViewById(R.id.rvRatings);
        rvRatings.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        ratingsModels = new ArrayList<>();
        ratingsAdapter = new RatingsAdapter(MainActivity.this, ratingsModels);
        rvRatings.setAdapter(ratingsAdapter);

        btnAddReview = (Button) findViewById(R.id.btnAddReview);

        movieResponseModel = new MovieResponseModel();
    }

    private void setUpEvents() {
        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() >= 3) {
                    svSearch.clearFocus();
                    llMain.setVisibility(View.GONE);
                    search(query);
                } else {
                    Toast.makeText(MainActivity.this, "Keyword lenth too short!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddReviewActivity.class);
                intent.putExtra("movieDetails", movieResponseModel);
                startActivity(intent);
            }
        });
    }

    private void search(String keyword) {
        try {
            keyword = URLEncoder.encode(keyword, "utf-8");
            String url = String.format("%s&t=%s", WSConfig.URL_DATA, keyword);
            HttpRequest httpRequest = new HttpRequest(MainActivity.this, Request.Method.GET, WSConfig.WS_DATA, this);
            httpRequest.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillData(MovieResponseModel data) {
        ImageLoader.getInstance().displayImage(data.getPoster(), imgPoster, DisplayImageOption.getDisplayImage());

        txtTitle.setText(AppMethod.fromHtml(data.getTitle()));
        movieDetailList.clear();
        movieDetailList.add(Pair.create("Title", data.getTitle()));
        movieDetailList.add(Pair.create("Year", data.getYear()));
        movieDetailList.add(Pair.create("Rated", data.getRated()));
        movieDetailList.add(Pair.create("Released", data.getReleased()));
        movieDetailList.add(Pair.create("Runtime", data.getRuntime()));
        movieDetailList.add(Pair.create("Genre", data.getGenre()));
        movieDetailList.add(Pair.create("Director", data.getDirector()));
        movieDetailList.add(Pair.create("Writer", data.getWriter()));
        movieDetailList.add(Pair.create("Actors", data.getActors()));
        movieDetailList.add(Pair.create("Plot", data.getPlot()));
        movieDetailList.add(Pair.create("Language", data.getLanguage()));
        movieDetailList.add(Pair.create("Country", data.getCountry()));
        movieDetailList.add(Pair.create("Awards", data.getAwards()));
        movieDetailList.add(Pair.create("imdb Rating", data.getImdbRating()));
        movieDetailList.add(Pair.create("imdb Votes", data.getImdbVotes()));
        movieDetailList.add(Pair.create("Type", data.getType()));
        movieDetailList.add(Pair.create("Production", data.getProduction()));
        movieDetailList.add(Pair.create("Website", data.getWebsite()));

        movieDetailsAdapter.notifyDataSetChanged();

        ratingsModels.clear();
        ratingsModels.addAll(data.getRatings());
        ratingsAdapter.notifyDataSetChanged();

        layoutSearchMovie.setVisibility(View.GONE);
        llMain.setVisibility(View.VISIBLE);
    }

    @Override
    public void onWSSuccess(String WSType, String response) {
        if (WSType.equals(WSConfig.WS_DATA)) {
            try {
                movieResponseModel = new Gson().fromJson(response, MovieResponseModel.class);
                if (movieResponseModel.getResponse().equalsIgnoreCase("True")) {
                    fillData(movieResponseModel);
                } else {
                    AppMethod.showAlertWithOk(MainActivity.this, getString(R.string.app_name), AppConstant.SOMETHING_WRONG_TRY_AGAIN);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
