package com.goodsign.sangkghanews.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.goodsign.sangkghanews.Models.DatsansModel;
import com.goodsign.sangkghanews.R;

import java.util.ArrayList;

/**
 * Created by Машка on 17.01.2017.
 */

public class DatsanListRecyclerAdapters extends RecyclerView.Adapter<DatsanListRecyclerAdapters.DatsansViewHolder> {
    private Context context;
    private ArrayList<DatsansModel> Datsan_arrayList;
    private FragmentManager fragmentManager;
    public DatsanListRecyclerAdapters(ArrayList<DatsansModel> Datsan_arrayList, Context context){
        this.context = context;
        this.Datsan_arrayList = Datsan_arrayList;

    }
    public static class DatsansViewHolder extends RecyclerView.ViewHolder{

        TextView datsan_name;
        TextView datsan_text;
        ExpandableRelativeLayout expandableRelativeLayout;
        LinearLayout linearLayout;
        public DatsansViewHolder(View itemView) {
            super(itemView);
            datsan_name = (TextView) itemView.findViewById(R.id.text_datsan_name);
            datsan_text = (TextView) itemView.findViewById(R.id.text_datsan);
            expandableRelativeLayout = (ExpandableRelativeLayout) itemView.findViewById(R.id.layout_expandable);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layout_datsan);
        }
    }
    @Override
    public DatsanListRecyclerAdapters.DatsansViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_datsans_list, parent, false);
        DatsansViewHolder dvh = new DatsansViewHolder(v);
        dvh.expandableRelativeLayout.collapse();
        return dvh;
    }

    @Override
    public void onBindViewHolder(final DatsansViewHolder holder, int position) {
//        holder.datsan_text.setText(R.string.test_long);
//        holder.datsan_name.setText(R.string.test_short);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.expandableRelativeLayout.toggle();

            }
        });
    }

    @Override
    public int getItemCount() {
        return Datsan_arrayList.size();
    }
}
