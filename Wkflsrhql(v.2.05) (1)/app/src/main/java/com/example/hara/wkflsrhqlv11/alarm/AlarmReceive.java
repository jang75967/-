package com.example.hara.wkflsrhqlv11.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.hara.wkflsrhqlv11.MainActivity;
import com.example.hara.wkflsrhqlv11.R;

//알람배너

public class AlarmReceive extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;
    final String TAG = "BOOT_START_SERVICE";
    Notification Notifi;


    /*@RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
        final String TAG = "BOOT_START_SERVICE";
        Log.i(TAG,"베너 알람 호출");

        //NotificationManager 안드로이드 상태바에 메세지를 던지기위한 서비스 불러온다
        NotificationManager nm = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        //알람시간 calendar에 set해주기
        *//*
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 12,00);//시간을 24시 set했음
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM,0);
        *//*

            Intent intentActivity = new Intent(context, MainActivity.class); //그메세지를 클릭했을때 불러올엑티비티를 설정함
            intentActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentActivity, PendingIntent.FLAG_UPDATE_CURRENT);

        //이건 여러번 알람 24*60*60*1000 이건 하루에한번 계속 알람한다는 뜻.
// Create Notification Object
        Notification notification= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(context)
                .setContentTitle("자린고비")
                .setContentText("오늘의 자린고비를 입력해주세요")
                .setSmallIcon(R.drawable.ic_menu_camera)
                .setTicker("알림!!")
                .setContentIntent(pendingIntent)
                .build();
        }


        //소리 추가
        notification.defaults= Notification.DEFAULT_SOUND;

        //알림 소리를 한번만 내도록
        notification.flags= Notification.FLAG_ONLY_ALERT_ONCE;

        //확인하면 자동으로 알림이 제거 되도록
        notification.flags= Notification.FLAG_AUTO_CANCEL;
        nm.notify(1, notification);




        }*/
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을때 onReceive를 호출함
        //NotificationManager 안드로이드 상태바에 메세지를 던지기위한 서비스 불러오고
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.coin).setTicker("HETT").setWhen(System.currentTimeMillis())
                .setNumber(1).setContentTitle("자린고비").setContentText("오늘의 목표량을 설정 하세요")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingIntent).setAutoCancel(true);

        notificationmanager.notify(1, builder.build());
    }

    }

