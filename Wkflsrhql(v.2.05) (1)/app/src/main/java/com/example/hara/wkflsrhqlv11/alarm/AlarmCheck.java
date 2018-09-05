package com.example.hara.wkflsrhqlv11.alarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.Intent.FLAG_ACTIVITY_MULTIPLE_TASK;

//알람 api



/**
 * Created by yjkwon07 on 2017-01-19.
 * 디비가 없는 알림 이였을 때
 * 지금은 사용하지 않는다
 */

public class AlarmCheck extends Activity {

    //알람 변수
    public static int cal_check; //오늘 하루 요일 값 받기
    public static int cal_today; //하루가 지났는지 확인 하기위해 요일 값 받기
    public int cal_day; //월요일 체크
    protected String result; //week에서 받은 자린고비 설정 값
    protected boolean Get_check;//오늘 알림을 받았는지 체크
    protected boolean Get_today;//오늘 요일을 받았는지 체크
    public boolean isFirstRun; //처음 켰는지 체크
    private final int fiexd_cal_today=6; //고정 날짜 case
    private final int valuealbe_cal_check=7; //변하는 날짜 case
    Context context;


    public AlarmCheck(Context context){
        this.context=context;
    }

    public  boolean  Alarm_Check(){
        restore_check(3);


        if(isFirstRun){
            Toast.makeText(context,"First", Toast.LENGTH_LONG).show();
            save_check(5); //처음 앱을 설치시 false로 저장. isFirstRun ->fakse
            checkToday(); //날짜 값 받기
            checkAlam();//주/요일 목표 금액 설정 받았는지 확인

            return true;
        }

        else{
            Toast.makeText(context,"Not first", Toast.LENGTH_LONG).show();
            save_check(valuealbe_cal_check);
            //24시간이 지났으면 값이 fixed_cal_check와 valueable_cal_check값이 틀리게 된다.
            // fiexd_cal_fixed=>
            restore_check(4);
            restore_check(5);
            if(cal_today!=cal_check) {
                Forcheck();
            }
        }
        return false;
    }

   private  void Forcheck(){
       //다시 켰을 경우
           save_check(1); //Get_chek ->false
           save_check(2); //Get_today ->false
           checkToday();
           checkAlam();
       }


    //오늘 하루 요일 값 받기 fixeed_cal_today(24시간 저장)
    private void checkToday(){
        restore_check(2);
        if(Get_today) {
            Toast.makeText(context,"오늘 하루 요일 값을 받았습니다.", Toast.LENGTH_LONG).show();
        }

        else{
            //고정값 설정
            save_check(fiexd_cal_today);
            save_check(4);
        }
    }

    // 주/요일 목표금액 알람을 받았는지 체크
    private void checkAlam(){
        restore_check(1); //목표 알림을 받았는지 Get_check 확인
        if(Get_check){
            Toast.makeText(context,"알람을 받았습니다.", Toast.LENGTH_LONG).show();
        }
        else{
            cal_day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);//오늘 요일 체크
            save_check(3); //알림을 받았다고 true
            if(cal_day==2)//월요일이면 이번주 목표 불러오기
            {
                //week intent 값을 받으면 -> day intent로 값을 보내기
                //week -> AlarmCheck.class 에서 받은 값을 sharedPreference 클래스에 저장 하여 값을 확인 한다.
                // 만약 저장된 값이 없다는것은 week 알림이 생성되지 않았기 때문에 값은 0으로 리턴 받는다.
                SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                result=myPrefs.getString("price","0");
                Intent intent2=new Intent(context,AlarmActivity_day.class);
                intent2.putExtra("price", result);

                context.startActivity(intent2);
                Intent intent=new Intent(context,AlarmActivity_week.class);
                intent.setFlags(FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);
                //진짜 의문점 왜 intentweek를 먼저하면 오류가 날까?.... 너무 이상하다
            }
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

        }


    }


    private  void restore_check(int check_number){

        switch (check_number){
            case 1:  //알림을 받았는지 체크 알림을 안받았으면 Get_check는 false
                SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                 Get_check=myPrefs.getBoolean("Get_check",false);
                break;
            case 2: //init->checkToday 오늘 날짜를 받았는지 체크. 만약 오늘 처음 받았으면 false
                SharedPreferences myPrefs2 = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                 Get_today=myPrefs2.getBoolean("Get_today",false);
                Toast.makeText(context,"Wellcomes!!", Toast.LENGTH_LONG).show();
                break;
            case 3: //처음으로 앱을 설치 했는지 비교 만약 SharedPrefernces값이 없다면 처음으로 설정 true.
                SharedPreferences myPrefs3 = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                   isFirstRun=myPrefs3.getBoolean("isFirstRun",true);
                break;
            case 4: // 저장된 값이 없을경우 week반환하는 값이 없는 10 으로 리턴
                SharedPreferences myPrefs4 = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                cal_today=myPrefs4.getInt("cal_today",10);
                break;
            case  5: // 저장된 값이 없을경우 week반환하는 값이 없는 10 으로 리턴
                SharedPreferences myPrefs5 = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                cal_check=myPrefs5.getInt("cal_check",10);
                break;
        }


    }

    private  void save_check(int check_number){

        switch (check_number){
            case 1:
                SharedPreferences myPrefs= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor myEditor=myPrefs.edit();
                myEditor.putBoolean("Get_check",false);
                myEditor.commit();
                break;
            case 2:
                SharedPreferences myPrefs2= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor myEditor2=myPrefs2.edit();
                myEditor2.putBoolean("Get_today",false);
                myEditor2.commit();
                break;
            case 3: //알림을 받았다고 true
                SharedPreferences myPrefs3= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor myEditor3=myPrefs3.edit();
                myEditor3.putBoolean("Get_check",true);
                myEditor3.commit();
                break;
            case 4: //오늘 날짜 받았다고 true
                SharedPreferences myPrefs4= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor myEditor4=myPrefs4.edit();
                myEditor4.putBoolean("Get_today",true);
                myEditor4.commit();
                break;
            case 5: //처음 앱을 설치시 false로 저장.
                SharedPreferences myPrefs5= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor myEditor5=myPrefs5.edit();
                myEditor5.putBoolean("isFirstRun",false);
                myEditor5.commit();
                break;
            case 6: //오늘 날짜를 저장한다.
                SharedPreferences myPrefs6= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor myEditor6=myPrefs6.edit();
                myEditor6.putInt("cal_today", Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
                myEditor6.commit();
            case 7: //inint->cal_check=> 오늘 날일을 저장 한다.
                SharedPreferences myPrefs7= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor myEditor7=myPrefs7.edit();
                myEditor7.putInt("cal_check", Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
                myEditor7.commit();

        }

}


}




