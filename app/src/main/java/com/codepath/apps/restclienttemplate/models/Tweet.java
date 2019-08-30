package com.codepath.apps.restclienttemplate.models;

import com.codepath.apps.restclienttemplate.TimeFormatter;
import com.codepath.apps.restclienttemplate.TimelineActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Tweet {
    public  String body;
    public  long uid;
    public  String createdAt;
    public String type;
    public String media_url;
    public int retweet_count;
    public int reply_count;
    public int favorite_count;
    public  User user;

    public static  Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.retweet_count = jsonObject.getInt("retweet_count");
//        tweet.reply_count = jsonObject.getInt("reply_count");
        tweet.favorite_count = jsonObject.getInt("favorite_count");

        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));

        JSONObject entities = jsonObject.getJSONObject("entities");
        if(entities.has("media")) {

            tweet.type = entities.getJSONArray("media").getJSONObject(0).getString("type");
            tweet.media_url = entities.getJSONArray("media").getJSONObject(0).getString("media_url");
        }
        else {
            tweet.type = null;
            tweet.media_url = null;
        }
        return tweet;
    }

    public String getFormattedTimestamp(){
       return TimeFormatter.getTimeDifference(createdAt);
    }
}
