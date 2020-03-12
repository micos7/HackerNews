package com.example.hackernews;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.content.ContentValues.TAG;

public class ThreadAdapter extends  RecyclerView.Adapter<ThreadAdapter.ThreadViewHolder> {
    private List<DataResponse> mComments;

    Context context;


    public ThreadAdapter(List<DataResponse> comments) {
        mComments = comments;

    }

    public static class ThreadViewHolder extends RecyclerView.ViewHolder {
        public TextView cTextView;
        public RecyclerView cRecyclerView;


        public ThreadViewHolder(@NonNull View itemView) {
            super(itemView);
            cTextView = itemView.findViewById(R.id.threadTextView);
            cRecyclerView = itemView.findViewById(R.id.threadRecyclerView);
        }
    }

    @NonNull
    @Override
    public ThreadAdapter.ThreadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment, parent, false);
        ThreadAdapter.ThreadViewHolder cvh = new ThreadAdapter.ThreadViewHolder(v);
        return cvh;

    }


    @Override
    public void onBindViewHolder(@NonNull ThreadAdapter.ThreadViewHolder holder, int position) {


        if (mComments.get(position) != null) {
            DataResponse currentComment = mComments.get(position);



            holder.cRecyclerView.setHasFixedSize(false);
            holder.cRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


            if (currentComment.getText() != null) {

                Log.d(TAG, "COMENTU " + currentComment.getId());
                holder.cTextView.setTag(currentComment.getId());
                holder.cTextView.setText(Html.fromHtml(currentComment.getText()));
                holder.cTextView.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                holder.cRecyclerView.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public int getItemCount() {
        return null != mComments ? mComments.size() : 0;
    }

}
