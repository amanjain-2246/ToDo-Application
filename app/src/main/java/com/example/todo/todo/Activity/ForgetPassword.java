package com.example.todo.todo.Activity;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import com.example.todo.todo.Connection.ForgotPasswordConnection;
import com.example.todo.todo.R;

public class ForgetPassword extends AppCompatActivity {
Toolbar toolbar;
    EditText mail;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        toolbar = (Toolbar) findViewById(R.id.forgottoolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
      //  ab.setTitle("Signup");
mail=(EditText)findViewById(R.id.forgettext);

    }

    public void sendmail(View view)
    {
        String mailid = mail.getText().toString();

        ForgotPasswordConnection forgotPasswordConnection = new ForgotPasswordConnection(this);
        forgotPasswordConnection.execute(mailid);



    }
}
