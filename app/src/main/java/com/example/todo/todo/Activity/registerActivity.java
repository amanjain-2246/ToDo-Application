package com.example.todo.todo.Activity;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todo.todo.Connection.RegisterConnection;
import com.example.todo.todo.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class registerActivity extends AppCompatActivity {

    EditText etname,etmail,etpass;
    String name,mail,upass;
    Toolbar toolbar;
    int validateemail,validatename,validatepass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toolbar = (Toolbar) findViewById(R.id.regtoolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Signup");

        etname = (EditText) findViewById(R.id.name);
        etmail = (EditText) findViewById(R.id.umail);
        etpass = (EditText) findViewById(R.id.upass);
    }

    public void register(View view)
    {
        name= etname.getText().toString();
        mail=etmail.getText().toString();
        upass=etpass.getText().toString();


        if (emailValidator(mail)) {

            validateemail =1;
        } else {
            validateemail = 0;
            Toast.makeText(this,"Wrong email ID", Toast.LENGTH_LONG).show();
            etmail.requestFocus();
        }
        if(name.equals(""))
        {
            validatename = 0;
            Toast.makeText(this,"Please insert name", Toast.LENGTH_LONG).show();
            etname.requestFocus();
        }
        else
        {
            validatename =1;
        }
        if(upass.length()>=8)
        {
            validatepass =1;
        }
        else
        {
            validatepass = 0;
            Toast.makeText(this,"Password should have minimum 8 characters", Toast.LENGTH_LONG).show();
            etpass.requestFocus();
        }
        if(validateemail==1 && validatename==1 && validatepass==1) {
            String method = "Register";
            RegisterConnection registerConnection = new RegisterConnection(this);
            registerConnection.execute(method, name, mail, upass);
            finish();
        }
    }

    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
