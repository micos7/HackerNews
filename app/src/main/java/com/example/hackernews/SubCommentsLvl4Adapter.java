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

public class SubCommentsLvl4Adapter extends  RecyclerView.Adapter<SubCommentsLvl4Adapter.SubCommentLvl4ViewHolder> {

    private List<DataResponse> mSubCommentsLvl4;
    private List<DataResponse> mSubCommentsLvl5;
    private List<DataResponse> mSubCommentsLvl6;
    private List<DataResponse> mSubCommentsLvl7;
    private TextView parTextView;
    Context context;

    public SubCommentsLvl4Adapter(List<DataResponse> commentsLvl4,List<DataResponse> commentsLvl5,List<DataResponse> commentsLvl6,List<DataResponse> commentsLvl7, TextView parentTextView) {
        mSubCommentsLvl4 = commentsLvl4;
        mSubCommentsLvl5 = commentsLvl5;
        mSubCommentsLvl6 = commentsLvl6;
        mSubCommentsLvl7 = commentsLvl7;
        parTextView = parentTextView;
    }

    public static class SubCommentLvl4ViewHolder extends RecyclerView.ViewHolder {
        public TextView scTextView;
        private RelativeLayout cardView;
        public RecyclerView lvl5RecyclerView;


        public SubCommentLvl4ViewHolder(@NonNull View itemView) {
            super(itemView);
            scTextView = itemView.findViewById(R.id.sub_comment_lvl4);
            cardView = itemView.findViewById(R.id.commentLvl4Card);
            lvl5RecyclerView  = itemView.findViewById(R.id.lvl5RecyclerView);
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

            SubCommentsLvl5Adapter subCommentsLvl5Adapter = new SubCommentsLvl5Adapter(mSubCommentsLvl4,mSubCommentsLvl5,mSubCommentsLvl6,mSubCommentsLvl7, holder.scTextView);

            holder.lvl5RecyclerView.setHasFixedSize(false);
            holder.lvl5RecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

            holder.lvl5RecyclerView.setAdapter(subCommentsLvl5Adapter);




            if (currentComment.getParent().equals(parTextView.getTag()) && currentComment.getText() != null) {
                Log.d(TAG, "LVL5 " + currentComment.getText());

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
