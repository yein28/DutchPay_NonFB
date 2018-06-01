package com.yein.dutch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText _id;
    private EditText _pwd;

    String link = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        this._id = findViewById(R.id.et_id);
        this._pwd = findViewById(R.id.et_pwd);
    }

    public void join(View view){
        Intent intent = new Intent(getApplicationContext(), null);
        //
        startActivity(intent);
    }

}
