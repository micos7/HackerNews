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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.content.ContentValues.TAG;

public class SubCommentsAdapter extends RecyclerView.Adapter<SubCommentsAdapter.SubCommentViewHolder>  {
    private List<DataResponse> mSubComments;
    private TextView parTextView;

    public SubCommentsAdapter(List<DataResponse> comments,TextView parentTextView) {
        mSubComments = comments;
        parTextView = parentTextView;
    }

    public static class SubCommentViewHolder extends RecyclerView.ViewHolder {
        public TextView scTextView;
        private RelativeLayout cardView;


        public SubCommentViewHolder(@NonNull View itemView) {
            super(itemView);
            scTextView = itemView.findViewById(R.id.sub_comment);
            cardView = itemView.findViewById(R.id.commentLvl1Card);
        }
    }

    @NonNull
    @Override
    public SubCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcomment, parent, false);
        SubCommentViewHolder cvh = new SubCommentViewHolder(v);
        return cvh;

    }


    @Override
    public void onBindViewHolder(@NonNull SubCommentViewHolder holder, int position) {

        if (mSubComments.get(position) != null){
            DataResponse currentComment = mSubComments.get(position);

            Log.d(TAG, "TAGU "+parTextView.getTag());

            Log.d(TAG, "PARINTE "+currentComment.getParent());

            if (currentComment.getParent().equals(parTextView.getTag()) && currentComment.getText() != null){
                Log.d(TAG, "ADEVARAT");
                holder.scTextView.setText(Html.fromHtml(currentComment.getText()));
                holder.scTextView.setMovementMethod(LinkMovementMethod.getInstance());
            }else{
                holder.cardView.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public int getItemCount() {
        return  null!= mSubComments ? mSubComments.size():0;
    }




}
