package com.example.moovi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    TextView textError;
    EditText editPassword1, editPassword2, editEmail;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);
        editEmail = findViewById(R.id.editEmail);
        editPassword1 = findViewById(R.id.editPhone);
        editPassword2 = findViewById(R.id.editAddress);
        textError = findViewById(R.id.textError);

    }
    public void onClick(View v) {
        String pass1 = editPassword1.getText().toString();
        String pass2 = editPassword2.getText().toString();
        String email = editEmail.getText().toString();
        String errortext = "";
        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasLowercase = false;
        boolean hasCapital = false;
        boolean hasSpecCharacter = false;
        boolean hasLenght = false;
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
        Matcher matcher = pattern.matcher(pass1);

        if (email.isEmpty() != true && pass1.isEmpty() != true && pass2.isEmpty() != true && pass1.equals(pass2)) {
            if(pass1.length() >= 12) {
            hasLenght = true;
            }
            for (int i = 0; i < pass1.length(); i++) {
                char x = pass1.charAt(i);
                if (Character.isLetter(x)) {
                    hasLetter = true;
                }
                if (Character.isDigit(x)) {
                    hasDigit = true;
                }
                if (Character.isUpperCase(x)) {
                    hasCapital = true;
                }
                if (Character.isLowerCase(x)) {
                    hasLowercase = true;
                }
                if (!matcher.matches()){
                    hasSpecCharacter = true;
                }
                if (hasLetter && hasDigit && hasCapital && hasSpecCharacter && hasLowercase) {
                    regAcc(email, pass1);
                    break;
                }
            }

        } if(pass1.isEmpty() || pass2.isEmpty()) {
            errortext = "Please enter a password on blank password spaces";
            textError.setText(errortext);
        }else if(email.isEmpty()) {
            errortext = "Please enter an email on the blank email space";
            textError.setText(errortext);
        }else if(pass1.equals(pass2) == false && pass1.isEmpty() == false && pass2.isEmpty() == false) {
            errortext = "Please check that both of the passwords matches with eachother.";
            textError.setText(errortext);

        }else{
            errortext = "Invalid password input! The password must consist of:\n";
            if(hasLenght == false) {
                errortext = errortext + "Lenght of atleast 12 characters.\n";
            }
            if(hasLetter == false) {
                errortext = errortext + "Atleast one letter\n";
            }
            if(hasCapital == false) {
                errortext = errortext + "Atleast one capital letter\n";
            }
            if(hasLowercase == false) {
                errortext = errortext + "Atleast one lowercase letter\n";
            }
            if(hasDigit == false) {
                errortext = errortext + "Atleast one number\n";
            }
            if(hasSpecCharacter == false) {
                errortext = errortext + "Atleast one special letter\n";
            }
            textError.setText(errortext);
        }
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
