package com.codepath.apps.restclienttemplate;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private RecyclerView rvTweets;
    private TweetsAdapter tweetsAdapter;
    private List<Tweet> tweets;

    private SwipeRefreshLayout  swipeContainer;

    private EndlessRecyclerViewScrollListener scrollListener;

    private Toolbar toolbar;

    private long last_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApp.getRestClient(this);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        rvTweets= findViewById(R.id.rvTweets);

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,

                android.R.color.holo_green_light,

                android.R.color.holo_orange_light,

                android.R.color.holo_red_light);


        tweets = new ArrayList<>();

        populateHomeTimeline();

        tweetsAdapter = new TweetsAdapter(this,tweets);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        rvTweets.setLayoutManager(linearLayoutManager);

        rvTweets.setAdapter(tweetsAdapter);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("TwitterClient","content is being refresh");
                populateHomeTimeline();
            }
        });

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager){
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(last_id);
            }
        };

        rvTweets.addOnScrollListener(scrollListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login,menu);
        return true;
    }

    private void loadNextDataFromApi(long page) {

        client.getNextPageOfTweets(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    last_id = response.getJSONObject(0).getLong("id");
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i=0; i<response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        if(jsonObject.getLong("id") < last_id)
                        {
                            last_id = jsonObject.getLong("id");
                        }
                        Tweet tweet =Tweet.fromJson(jsonObject);

                        tweetsAdapter.addTweet(tweet);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                tweetsAdapter.notifyDataSetChanged();

                scrollListener.resetState();

            }
        },page);
    }

    private void populateHomeTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                List<Tweet> TweetToAdd = new ArrayList<>();

                try
                {
                    last_id = response.getJSONObject(0).getLong("id");
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                for (int i=0; i<response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        if(jsonObject.getLong("id") < last_id)
                        {
                            last_id = jsonObject.getLong("id");
                        }

                        Tweet tweet =Tweet.fromJson(jsonObject);

                        TweetToAdd.add(tweet);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                tweetsAdapter.clear();

                tweetsAdapter.addTweets(TweetToAdd);

                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("TwitterClient ",responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("TwitterClient ",errorResponse.toString());
            }
        });
    }
}
