package com.codepath.apps.restclienttemplate.models;

import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tweet {
    public String body;
    public String createdAt;
    public User user;


    public static Tweet fromJson(JSONObject json) throws JSONException {
        Tweet tweet = new Tweet();

        tweet.body = json.getString("text");
        tweet.createdAt = json.getString("created_at");
        tweet.user = User.fromJson(json.getJSONObject("user"));

        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for(int i=0; i<jsonArray.length();i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    public String getFormattedTimestamp(){
        return TimeFormatter.getTimeDifference(createdAt);
    }

}