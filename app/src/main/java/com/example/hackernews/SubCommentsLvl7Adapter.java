package com.example.hackernews;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.content.ContentValues.TAG;

public class SubCommentsLvl7Adapter extends RecyclerView.Adapter<SubCommentsLvl7Adapter.SubCommentLvl7ViewHolder> {

    private List<DataResponse> mSubCommentsLvl4;
    private List<DataResponse> mSubCommentsLvl5;
    private List<DataResponse> mSubCommentsLvl6;
    private List<DataResponse> mSubCommentsLvl7;
    private List<DataResponse> mSubCommentsLvl8;
    private TextView parTextView;

    public SubCommentsLvl7Adapter(List<DataResponse> commentsLvl4, List<DataResponse> commentsLvl5, List<DataResponse> commentsLvl6, List<DataResponse> commentsLvl7,List<DataResponse> commentsLvl8, TextView parentTextView) {
        mSubCommentsLvl4 = commentsLvl4;
        mSubCommentsLvl5 = commentsLvl5;
        mSubCommentsLvl6 = commentsLvl6;
        mSubCommentsLvl7 = commentsLvl7;
        mSubCommentsLvl8 = commentsLvl8;
        parTextView = parentTextView;
    }

    public static class SubCommentLvl7ViewHolder extends RecyclerView.ViewHolder {
        public TextView scTextView;
        private RelativeLayout cardView;


        public SubCommentLvl7ViewHolder(@NonNull View itemView) {
            super(itemView);
            scTextView = itemView.findViewById(R.id.sub_comment_lvl7);
            cardView = itemView.findViewById(R.id.commentLvl7Card);
        }
    }

    @NonNull
    @Override
    public SubCommentsLvl7Adapter.SubCommentLvl7ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcommentlvl7, parent, false);
        SubCommentsLvl7Adapter.SubCommentLvl7ViewHolder cvh = new SubCommentsLvl7Adapter.SubCommentLvl7ViewHolder(v);
        return cvh;

    }


    @Override
    public void onBindViewHolder(@NonNull SubCommentsLvl7Adapter.SubCommentLvl7ViewHolder holder, int position) {

        if (mSubCommentsLvl8 != null) {
            DataResponse currentComment = mSubCommentsLvl8.get(position);



            if (currentComment.getParent().equals(parTextView.getTag()) && currentComment.getText() != null) {
                Log.d(TAG, "LVL7 " + currentComment.getText());
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
        return null != mSubCommentsLvl8 ? mSubCommentsLvl8.size() : 0;
    }
}
