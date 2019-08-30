package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class Detail_tweetActivity extends AppCompatActivity {

    Tweet tweet;
    public ImageView ivProfileImage;
    public ImageView ivImage;
    public TextView tvScreenName;
    public TextView tvBody;
    public TextView tvCreatedAt;
    public TextView tvReply;
    public TextView tvRetweet;
    public TextView tvLike;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        if(tweet.type!=null){
            setContentView(R.layout.item_tweet_image_detail);

            ivProfileImage = findViewById(R.id.ivProfileImage);
            ivImage = findViewById(R.id.ivImage);
            tvScreenName = findViewById(R.id.tvScreenName);
            tvCreatedAt = findViewById(R.id.tvCreatedAt);
            tvBody = findViewById(R.id.tvBody);
            tvReply = findViewById(R.id.tvReply);
            tvRetweet = findViewById(R.id.tvRetweet);
            tvLike = findViewById(R.id.tvLike);




            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvCreatedAt.setText(tweet.getFormattedTimestamp());
//        viewHolder.tvReply.setText(tweet.reply_count);
            tvRetweet.setText(String.valueOf(tweet.retweet_count));
            tvLike.setText(String.valueOf(tweet.favorite_count));

            Glide.with(this).load(tweet.user.profileImageUrl).into(ivProfileImage);
            Glide.with(this).load(tweet.media_url).into(ivImage);
        }
        else
        {
            setContentView(R.layout.item_tweet_detail);

            ivProfileImage = findViewById(R.id.ivProfileImage);
//            ivImage = findViewById(R.id.ivImage);
            tvScreenName = findViewById(R.id.tvScreenName);
            tvCreatedAt = findViewById(R.id.tvCreatedAt);
            tvBody = findViewById(R.id.tvBody);
            tvReply = findViewById(R.id.tvReply);
            tvRetweet = findViewById(R.id.tvRetweet);
            tvLike = findViewById(R.id.tvLike);


            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvCreatedAt.setText(tweet.getFormattedTimestamp());
//        viewHolder.tvReply.setText(tweet.reply_count);
            tvRetweet.setText(String.valueOf(tweet.retweet_count));
            tvLike.setText(String.valueOf(tweet.favorite_count));

            Glide.with(this).load(tweet.user.profileImageUrl).into(ivProfileImage);
//            Glide.with(this).load(tweet.media_url).into(ivImage);
        }



    }
}
