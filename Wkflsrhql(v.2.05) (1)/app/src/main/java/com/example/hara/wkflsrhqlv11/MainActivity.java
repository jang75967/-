package com.example.hara.wkflsrhqlv11;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hara.wkflsrhqlv11.alarm.AlarmReceive;

/*
마시멜로 버전으로 개발
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    Button btn[] = new Button[3];
    ViewPager viewPager = null;
    Handler handler = null;
    int p = 0;    //페이지번호
    int v = 1;    //화면 전환 뱡향
    boolean Alcheck;

    protected void onUserLeaveHint() {
//여기서 감지

        super.onUserLeaveHint();
    }




    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        //알람 12AM설정
        Alarm();

        //퍼미션 체크 핸드폰 번호 가져오기
        //고유 아이디
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Start start=new Start(this);
        } else {
            Toast.makeText(this, "전화부 권한 없음", Toast.LENGTH_SHORT).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                Toast.makeText(this, "권한 설명 필요함", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 2);
            }
        }

        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if (permissionCheck2 == PackageManager.PERMISSION_GRANTED) {
            //문자 권한 있음
        } else{
            Toast.makeText(this, "SMS 수신 권한 없음.", Toast.LENGTH_LONG).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {
                Toast.makeText(this, "SMS 권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
            }//퍼미션 끝
        }



            //-------------------

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); //화면 한쪽에 버튼 */

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();


            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            //---------------------------------------------------------
            viewPager = (ViewPager) findViewById(R.id.viewPager);
            MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());

            viewPager.setAdapter(adapter);
            btn[0] = (Button) findViewById(R.id.btn_a);
            btn[1] = (Button) findViewById(R.id.btn_b);
            btn[2] = (Button) findViewById(R.id.btn_c);

            for (int i = 0; i < btn.length; i++) {
                btn[i].setOnClickListener(this);
            }

            handler = new Handler() {
                public void handleMessage(android.os.Message msg) {
                    if (p == 0) {
                        viewPager.setCurrentItem(1);

                        p++;
                        v = 1;
                    }
                    if (p == 1 && v == 0) {
                        viewPager.setCurrentItem(1);
                        p--;
                    }
                    if (p == 1 && v == 1) {
                        viewPager.setCurrentItem(2);
                        p++;
                    }
                    if (p == 2) {
                        viewPager.setCurrentItem(1);
                        p--;
                        v = 0;
                    }
                }
            };


        }

    //마시멜로 버전이상의 권한 설정하기
    @Override
        public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    switch (requestCode){
        case 1:{
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
            else{
                Toast.makeText(this,"문자 권한 거부",Toast.LENGTH_SHORT).show();
            }
            return;
        }
        case 2:{
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                finish();
            }
            else{
                Toast.makeText(this,"전화부 권한 거부",Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }
    }

    //배너 알람 기능 설정하기
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Alarm() {
        Log.i("Alarm","SET_12AM");
        // AlarmManager, PendingIntent, Calendar android안에 페키지
        //
    AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
    Intent intent = new Intent(MainActivity.this, AlarmReceive.class);

    PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

    Calendar calendar = Calendar.getInstance();
    //알람시간 calendar에 set해주기

    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)+1, 00, 00, 00);

    //알람 예약
    am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
}


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notice) {
            // Handle the camera action
        } else if (id == R.id.nav_qa) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_infomation) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_a:
                viewPager.setCurrentItem(0);
                Toast.makeText(this, "가계부", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_b:
                viewPager.setCurrentItem(1);
                Toast.makeText(this, "오늘의 양", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_c:
                viewPager.setCurrentItem(2);
                Toast.makeText(this, "월사용 량", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }



}
