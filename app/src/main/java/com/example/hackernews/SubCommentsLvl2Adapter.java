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

public class SubCommentsLvl2Adapter extends RecyclerView.Adapter<SubCommentsLvl2Adapter.SubCommentLvl2ViewHolder>  {

    private List<DataResponse> mSubCommentsLvl2;
    private TextView parTextView;

    public SubCommentsLvl2Adapter(List<DataResponse> comments, TextView parentTextView) {
        mSubCommentsLvl2 = comments;
        parTextView = parentTextView;
    }

    public static class SubCommentLvl2ViewHolder extends RecyclerView.ViewHolder {
        public TextView scTextView;
        private RelativeLayout cardView;


        public SubCommentLvl2ViewHolder(@NonNull View itemView) {
            super(itemView);
            scTextView = itemView.findViewById(R.id.sub_comment_lvl2);
            cardView = itemView.findViewById(R.id.commentLvl2Card);
        }
    }

    @NonNull
    @Override
    public SubCommentsLvl2Adapter.SubCommentLvl2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcommentlvl2, parent, false);
        SubCommentsLvl2Adapter.SubCommentLvl2ViewHolder cvh = new SubCommentsLvl2Adapter.SubCommentLvl2ViewHolder(v);
        return cvh;

    }


    @Override
    public void onBindViewHolder(@NonNull SubCommentsLvl2Adapter.SubCommentLvl2ViewHolder holder, int position) {

        if (mSubCommentsLvl2.get(position) != null) {
            DataResponse currentComment = mSubCommentsLvl2.get(position);

            Log.d(TAG, "LVL3 " + currentComment.getText());

//            Log.d(TAG, "TAGU " + parTextView.getTag());
//
//            Log.d(TAG, "PARINTE " + currentComment.getParent());

            if (currentComment.getParent().equals(parTextView.getTag()) && currentComment.getText() != null) {
//                Log.d(TAG, "ADEVARAT");
                holder.scTextView.setText(Html.fromHtml(currentComment.getText()));
                holder.scTextView.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                holder.cardView.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public int getItemCount() {
        return null != mSubCommentsLvl2 ? mSubCommentsLvl2.size() : 0;
    }
}
