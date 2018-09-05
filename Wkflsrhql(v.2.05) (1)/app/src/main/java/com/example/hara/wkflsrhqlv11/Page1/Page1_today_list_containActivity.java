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

public class Page1_today_list_containActivity extends Fragment implements View.OnClickListener {
    View view;
    public CreateID_UUID createID_uuid;
    String PhoneNum;
    EditText List_Card,List_Place,List_Price;
    DB_Background_Task db_background_task_contain;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.page1_today_list_add_contain, container, false);
        List_Card = (EditText) view.findViewById(R.id.List_Card);
        List_Place = (EditText) view.findViewById(R.id.List_Place);
        List_Price = (EditText) view.findViewById(R.id.List_Price);
        createID_uuid=new CreateID_UUID();
        PhoneNum=(String) createID_uuid.getUniqueID(view.getContext());
        db_background_task_contain = new DB_Background_Task(view.getContext());
        return  view;
    }

    //라디오버튼(카드,현금)에 따라 화면 달라지기

    public void onStart() {
        super.onStart();
        getView().findViewById(R.id.radio_card).setOnClickListener(this);
        getView().findViewById(R.id.radio_cash).setOnClickListener(this);
        getView().findViewById(R.id.card_send).setOnClickListener(this);
        getView().findViewById(R.id.contain_close).setOnClickListener(this);
        getView().findViewById(R.id.card_close).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
                case R.id.radio_card:
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.list_frag_container, new Page1_today_list_cardActivity())
                            .commit();
                    break;
                case R.id.radio_cash:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.list_frag_container, new Page1_today_list_cashActivity())
                        .commit();
                break;
                case R.id.card_close:
                    getFragmentManager().beginTransaction().replace(R.id.frag_container, new Page1_today_listActivity()).commit();
                break;
                case R.id.contain_close:
                    getFragmentManager().beginTransaction().replace(R.id.frag_container, new Page1_today_listActivity()).commit();
                break;

                case R.id.card_send:
                    String card_name= toString().valueOf(List_Card.getText());
                    String card_place=toString().valueOf(List_Place.getText());
                    String card_price=toString().valueOf(List_Price.getText());
                    if(card_name.isEmpty() || card_place.isEmpty()|| card_price.isEmpty()) {
                        Intent waringIntent = new Intent(view.getContext(), Page1_today_list_waring.class);
                        waringIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        view.getContext().startActivity(waringIntent);

                    }
                    else {
                        Log.i("INSERT","INSERT_card "+card_place+" "+card_price);
                        db_background_task_contain.execute("register_List", PhoneNum, card_name, card_place, card_price);
                        getFragmentManager().beginTransaction().replace(R.id.frag_container, new Page1_today_listActivity()).commit();
                    }
                break;

        }

    }
}