package com.goodsign.sangkghanews.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.goodsign.sangkghanews.models.DatsanModel;
import com.goodsign.sangkghanews.R;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

/**
 * Created by Машка on 17.01.2017.
 */

public class DatsanListRecyclerAdapter extends RecyclerView.Adapter<DatsanListRecyclerAdapter.DatsansViewHolder> {
    private ArrayList<DatsanModel> datsansList;

    public DatsanListRecyclerAdapter(ArrayList<DatsanModel> datsansList)
    {
        this.datsansList = datsansList;

    }
    public static class DatsansViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        HtmlTextView text;
        ExpandableRelativeLayout expandableRelativeLayout;
        LinearLayout linearLayout;
        ImageView arrow;
        public DatsansViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.text_datsan_title);
            text = (HtmlTextView) itemView.findViewById(R.id.text_datsan_description);
            expandableRelativeLayout = (ExpandableRelativeLayout) itemView.findViewById(R.id.layout_expandable);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layout_datsan);
            arrow = (ImageView) itemView.findViewById(R.id.image_datsans_arrow);
        }
    }
    @Override
    public DatsanListRecyclerAdapter.DatsansViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        holder.title.setText(datsansList.get(position).getTitle());
        Log.e("DATSAN", datsansList.get(position).getText());
        holder.text.setHtml(datsansList.get(position).getText(), new HtmlHttpImageGetter(holder.text));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (holder.expandableRelativeLayout.isExpanded())
                {
                    holder.arrow.setRotation(0);
                }
                else
                {
                    holder.arrow.setRotation(90);
                }
                holder.expandableRelativeLayout.toggle();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datsansList.size();
    }

    public void updateList(ArrayList<DatsanModel> datsansList)
    {
        this.datsansList = datsansList;
        notifyDataSetChanged();
    }
}
