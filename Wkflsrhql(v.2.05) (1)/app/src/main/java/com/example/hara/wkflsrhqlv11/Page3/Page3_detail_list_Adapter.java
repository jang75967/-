package com.example.hara.wkflsrhqlv11.Page3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hara.wkflsrhqlv11.CreateID_UUID;
import com.example.hara.wkflsrhqlv11.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HARA on 2017-07-15.
 */

public class Page3_detail_list_Adapter extends RecyclerView.Adapter<Page3_detail_list_Adapter.ViewHolder> {
    private List<String> mDataset;
    private Context mcontext;
    private ViewHolder holder;
    private int position;

    public CreateID_UUID createID_uuid;
    public String PhoneNum;
    public View view;
    public static Page3_list_Adapter p3;

    private String month;
    private ArrayList<String> date, datesumprice;

    public Page3_detail_list_Adapter(){
    }

    public View getView() {
        return view;
    }
    public void setView(View view) {
        this.view = view;
    }

    public ViewHolder getHolder() {
        return holder;
    }
    public void setHolder(ViewHolder holder) {
        this.holder = holder;
    }

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    public Page3_detail_list_Adapter(int position, List<String> myDataset, Context context) {
        month = Integer.toString(position);
        date = new ArrayList<String>();
        datesumprice = new ArrayList<String>();
        for(int i=0; i<myDataset.size(); i++){
            String[] data = myDataset.get(i).split(":");
            date.add(data[0]);
            datesumprice.add(data[1]);
        }

        mDataset = myDataset;
        mcontext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMonth, tvDate, tvDateSumPrice;

        public ViewHolder(View view) {
            super(view);
            tvMonth = (TextView)view.findViewById(R.id.tvMonth);
            tvDate = (TextView)view.findViewById(R.id.tvDate);
            tvDateSumPrice = (TextView)view.findViewById(R.id.tvDateSumPrice);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.page3_list_detail_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        this.holder = holder;
        this.position = position;

        holder.tvMonth.setText(month);
        holder.tvDate.setText(date.get(position));
        holder.tvDateSumPrice.setText(datesumprice.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
