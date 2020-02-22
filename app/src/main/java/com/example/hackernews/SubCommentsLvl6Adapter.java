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

public class SubCommentsLvl6Adapter extends RecyclerView.Adapter<SubCommentsLvl6Adapter.SubCommentLvl6ViewHolder> {

    private List<DataResponse> mSubCommentsLvl4;
    private List<DataResponse> mSubCommentsLvl5;
    private List<DataResponse> mSubCommentsLvl6;
    private List<DataResponse> mSubCommentsLvl7;
    private TextView parTextView;

    public SubCommentsLvl6Adapter(List<DataResponse> commentsLvl4, List<DataResponse> commentsLvl5,List<DataResponse> commentsLvl6,List<DataResponse> commentsLvl7, TextView parentTextView) {
        mSubCommentsLvl4 = commentsLvl4;
        mSubCommentsLvl5 = commentsLvl5;
        mSubCommentsLvl6 = commentsLvl6;
        mSubCommentsLvl7 = commentsLvl7;
        parTextView = parentTextView;
    }

    public static class SubCommentLvl6ViewHolder extends RecyclerView.ViewHolder {
        public TextView scTextView;
        private RelativeLayout cardView;


        public SubCommentLvl6ViewHolder(@NonNull View itemView) {
            super(itemView);
            scTextView = itemView.findViewById(R.id.sub_comment_lvl6);
            cardView = itemView.findViewById(R.id.commentLvl6Card);
        }
    }

    @NonNull
    @Override
    public SubCommentsLvl6Adapter.SubCommentLvl6ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcommentlvl6, parent, false);
        SubCommentsLvl6Adapter.SubCommentLvl6ViewHolder cvh = new SubCommentsLvl6Adapter.SubCommentLvl6ViewHolder(v);
        return cvh;

    }


    @Override
    public void onBindViewHolder(@NonNull SubCommentsLvl6Adapter.SubCommentLvl6ViewHolder holder, int position) {

        if (mSubCommentsLvl5 != null) {
            DataResponse currentComment = mSubCommentsLvl7.get(position);



//            Log.d(TAG, "TAGU " + parTextView.getTag());
//
//            Log.d(TAG, "PARINTE " + currentComment.getParent());

            if (currentComment.getParent().equals(parTextView.getTag()) && currentComment.getText() != null) {
                Log.d(TAG, "LVL6 " + currentComment.getText());
//                Log.d(TAG, "ADEVARAT");
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
        return null != mSubCommentsLvl7 ? mSubCommentsLvl7.size() : 0;
    }
}
