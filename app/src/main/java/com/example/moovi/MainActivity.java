package com.example.moovi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText password;
    EditText userName;
    CheckBox showPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        password = (EditText) findViewById(R.id.password);
        userName = (EditText) findViewById(R.id.userName);
        showPassword = (CheckBox) findViewById(R.id.showPassword);

        //Hides/Shows password to user
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    //NEW REGISTER
    public void byPassLogin(View v) {
        String email = "jere@hotmail.com";
        String pass = "testitesti";

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SUCCESS", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            HallSystem.getInstance().setUser(user);
                            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FAILURE", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            HallSystem.getInstance().setUser(null);
                        }

                        // ...
                    }
                });



    }



    //takes user to register window
    public void toRegister(View v) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);

    }
    //takes user to login window
    public void toLogin(View v) {
        String email = userName.getText().toString();
        String pass = password.getText().toString();

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SUCCESS", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            HallSystem.getInstance().setUser(user);
                            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FAILURE", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            HallSystem.getInstance().setUser(null);
                        }

                        // ...
                    }
                });
        //TODO tähän tapa tarkistaa käyttäjän nimi sekä salasana tietokannasta, jotta voi kirjautua sisään


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        HallSystem.getInstance().setUser(currentUser);
    }

}
