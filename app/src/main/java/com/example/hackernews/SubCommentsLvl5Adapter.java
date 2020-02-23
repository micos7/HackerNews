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

public class SubCommentsLvl5Adapter extends RecyclerView.Adapter<SubCommentsLvl5Adapter.SubCommentLvl5ViewHolder> {

    private List<DataResponse> mSubCommentsLvl4;
    private List<DataResponse> mSubCommentsLvl5;
    private List<DataResponse> mSubCommentsLvl6;
    private List<DataResponse> mSubCommentsLvl7;
    private List<DataResponse> mSubCommentsLvl8;
    private TextView parTextView;
    Context context;

    public SubCommentsLvl5Adapter(List<DataResponse> commentsLvl4, List<DataResponse> commentsLvl5,List<DataResponse> commentsLvl6,List<DataResponse> commentsLvl7,List<DataResponse> commentsLvl8, TextView parentTextView) {
        mSubCommentsLvl4 = commentsLvl4;
        mSubCommentsLvl5 = commentsLvl5;
        mSubCommentsLvl6 = commentsLvl6;
        mSubCommentsLvl7 = commentsLvl7;
        mSubCommentsLvl8 = commentsLvl8;
        parTextView = parentTextView;
    }

    public static class SubCommentLvl5ViewHolder extends RecyclerView.ViewHolder {
        public TextView scTextView;
        private RelativeLayout cardView;
        public RecyclerView lvl6RecyclerView;


        public SubCommentLvl5ViewHolder(@NonNull View itemView) {
            super(itemView);
            scTextView = itemView.findViewById(R.id.sub_comment_lvl5);
            cardView = itemView.findViewById(R.id.commentLvl5Card);
            lvl6RecyclerView = itemView.findViewById(R.id.lvl6RecyclerView);
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

        if (mSubCommentsLvl6 != null) {
            DataResponse currentComment = mSubCommentsLvl6.get(position);

            SubCommentsLvl6Adapter subCommentsLvl6Adapter = new SubCommentsLvl6Adapter(mSubCommentsLvl4,mSubCommentsLvl5,mSubCommentsLvl6,mSubCommentsLvl7,mSubCommentsLvl8, holder.scTextView);

            holder.lvl6RecyclerView.setHasFixedSize(false);
            holder.lvl6RecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

            holder.lvl6RecyclerView.setAdapter(subCommentsLvl6Adapter);



            if (currentComment.getParent().equals(parTextView.getTag()) && currentComment.getText() != null) {
//                Log.d(TAG, "LVL5 " + currentComment.getText());

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
