package com.example.signup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.Object;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import pk.codebase.requests.HttpError;
import pk.codebase.requests.HttpRequest;
import pk.codebase.requests.HttpResponse;


public class AfterLogin extends AppCompatActivity {
    TextView tv,tv1,tv2,tv3,tv4;
    Button btn,btn1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_login);
        tv = (TextView) findViewById(R.id.tv);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv.setText(getIntent().getStringExtra("firstname"));
        tv1.setText(getIntent().getStringExtra("lastname"));
        tv2.setText(getIntent().getStringExtra("email"));
        tv3.setText(getIntent().getStringExtra("number"));
        tv4.setText(getIntent().getStringExtra("username"));
        btn = (Button) findViewById(R.id.btn);
        btn1 = (Button) findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = tv.getText().toString();
                String last = tv1.getText().toString();
                String uname = tv4.getText().toString();
                String mobile = tv3.getText().toString();
                String email = tv2.getText().toString();
                Intent intent = new Intent(AfterLogin.this,Edit.class);
                intent.putExtra("firstname",first);
                intent.putExtra("lastname",last);
                intent.putExtra("username",uname);
                intent.putExtra("mobile",mobile);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLogin.this,First_Screen.class);
                startActivity(intent);
            }
        });
    }

}

