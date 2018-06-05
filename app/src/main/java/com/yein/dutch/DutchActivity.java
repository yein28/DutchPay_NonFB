package com.yein.dutch;


import android.app.DatePickerDialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DutchActivity extends Bank implements View.OnClickListener{

    int year, month, day;
    String _id;

    EditText et_dutch_money;
    EditText et_findMember;
    EditText et_account;

    TextView tv_date;
    ListView lv_debtMember;

    DatePickerDialog datePicker;
    String find_member;

    ArrayList<Member> debtMembers = new ArrayList<>();
    DebtMemberListAdapter debtMemberListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dutch);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        this._id = intent.getExtras().getString("id");

        btn_bank = findViewById(R.id.btn_bank);
        btn_bank.setOnClickListener(this);
        findViewById(R.id.btn_search).setOnClickListener(this);
        findViewById(R.id.btn_complete).setOnClickListener(this);

        et_dutch_money = findViewById(R.id.et_money);
        et_findMember = findViewById(R.id.et_find);
        et_account = findViewById(R.id.et_account);

        tv_date = findViewById(R.id.tv_date);
        tv_date.setOnClickListener(this);

        lv_debtMember = findViewById(R.id.lv_debt_member);

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

        datePicker = new DatePickerDialog(this, dateSetListener, year, month, day);
    }


    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String msg = String.format(Locale.US, "%d/%d/%d", year, month + 1, dayOfMonth);
            tv_date.setText(msg);
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

    private class GetDebtMemberInfo extends SendOneStringToServer{
        @Override
        protected void onPostExecute(String rate) {
            try {
                if (!rate.equalsIgnoreCase("fail")) {

                    Member member = new Member();

                    member.setId(find_member);
                    member.setRate(rate);
                    member.setMoney(0);

                    debtMembers.add(member);

                    debtMemberListAdapter= new DebtMemberListAdapter(debtMembers);

                    lv_debtMember.setAdapter(debtMemberListAdapter);
                    lv_debtMember.setOnItemClickListener(new DebtMemberListener());
                }else {
                    showAlert("사용자 검색", "해당 사용자가 존재하지 않습니다.");
                }
            } catch (Exception e) {
                Log.e("Error", "Exception: " + e.getMessage());
            }
            et_findMember.setText("");
        }
    }

    class DebtMemberListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DutchActivity.this);
            alertDialogBuilder
                    .setTitle("주의")
                    .setMessage("삭제하시겠습니까?")
                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            debtMembers.remove(i);
                            debtMemberListAdapter.notifyDataSetChanged();
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                       }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    public void showAlert(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // AlertDialog 셋팅
        alertDialogBuilder
                .setTitle(title)
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
        String money = et_dutch_money.getText().toString();
        String date = tv_date.getText().toString();
        String bank = btn_bank.getText().toString();
        String account = et_account.getText().toString();

        if( money.getBytes().length > 0  && !date.equalsIgnoreCase(getString(R.string.input_date))
                && debtMembers.size() > 0 && !bank.equalsIgnoreCase(getString(R.string.choose)) && account.getBytes().length >0 ) {
            Intent intent = new Intent(getApplicationContext(), DutchCompleteActivity.class);

            intent.putExtra("loan", _id);
            intent.putExtra("money", money);
            intent.putExtra("date", date);
            intent.putExtra("bank", bank);
            intent.putExtra("account", account);
            intent.putExtra("members", debtMembers);

            startActivity(intent);
        }else{
            Snackbar.make(findViewById(R.id.activity_dutch), getString(R.string.must_input), Snackbar.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        int btn_id = view.getId();
        switch (btn_id){
            case R.id.btn_bank:
                showDialog();
                break;
            case R.id.tv_date:
                datePicker.show();
                break;
            case R.id.btn_search:
                find_member = et_findMember.getText().toString();
                if( find_member.length() > 0 ){
                    GetDebtMemberInfo getDebtMemberInfo = new GetDebtMemberInfo();
                    getDebtMemberInfo.execute(getString(R.string.search_link), find_member);
                }else{
                    Snackbar.make(findViewById(R.id.activity_dutch), getString(R.string.input_debt_member), Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_complete:
                dutchComplete();
                break;
        }
    }
}
