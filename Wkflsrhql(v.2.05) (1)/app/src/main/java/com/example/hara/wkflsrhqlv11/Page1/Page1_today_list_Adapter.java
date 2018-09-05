package com.example.hara.wkflsrhqlv11.Page1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hara.wkflsrhqlv11.DBConnect.DB_Background_get_Task;
import com.example.hara.wkflsrhqlv11.DBConnect.Goal;
import com.example.hara.wkflsrhqlv11.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HARA on 2017-07-15.
 */

public class Page1_today_list_Adapter extends RecyclerView.Adapter<Page1_today_list_Adapter.ViewHolder> {
    private ArrayList<String> name, place, price;
    private Context mcontext;
    private ViewHolder holder;
    private int position, flag=0;

    public Page1_today_list_Adapter(List<String> myDataset, Context context) {
        name = new ArrayList<String>();
        place = new ArrayList<String>();
        price = new ArrayList<String>();

        for(int i=0; i<myDataset.size(); i++){
            String[] data = myDataset.get(i).split(":");
            Log.i("ddd","data[0]-"+data[0]+" : data[1]-"+data[1]+" : data[0]-"+data[2]);
            name.add(data[0]);
            place.add(data[1]);
            price.add(data[2]);
        }
        mcontext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView page1_list_tvcardname, page1_list_tvplace, page1_list_tvprice;

        public ViewHolder(View view) {
            super(view);
            page1_list_tvcardname = (TextView)view.findViewById(R.id.page1_list_tvcardname);
            page1_list_tvplace = (TextView)view.findViewById(R.id.page1_list_tvplace);
            page1_list_tvprice = (TextView)view.findViewById(R.id.page1_list_tvprice);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.page1_today_list_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        this.holder = holder;
        this.position = position;

        if(name.get(position).equals("cash")){
            holder.page1_list_tvcardname.setText("현금");
        }else {
            holder.page1_list_tvcardname.setText("카드("+name.get(position)+")");
        }
        holder.page1_list_tvplace.setText(place.get(position));
        holder.page1_list_tvprice.setText(price.get(position));
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

}
