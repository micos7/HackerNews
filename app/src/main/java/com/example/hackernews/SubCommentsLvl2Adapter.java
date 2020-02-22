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

public class SubCommentsLvl2Adapter extends RecyclerView.Adapter<SubCommentsLvl2Adapter.SubCommentLvl2ViewHolder>  {

    private List<DataResponse> mSubCommentsLvl2;
    private List<DataResponse> mSubCommentsLvl3;
    private List<DataResponse> mSubCommentsLvl4;
    private List<DataResponse> mSubCommentsLvl5;
    private List<DataResponse> mSubCommentsLvl6;
    private TextView parTextView;
    Context context;

    public SubCommentsLvl2Adapter(List<DataResponse> comments,List<DataResponse> commentsLvl3,List<DataResponse> commentsLvl4,List<DataResponse> commentsLvl5,List<DataResponse> commentsLvl6, TextView parentTextView) {
        mSubCommentsLvl2 = comments;
        mSubCommentsLvl3 = commentsLvl3;
        mSubCommentsLvl4 = commentsLvl4;
        mSubCommentsLvl5 = commentsLvl5;
        mSubCommentsLvl6 = commentsLvl6;
        parTextView = parentTextView;
    }

    public static class SubCommentLvl2ViewHolder extends RecyclerView.ViewHolder {
        public TextView scTextView;
        private RelativeLayout cardView;
        public RecyclerView lvl3RecyclerView;


        public SubCommentLvl2ViewHolder(@NonNull View itemView) {
            super(itemView);
            scTextView = itemView.findViewById(R.id.sub_comment_lvl2);
            cardView = itemView.findViewById(R.id.commentLvl2Card);
            lvl3RecyclerView  = itemView.findViewById(R.id.lvl3RecyclerView);
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

        if (mSubCommentsLvl2.get(position) != null && mSubCommentsLvl2.size() >0) {
            DataResponse currentComment = mSubCommentsLvl2.get(position);

//            Log.d(TAG, "LVL2 " + currentComment.getText());


            SubCommentsLvl3Adapter subCommentsLvl3Adapter = new SubCommentsLvl3Adapter(mSubCommentsLvl3,mSubCommentsLvl4,mSubCommentsLvl5,mSubCommentsLvl6, holder.scTextView);

            holder.lvl3RecyclerView.setHasFixedSize(false);
            holder.lvl3RecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

            holder.lvl3RecyclerView.setAdapter(subCommentsLvl3Adapter);

            if (currentComment.getParent().equals(parTextView.getTag()) && currentComment.getText() != null) {
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
        return null != mSubCommentsLvl2 ? mSubCommentsLvl2.size() : 0;
    }
}
