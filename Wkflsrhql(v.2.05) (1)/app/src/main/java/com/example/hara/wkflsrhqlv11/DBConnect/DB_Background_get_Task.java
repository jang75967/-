package com.example.hara.wkflsrhqlv11.DBConnect;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.hara.wkflsrhqlv11.alarm.AlarmCheck_;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by yjkwon07 on 2017-06-24.
 * 오늘 하루 목표량을 가져온다 1 get_today+유저 아이디
 * 이번주 목표량을 가져온다 2    get_week+유저 아이디
 * 어제 목표량을 가져온다 3      get_yesterday+유저 아이디
 * 리스트 목록을 가져온다 4      List_get+유저 아이디+날짜
 * 카드사 번호를 가져온다 5      get_CardNumber + 카드이름
 * 리스트 daysum을 가져온다 6    List_get + 유저 아이디
 * 리스트 weeksum을 가져온다 7   List_week +유저 아이디
 * 리스트 각 날짜마다의 기록을 가져온다 8 Get_list_day + 유저 아이디
 */




public class DB_Background_get_Task extends AsyncTask<String, Void, String> {
    Context ctx;
    AlertDialog alertDialog;
    String response = "";
    String line = "";
    ProgressDialog progressDialog;
    String errorString = null;
    Goal goal;

    public DB_Background_get_Task(Context ctx){
        this.ctx=ctx;
    }

