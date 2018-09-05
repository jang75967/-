package com.example.hara.wkflsrhqlv11.DBConnect;

import java.util.ArrayList;

/**
 * Created by HARA on 2017-06-25.
 */

public class Goal {
    public Goal(){
    }

    //목표량 가져오기
    private static String goal_date="";
    private static String goal_today="0";
    private static String goal_week="";
    private static String goal_yesterday="";

    //리스트 필드
    private static ArrayList<String> List_Date;
    private static ArrayList<String> Card_Name;
    private static ArrayList<String> List_Place;
    private static ArrayList<String> List_Price;

    //카드 번호
    private  static String Card_Number;


    //리스트 하루 sum
    private static ArrayList<String> List_DaySum=new ArrayList<String>(31);

    //리스트 week
    private static ArrayList<String> List_WeekSum=new ArrayList<String>(5);

    //각 리스트 필드
    private static ArrayList<String> List_Date_day;
    private static ArrayList<String> Card_Name_day;
    private static ArrayList<String> List_Place_day;
    private static ArrayList<String> List_Price_day;



    private static  ArrayList<Integer> Lis_day;

    public static String getCard_Number() {
        return Card_Number;
    }

    public static void setCard_Number(String card_Number) {
        Card_Number = card_Number;
    }


    public static ArrayList<String> getList_Date() {
        return List_Date;
    }

    public static void setList_Date(ArrayList<String> list_Date) {
        List_Date = list_Date;
    }

    public static ArrayList<String> getCard_Name() {
        return Card_Name;
    }

    public static void setCard_Name(ArrayList<String> card_Name) {
        Card_Name = card_Name;
    }

    public static ArrayList<String> getList_Place() {
        return List_Place;
    }

    public static void setList_Place(ArrayList<String> list_Place) {
        List_Place = list_Place;
    }

    public static ArrayList<String> getList_Price() {
        return List_Price;
    }

    public static void setList_Price(ArrayList<String> list_Price) {
        List_Price = list_Price;
    }





    public static String getGoal_date() {
        return goal_date;
    }
    public void setGoal_date(String goal_date) {
        this.goal_date = goal_date;
    }

    public static String getGoal_today() {
        return goal_today;
    }
    public void setGoal_today(String goal_today) {
        this.goal_today = goal_today;
    }

    public static String getGoal_week() {
        return goal_week;
    }
    public void setGoal_week(String goal_week) {
        this.goal_week = goal_week;
    }

    public static String getGoal_yesterday() {
        return goal_yesterday;
    }
    public static void setGoal_yesterday(String goal_yesterday) {
        Goal.goal_yesterday = goal_yesterday;
    }

    public static ArrayList<String> getList_DaySum() {
        return Goal.List_DaySum;
    }

    public static ArrayList<String> getList_WeekSum() {
        return Goal.List_WeekSum;
    }

    public static void setList_DaySum(ArrayList<String> list_DaySum) {
        Goal.List_DaySum = list_DaySum;
    }

    public static void setList_WeekSum(ArrayList<String> list_WeekSum) {
        Goal.List_WeekSum = list_WeekSum;
    }

    public static ArrayList<String> getList_Date_day() {
        return Goal.List_Date_day;
    }

    public static void setList_Date_day(ArrayList<String> list_Date_day) {
        Goal.List_Date_day = list_Date_day;
    }

    public static ArrayList<String> getCard_Name_day() {
        return Goal.Card_Name_day;
    }

    public static void setCard_Name_day(ArrayList<String> card_Name_day) {
        Goal.Card_Name_day = card_Name_day;
    }

    public static ArrayList<String> getList_Price_day() {
        return Goal.List_Price_day;
    }

    public static void setList_Price_day(ArrayList<String> list_Price_day) {
        Goal.List_Price_day = list_Price_day;
    }

    public static ArrayList<String> getList_Place_day() {
        return Goal.List_Place_day;
    }

    public static void setList_Place_day(ArrayList<String> list_Place_day) {
        Goal.List_Place_day = list_Place_day;
    }

    public static ArrayList<Integer> getLis_day() {
        return Goal.Lis_day;
    }

    public static void setLis_day(ArrayList<Integer> lis_day) {
        Goal.Lis_day = lis_day;
    }
    @Override
    public String toString() {
        return "Goal{" +
                "goal_date='" + goal_date + '\'' +
                ", goal_today='" + goal_today + '\'' +
                ", goal_week='" + goal_week + '\'' +
                '}';
    }


}
