package com.example.hackernews;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubmissionsAdapter extends RecyclerView.Adapter<SubmissionsAdapter.ExampleViewHolder> {

    private List<DataResponse> sStories;
    private OnStoryListener mOnStoryListener;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextView;
        public TextView lTextView;
        OnStoryListener onStoryListener;

        public ExampleViewHolder(@NonNull View itemView, OnStoryListener onStoryListener) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.submission_comment);
            lTextView = itemView.findViewById(R.id.subLinkTextView);
            this.onStoryListener = onStoryListener;
            lTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onStoryListener.onStoryClick(getAdapterPosition());
        }
    }

    public SubmissionsAdapter(List<DataResponse> stories, OnStoryListener onStoryListener) {
        sStories = stories;
        mOnStoryListener = onStoryListener;
    }

    @NonNull
    @Override
    public SubmissionsAdapter.ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.submissions, parent, false);
        SubmissionsAdapter.ExampleViewHolder evh = new SubmissionsAdapter.ExampleViewHolder(v, mOnStoryListener);
        return evh;
    }

    public void clear() {

        sStories.clear();

        notifyDataSetChanged();

    }


    public void addAll(List<DataResponse> list) {

        sStories.addAll(list);

        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(@NonNull SubmissionsAdapter.ExampleViewHolder holder, int position) {
        DataResponse currentStory = sStories.get(position);

        if (currentStory.getTitle() != null) {
            holder.mTextView.setText(currentStory.getTitle());
            holder.lTextView.setText("View story");
        } else {
            if (currentStory.getText() != null) {
                holder.mTextView.setText(Html.fromHtml(currentStory.getText()));
                holder.lTextView.setText("View thread");
            }
        }


    }

    @Override
    public int getItemCount() {
        return sStories.size();
    }

    public interface OnStoryListener {
        void onStoryClick(int position);
    }


}
