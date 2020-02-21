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

public class SubCommentsLvl5Adapter extends RecyclerView.Adapter<SubCommentsLvl5Adapter.SubCommentLvl5ViewHolder> {

    private List<DataResponse> mSubCommentsLvl4;
    private List<DataResponse> mSubCommentsLvl5;
    private List<DataResponse> mSubCommentsLvl6;
    private TextView parTextView;

    public SubCommentsLvl5Adapter(List<DataResponse> commentsLvl4, List<DataResponse> commentsLvl5,List<DataResponse> commentsLvl6, TextView parentTextView) {
        mSubCommentsLvl4 = commentsLvl4;
        mSubCommentsLvl5 = commentsLvl5;
        mSubCommentsLvl6 = commentsLvl6;
        parTextView = parentTextView;
    }

    public static class SubCommentLvl5ViewHolder extends RecyclerView.ViewHolder {
        public TextView scTextView;
        private RelativeLayout cardView;


        public SubCommentLvl5ViewHolder(@NonNull View itemView) {
            super(itemView);
            scTextView = itemView.findViewById(R.id.sub_comment_lvl5);
            cardView = itemView.findViewById(R.id.commentLvl5Card);
        }
    }

    @NonNull
    @Override
    public SubCommentsLvl5Adapter.SubCommentLvl5ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcommentlvl5, parent, false);
        SubCommentsLvl5Adapter.SubCommentLvl5ViewHolder cvh = new SubCommentsLvl5Adapter.SubCommentLvl5ViewHolder(v);
        return cvh;

    }


    @Override
    public void onBindViewHolder(@NonNull SubCommentsLvl5Adapter.SubCommentLvl5ViewHolder holder, int position) {

        if (mSubCommentsLvl5 != null) {
            DataResponse currentComment = mSubCommentsLvl6.get(position);



//            Log.d(TAG, "TAGU " + parTextView.getTag());
//
//            Log.d(TAG, "PARINTE " + currentComment.getParent());

            if (currentComment.getParent().equals(parTextView.getTag()) && currentComment.getText() != null) {
                Log.d(TAG, "LVL5 " + currentComment.getText());
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
        return null != mSubCommentsLvl6 ? mSubCommentsLvl6.size() : 0;
    }
}
