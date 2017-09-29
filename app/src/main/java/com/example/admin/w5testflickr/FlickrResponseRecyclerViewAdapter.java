package com.example.admin.w5testflickr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.w5testflickr.model.Item;

import java.util.List;

public class FlickrResponseRecyclerViewAdapter extends RecyclerView.Adapter<FlickrResponseRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "TAG";
    private final List<Item> mValues;
    private final OnListInteractionListener mListener;
    private final Context context;

    public FlickrResponseRecyclerViewAdapter(List<Item> items, OnListInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_flickrresponse, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).getTitle());
        holder.mAuthorView.setText(mValues.get(position).getAuthor());
        Log.d(TAG, "onBindViewHolder: " + mValues.get(position).getMedia().getM());
        Glide.with(context).load(mValues.get(position).getMedia().getM()).into(holder.mImageView);

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mListener.onLongClick(mValues.get(position));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mAuthorView;
        public final ImageView mImageView;
        public Item mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = view.findViewById(R.id.tvTitle);
            mAuthorView = view.findViewById(R.id.tvAuthor);
            mImageView = view.findViewById(R.id.ivImg);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mAuthorView.getText() + "'";
        }
    }

    public void addData(List<Item> items){
        for (Item i :
                items) {
            mValues.add(i);
            notifyDataSetChanged();
        }
    }

    interface OnListInteractionListener{
        void onLongClick(Item item);
    }
}
