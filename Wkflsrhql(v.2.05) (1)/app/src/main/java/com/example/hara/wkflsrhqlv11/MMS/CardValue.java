package com.example.hara.wkflsrhqlv11.MMS;

import java.util.HashMap;

/**
 * Created by yjkwon07 on 2017-07-11.
 */

public class CardValue {
    private static HashMap<String , String> hashMap=new HashMap();
    CardValue(){
        hashMap.put("15447200","Shinhan");
    }
    public static void setHashMap(HashMap<String, String> hashMap) {
        CardValue.hashMap = hashMap;
    }

    public static HashMap<String, String> getHashMap() {
        return hashMap;
    }
}
