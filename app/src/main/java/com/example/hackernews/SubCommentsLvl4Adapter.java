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

public class SubCommentsLvl4Adapter extends  RecyclerView.Adapter<SubCommentsLvl4Adapter.SubCommentLvl4ViewHolder> {

    private List<DataResponse> mSubCommentsLvl4;
    private List<DataResponse> mSubCommentsLvl5;
    private TextView parTextView;

    public SubCommentsLvl4Adapter(List<DataResponse> commentsLvl4,List<DataResponse> commentsLvl5, TextView parentTextView) {
        mSubCommentsLvl4 = commentsLvl4;
        mSubCommentsLvl5 = commentsLvl5;
        parTextView = parentTextView;
    }

    public static class SubCommentLvl4ViewHolder extends RecyclerView.ViewHolder {
        public TextView scTextView;
        private RelativeLayout cardView;


        public SubCommentLvl4ViewHolder(@NonNull View itemView) {
            super(itemView);
            scTextView = itemView.findViewById(R.id.sub_comment_lvl4);
            cardView = itemView.findViewById(R.id.commentLvl4Card);
        }
    }

    @NonNull
    @Override
    public SubCommentsLvl4Adapter.SubCommentLvl4ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcommentlvl4, parent, false);
        SubCommentsLvl4Adapter.SubCommentLvl4ViewHolder cvh = new SubCommentsLvl4Adapter.SubCommentLvl4ViewHolder(v);
        return cvh;

    }


    @Override
    public void onBindViewHolder(@NonNull SubCommentsLvl4Adapter.SubCommentLvl4ViewHolder holder, int position) {

        if (mSubCommentsLvl5 != null) {
            DataResponse currentComment = mSubCommentsLvl5.get(position);



//            Log.d(TAG, "TAGU " + parTextView.getTag());
//
//            Log.d(TAG, "PARINTE " + currentComment.getParent());

            if (currentComment.getParent().equals(parTextView.getTag()) && currentComment.getText() != null) {
                Log.d(TAG, "LVL4 " + currentComment.getText());
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
        return null != mSubCommentsLvl5 ? mSubCommentsLvl5.size() : 0;
    }
}
