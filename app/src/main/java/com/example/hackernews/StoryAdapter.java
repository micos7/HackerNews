package com.example.hackernews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ExampleViewHolder> {
    private static final String TAG = "STORY";
    private List<DataResponse> mStories;
    private OnStoryListener mOnStoryListener;
    Context mContext;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextView;
        public TextView mTextViewScore;
        public TextView mTextViewDescendants;
        public TextView mTextViewUrl;
        public TextView mTextViewUser;
        public TextView sTextViewTitle;
        OnStoryListener onStoryListener;

        public ExampleViewHolder(@NonNull View itemView, OnStoryListener onStoryListener) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.textView);
            mTextViewScore = itemView.findViewById(R.id.textViewScore);
            mTextViewDescendants = itemView.findViewById(R.id.textViewDescendants);
            mTextViewUrl = itemView.findViewById(R.id.urlTxtView);
            mTextViewUser = itemView.findViewById(R.id.userTxtView);
            this.onStoryListener = onStoryListener;
            mTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onStoryListener.onStoryClick(getAdapterPosition());
        }
    }

    public StoryAdapter(List<DataResponse> stories, OnStoryListener onStoryListener, Context context) {
        mStories = stories;
        this.mOnStoryListener = onStoryListener;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.story, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mOnStoryListener);
        return evh;
    }

    public void clear() {

        mStories.clear();

        notifyDataSetChanged();

    }


    public void addAll(List<DataResponse> list) {

        mStories.addAll(list);

        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        DataResponse currentStory = mStories.get(position);

        holder.mTextView.setText(currentStory.getTitle());
        holder.mTextViewScore.setText(currentStory.getScore().toString());


        if (currentStory.getDescendants() != null) {
            holder.mTextViewDescendants.setText(currentStory.getDescendants().toString());
        }
        holder.mTextViewUrl.setText(currentStory.getUrl());
        holder.mTextViewUser.setText(currentStory.getBy());
        holder.mTextViewUrl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentStory.getUrl()));
                mContext.startActivity(intent);
            }
        });

        holder.mTextView.setTag(currentStory.getId());
    }

    @Override
    public int getItemCount() {
        return mStories.size();
    }

    public interface OnStoryListener {
        void onStoryClick(int position);
    }


}
