package com.yein.dutch;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DebtActivity extends AppCompatActivity {

    ExpandableListView elv_debt;
    String _id;
    ArrayList<Member> debt_members;
    ExpandableListAdapter ela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        this._id = intent.getExtras().getString("id");

        elv_debt = findViewById(R.id.elv_debt_member);
        getList gl = new getList();
        gl.execute(getString(R.string.rent_link), this._id);
    }

    //액션 바 뒤로 가기 버튼 활성화
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class getList extends SendOneStringToServer{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // 문자열을 받아서 파싱
            DebtParser dp = new DebtParser();
            debt_members = dp.returnList(s);
            ela = new ExpandableListAdapter(debt_members, "debt");
            elv_debt.setAdapter(ela);
        }
    }
}
