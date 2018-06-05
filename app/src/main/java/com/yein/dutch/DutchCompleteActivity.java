package com.yein.dutch;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.text.InputType.TYPE_CLASS_NUMBER;

public class DutchCompleteActivity extends AppCompatActivity implements View.OnClickListener{

    String money;
    String date;
    String bank;
    String account;
    String loan;

    TextView tv_money, tv_date, tv_bank, tv_account;
    ListView lv_debt_member;

    ArrayList<Member> debtMembers;
    DebtListWithMoneyAdapter debtListWithMoneyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dutch_complete);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        loan = intent.getExtras().getString("loan");
        money = intent.getExtras().getString("money");
        date = intent.getExtras().getString("date");
        bank = intent.getExtras().getString("bank");
        account = intent.getExtras().getString("account");
        debtMembers = (ArrayList<Member>)intent.getSerializableExtra("members");

        tv_money = findViewById(R.id.tv_money);
        tv_date = findViewById(R.id.tv_date);
        tv_bank = findViewById(R.id.tv_bank);
        tv_account = findViewById(R.id.tv_account);
        lv_debt_member = findViewById(R.id.lv_debt);

        tv_money.setText(money);
        tv_date.setText(date);
        tv_bank.setText(bank);
        tv_account.setText(account);

        findViewById(R.id.btn_dutch_complete).setOnClickListener(this);

        // 인원수에 맞추어 더치페이
        int number = debtMembers.size();
        int individual = Integer.parseInt(money) / number;
        for( int i = 0 ; i < number ; i++ ){
            debtMembers.get(i).setMoney(individual);
        }

        debtListWithMoneyAdapter = new DebtListWithMoneyAdapter(debtMembers);
        lv_debt_member.setAdapter(debtListWithMoneyAdapter);
        lv_debt_member.setOnItemClickListener(new DebtMemberListener());
    }

    // 액션 바 뒤로 가기 버튼 활성화
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_dutch_complete){
            DebtMemberToJson();
        }
    }

    private void DebtMemberToJson(){
        JSONObject obj = new JSONObject();
        try {
            JSONArray jsonArr = new JSONArray();
            for (int i = 0; i < debtMembers.size(); i++) {
                JSONObject jo = new JSONObject();
                jo.put("debt", debtMembers.get(i).getId());
                jo.put("money", debtMembers.get(i).getMoney());
                jsonArr.put(jo);
            }
            obj.put("loan", loan);
            obj.put("date", date);
            obj.put("bank", bank);
            obj.put("account", account);
            obj.put("item", jsonArr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GetResultFromServer getResultFromServer = new GetResultFromServer();
        getResultFromServer.execute(getString(R.string.complete_link), obj.toString());
    }

    class GetResultFromServer extends SendOneStringToServer{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if (s.equalsIgnoreCase("success")) {
                    Toast.makeText(DutchCompleteActivity.this, "더치페이가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent( getApplicationContext(), StateActivity.class );
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id",loan );
                    startActivity( intent );
                } else {
                    Toast.makeText(DutchCompleteActivity.this, "다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("Error", "Exception: " + e.getMessage());
            }
        }

    }

    class DebtMemberListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
            // 돈 수정을 위한 새로운 EditText();
            final EditText revise_money = new EditText(DutchCompleteActivity.this);
            revise_money.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            revise_money.setInputType(TYPE_CLASS_NUMBER);

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DutchCompleteActivity.this);
                alertDialogBuilder
                        .setTitle(getString(R.string.revise_money))
                        .setMessage(getString(R.string.input_revise_money))
                        .setView(revise_money)
                        .setPositiveButton(getString(R.string.revise), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String m = revise_money.getText().toString();
                                if (m.getBytes().length > 0) {
                                    Member rm = (Member) adapterView.getAdapter().getItem(i);
                                    rm.setMoney(Integer.parseInt(m));
                                    debtListWithMoneyAdapter.notifyDataSetChanged();
                                    dialog.cancel();
                                } else {
                                    Snackbar.make(adapterView, "수정할 금액을 입력해주세요.", Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();
        }
    }
}
