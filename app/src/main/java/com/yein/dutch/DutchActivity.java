package com.yein.dutch;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DutchActivity extends Bank implements View.OnClickListener{

    int year, month, day;
    String _id;

    EditText et_dutch_money;
    EditText et_findMember;
    EditText et_account;

    TextView tv_date;
    ListView lv_rentMember;

    DatePickerDialog datePicker;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dutch);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        this._id = intent.getExtras().getString("id");

        findViewById(R.id.btn_bank).setOnClickListener(this);
        findViewById(R.id.btn_search).setOnClickListener(this);
        findViewById(R.id.btn_complete).setOnClickListener(this);

        et_dutch_money = findViewById(R.id.et_money);
        et_findMember = findViewById(R.id.et_find);
        et_account = findViewById(R.id.et_account);

        tv_date = findViewById(R.id.tv_date);

        datePicker = new DatePickerDialog(this, dateSetListener, year, month, day);

        // 은행 선택하는 커스텀 다이얼로그
        LayoutInflater inflater = LayoutInflater.from(this);
        bank_list_view = inflater.inflate(R.layout.bank_list_dialog, null, true );
        // 은행 선택 하는 view 객체화
        dialog_bank = new Dialog(this);

        // 현재 날짜 가져오기
        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }


    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String msg = String.format("%d/%d/%d", year, month + 1, dayOfMonth);
            tv_date.setText(dateFormat.format());
        }
    };

    //액션 바 뒤로 가기 버튼 활성화
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class searchMember extends SendIdToServer{
        @Override
        protected void onPostExecute(String s) {
            try {
                if (!s.equalsIgnoreCase("fail")) {
                    Member member = new Member();
                    member.id = id;
                    member.rate = s;

                    rentmembers.add(member);

                    DutchMember dutchMember = new DutchMember(id);
                    dutchMember.info.add(s);

                    rentmembers2.add(dutchMember);

                    myAdapter = new MyAdapter(DutchActivity.this, R.layout.id_ratingbar, rentmembers2) ;

                    lv_rentMember.setAdapter(myAdapter);
                    lv_rentMember.setOnItemClickListener(itemClickListener);
                }else {
                    showAlert("사용자 검색", "해당 사용자가 존재하지 않습니다.");
                }
            } catch (Exception e) {
                Log.e("Error", "Exception: " + e.getMessage());
            }
            et_findMember.setText("");
        }
    }

    public void showAlert(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // 알림창 제목 셋팅
        alertDialogBuilder.setTitle(title);

        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();
        // 다이얼로그 보여주기
        alertDialog.show();
    }

    public void dutchComplete(){

    }

    @Override
    public void onClick(View view) {
        int btn_id = view.getId();
        switch (btn_id){
            case R.id.btn_bank:
                datePicker.show();
                break;
            case R.id.btn_search:
                String member = et_findMember.getText().toString();
                break;
            case R.id.btn_complete:
                dutchComplete();
                break;

        }
    }
}
