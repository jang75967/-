package com.example.hara.wkflsrhqlv11.Page3;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hara.wkflsrhqlv11.CreateID_UUID;
import com.example.hara.wkflsrhqlv11.DBConnect.DB_Background_get_Task;
import com.example.hara.wkflsrhqlv11.DBConnect.Goal;
import com.example.hara.wkflsrhqlv11.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HARA on 2017-07-15.
 */

public class Page3_list_Adapter extends RecyclerView.Adapter<Page3_list_Adapter.ViewHolder> {
    private List<String> mDataset;
    private Context mcontext;
    private ViewHolder holder;
    private int position;
    private int[] flag;

    private RecyclerView.LayoutManager mLayoutManager;
    private Page3_detail_list_Adapter detailListAdapter;
    private List<String> detailListDataset;

    public CreateID_UUID createID_uuid;
    public String PhoneNum;
    public View view;
    public static Page3_list_Adapter p3;

    private ArrayList<String> month, sumprice;

    public Page3_list_Adapter(){
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

    public Page3_list_Adapter(List<String> myDataset, Context context) {

        month = new ArrayList<String>();
        sumprice = new ArrayList<String>();
        for(int i=0; i<myDataset.size(); i++){
            String[] data = myDataset.get(i).split(":");
            month.add(data[0]);
            sumprice.add(data[1]);
        }

        mDataset = myDataset;
        mcontext = context;
        flag = new int[getItemCount()];
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMonth, tvYear, tvMonthSumPrice, tvMore;
        private RecyclerView mRecyclerView;

        public ViewHolder(View view) {
            super(view);
            p3 = new Page3_list_Adapter();
            p3.setView(view);
            tvMonth = (TextView)view.findViewById(R.id.tvMonth);
            tvYear = (TextView)view.findViewById(R.id.tvYear);
            tvMonthSumPrice = (TextView)view.findViewById(R.id.tvMonthSumPrice);
            tvMore = (TextView)view.findViewById(R.id.tvMore);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.page3_detail_list_recycler_view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.page3_list_adapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        this.holder = holder;
        this.position = position;

        Typeface fontAwesomeFont = Typeface.createFromAsset(mcontext.getAssets(), "fontawesome-webfont.ttf");
        holder.tvMore.setTypeface(fontAwesomeFont);

        Date today = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("YYYY");
        holder.tvYear.setText(sd.format(today));

        holder.tvMonth.setText(month.get(position));
        holder.tvMonthSumPrice.setText(sumprice.get(position));

        holder.tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p3 = new Page3_list_Adapter();
                p3.setView(view);
                p3.setPosition(position);
                p3.setHolder(holder);

                if(flag[p3.getPosition()] == 0){
                    holder.tvMore.setText(R.string.fa_angle_up);
                    holder.mRecyclerView.setVisibility(View.VISIBLE);
                    p3.setHolder(holder);

                    createID_uuid=new CreateID_UUID();
                    PhoneNum=(String) createID_uuid.getUniqueID(view.getContext());

                    Task task=new Task(view.getContext());
                    if(p3.getPosition()+1 < 10){
                        task.execute("want_day",PhoneNum,"0"+(p3.getPosition()+1));
                    }else{
                        task.execute("want_day",PhoneNum,(p3.getPosition()+1)+"");
                    }
                    flag[p3.getPosition()] = 1;
                }else {
                    holder.tvMore.setText(R.string.fa_angle_down);
                    holder.mRecyclerView.setVisibility(View.GONE);
                    p3.setHolder(holder);
                    flag[p3.getPosition()] = 0;
                }
            }
        });
    }

    class Task extends DB_Background_get_Task {
        public Task(Context ctx) {
            super(ctx);
        }

        @Override
        protected void onPostExecute(String result) {
            result.trim();
            Goal goal = null;
            //리스트 설정
            String[] rr = new String[0];
            int cal_day = 0;
            if (result.charAt(0) == 'a') {

                goal = new Goal();
                String[] result_split = result.split("<br/>");
                String[] result_list;

                detailListDataset = new ArrayList<String>();
                for(int i=2; i<result_split.length; i++){
                    result_list = result_split[i].split("<1>");
                    detailListDataset.add(i-1+":"+result_list[1]);
                }

                Log.i("ddd","myDataset : "+detailListDataset);
                Log.i("ddd",p3.getPosition()+1+"");
                //recycleview사용선언
                mLayoutManager = new LinearLayoutManager(mcontext);
                p3.getHolder().mRecyclerView.setLayoutManager(mLayoutManager);
                p3.getHolder().mRecyclerView.setNestedScrollingEnabled(false);
                detailListAdapter = new Page3_detail_list_Adapter(p3.getPosition()+1, detailListDataset, mcontext);
                p3.getHolder().mRecyclerView.setAdapter(detailListAdapter);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
