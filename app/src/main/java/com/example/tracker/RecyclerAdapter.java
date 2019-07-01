package com.example.tracker;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.List;

import com.example.tracker.util.BirthCalculator;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemHolder>{
    private List<Item> mItems;

    public RecyclerAdapter(List<Item> items) {
        mItems = items;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //2

        private TextView mItemName;
        private TextView mItemAge;
        private TextView mItemGender;
        private TextView mItemJob;

        private Item mItem;


        //4
        public ItemHolder(View v) {
            super(v);


            mItemName = (TextView) v.findViewById(R.id.name);
            mItemAge = (TextView) v.findViewById(R.id.age);
            mItemGender = (TextView) v.findViewById(R.id.gender);
            mItemJob = (TextView) v.findViewById(R.id.job);

            v.setOnClickListener(this);
        }

        //5
        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();
            Intent nuevoIntent;
            nuevoIntent = new Intent(context, MapActivity.class);
            nuevoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(nuevoIntent);
        }

        public void bindItem(Item item) {
            mItem = item;


            mItemName.setText(item.getName());
            mItemAge.setText(BirthCalculator.getYear(item.getAge()));
            mItemGender.setText(item.getGender());
            mItemJob.setText(item.getJob());
        }
    }

    @Override
    public RecyclerAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ItemHolder(inflatedView);
    }
    @Override
    public void onBindViewHolder(RecyclerAdapter.ItemHolder holder, int position) {
        Item item = mItems.get(position);
        holder.bindItem(item);
    }

    @Override
    public int getItemCount() {
        if(mItems!=null)
            return mItems.size();
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
