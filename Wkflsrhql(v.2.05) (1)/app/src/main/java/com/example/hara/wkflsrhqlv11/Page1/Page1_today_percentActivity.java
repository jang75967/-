package com.example.hara.wkflsrhqlv11.Page1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hara.wkflsrhqlv11.DBConnect.Goal;
import com.example.hara.wkflsrhqlv11.R;

import java.util.Calendar;

public class Page1_today_percentActivity extends Fragment {
    public View view;
    TextView textView;
    ProgressBar progressBar;
    int cal_day;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.page1_today_percent, container, false);
        textView=(TextView)view.findViewById(R.id.textView1);
        progressBar=(ProgressBar)view.findViewById(R.id.progressbar);
        cal_day= Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        if(Integer.parseInt(Goal.getGoal_today())<Integer.parseInt(Goal.getList_DaySum().get(cal_day))) {
            progressBar.setProgress(100);
            textView.setText("100%");
        }

        else {
            try {
                int goal_day=Integer.parseInt(Goal.getGoal_today());
                int daysum=Integer.parseInt(Goal.getList_DaySum().get(cal_day ));
                int percent= (int) (((double)daysum/goal_day)*100);
                progressBar.setProgress(percent);
                textView.setText(percent + "%");
            }catch (Exception e){
                progressBar.setProgress(0);
                textView.setText("0%");
            }
        }
        return view;
    }
}