package com.example.aliceanglesjo.marvel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aliceanglesjo on 2018-05-08.
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder>{
    public List<Film> mDataset;

    public interface OnItemClickListener {
        void onItemClick(Film item);
    }
    private final OnItemClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public TextView mTextView2;
        public TextView mTextView3;
        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.textview);
            mTextView2 = v.findViewById(R.id.textview2);
            mTextView3 = v.findViewById(R.id.textview3);
        }
    }

    public FilmAdapter(List<Film> myDataset, OnItemClickListener inListener) {
        mDataset = myDataset;
        listener = inListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FilmAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_textview, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextView.setText(mDataset.get(position).toString());
        holder.mTextView2.setText(mDataset.get(position).year());
        holder.mTextView3.setText(mDataset.get(position).director());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(mDataset.get(position));
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
