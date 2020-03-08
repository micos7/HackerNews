package com.example.hackernews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubmissionsAdapter extends RecyclerView.Adapter<SubmissionsAdapter.ExampleViewHolder> {

    private List<DataResponse> sStories;
    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.submission_comment);

        }

    }

    public SubmissionsAdapter(List<DataResponse> stories) {
        sStories = stories;
    }

    @NonNull
    @Override
    public SubmissionsAdapter.ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.submissions, parent, false);
        SubmissionsAdapter.ExampleViewHolder evh = new SubmissionsAdapter.ExampleViewHolder(v);
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

        holder.mTextView.setText(currentStory.getTitle());

    }

    @Override
    public int getItemCount() {
        return sStories.size();
    }


}