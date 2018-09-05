package com.example.hara.wkflsrhqlv11;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hara.wkflsrhqlv11.DBConnect.DB_Background_Task;
import com.example.hara.wkflsrhqlv11.DBConnect.DB_Background_get_Task;
import com.example.hara.wkflsrhqlv11.DBConnect.Goal;
import com.example.hara.wkflsrhqlv11.alarm.AlarmCheck_;

/**
 * Created by yjkwon07 on 2017-07-19.
 * 유저 아이디 등록과 어제 금액가져오기
 * 오늘의 금액을 받았는지 확인을 하여 알림창 뜨기
 */

public class Start {
    Context context;
    CreateID_UUID createID_uuid = new CreateID_UUID();
    String phoneNum;
    AlarmCheck_ alarmCheck;

    Start(Context context) {
        this.context = context;
        phoneNum = (String) createID_uuid.getUniqueID(context);

        //--------register_User---
        DB_Background_Task db_background_task = new DB_Background_Task(context);
        String mode = "register_user";
        db_background_task.execute(mode, phoneNum);

        //------get_yesterday------
        String get_method = "get_yesterday";
        DB_Background_get_Task db_background_get_task = new DB_Background_get_Task(context);
        db_background_get_task.execute(get_method, phoneNum);

        //----get_today& Alarm_--------------
        Task task=new Task(context);
        task.execute("get_today", phoneNum);

/*
        //----------list_week-----------
        DB_Background_get_Task db_background_get_task2 = new DB_Background_get_Task(context);
        db_background_get_task2.execute("List_week", phoneNum);
*/
    }

    class Task extends DB_Background_get_Task {

        public Task(Context ctx) {
            super(ctx);
        }


        @Override
        protected void onPostExecute(String result) {
            result.trim();
            Goal goal = null;
            TextView textView, textView2;
            String[] rr = new String[0];
            int cal_day;
            ProgressBar progressBar;

            if (result.charAt(0) == 'y') {   //오늘 하루 목표량
                rr = result.split("<br/>");
                String check[] = rr;

                    goal = new Goal();
                    goal.setGoal_today(rr[rr.length - 1]);
                if(Goal.getGoal_today().contains("y"))
                    goal.setGoal_today("0");
                    Log.i("SELECT", "getGoal_today :" + Goal.getGoal_today());

                AlarmCheck_ alarmCheck_ = new AlarmCheck_(context);
            }
        }

    }
}
