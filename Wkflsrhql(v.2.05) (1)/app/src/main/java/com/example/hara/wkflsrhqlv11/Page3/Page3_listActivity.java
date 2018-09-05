package com.example.hara.wkflsrhqlv11.Page3;

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

import java.util.ArrayList;
import java.util.List;

public class Page3_listActivity extends Fragment {
    public View view;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Page3_list_Adapter mAdapter;
    private List<String> myDataset;
    public CreateID_UUID createID_uuid;
    String PhoneNum;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.page3_list, container, false);
        createID_uuid=new CreateID_UUID();
        PhoneNum=(String) createID_uuid.getUniqueID(view.getContext());

        Task task=new Task(view.getContext());
        task.execute("Get_year_each_month",PhoneNum);

        return view;
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
            if (result.charAt(0) == '9') {

                goal = new Goal();
                String[] result_split = result.split("<br/>");
                String[] result_list;

                Log.i("result_split", "result_split_page3 : " + result_split.length);

                myDataset = new ArrayList<String>();
                for(int i=1; i<result_split.length; i++){
                    result_list = result_split[i].split("<1>");
                    myDataset.add(i+":"+result_list[1]);
                }

                Log.i("ddd","myDataset : "+myDataset);
                //recycleview사용선언
                mRecyclerView = (RecyclerView) view.findViewById(R.id.page3_list_recycler_view);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setNestedScrollingEnabled(false);
                mAdapter = new Page3_list_Adapter(myDataset, getContext());
                mRecyclerView.setAdapter(mAdapter);
            }

        }

    }
}