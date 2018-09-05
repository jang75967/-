package com.example.hara.wkflsrhqlv11.alarm;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hara.wkflsrhqlv11.CreateID_UUID;
import com.example.hara.wkflsrhqlv11.DBConnect.DB_Background_Task;
import com.example.hara.wkflsrhqlv11.DBConnect.Goal;
import com.example.hara.wkflsrhqlv11.R;

/**
 * Created by yjkwon07 on 2017-01-18
 * 오늘의 목표량 알림 창
 */

public class AlarmActivity_day extends Activity {
    CreateID_UUID createID_uuid=new CreateID_UUID();
    Button exit_day; //닫기
    EditText edit_text_day; //가격 체크
    TextView xButton_day;//엑스 박스
    int result=0; //오늘의 자린고비량
    String phoneNum;
    DB_Background_Task db_background_task;
    int yesterday=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_day);
        edit_text_day=(EditText)findViewById(R.id.edit_text_day);



    }

    //확인 버튼
    //확인 버튼을 눌렀을때 0보다 작으면 닫기 못함
    public void start_day(View view){
        result= Integer.parseInt(edit_text_day.getText().toString());
        if(result < 10 )
        {
            Toast.makeText(getApplicationContext(), "오늘의 자린 고비량을 다시 입력 해주세요", Toast.LENGTH_LONG).show();
        }
        else{
            try {
                if (!(Goal.getGoal_yesterday().charAt(1) == (char) 65279)) {
                    yesterday = Integer.parseInt(Goal.getGoal_yesterday().substring(0, Goal.getGoal_yesterday().length()));
                    if (yesterday < result) {
                        Intent CheckIntent = new Intent(this, AlarmActivity_overmax.class);
                        CheckIntent.putExtra("Price_day", result);
                        CheckIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        this.startActivity(CheckIntent);
                        finish();
                    }
                }
            }
            catch (Exception e){
                Log.i("ERROR_INSERT","ERROR_INSERT_DAY");
            }
                 finally {
                if (yesterday >=result || (Goal.getGoal_yesterday().charAt(1) == (char) 65279)) {
                    phoneNum = (String) createID_uuid.getUniqueID(this);
                    String method = "register_day/week";
                    db_background_task = new DB_Background_Task(this);
                    //week -> AlarmCheck.class 에서 받은 값을 sharedPreference 클래스에 저장 하여 값을 확인 한다.
                    // 만약 저장된 값이 없다는것은 week Alert가 생성되지 않았기 때문에 값은 0으로 리턴 받는다.
                    SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String price = myPrefs.getString("price", "0");

                    db_background_task.execute(method, String.valueOf(result), price, phoneNum);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("price_day", String.valueOf(result));
                    setResult(1, resultIntent);

                    //price 초기화
                    SharedPreferences.Editor myEditor = myPrefs.edit();
                    myEditor.putString("price", "0");
                    myEditor.commit();
                    finish();
                }

            }
        }


    }
    @Override
    public void onBackPressed(){
        Toast.makeText(getApplicationContext(), "오늘의 자린 고비량을 입력 해주세요", Toast.LENGTH_LONG).show();
    }
}