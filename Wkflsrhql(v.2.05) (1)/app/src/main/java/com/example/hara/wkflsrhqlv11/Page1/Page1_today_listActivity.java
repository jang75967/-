package com.example.hara.wkflsrhqlv11.Page1;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hara.wkflsrhqlv11.CreateID_UUID;
import com.example.hara.wkflsrhqlv11.DBConnect.DB_Background_get_Task;
import com.example.hara.wkflsrhqlv11.DBConnect.Goal;
import com.example.hara.wkflsrhqlv11.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Page1_today_listActivity extends Fragment implements View.OnClickListener {
    View view;
    public CreateID_UUID createID_uuid;
    String PhoneNum;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Page1_today_list_Adapter mAdapter;
    private List<String> myDataset = new ArrayList<>();

    //리스트타입 설정

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.page1_today_list, container, false);
        createID_uuid=new CreateID_UUID();
        PhoneNum=(String) createID_uuid.getUniqueID(view.getContext());

        Task task=new Task(view.getContext());

        //수정
        //task.execute("List_get",PhoneNum,//날짜 y-m-d형식으로);
        long now = System.currentTimeMillis();
        Date today = new Date(now);
        task.execute("List_get",PhoneNum);

        return  view;
    }

    public void onStart() {
        super.onStart();
        getView().findViewById(R.id.today_add).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        getFragmentManager().beginTransaction().replace(R.id.frag_container, new Page1_today_list_containActivity()).commit();
    }

    class Task extends DB_Background_get_Task{
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
            if (result.charAt(0) == '4') {

                //리스트 항목
                ArrayList<String> List_Date = new ArrayList<String>();
                ArrayList<String> Card_Name = new ArrayList<String>();
                ArrayList<String> List_Place = new ArrayList<String>();
                ArrayList<String> List_Price = new ArrayList<String>();

                goal = new Goal();
                String[] result_split = result.split("<br/>");
                String[] result_list;

                for (int i=0; i<result_split.length; i++) {
                    result_list = result_split[i].split("<1>");
                    Log.i("GET_LIST", "GET_LIST_Page1 : " + result_split[i]);

                    if(result_list.length >= 2){
                        List_Date.add(result_list[1]);
                        Card_Name.add(result_list[2]);
                        List_Place.add(result_list[3]);
                        List_Price.add(result_list[4]);

                        myDataset.add(result_list[2]+":"+result_list[3]+":"+result_list[4]);
                    }
                }
                goal.setList_Date(List_Date);
                goal.setList_Place(List_Place);
                goal.setCard_Name(Card_Name);
                goal.setList_Price(List_Price);

                Log.i("ddd","myDataset : "+myDataset);
                //recycleview사용선언
                mRecyclerView = (RecyclerView) view.findViewById(R.id.page1_list_recycler_view);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setNestedScrollingEnabled(false);
                mAdapter = new Page1_today_list_Adapter(myDataset, getContext());
                mRecyclerView.setAdapter(mAdapter);
            }

        }

    }
}