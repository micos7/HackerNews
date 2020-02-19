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

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {
    private List<DataResponse> mComments;
    private List<DataResponse> subCommentsLvl1;
    private List<DataResponse> subCommentsLvl2;
    private List<DataResponse> subCommentsLvl3;
    Context context;


    public CommentsAdapter(List<DataResponse> comments, List<DataResponse> sComments, List<DataResponse> tComments, List<DataResponse> thComments) {
        mComments = comments;
        subCommentsLvl1 = sComments;
        subCommentsLvl2 = tComments;
        subCommentsLvl3 = thComments;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView cTextView;
        public RecyclerView cRecyclerView;


        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            cTextView = itemView.findViewById(R.id.commentTextView);
            cRecyclerView = itemView.findViewById(R.id.comRecyclerView);
        }
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment, parent, false);
        CommentViewHolder cvh = new CommentViewHolder(v);
        return cvh;

    }


    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {


        if (mComments.get(position) != null) {
            DataResponse currentComment = mComments.get(position);


            SubCommentsAdapter subCommentsAdapter = new SubCommentsAdapter(subCommentsLvl1,subCommentsLvl2,subCommentsLvl3, holder.cTextView);

            holder.cRecyclerView.setHasFixedSize(false);
            holder.cRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

            holder.cRecyclerView.setAdapter(subCommentsAdapter);


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
