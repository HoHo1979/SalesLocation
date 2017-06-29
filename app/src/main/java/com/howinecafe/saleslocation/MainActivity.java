package com.howinecafe.saleslocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener, View.OnClickListener {

    private static final int REQUEST_SINGIN = 100 ;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REGISTE_USER = 200 ;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private EditText email;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseApp.initializeApp(this);

        findViewById(R.id.but_login).setOnClickListener(this);

        email = (EditText) findViewById(R.id.ed_lgemail);
        password = (EditText) findViewById(R.id.ed_lgpassword);


        if(auth!=null){
            auth.signOut();
        }

        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };



//        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
//        .setProviders(Arrays.asList(
//                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
//                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()
//                )).build(),REQUEST_SINGIN
//        );


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivityForResult(intent,REGISTE_USER);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id==R.id.logout){

            auth.signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user!=null){




        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (auth != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==REGISTE_USER) {
            if (resultCode == RESULT_OK) {
                email.setText(data.getStringExtra("LOGIN_EMAIL"));
                password.setText(data.getStringExtra("LOGIN_PASSWORD"));
            }else{
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {

        String email = this.email.getText().toString();
        final String password = this.password.getText().toString();

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Intent intent = new Intent(MainActivity.this, LocationsActivity.class);
                    startActivity(intent);

                }else{
                    Log.d(TAG, "onComplete: email and password is not corrected");
                }
            }
        });

    }
}
