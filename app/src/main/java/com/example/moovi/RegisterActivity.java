package com.example.moovi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    EditText editLast;
    EditText editEmail;
    EditText editFirst;
    TextView textError;
    EditText editPassword;
    DatePicker datePicker;
    private FirebaseAuth mAuth;
    String email;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);

        editFirst = findViewById(R.id.editFirst);
        editLast = findViewById(R.id.editLast);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        textError = findViewById(R.id.textError);
        datePicker = findViewById(R.id.datePicker);
        Database db = Database.getInstance();
        User testi = new User("salainensana", "jere@hotmail.com", "Jack", "Death", null);
        db.addUser(testi);






    }

    public void registerAccount(View v) {

        email = editEmail.getText().toString();
        password = editPassword.getText().toString();
        System.out.println(email +"  JA SALASANA ON  " + password);
        regAcc(email, password);
    }

    public void regAcc(String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SUCCESS", "createUserWithEmail:success");
                            Toast.makeText(RegisterActivity.this, "Account created.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            HallSystem.getInstance().setUser(user);
                            Intent intent = new Intent(RegisterActivity.this, Main2Activity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FAILURE", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            HallSystem.getInstance().setUser(null);
                        }

                        // ...
                    }
                });
    }


}
