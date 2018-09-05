package com.example.hara.wkflsrhqlv11.alarm;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hara.wkflsrhqlv11.R;

/**
 * Created by yjkwon07 on 2017-01-18.
 */

public class AlarmActivity_week extends Activity {


    EditText edit_text_week; //가격 체크
    int result=0; //오늘의 자린고비량

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_week);
        edit_text_week=(EditText)findViewById(R.id.edit_text_week);

    }

    //확인 버튼
    //확인 버튼을 눌렀을때 0보다 작으면 닫기 못함
    public void start_week(View view){
        result= Integer.parseInt(edit_text_week.getText().toString());
        if(result < 0 )
        {
            Toast.makeText(getApplicationContext(), "이번주 자린 고비량을 다시 입력 해주세요", Toast.LENGTH_LONG).show();
        }
        else{

            SharedPreferences myPrefs= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor myEditor=myPrefs.edit();
            myEditor.putString("price", String.valueOf(result));
            myEditor.commit();
            finish();

        }

    }
    @Override
    public void onBackPressed(){
        Toast.makeText(getApplicationContext(), "오늘의 자린 고비량을 입력 해주세요", Toast.LENGTH_LONG).show();
    }
}