package com.yein.dutch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class StateActivity extends AppCompatActivity implements View.OnClickListener {

    String _id;
    String _rate;
    RatingBar rate;
    TextView tv_debt;
    TextView tv_loan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_state);

        rate = findViewById(R.id.rb_rate);

        // 버튼 리스너
        findViewById(R.id.btn_debt).setOnClickListener(this);
        findViewById(R.id.btn_loan).setOnClickListener(this);
        findViewById(R.id.btn_dutch).setOnClickListener(this);

        Intent intent = getIntent();
        // 사용자의 ID를 상단에 표시, 없을 경우 종료
        this._id = intent.getExtras().getString("id");
        if(this._id == null) {
            finish();
            return;
        }else{
            this.setTitle(this._id);
        }

        this.tv_debt = findViewById(R.id.tv_debt);
        this.tv_loan = findViewById(R.id.tv_loan);

        // 사용자의 정보를 가져와서 세팅하는 부분
        SetStateToUI setStateToUI = new SetStateToUI();
        setStateToUI.execute(getString(R.string.state_link),this._id);
    }

    class SetStateToUI extends SendIdToServer{
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String[] val = result.split("/");
            rate.setRating(Float.parseFloat(val[0]));
            tv_loan.setText(val[1]);
            tv_debt.setText(val[2]);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        int btn_id = view.getId();

        if( btn_id == R.id.btn_debt ){
            intent = new Intent(getApplicationContext(), DebtActivity.class);
            intent.putExtra( "id", this._id);
        }else if( btn_id == R.id.btn_loan ){
            intent = new Intent(getApplicationContext(), LoanActivity.class);
            intent.putExtra( "id", this._id );
        }else if( btn_id == R.id.btn_dutch ){
            intent = new Intent(getApplicationContext(), DutchActivity.class);
            intent.putExtra( "id", this._id );
            intent.putExtra("rate", this._rate);
        }

        startActivity(intent);
    }
}
