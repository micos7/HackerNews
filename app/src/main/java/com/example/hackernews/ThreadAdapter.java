package com.example.hackernews;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ThreadAdapter extends  RecyclerView.Adapter<ThreadAdapter.ThreadViewHolder> {
    private List<DataResponse> mComments = new ArrayList<>();
    Context context;


    public ThreadAdapter(List<DataResponse> comments) {
        mComments = comments;
        Log.d(TAG, "ThreadAdapter: "+mComments.size());

    }

    public static class ThreadViewHolder extends RecyclerView.ViewHolder {
        public TextView cTextView;
        public TextView pTextView;
        public RecyclerView cRecyclerView;


        public ThreadViewHolder(@NonNull View itemView) {
            super(itemView);
            cTextView = itemView.findViewById(R.id.threadoriginal);
            pTextView = itemView.findViewById(R.id.threadParent);
            cRecyclerView = itemView.findViewById(R.id.threadRecyclerView);
        }
    }

    @NonNull
    @Override
    public ThreadAdapter.ThreadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.thread, parent, false);
        ThreadAdapter.ThreadViewHolder cvh = new ThreadAdapter.ThreadViewHolder(v);
        return cvh;

    }


    @Override
    public void onBindViewHolder(@NonNull ThreadAdapter.ThreadViewHolder holder, int position) {


        Log.d(TAG, "plmmmm "+mComments.get(position).getTitle());
        if (mComments.get(position) != null) {
            DataResponse currentComment = mComments.get(position);
            holder.cTextView.setText(Html.fromHtml(currentComment.getTitle()));
            holder.cTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), StoryActivity.class);
                    intent.putExtra("STORY", mComments.get(position));
                    v.getContext().startActivity(intent);
                }
            });

            Log.d(TAG, "plm1 "+ holder.cTextView.getText());

        }

    }

    @Override
    public int getItemCount() {
        return null != mComments ? mComments.size() : 0;
    }

}
