package com.example.hara.wkflsrhqlv11.Page1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.hara.wkflsrhqlv11.CreateID_UUID;
import com.example.hara.wkflsrhqlv11.DBConnect.DB_Background_Task;
import com.example.hara.wkflsrhqlv11.R;

public class Page1_today_list_cashActivity extends Fragment implements View.OnClickListener {
    View view;
    public CreateID_UUID createID_uuid;
    String PhoneNum;
    EditText Cash_Place,Cash_Price;
    DB_Background_Task db_background_task_cash;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.page1_today_list_add_cash, container, false);
        PhoneNum=(String) createID_uuid.getUniqueID(view.getContext());
        Cash_Place=(EditText)view.findViewById(R.id.Cash_Place);
        Cash_Price=(EditText)view.findViewById(R.id.Cas_Price);
        db_background_task_cash=new DB_Background_Task(view.getContext());
        return view;
    }
    public void onStart() {
        super.onStart();
        getView().findViewById(R.id.close).setOnClickListener(this);
        getView().findViewById(R.id.Cash_send).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                getFragmentManager().beginTransaction().replace(R.id.frag_container, new Page1_today_listActivity()).commit();
                break;
            case R.id.Cash_send:
                String place=toString().valueOf(Cash_Place.getText());
                String price=toString().valueOf(Cash_Price.getText());
                String cash="cash";
                if(place.isEmpty() || price.isEmpty()){
                    Intent waringIntent = new Intent(view.getContext(), Page1_today_list_waring.class);
                    waringIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    view.getContext().startActivity(waringIntent);

                    /*Page1_today_containActivity page1 = new Page1_today_containActivity();
                    page1.task.execute("List_day",PhoneNum);
                    page1.task1.execute("get_today",PhoneNum);*/
                }
               else {
                    Log.i("INSERT", "INSERT_cash " + place + " " + price);
                    db_background_task_cash.execute("register_List", PhoneNum, cash, place, price);
                    getFragmentManager().beginTransaction().replace(R.id.frag_container, new Page1_today_listActivity()).commit();
                    break;
                }
        }
    }
}