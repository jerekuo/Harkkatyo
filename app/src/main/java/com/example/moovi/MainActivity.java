package com.example.moovi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText password;
    EditText userName;
    CheckBox showPassword;
    String email;
    String pass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
        password = findViewById(R.id.password);
        userName = findViewById(R.id.userName);
        showPassword = findViewById(R.id.showPassword);

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
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SUCCESS", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            HallSystem.getInstance().setUser(user);
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
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
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        email = userName.getText().toString();
        pass = password.getText().toString();

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            if(email.equals("admin@gmail.com")){
                                FirebaseUser user = mAuth.getCurrentUser();
                                HallSystem.getInstance().setUser(user);
                                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                                startActivity(intent);
                            }else{
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("SUCCESS", "signInWithEmail:success");
                                loginAuth();
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FAILURE", "signInWithEmail:failure", task.getException());
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            HallSystem.getInstance().setUser(null);
                        }

                        // ...
                    }
                });

    }

    public void loginAuth() {
        //INFLATE THEY LAYOUT OF POPUP
        Random r = new Random();
        final String randomNum = Integer.toString(r.nextInt(50000) + 10000);
        final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.popup_window, null);




        //CREATE POPUPWINDOW
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        final View pView = popupWindow.getContentView();
        final EditText editPin = pView.findViewById(R.id.editText3);
        final TextView textView = pView.findViewById(R.id.textViewPin);
        Button button = pView.findViewById(R.id.button3);

        popupWindow.showAtLocation(pView, Gravity.CENTER, 0, 0);

        textView.setText(randomNum);

        //DISMISS WHEN TOUCHEDDDDDD
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editPin.getText().toString().equals(textView.getText())) {
                    Toast.makeText(MainActivity.this, "Authentication succesful!",
                            Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                    FirebaseUser user = mAuth.getCurrentUser();
                    HallSystem.getInstance().setUser(user);
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(MainActivity.this, "Wrong pin, try again!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        HallSystem.getInstance().setUser(currentUser);

    }

}
