package com.yein.dutch;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class JoinActivity extends AppCompatActivity {

    private EditText _id;
    private EditText _pwd;
    private EditText _pwdCheck;

    String link = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        this._id = findViewById(R.id.et_inputID);
        this._pwd = findViewById(R.id.et_inputPwd);
        this._pwdCheck = findViewById(R.id.et_pwdCheck);
    }

    public void join(View view){
        String id = this._id.getText().toString();
        String pwd = this._pwd.getText().toString();
        String chk = this._pwdCheck.getText().toString();

        if(pwd.equals(chk)){
            if(id.getBytes().length > 0 && pwd.getBytes().length > 0){
                InputMemberToDB inputMemberToDB = new InputMemberToDB();
                inputMemberToDB.execute(getString(R.string.default_link)+"join.php", id, pwd);
            }else{
                Snackbar.make(view,getString(R.string.input_id_pwd),Snackbar.LENGTH_SHORT).show();
            }
        }else{
            Snackbar.make(view, getString(R.string.diff_pwd_chk), Snackbar.LENGTH_SHORT).show();
        }
    }



}
