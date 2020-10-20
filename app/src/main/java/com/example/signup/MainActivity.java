package com.example.signup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import pk.codebase.requests.HttpError;
import pk.codebase.requests.HttpRequest;
import pk.codebase.requests.HttpResponse;

public class MainActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    private static final Pattern USER_NAME =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    private TextInputLayout firstname;
    private TextInputLayout lastname;
    private TextInputLayout password;
    private TextInputLayout confirmpassword;
    private TextInputLayout  email;
    private TextInputLayout username;
    private TextInputLayout phone;
    Button btn;
    ImageView myImg;
    Uri mImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btn= findViewById(R.id.btn2);
        myImg = findViewById(R.id.img);









    firstname= findViewById(R.id.text_input_fname);
    lastname= findViewById(R.id.text_input_lname);
    password= findViewById(R.id.text_input_password);
    confirmpassword = findViewById(R.id.text_input_repassword);
    email= findViewById(R.id.text_input_email);
    username= findViewById(R.id.text_input_username);
    phone = findViewById(R.id.text_input_phone);


    }


    private boolean validatefirstname(){
        String emailInput = firstname.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            firstname.setError("Field can't be empty");
            return false;
        } else {
            firstname.setError(null);
            return true;
        }
    }

    private boolean validatelastname(){
        String emailInput = lastname.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            lastname.setError("Field can't be empty");
            return false;
        } else {
            lastname.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String passwordInput = password.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            password.setError("Field can't be empty");
            return false;
        }
        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()){
            password.setError("Password too weak");
            return false;

        }
        else {
            password.setError(null);
            return true;
        }
    }
    private boolean validateConfirmPassword() {
        String passwordInput1 = password.getEditText().getText().toString().trim();
        String passwordInput = confirmpassword.getEditText().getText().toString().trim();
        System.out.println(passwordInput1);
        System.out.println(passwordInput);
        if (passwordInput.isEmpty()) {
            confirmpassword.setError("Field can't be empty");
            return false;
        }
        else if (!passwordInput1.equals(passwordInput)){
            confirmpassword.setError("Password doesnot match");
            return false;
        } else {
            confirmpassword.setError(null);
            return true;
        }
    }
    private boolean validateEmail() {
        String emailInput = email.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            email.setError("Field can't be empty");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            email.setError("Enter a valid Email Address");
            return false;
        }
        else {
            email.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        String usernameInput = username.getEditText().getText().toString().trim();
        if (usernameInput.isEmpty()) {
            username.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            username.setError("Username too long");
            return false;
        }
        else if (!USER_NAME.matcher(usernameInput).matches()){
            username.setError("Pattren doesnot match");
            return false;

        }
        else {
            username.setError(null);
            return true;
        }
    }

    private boolean validatePhone() {
        String emailInput = phone.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            phone.setError("Field can't be empty");
            return false;
        } else {
            phone.setError(null);
            return true;
        }
    }

    public void confirmInput(View v) {
        if (!validatefirstname() || !validatelastname() || !validatePassword() ||
                !validateConfirmPassword() || !validateEmail() || !validateUsername() || !validatePhone()) {
            System.out.println("BHAI SOMETHINGH WAS BROKEN");
            return;
        }
        /**String input = "First Name: " + firstname.getEditText().getText().toString();
        input += "\n";
        input += "Last Name: " + lastname.getEditText().getText().toString();
        input += "\n";
        input += "Password: " + password.getEditText().getText().toString();
        input += "\n";
        input += "Confirm Password: " + confirmpassword.getEditText().getText().toString();
        input += "\n";
        input += "Email: " + email.getEditText().getText().toString();
        input += "\n";
        input += "Username: " + username.getEditText().getText().toString();
        input += "\n";
        input += "Phone: " + phone.getEditText().getText().toString();
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();**/
        String first = firstname.getEditText().getText().toString();
        String last = lastname.getEditText().getText().toString();
        String pass = password.getEditText().getText().toString();
        String cpass = confirmpassword.getEditText().getText().toString();
        String emaill = email.getEditText().getText().toString();
        String uname = username.getEditText().getText().toString();
        String number = phone.getEditText().getText().toString();


        HttpRequest request = new HttpRequest();
        request.setOnResponseListener(new HttpRequest.OnResponseListener() {
            @Override
            public void onResponse(HttpResponse response) {
                if (response.code == HttpResponse.HTTP_OK) {
                    System.out.println(response.toJSONObject());
                    Intent intent = new Intent(MainActivity.this,First_Screen.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this,"Signup Successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
        request.setOnErrorListener(new HttpRequest.OnErrorListener() {
            @Override
            public void onError(HttpError error) {
                System.out.println("THERE WWSDS SD S D");
                System.out.println(error.reason);
                // There was an error, deal with it
            }
        });

        JSONObject json;
        try {
            json = new JSONObject();
            json.put("firstname", first);
            json.put("lastname", last);
            json.put("password",pass);
            json.put("email",emaill);
            json.put("username",uname);
            json.put("mobile",number);
            request.post("http://codebase.pk:7000/api/users/", json);
        } catch (JSONException ignore) {
            ignore.printStackTrace();
        }
    }
    public void onChooseFile(View view){
        CropImage.activity().start(MainActivity.this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode==RESULT_OK){
                mImageUrl= result.getUri();
                myImg.setImageURI(mImageUrl);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){

                Exception e = result.getError();
                Toast.makeText(this,"Possible Error is"+e,Toast.LENGTH_SHORT).show();

            }


        }
    }
}