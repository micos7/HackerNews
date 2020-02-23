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

public class SubCommentsLvl6Adapter extends RecyclerView.Adapter<SubCommentsLvl6Adapter.SubCommentLvl6ViewHolder> {

    private List<DataResponse> mSubCommentsLvl4;
    private List<DataResponse> mSubCommentsLvl5;
    private List<DataResponse> mSubCommentsLvl6;
    private List<DataResponse> mSubCommentsLvl7;
    private List<DataResponse> mSubCommentsLvl8;
    private TextView parTextView;
    Context context;

    public SubCommentsLvl6Adapter(List<DataResponse> commentsLvl4, List<DataResponse> commentsLvl5,List<DataResponse> commentsLvl6,List<DataResponse> commentsLvl7,List<DataResponse> commentsLvl8, TextView parentTextView) {
        mSubCommentsLvl4 = commentsLvl4;
        mSubCommentsLvl5 = commentsLvl5;
        mSubCommentsLvl6 = commentsLvl6;
        mSubCommentsLvl7 = commentsLvl7;
        mSubCommentsLvl8 = commentsLvl8;
        parTextView = parentTextView;
    }

    public static class SubCommentLvl6ViewHolder extends RecyclerView.ViewHolder {
        public TextView scTextView;
        private RelativeLayout cardView;
        public RecyclerView lvl7RecyclerView;


        public SubCommentLvl6ViewHolder(@NonNull View itemView) {
            super(itemView);
            scTextView = itemView.findViewById(R.id.sub_comment_lvl6);
            cardView = itemView.findViewById(R.id.commentLvl6Card);
            lvl7RecyclerView = itemView.findViewById(R.id.lvl7RecyclerView);
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

        if (mSubCommentsLvl7 != null) {
            DataResponse currentComment = mSubCommentsLvl7.get(position);


            SubCommentsLvl7Adapter subCommentsLvl7Adapter = new SubCommentsLvl7Adapter(mSubCommentsLvl4,mSubCommentsLvl5,mSubCommentsLvl6,mSubCommentsLvl7,mSubCommentsLvl8, holder.scTextView);

            holder.lvl7RecyclerView.setHasFixedSize(false);
            holder.lvl7RecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

            holder.lvl7RecyclerView.setAdapter(subCommentsLvl7Adapter);

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
