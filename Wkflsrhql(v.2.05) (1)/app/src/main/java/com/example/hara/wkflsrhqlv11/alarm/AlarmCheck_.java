package com.example.hara.wkflsrhqlv11.alarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.hara.wkflsrhqlv11.CreateID_UUID;
import com.example.hara.wkflsrhqlv11.DBConnect.DB_Background_get_Task;
import com.example.hara.wkflsrhqlv11.DBConnect.Goal;

import java.util.Calendar;

import static android.content.Intent.FLAG_ACTIVITY_MULTIPLE_TASK;

/**
 * Created by yjkwon07 on 2017-06-27.
 * 알람을 받을때 디비서버에서 오늘 목표량을 받았는지 확인하고 1일시 알람 띄우기
 * 월요이면 이번 주 목표도 불러오기
 */

public class AlarmCheck_ {
    Context context;
    DB_Background_get_Task db_background_get_task;
    CreateID_UUID createID_uuid= new CreateID_UUID();
    String phoneNum;
    String result;
    public int cal_day; //월요일 체크

    Goal goal=new Goal();

    public AlarmCheck_(Context context){
            this.context=context;
            String goal_today = Goal.getGoal_today().trim();
            Log.i("Alarm", "ALARM_Get_today " + Goal.getGoal_today());
            boolean check = goal_today.equals("0");

            if (check) {

                cal_day= Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                if (cal_day == 2)//월요일이면 이번주 목표 불러오기
                {

                    //week intent 값을 받으면 -> day intent로 값을 보내기
                    //week -> AlarmCheck.class 에서 받은 값을 sharedPreference 클래스에 저장 하여 값을 확인 한다.
                    // 만약 저장된 값이 없다는것은 week 알림이 생성되지 않았기 때문에 값은 0으로 리턴 받는다.
                    SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());


                    result = myPrefs.getString("price", "0");
                    Intent intent2 = new Intent(context, AlarmActivity_day.class);
                    intent2.putExtra("price", result);
                    context.startActivity(intent2);

                    Intent intent = new Intent(context, AlarmActivity_week.class);
                    intent.setFlags(FLAG_ACTIVITY_MULTIPLE_TASK);
                    context.startActivity(intent);

                    //진짜 의문점 왜 intentweek를 먼저하면 오류가 날까?.... 너무 이상하다
                } //innner if문 끝
                else {
                    //week -> AlarmCheck.class 에서 받은 값을 sharedPreference 클래스에 저장 하여 값을 확인 한다.
                    // 만약 저장된 값이 없다는것은 week Alert가 생성되지 않았기 때문에 값은 0으로 리턴 받는다.
                    SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                    result = myPrefs.getString("price", "0");
                    Intent intent = new Intent(context, AlarmActivity_day.class);
                    intent.putExtra("price", result);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
            }//if문 끝
    }//메서드 끝




}
