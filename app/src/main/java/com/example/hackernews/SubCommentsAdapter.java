package com.example.hackernews;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.content.ContentValues.TAG;

public class SubCommentsAdapter extends RecyclerView.Adapter<SubCommentsAdapter.SubCommentViewHolder> {
    private List<DataResponse> mSubComments;
    private List<DataResponse> mSubCommentsLvl2;
    private List<DataResponse> mSubCommentsLvl3;
    private List<DataResponse> mSubCommentsLvl4;
    private List<DataResponse> mSubCommentsLvl5;
    private TextView parTextView;
    Context context;

    public SubCommentsAdapter(List<DataResponse> comments,List<DataResponse> commentsLvl2,List<DataResponse> commentsLvl3,List<DataResponse> commentsLvl4,List<DataResponse> commentsLvl5, TextView parentTextView) {
        mSubComments = comments;
        mSubCommentsLvl2 = commentsLvl2;
        mSubCommentsLvl3 = commentsLvl3;
        mSubCommentsLvl4 = commentsLvl4;
        mSubCommentsLvl5 = commentsLvl5;
        parTextView = parentTextView;
    }

    public static class SubCommentViewHolder extends RecyclerView.ViewHolder {
        public TextView scTextView;
        private RelativeLayout cardView;
        public RecyclerView lvl2RecyclerView;


        public SubCommentViewHolder(@NonNull View itemView) {
            super(itemView);
            scTextView = itemView.findViewById(R.id.sub_comment);
            cardView = itemView.findViewById(R.id.commentLvl1Card);
            lvl2RecyclerView  = itemView.findViewById(R.id.lvl2RecyclerView);
        }
    }

    @NonNull
    @Override
    public SubCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcommentlvl1, parent, false);
        SubCommentViewHolder cvh = new SubCommentViewHolder(v);
        return cvh;

    }


    @Override
    public void onBindViewHolder(@NonNull SubCommentViewHolder holder, int position) {

        if (mSubComments.get(position) != null) {
            DataResponse currentComment = mSubComments.get(position);

            DataResponse currentSubComment = mSubComments.get(position);


            Log.d(TAG, "LVL1 "+currentComment.getText());

            SubCommentsLvl2Adapter subCommentsLvl2Adapter = new SubCommentsLvl2Adapter(mSubCommentsLvl2,mSubCommentsLvl3,mSubCommentsLvl4,mSubCommentsLvl5, holder.scTextView);

            holder.lvl2RecyclerView.setHasFixedSize(false);
            holder.lvl2RecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

            holder.lvl2RecyclerView.setAdapter(subCommentsLvl2Adapter);

            if (currentComment.getParent().equals(parTextView.getTag()) && currentComment.getText() != null) {
                Log.d(TAG, "ADEVARAT");
                holder.scTextView.setText(Html.fromHtml(currentComment.getText()));
                holder.scTextView.setTag(currentComment.getId());
                holder.scTextView.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                holder.cardView.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public int getItemCount() {
        return null != mSubComments ? mSubComments.size() : 0;
    }


}
