package com.example.hara.wkflsrhqlv11.Page1;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hara.wkflsrhqlv11.CreateID_UUID;
import com.example.hara.wkflsrhqlv11.DBConnect.DB_Background_get_Task;
import com.example.hara.wkflsrhqlv11.DBConnect.Goal;
import com.example.hara.wkflsrhqlv11.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by HARA on 2017-01-21.
 */

public class Page1_today_containActivity extends Fragment implements View.OnClickListener {
    public View view;
    public CreateID_UUID createID_uuid;
    String PhoneNum;
    Task task, task1;
    public TextView tvToday;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.page1_today_contain, container, false);
        createID_uuid=new CreateID_UUID();
        PhoneNum=(String) createID_uuid.getUniqueID(view.getContext());

        task=new Task(view.getContext());
        task.execute("List_day",PhoneNum);
        task1 =new Task(view.getContext());
        task1.execute("get_today",PhoneNum);

        tvToday = (TextView)view.findViewById(R.id.tvToday);
        Date today = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("MM월 dd일 (E)");
        tvToday.setText(sd.format(today));

        return view;
    }


    public void onStart() {
        super.onStart();
        getView().findViewById(R.id.change_percent).setOnClickListener(this);
        getView().findViewById(R.id.change_list).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_percent:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frag_container, new Page1_today_percentActivity())
                        .commit();
                break;
            case R.id.change_list:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frag_container, new Page1_today_listActivity())
                        .commit();
                break;
        }
    }

    class Task extends DB_Background_get_Task {

        public Task(Context ctx) {
            super(ctx);
        }


        @Override
        protected void onPostExecute(String result) {
            result.trim();
            Goal goal = null;
            TextView textView,textView2;
            String[] rr = new String[0];
            int cal_day = 0;
            ProgressBar progressBar;

            if (result.charAt(0) == 'y') {   //오늘 하루 목표량
                rr = result.split("<br/>");
                String check[] = rr;
                goal = new Goal();
                goal.setGoal_today(rr[rr.length - 1]);
                if(Goal.getGoal_today().contains("y"))
                    goal.setGoal_today("0");
                Log.i("SELECT_Page1", "getGoalo_today_Page1 :" + Goal.getGoal_today());
           }

           else if (result.charAt(0) == '6') {
                //리스트 day 항목
                ArrayList<String> list_day = new ArrayList<String>();
                int length = 4;
                int count = 1;
                goal = new Goal();
                String[] result_split = result.split("<br/>");
                String[] result_list;
                while (count < result_split.length) {
                    result_list = result_split[count].split("<1>");
                    Log.i("GET_DAY_LIST", count+"GET_DAY_LIST_Page1 : " + result_split[count]);
                    list_day.add(result_list[1]);
                    count++;
                }

                cal_day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                Goal.setList_DaySum(list_day);
                textView=(TextView)view.findViewById(R.id.today_goal21);
                Log.i("목표량", count+"GET_DAY_LIST_Page1 : " +list_day.get(cal_day));
                textView.setText("오늘의 목표량"+Goal.getGoal_today()+"원 이며, "+list_day.get(cal_day)+"원을 사용 하였습니다");
                progressBar=(ProgressBar)view.findViewById(R.id.progressbar);
                textView2=(TextView)view.findViewById(R.id.textView1);

                if(Integer.parseInt(Goal.getGoal_today())<Integer.parseInt(list_day.get(cal_day))) {
                    TextView textView3 = (TextView) view.findViewById(R.id.today_goal22);
                    textView3.setText("오늘의 목표량을 넘었습니다!!");
                    progressBar.setProgress(100);
                    textView2.setText("100%");
                } else {
                    try {
                        int goal_day=Integer.parseInt(Goal.getGoal_today());
                        int daysum=Integer.parseInt(Goal.getList_DaySum().get(cal_day ));
                        int percent= (int) (((double)daysum/goal_day)*100);
                        progressBar.setProgress(percent);
                        textView2.setText(percent + "%");
                    }catch (Exception e){
                        progressBar.setProgress(0);
                        textView2.setText("0%");
                    }
                }
            }
        }
    }
}