package com.howinecafe.saleslocation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.howinecafe.saleslocation.data.Sales;
import com.howinecafe.saleslocation.data.SalesLocation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private EditText password;
    private EditText passwordComfirm;

    FirebaseAuth auth;
    FirebaseUser user;
    private String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();

        retrive_userInput();

        findViewById(R.id.but_register).setOnClickListener(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void retrive_userInput() {
        email = (EditText) findViewById(R.id.ed_lgemail);
        password = (EditText) findViewById(R.id.ed_lgpassword);
        passwordComfirm = (EditText) findViewById(R.id.ed_password_comfirm);
    }

    @Override
    public void onClick(View v) {

        String email= this.email.getText().toString();
        String password = this.password.getText().toString();
        String passwordComfirm = this.passwordComfirm.getText().toString();


        if(checkEmailPassword(email, password, passwordComfirm)){
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        String email=RegisterActivity.this.email.getText().toString();
                        String password = RegisterActivity.this.password.getText().toString();

                        FirebaseUser user = task.getResult().getUser();

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference mRef = database.getReference("sales").child(user.getUid());
                        Sales sales = new Sales();
                        sales.setEmail(email);
                        sales.setName("James Ho");

                        Map<String,Boolean> locationKeyMap = new HashMap<String, Boolean>();

                        DatabaseReference locationRef=database.getReference("locations").child(user.getUid());

                        SalesLocation location = new SalesLocation(-33.45,35.55,"Google",new Date().getTime());
                        String locationkey=locationRef.push().getKey();
                        locationRef.child(locationkey).setValue(location);

                        SalesLocation location1 = new SalesLocation(45.44,33.44,"Google",new Date().getTime());
                        String locationkey2=locationRef.push().getKey();
                        locationRef.child(locationkey2).setValue(location1);

                        mRef.setValue(sales);

                        mRef.child("locations").child(locationkey).setValue(true);
                        mRef.child("locations").child(locationkey2).setValue(true);

                        getIntent().putExtra("LOGIN_EMAIL",email);
                        getIntent().putExtra("LOGIN_PASSWORD",password);
                        setResult(RESULT_OK,getIntent());
                        finish();
                    }else{

                        new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle("The Account is not created")
                                .setMessage("Fail to Create Account")
                                .setPositiveButton("OK",null).show();

                    }
                }
            });

        }


    }

    private boolean checkEmailPassword(String email, String password, String passwordComfirm) {

        if(TextUtils.isEmpty(email)){
            this.email.setTransformationMethod(new AsteriskPasswordTransformationMethod());
            return false;
        }else if(!password.equals(passwordComfirm)){
            this.passwordComfirm.setTransformationMethod(new AsteriskPasswordTransformationMethod());
            return false;
        }

        return true;
    }
}
