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

public class SubCommentsLvl3Adapter extends RecyclerView.Adapter<SubCommentsLvl3Adapter.SubCommentLvl3ViewHolder> {


    private List<DataResponse> mSubCommentsLvl3;
    private List<DataResponse> mSubCommentsLvl4;
    private List<DataResponse> mSubCommentsLvl5;
    private TextView parTextView;
    Context context;

    public SubCommentsLvl3Adapter(List<DataResponse> comments,List<DataResponse> commentsLvl4,List<DataResponse> commentsLvl5, TextView parentTextView) {
        mSubCommentsLvl3 = comments;
        mSubCommentsLvl4 = commentsLvl4;
        mSubCommentsLvl5 = commentsLvl5;
        parTextView = parentTextView;
    }

    public static class SubCommentLvl3ViewHolder extends RecyclerView.ViewHolder {
        public TextView scTextView;
        private RelativeLayout cardView;
        public RecyclerView lvl4RecyclerView;


        public SubCommentLvl3ViewHolder(@NonNull View itemView) {
            super(itemView);
            scTextView = itemView.findViewById(R.id.sub_comment_lvl3);
            cardView = itemView.findViewById(R.id.commentLvl3Card);
            lvl4RecyclerView  = itemView.findViewById(R.id.lvl4RecyclerView);
        }
    }

    @NonNull
    @Override
    public SubCommentsLvl3Adapter.SubCommentLvl3ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcommentlvl3, parent, false);
        SubCommentsLvl3Adapter.SubCommentLvl3ViewHolder cvh = new SubCommentsLvl3Adapter.SubCommentLvl3ViewHolder(v);
        return cvh;

    }


    @Override
    public void onBindViewHolder(@NonNull SubCommentsLvl3Adapter.SubCommentLvl3ViewHolder holder, int position) {

        if (mSubCommentsLvl3.get(position) != null) {
            DataResponse currentComment = mSubCommentsLvl3.get(position);

//            Log.d(TAG, "LVL3 " + currentComment.getText());

            SubCommentsLvl4Adapter subCommentsLvl4Adapter = new SubCommentsLvl4Adapter(mSubCommentsLvl3,mSubCommentsLvl4,mSubCommentsLvl5, holder.scTextView);

            holder.lvl4RecyclerView.setHasFixedSize(false);
            holder.lvl4RecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

            holder.lvl4RecyclerView.setAdapter(subCommentsLvl4Adapter);


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
        return null != mSubCommentsLvl3 ? mSubCommentsLvl3.size() : 0;
    }
}
