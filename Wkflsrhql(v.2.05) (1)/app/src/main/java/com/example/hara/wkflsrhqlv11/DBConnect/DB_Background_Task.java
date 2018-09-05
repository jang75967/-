package com.example.hara.wkflsrhqlv11.DBConnect;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by yjkwon07 on 2017-01-16.
 * 오늘 이번주 목표량을 등록한다. 1 register_day/week day+week+유저 아이디
 * 유저 아이디를 등록한다(고유 uuid) 2  register_user   유저 아이디
 * 리스트 목록을 등록한다. 3  register_List  User_Number + Card_Name + List_Place +List_Price
 */

public class DB_Background_Task extends AsyncTask<String, Void, String> {
    Context ctx;
    AlertDialog alertDialog;
    String response="";
    String line="";

    public DB_Background_Task(Context ctx)
    {
        this.ctx=ctx;
    }

    @Override
    protected void onPreExecute() {
        alertDialog =new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Login Information");
    }

    @Override
    public String doInBackground(String... params) {
        String reg_url = Value.reg_url;
        String login_url = Value.login_url;
        String reg_user=Value.reg_user;
        String reg_list=Value.reg_list;
        String method=params[0];

        //로그인 검사(디비 접속)
        if(method.equals("login")) {
            String login_name = params[1];
            String login_pass = params[2];

            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(login_name, "UTF-8") + "&" +
                        URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(login_pass, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream, "iso-8859-1")));


                while ((line = bufferedReader.readLine()) != null) {
                    response += line;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //목표량 등록
        else if(method.equals("register_day/week")){

            String result_day = params[1];
            String result_week= params[2];
            String User_Number=params[3];
            try{
            URL url = new URL(reg_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
            String data = /*php*/URLEncoder.encode("result_day", "UTF-8") +"="+ URLEncoder.encode(result_day, "UTF-8")+"&"+
                    URLEncoder.encode("result_week", "UTF-8") +"="+ URLEncoder.encode(result_week, "UTF-8")+"&"+
                    URLEncoder.encode("User_Number", "UTF-8") +"="+ URLEncoder.encode(User_Number, "UTF-8");

            //오늘 하루 자린 고비 설정값
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();
            InputStream IS = httpURLConnection.getInputStream();
            IS.close();
            return "목표 등록완료";

            } catch (MalformedURLException e) {
            e.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();
            }
        }

        else if(method.equals("register_user")){

            String User_Number = params[1];
            try{
                URL url = new URL(reg_user);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = /*php*/URLEncoder.encode("User_Number", "UTF-8") +"="+ URLEncoder.encode(User_Number, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "등록 완료!";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(method.equals("register_List")){

            String User_Number = params[1];
            String Card_Name=params[2];
            String List_Place=params[3];
            String List_Price=params[4];

            try{
                URL url = new URL(reg_list);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = /*php*/URLEncoder.encode("User_Number", "UTF-8") +"="+ URLEncoder.encode(User_Number, "UTF-8")+"&"+
                        URLEncoder.encode("Card_Name", "UTF-8") +"="+ URLEncoder.encode(Card_Name, "UTF-8")+"&"+
                        URLEncoder.encode("List_Place", "UTF-8") +"="+ URLEncoder.encode(List_Place, "UTF-8")+"&"+
                        URLEncoder.encode("List_Price", "UTF-8") +"="+ URLEncoder.encode(List_Price, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "리스트 등록 완료!";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        return null;
    }


    @Override
    protected void onProgressUpdate(Void... values)
    {
        super.onProgressUpdate(values);
    }

    @Override

    protected void onPostExecute(String result) {
        if(result.equals("목표 등록완료"))
            Log.i("INSERT", "INSERT_DAY/WEEK ");
        else if(result.equals("등록 완료!"))
            Log.i("INSERT", "User_Number ");
        else if(result.equals("리스트 등록 완료!"))
            Log.i("INSERT", "INSERT_List ");


    }
}
