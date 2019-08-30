package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder;
        View view;
        switch (i)
        {
            case R.layout.item_tweet:
                view = LayoutInflater.from(context).inflate(R.layout.item_tweet,viewGroup,false);
                holder = new ViewHolder(view);
                break;
            case R.layout.item_tweet_image:
                view = LayoutInflater.from(context).inflate(R.layout.item_tweet_image,viewGroup,false);
                holder = new ViewHolder_Image(view);
                break;
            default:
                view = LayoutInflater.from(context).inflate(R.layout.item_tweet,viewGroup,false);
                holder = new ViewHolder(view);
                break;
        }
        return holder;

//        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet,viewGroup,false);
//        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        Tweet tweet = tweets.get(i);
        if (viewHolder instanceof ViewHolder) {
            ((ViewHolder) viewHolder).bind(tweet);
        } else if (viewHolder instanceof ViewHolder_Image) {

            ((ViewHolder_Image) viewHolder).bind(tweet);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(tweets.get(position).type!=null){
            if(tweets.get(position).type.contentEquals("photo"))
                return R.layout.item_tweet_image;
            else
            {
                return R.layout.item_tweet;
            }
        }
        else
        {
            return R.layout.item_tweet;
        }
    }

//
    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public void clear()
    {
        tweets.clear();
        notifyDataSetChanged();
    }

    public void addTweets(List<Tweet> tweetList)
    {
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }

    public void addTweet(Tweet tweet) {
        tweets.add(tweet);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView ivProfileImage;
        public TextView tvScreenName;
        public TextView tvBody;
        public TextView tvCreatedAt;
        public TextView tvReply;
        public TextView tvRetweet;
        public TextView tvLike;
        public RelativeLayout rvContainer;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvReply = itemView.findViewById(R.id.tvReply);
            tvRetweet = itemView.findViewById(R.id.tvRetweet);
            tvLike = itemView.findViewById(R.id.tvLike);
            rvContainer = itemView.findViewById(R.id.rvContainer);
        }

        public void bind(final Tweet tweet) {

            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvCreatedAt.setText(tweet.getFormattedTimestamp());
//        viewHolder.tvReply.setText(tweet.reply_count);
            tvRetweet.setText(String.valueOf(tweet.retweet_count));
            tvLike.setText(String.valueOf(tweet.favorite_count));

             Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);

            rvContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Detail_tweetActivity.class);

                    intent.putExtra("tweet", Parcels.wrap(tweet));

                    context.startActivity(intent);
                }
            });
        }
    }

    public class ViewHolder_Image extends RecyclerView.ViewHolder
    {
        public ImageView ivProfileImage;
        public ImageView ivImage;
        public TextView tvScreenName;
        public TextView tvBody;
        public TextView tvCreatedAt;
        public TextView tvReply;
        public TextView tvRetweet;
        public TextView tvLike;
        public RelativeLayout rvContainer;

        public ViewHolder_Image(@NonNull View itemView) {
            super(itemView);

            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvReply = itemView.findViewById(R.id.tvReply);
            tvRetweet = itemView.findViewById(R.id.tvRetweet);
            tvLike = itemView.findViewById(R.id.tvLike);
            rvContainer = itemView.findViewById(R.id.rvContainer);
        }

        public void bind(final Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvCreatedAt.setText(tweet.getFormattedTimestamp());
//        viewHolder.tvReply.setText(tweet.reply_count);
            tvRetweet.setText(String.valueOf(tweet.retweet_count));
            tvLike.setText(String.valueOf(tweet.favorite_count));

            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);
            Glide.with(context).load(tweet.media_url).into(ivImage);

            rvContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Detail_tweetActivity.class);

                    intent.putExtra("tweet", Parcels.wrap(tweet));

                    context.startActivity(intent);
                }
            });
        }
    }
}
