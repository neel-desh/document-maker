package com.neeldeshmukh.vpn.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.neeldeshmukh.vpn.model.User;
import com.applozic.mobicomkit.uiwidgets.conversation.UIService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.neeldeshmukh.vpn.Core.MainActivity2;
import com.neeldeshmukh.vpn.R;
import com.neeldeshmukh.vpn.view.MainActivity;

public class Login extends AppCompatActivity {
        /*
        * Varaible Definiton
        */
        TextInputEditText email,password;
        FloatingActionButton arrow_login_btn;
        Button login_btn;
        TextView signup_btn,forget_password;
        FirebaseAuth firebaseAuth;
        DatabaseReference mDatabaseReference;
        private static String TAG = "Login Error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*
        * Variable Initilization
        */
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        login_btn = findViewById(R.id.login_btn);
        signup_btn = findViewById(R.id.signup_btn);
        forget_password = findViewById(R.id.forget_password);
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        /*
        * If User is Alredy Logged in then Skips the Login
        */
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(Login.this, MainActivity.class));
        }

        /*
         * Check if email and password are not empty
         */
        if(email.getText() == null){
            email.setError("Enter email");
        }else if(password.getText() == null){
            email.setError("Enter password");
        }
        /*
         * Send Data to Database and Forward to Main activity
         */
        //Login button login
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            loginfun(email.getText().toString(),password.getText().toString(),firebaseAuth);
            }
        });
//        //Arrow button login
//        login_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loginfun(email.getText().toString(),password.getText().toString(),firebaseAuth);
//
//            }
//        });
        /*
        * Redirect to Signup Activity
        */
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Signup.class));
                finish();
            }
        });
        /*
        * Redirect to Forget password Activity
        */
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ResetPassword.class));
//                finish();
            }
        });
    }

    /*
     * Common Login Function
     */
    public void loginfun(String email, String password, final FirebaseAuth mAuth){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//
//                            User appuser = new User(user.getUid(),user.getDisplayName(),user.getEmail(),password);
//                            mDatabaseReference.child("Users").child(appuser.getUuid()).setValue(appuser);
                           // mDatabaseReference.push();
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
}