    protected void onPostExecute(String result) {

        result.trim();
        if (result.charAt(0) == 'y') {   //오늘 하루 목표량
            String[] rr = result.split("<br/>");
            String check[] = rr;
            if (result.charAt(2) == ' ') {
            } else {
                goal = new Goal();
                goal.setGoal_today(rr[rr.length - 1]);
                Log.i("SELECT", "getGoal_today :" + Goal.getGoal_today());
            }
            AlarmCheck_ alarmCheck_ = new AlarmCheck_(ctx);
        } else if (result.charAt(0) == '2') {   //이번주 목표량
            if (result.charAt(1) == ' ')
                return;
            String[] rr = result.split("<br/>");
            goal = new Goal();
            goal.setGoal_week(rr[rr.length - 1]);
            Log.i("ddd", "getGoal_week : " + Goal.getGoal_week());
        } else if (result.charAt(0) == '3') {      //어제 목표량
            if (result.charAt(1) == ' ')
                return;
            String[] rr = result.split("<br/>");
            goal = new Goal();
            goal.setGoal_yesterday(rr[rr.length - 1]);
            Log.i("ddd", "getGoal_yesterday :" + Goal.getGoal_yesterday());
        } else if (result.charAt(0) == '4') {
            //리스트 항목
            ArrayList<String> List_Date = new ArrayList<String>();
            ArrayList<String> Card_Name = new ArrayList<String>();
            ArrayList<String> List_Place = new ArrayList<String>();
            ArrayList<String> List_Price = new ArrayList<String>();

            int length = 4;
            int count = 1;
            goal = new Goal();
            String[] result_split = result.split("<br/>");
            String[] result_list;

            while (count < result_split.length) {
                result_list = result_split[count].split("<1>");
                Log.i("GET_LIST", "GET_LIST : " + result_split[count]);
                List_Date.add(result_list[1]);
                Card_Name.add(result_list[2]);
                List_Place.add(result_list[3]);
                List_Price.add(result_list[4]);
                count++;
            }
            goal.setList_Date(List_Date);
            goal.setList_Place(List_Place);
            goal.setCard_Name(Card_Name);
            goal.setList_Price(List_Price);
        }

/*        else if(result.charAt(0)=='5') {      //카드 번호 가져오기
            Goal goal=new Goal();
            if(result.charAt(1)==' ')
                return;

            String[] rr = result.split("<br/>");
            Log.i("Card_Number", "Card_Number : " + rr[1]);
            goal.setCard_Number(rr[1]);

        }*/

        else if (result.charAt(0) == '6') {
            //리스트 day 항목
            ArrayList<String> list_day = new ArrayList<String>();
            int length = 4;
            int count = 1;
            goal = new Goal();
            String[] result_split = result.split("<br/>");
            String[] result_list;

            while (count < result_split.length) {
                result_list = result_split[count].split("<br/>");
                Log.i("GET_DAY_LIST", "GET_DAY_LIST : " + result_split[count]);
                list_day.add(result_list[0]);
                count++;
            }
            Goal.setList_DaySum(list_day);

        }

            /*else if (result.charAt(0) == '7') {
                //리스트 day 항목
                ArrayList<String> list_week = new ArrayList<String>();
                int length = 4;
                int count = 1;
                goal = new Goal();
                String[] result_split = result.split("<1>");
                String[] result_list;

                while (count < result_split.length - 1) {
                    result_list = result_split[count].split("<br/>");
                    Log.i("GET_WEEK_LIST", "GET_WEEK_LIST : " + result_list[0]);
                    list_week.add(result_list[0]);
                    count++;
                }

                Goal.setList_WeekSum(list_week);
                Intent intent =new Intent(ctx, Page2_monthActivity.class);
                intent.putExtra("week",list_week);

            }*/

        else if (result.charAt(0) == '8') {
            //리스트 항목
            ArrayList<String> List_Date = new ArrayList<String>();
            ArrayList<String> Card_Name = new ArrayList<String>();
            ArrayList<String> List_Place = new ArrayList<String>();
            ArrayList<String> List_Price = new ArrayList<String>();
            ArrayList<Integer> List_day=new ArrayList<Integer>();
            int length = 4;
            int count = 1;
            int i=0;
            goal = new Goal();
            String[] result_split = result.split("<br/>");
            String[] result_split2;
            String[] result_list;

            while (count < result_split.length) {
                result_list = result_split[count].split("<40>");

                while (i<result_list.length-1) {
                    int start=result_list[i].lastIndexOf('-');
                    int end=result_list[i].indexOf(' ');
                    int a=Integer.parseInt(result_list[i].substring(start+1,end));
                    List_day.add(a);
                    List_Date.add(result_list[i]);
                    Card_Name.add(result_list[i+1]);
                    List_Place.add(result_list[i+2]);
                    List_Price.add(result_list[i+3]);
                    Log.i("GET_All_LIST", "GET_All_LIST : " + a+" "+result_list[i]+ result_list[i+1]+ result_list[i+2]+ result_list[i+3]);

                    i+=4;
                }
                i=0;
                count++;
            }
            goal.setList_Date_day(List_Date);
            goal.setList_Place_day(List_Place);
            goal.setCard_Name_day(Card_Name);
            goal.setList_Price_day(List_Price);
            goal.setLis_day(List_day);
        }

    }


    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {
        String goal_get = Value.goal_get;
        String goal_yesterday_get = Value.goal_yesterday;
        String goal_week = Value.goal_week;
        String List_get = Value.List_get;
        String Card_get = Value.Card_get;
        String method = params[0];
        String User_Number = params[1];

        //오늘 목표량과 가져오기
        if (method.equals("get_today")) {
            try {
                URL url = new URL(goal_get);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = /*php*/URLEncoder.encode("User_Number", "UTF-8") + "=" + URLEncoder.encode(User_Number, "UTF-8");

                //오늘 하루 자린 고비 설정값
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("SELECT_GET", "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                char a = 'y';

                return a + sb.toString().trim();


            } catch (Exception e) {
                Log.d("SELECT_GET", "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }
        }

        //이번주 목표량 가져오기
        else if (method.equals("get_week")) {

            try {
                URL url = new URL(goal_week);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = /*php*/URLEncoder.encode("User_Number", "UTF-8") + "=" + URLEncoder.encode(User_Number, "UTF-8");

                //오늘 하루 자린 고비 설정값
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("SELECT_YESTERDAY_GET", "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();


                return 2 + sb.toString().trim();


            } catch (Exception e) {
                Log.d("SELECT_YESTERDAY_GET", "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }
        }

        //어제 목표량 가져오기 (오늘 목표량 비교를 위해)
        else if (method.equals("get_yesterday")) {
            try {
                URL url = new URL(goal_yesterday_get);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = /*php*/URLEncoder.encode("User_Number", "UTF-8") + "=" + URLEncoder.encode(User_Number, "UTF-8");

                //오늘 하루 자린 고비 설정값
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("SELECT_YESTERDAY_GET", "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();


                return 3 + sb.toString().trim();


            } catch (Exception e) {
                Log.d("SELECT_YESTERDAY_GET", "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }
        } else if (method.equals("List_get")) {

            try {
                URL url = new URL(List_get);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = /*php*/URLEncoder.encode("User_Number", "UTF-8") + "=" + URLEncoder.encode(User_Number, "UTF-8");


                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("SELECT_List_GET", "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();


                return 4 + "<br/>" + sb.toString().trim();


            } catch (Exception e) {
                Log.d("SELECT_List_GET", "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }
        }

        //선택된 카드 번호 가져오기
        else if (method.equals("get_CardNumber")) {
            String Card_Name = params[1];
            try {
                URL url = new URL(Card_get);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = /*php*/URLEncoder.encode("Card_Name", "UTF-8") + "=" + URLEncoder.encode(Card_Name, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("SELECT_Card_Number_GET", "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();


                return 5 + sb.toString().trim();


            } catch (Exception e) {
                Log.d("SELECT_Card_Number_GET", "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }
        } else if (method.equals("List_day")) {
            String date = params[1];
            try {
                URL url = new URL(Value.List_day);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = /*php*/URLEncoder.encode("User_Number", "UTF-8") + "=" + URLEncoder.encode(User_Number, "UTF-8");


                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("SELECT_List_GET_DAY", "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();


                return 6 + "<br/>" + sb.toString().trim();


            } catch (Exception e) {
                Log.d("SELECT_List_GET_WEEK", "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }
        } else if (method.equals("List_week")) {
            try {
                URL url = new URL(Value.List_week);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = /*php*/URLEncoder.encode("User_Number", "UTF-8") + "=" + URLEncoder.encode(User_Number, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("SELECT_List_GET", "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();


                return 7 + "<br/>" + sb.toString().trim();


            } catch (Exception e) {
                Log.d("SELECT_List_GET", "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }
        }
        else if (method.equals("Get_list_day")) {
            try {
                URL url = new URL(Value.Get_list_day);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

                String data = /*php*/URLEncoder.encode("User_Number", "UTF-8") + "=" + URLEncoder.encode(User_Number, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("SELECT_List_GET", "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();


                return 8 + "<br/>" + sb.toString().trim();


            } catch (Exception e) {
                Log.d("SELECT_List_GET", "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }
        }
        else if (method.equals("Get_list_day")) {
            try {
                URL url = new URL(Value.Get_list_day);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

                String data = /*php*/URLEncoder.encode("User_Number", "UTF-8") + "=" + URLEncoder.encode(User_Number, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("SELECT_List_GET", "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();


                return 8 + "<br/>" + sb.toString().trim();


            } catch (Exception e) {
                Log.d("SELECT_List_GET", "InsertData: Error ", e);
                errorString = e.toString();
                return null;
            }
        }else if (method.equals("Get_year_each_month")) {
            try {
                URL url = new URL(Value.Get_year_each_month);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

                String data = /*php*/URLEncoder.encode("User_Number", "UTF-8") + "=" + URLEncoder.encode(User_Number, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();


                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();


                return 9 + "<br/>" + sb.toString().trim();


            } catch (Exception e) {
                Log.d("Get_year_each_month", "Get_year_each_month: Error ", e);
                errorString = e.toString();
                return null;
            }
        }
        else if (method.equals("want_day")) {
            String Month = params[2];
            try {
                URL url = new URL(Value.want_day);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

                String data = /*php*/URLEncoder.encode("User_Number", "UTF-8") + "=" + URLEncoder.encode(User_Number, "UTF-8")+"&"+
                        URLEncoder.encode("Month", "UTF-8") +"="+ URLEncoder.encode(Month, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();


                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();


                return 'a' + "<br/>" + sb.toString().trim();


            } catch (Exception e) {
                Log.d("want_day", "want_day: Error ", e);
                errorString = e.toString();
                return null;
            }
        }
        else
            return null;
    }
}