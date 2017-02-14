package com.goodsign.sangkghanews.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodsign.sangkghanews.R;

/**
 * Created by Машка on 22.01.2017.
 */

public class ZurhaiListRecyclerAdapter extends RecyclerView.Adapter<ZurhaiListRecyclerAdapter.ZurhaiListViewHolder> {

    public class ZurhaiListViewHolder extends RecyclerView.ViewHolder {
        private TextView zurhai_name, zurhai_description;
        public ZurhaiListViewHolder(View itemView) {
            super(itemView);
            zurhai_name = (TextView) itemView.findViewById(R.id.text_zurhai_name);
            zurhai_description = (TextView) itemView.findViewById(R.id.text_zurhai_description);
        }
    }
    public ZurhaiListRecyclerAdapter(){

    }
    @Override
    public ZurhaiListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_zurhai, parent, false);
        ZurhaiListRecyclerAdapter.ZurhaiListViewHolder zlvh = new ZurhaiListRecyclerAdapter.ZurhaiListViewHolder(view);
        return zlvh;
    }

    @Override
    public void onBindViewHolder(ZurhaiListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
