package com.yein.dutch;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Bank extends AppCompatActivity {

    View bank_list_view;
    Button btn_bankChose;
    Dialog dialog_bank;

    public void bank_select(View view){
        showDialog();
    }

    private void showDialog() {
        dialog_bank.setTitle("은행 선택");
        dialog_bank.setContentView(bank_list_view);
        dialog_bank.show();
    }

    //은행 선택 dialog에서 은행 선택하여 버튼 text 변경
    public void myBank(View view) {
        switch(view.getId()) {
            case R.id.bank1:
                btn_bankChose.setText(getString(R.string.nh_bank));
                break;
            case R.id.bank2:
                btn_bankChose.setText(getString(R.string.kb_bank));
                break;
            case R.id.bank3:
                btn_bankChose.setText(getString(R.string.shinhan_bank));
                break;
            case R.id.bank4:
                btn_bankChose.setText(getString(R.string.jeonbook_bank));
                break;
            case R.id.bank5:
                btn_bankChose.setText(getString(R.string.ibk_bank));
                break;
            case R.id.bank6:
                btn_bankChose.setText(getString(R.string.post_bank));
                break;
            case R.id.bank7:
                btn_bankChose.setText(getString(R.string.daegu_bank));
                break;
            case R.id.bank8:
                btn_bankChose.setText(getString(R.string.woori_bank));
                break;
            case R.id.bank9:
                btn_bankChose.setText(getString(R.string.hana_bank));
                break;
        }
        btn_bankChose.setTextColor(Color.parseColor("#737373"));
        dialog_bank.cancel();
    }
}
