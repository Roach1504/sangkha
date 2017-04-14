package com.goodsign.sangkghanews.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.goodsign.sangkghanews.fragments.HooralOrder;
import com.goodsign.sangkghanews.models.HooralModel;
import com.goodsign.sangkghanews.R;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

/**
 * Created by Roman on 19.01.2017.
 */

public class HooralsListRecyclerAdapter extends RecyclerView.Adapter<HooralsListRecyclerAdapter.HooralsListViewHolder>
{
    private ArrayList<HooralModel> hooralsList;
    private FragmentManager fragmentManager;

    public static class HooralsListViewHolder extends RecyclerView.ViewHolder
    {
        private TextView hooral_name, hooral_time;
        private HtmlTextView hooral_description;
        private Button hooral_order;

        public HooralsListViewHolder(View itemView)
        {
            super(itemView);
            hooral_name = (TextView) itemView.findViewById(R.id.text_hooral_name);
            hooral_time = (TextView) itemView.findViewById(R.id.text_hooral_time);
            hooral_description = (HtmlTextView) itemView.findViewById(R.id.text_hooral_description);
            hooral_order = (Button) itemView.findViewById(R.id.button_order_hooral);
        }
    }

    public HooralsListRecyclerAdapter(ArrayList<HooralModel> hooralsList, FragmentManager fragmentManager)
    {
        this.hooralsList = hooralsList;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public HooralsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_hooral, parent, false);
        HooralsListRecyclerAdapter.HooralsListViewHolder hlvh = new HooralsListRecyclerAdapter.HooralsListViewHolder(view);
        return hlvh;
    }

    @Override
    public void onBindViewHolder(final HooralsListViewHolder holder, int position)
    {
        holder.hooral_name.setText(hooralsList.get(position).getTitle());
        holder.hooral_time.setText(hooralsList.get(position).getTime());
        holder.hooral_description.setHtml(hooralsList.get(position).getText());
        holder.hooral_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                fragmentManager.beginTransaction().replace(R.id.container, HooralOrder.newInstance()).addToBackStack(null).commit();
//                Toast.makeText(holder.itemView.getContext(), "Данная функция появится в ближайших обновлениях", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateList(ArrayList<HooralModel> hooralsList)
    {
        this.hooralsList = hooralsList;
    }

    @Override
    public int getItemCount() {
        return hooralsList.size();
    }
}
