package com.example.hackernews;

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

    public static class ExampleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextView;
        public TextView mTextViewScore;
        OnStoryListener onStoryListener;

        public ExampleViewHolder(@NonNull View itemView, OnStoryListener onStoryListener) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.textView);
            mTextViewScore = itemView.findViewById(R.id.textViewScore);
            this.onStoryListener = onStoryListener;
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            onStoryListener.onStoryClick(getAdapterPosition());
        }
    }

    public StoryAdapter(List<DataResponse> stories, OnStoryListener onStoryListener) {
        mStories = stories;
        this.mOnStoryListener = onStoryListener;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.story, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mOnStoryListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        DataResponse currentStory = mStories.get(position);

        holder.mTextView.setText(currentStory.getTitle());
        holder.mTextViewScore.setText(currentStory.getScore().toString());
        Log.d(TAG, "IDUL "+currentStory.getId().toString());
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
