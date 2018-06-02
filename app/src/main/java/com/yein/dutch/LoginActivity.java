package com.yein.dutch;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText _id;
    private EditText _pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        this._id = findViewById(R.id.et_id);
        this._pwd = findViewById(R.id.et_pwd);
    }

    // 메인 화면에서 회원가입을 선택했을 경우, JoinActivity 시작
    public void join(View view){
        Intent intent = new Intent(this, JoinActivity.class);
        startActivity(intent);
    }

    // 메인화면에서 ID와 비밀번호를 입력한 후 로그인을 눌렀을 경우
    public void login(View view){
        String id = this._id.getText().toString();
        String pwd = this._pwd.getText().toString();

        if( id.length() > 0 && pwd.length() >0 ){
            GetLoginResultFromServer getLoginResultFromServer = new GetLoginResultFromServer();
            getLoginResultFromServer.execute(getString(R.string.login_link), id, pwd);
        }else{
            Snackbar.make(view, getString(R.string.input_id_pwd), Snackbar.LENGTH_SHORT).show();
        }
    }

    class GetLoginResultFromServer extends SendInfoToServer{
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            if(result.equalsIgnoreCase("success")){
                Intent intent = new Intent(getApplicationContext(), StateActivity.class);
                intent.putExtra("id", _id.getText().toString());
                startActivity(intent);
                finish();
            }else{
                Snackbar.make(findViewById(R.id.activity_login), getString(R.string.chk_id_pwd), Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
