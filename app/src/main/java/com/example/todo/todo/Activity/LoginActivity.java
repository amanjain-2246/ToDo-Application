package com.example.todo.todo.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import com.example.todo.todo.Connection.LoginConnection;
import com.example.todo.todo.R;

public class LoginActivity extends AppCompatActivity {

    EditText et1,et2;
    String mail,upass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et1 = (EditText) findViewById(R.id.unameedit);
        et2 = (EditText) findViewById(R.id.upassedit);
    }

    public void logIn(View view)
    {

        mail = et1.getText().toString();
        upass = et2.getText().toString();
        String method = "login";
        LoginConnection loginConnection=new LoginConnection(this);
        loginConnection.execute(method,mail,upass);

    }

    public void register(View view)
    {
        Intent intent = new Intent(this,registerActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.card_flip_right_in,R.anim.card_flip_right_out);
    }

    public void forgot(View view)
    {
        Intent intent=new Intent(this,ForgetPassword.class);
        startActivity(intent);
        overridePendingTransition(R.anim.card_flip_right_in,R.anim.card_flip_right_out);

    }
    @Override
    public void onBackPressed() {
       super.onBackPressed();
        moveTaskToBack(true);
        finish();
        System.exit(0);
    }

}
