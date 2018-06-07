package com.yein.dutch;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;

public class LoanActivity extends AppCompatActivity {

    String _id;
    ArrayList<Member> loan_members;
    ExpandableListView elv_debt;
    ExpandableListAdapter ela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        this._id = intent.getExtras().getString("id");

        elv_debt = findViewById(R.id.elv_loan_member);
        getList gl = new getList();
        gl.execute(getString(R.string.loan_link), this._id);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class getList extends SendOneStringToServer{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // 문자열을 받아서 파싱
            LoanParser lp = new LoanParser();
            loan_members = lp.returnList(s);
            ela = new ExpandableListAdapter(loan_members, "loan");
            elv_debt.setAdapter(ela);
        }
    }
}
