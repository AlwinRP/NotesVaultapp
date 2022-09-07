package com.example.projct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register,forgorpassword;
    private EditText memail,mpassword;
    private Button signin;
    private FirebaseAuth mAuth;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register=(TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        signin=(Button) findViewById(R.id.signin);
        signin.setOnClickListener(this);
        memail=(EditText) findViewById(R.id.email);
        mpassword=(EditText) findViewById(R.id.password);
        progressbar=(ProgressBar) findViewById(R.id.progressBar);
        mAuth=FirebaseAuth.getInstance();
        forgorpassword=(TextView) findViewById(R.id.forgot);
        forgorpassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.register:
                startActivity(new Intent(this,Register.class));
                break;

            case R.id.signin:
                userlogin();
                break;

            case R.id.forgot:
                startActivity(new Intent(this,ForgotPassword.class));
                break;
        }


    }

    private void userlogin() {
        String email=memail.getText().toString().trim();
        String password=mpassword.getText().toString().trim();

        if(email.isEmpty()){
            memail.setError("Email is required");
            memail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            mpassword.setError("Password is Required");
            mpassword.requestFocus();
            return;
        }
        if(password.length()< 6){
            mpassword.setError("Minimum password length is 6");
            mpassword.requestFocus();
            return;
        }

        progressbar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful() ){
                    //redirect to notes
                  startActivity(new Intent(MainActivity.this,notes.class));
                  progressbar.setVisibility(View.GONE);

                }
                else{
                    Toast.makeText(MainActivity.this, "Login Failed please check your credentials", Toast.LENGTH_SHORT).show();
                    progressbar.setVisibility(View.GONE);
                }
            }
        });
    }
}