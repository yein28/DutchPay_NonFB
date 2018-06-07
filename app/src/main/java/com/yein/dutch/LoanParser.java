package com.yein.dutch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoanParser {
    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_RATE = "rate";
    private static final String TAG_MONEY = "money";
    private static final String TAG_DATE= "date";

    public ArrayList<Member> returnList(String trim){
        ArrayList<Member> tmp = new ArrayList<>();
        try {
            JSONObject jsonObj = new JSONObject(trim);
            JSONArray jsonarray = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i = 0 ; i < jsonarray.length() ; i++ ){
                JSONObject c = jsonarray.getJSONObject(i);

                String id = c.getString(TAG_ID);
                String rate = c.getString(TAG_RATE);
                String money = c.getString(TAG_MONEY);
                String date = c.getString(TAG_DATE);

                Member m = new Member(id, rate, money, date);

                tmp.add(m);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tmp;
    }
}
