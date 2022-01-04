package com.example.signup;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import pk.codebase.requests.HttpRequest;
import pk.codebase.requests.HttpResponse;

public class First_Screen extends AppCompatActivity {
    private TextInputLayout emailInput;
    private TextInputLayout password;
    private Button button;
    private boolean mHasNetwork;
    Dialog dialog;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        emailInput = findViewById(R.id.text_input_email);
        password = findViewById(R.id.text_input_password);
        TextView textView = findViewById(R.id.signup);
        button = findViewById(R.id.btn);
        textView.setOnClickListener(v -> {
            Intent intent = new Intent(First_Screen.this, MainActivity.class);
            startActivity(intent);
        });

        button.setOnClickListener(v -> {


            progressDialog = new ProgressDialog(First_Screen.this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(
                    android.R.color.transparent
            );


            String emaill = emailInput.getEditText().getText().toString();
            String pass = password.getEditText().getText().toString();


            HttpRequest request = new HttpRequest();
            request.setOnResponseListener(response -> {
                try {
                    if (response.code == HttpResponse.HTTP_OK) {
                        System.out.println(response.toJSONObject());
                        JSONObject result = response.toJSONObject();
                        String firstname = result.getString("firstname");
                        String username = result.getString("username");
                        String lastname = result.getString("lastname");
                        String mobile = result.getString("mobile");
                        String email = result.getString("email");
                        Intent intent = new Intent(First_Screen.this, AfterLogin.class);
                        intent.putExtra("firstname", firstname);
                        intent.putExtra("lastname", lastname);
                        intent.putExtra("username", username);
                        intent.putExtra("mobile", mobile);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        Toast.makeText(First_Screen.this, "Login Successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(First_Screen.this, "Invalid Email or Password", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            request.setOnErrorListener(error -> {
                System.out.println("There is some problem");

                // There was an error, deal with it
            });

            JSONObject json;
            try {
                json = new JSONObject();
                json.put("email", emaill);
                json.put("password", pass);
                request.post("http://codebase.pk:7000/api/login/", json);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
        trackNetworkState();
    }

    @Override
    public void onBackPressed() {
        progressDialog.dismiss();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            connMgr = getSystemService(ConnectivityManager.class);
        }
        boolean isWifiConn = false;
        boolean isMobileConn = false;
        for (Network network : connMgr.getAllNetworks()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }

        return isWifiConn | isMobileConn;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void trackNetworkState() {
        mHasNetwork = isNetworkConnected();
        if (!mHasNetwork) {
            showDialog();
        }

        ConnectivityManager cm = getSystemService(ConnectivityManager.class);
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
        builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR);

        cm.requestNetwork(builder.build(), new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                mHasNetwork = true;
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                mHasNetwork = false;
                showDialog();
            }
        });
    }

    private void showDialog() {
        dialog = new Dialog(First_Screen.this);
        dialog.setContentView(R.layout.alert_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations =
                android.R.style.Animation_Dialog;
        button = dialog.findViewById(R.id.btn);
        button.setOnClickListener(v -> recreate());
        dialog.show();
    }


//    private void check() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
//            Dialog dialog = new Dialog(this);
//            dialog.setContentView(R.layout.alert_dialog);
//            dialog.setCancelable(false);
//            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
//                    WindowManager.LayoutParams.WRAP_CONTENT);
//            dialog.getWindow().getAttributes().windowAnimations =
//                    android.R.style.Animation_Dialog;
//            button = dialog.findViewById(R.id.btn);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    recreate();
//                }
//            });
//            dialog.show();
//        }
//
//
//    }

}
