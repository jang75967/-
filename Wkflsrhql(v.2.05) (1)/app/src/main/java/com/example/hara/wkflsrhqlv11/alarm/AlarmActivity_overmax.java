package com.example.hara.wkflsrhqlv11.alarm;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.example.hara.wkflsrhqlv11.CreateID_UUID;
import com.example.hara.wkflsrhqlv11.DBConnect.DB_Background_Task;
import com.example.hara.wkflsrhqlv11.R;

/**
 * Created by yjkwon07 on 2017-07-03.
 */

public class AlarmActivity_overmax extends Activity {

    String phoneNum;
    int price_day;
    Intent intent;
    CreateID_UUID createID_uuid=new CreateID_UUID();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_overmax);


    }

    public void start_over(View view) {
        //현재 활성화된 액티비티를 시작하게 한 인텐트 호출
        intent = getIntent();
        if (intent != null) {
            price_day = getIntent().getIntExtra("Price_day", 0);
        }
        //goal목표량 등록
        phoneNum = (String) createID_uuid.getUniqueID(this);
        String method = "register_day/week";
        DB_Background_Task db_background_task = new DB_Background_Task(this);

        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String price = myPrefs.getString("price", "0");

        db_background_task.execute(method, String.valueOf(price_day), price, phoneNum);

        SharedPreferences.Editor myEditor = myPrefs.edit();
        myEditor.putString("price", "0");
        myEditor.commit();
        finish();
    }

    public void change(View view) {
        Intent intent = new Intent(this, AlarmActivity_day.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        finish();
    }
}
