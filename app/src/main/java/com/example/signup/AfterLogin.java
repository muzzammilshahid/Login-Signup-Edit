package com.example.signup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AfterLogin extends AppCompatActivity {
    TextView tv, tv1, tv2, tv3, tv4;
    Button btn, btn1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_login);
        tv = findViewById(R.id.tv);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv.setText(getIntent().getStringExtra("firstname"));
        tv1.setText(getIntent().getStringExtra("lastname"));
        tv2.setText(getIntent().getStringExtra("email"));
        tv3.setText(getIntent().getStringExtra("number"));
        tv4.setText(getIntent().getStringExtra("username"));
        btn = findViewById(R.id.btn);
        btn1 = findViewById(R.id.btn1);
        btn.setOnClickListener(v -> {
            String first = tv.getText().toString();
            String last = tv1.getText().toString();
            String uname = tv4.getText().toString();
            String mobile = tv3.getText().toString();
            String email = tv2.getText().toString();
            Intent intent = new Intent(AfterLogin.this, Edit.class);
            intent.putExtra("firstname", first);
            intent.putExtra("lastname", last);
            intent.putExtra("username", uname);
            intent.putExtra("mobile", mobile);
            intent.putExtra("email", email);
            startActivity(intent);
        });

        btn1.setOnClickListener(v -> {
            Intent intent = new Intent(AfterLogin.this, First_Screen.class);
            startActivity(intent);
        });
    }

}

