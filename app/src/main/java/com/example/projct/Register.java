package com.example.projct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private TextView mbanner;
    private Button mRegister;
    private EditText mname,mage,memail,mpassword;
    private ProgressBar mprogressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mbanner=findViewById(R.id.welcome);
        mbanner.setOnClickListener(this);
        mRegister=findViewById(R.id.Register);
        mRegister.setOnClickListener(this);
        mname=findViewById(R.id.name);
        mage=findViewById(R.id.age);
        memail=findViewById(R.id.email2);
        mpassword=findViewById(R.id.password2);
        mprogressBar=findViewById(R.id.progressBar2);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.welcome:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.Register:
                startActivity(new Intent(this,MainActivity.class));

                Register();
                break;

        }
    }
    private void Register(){
        String email=memail.getText().toString().trim();
        String password2=mpassword.getText().toString().trim();
        String name=mname.getText().toString().trim();
        String age=mage.getText().toString().trim();

        if(name.isEmpty()){
            mname.setError("Fullname is Required");
            mname.requestFocus();
            return;
        }
        if(age.isEmpty()){
            mage.setError("Age is Required");
            mage.requestFocus();
            return;
        }
        if(age.length() != 10){
            mage.setError("Invalid Phone number");
            mage.requestFocus();
            return;
        }
        if(email.isEmpty()){
            memail.setError("Email is Required");
            memail.requestFocus();
            return;
        }

        if(password2.isEmpty()){
            mpassword.setError("Password is Required");
            mpassword.requestFocus();
            return;
        }
        if(password2.length() < 6){
            mpassword.setError("Password should be at least 6 characters");
            mpassword.requestFocus();
            return;
        }
        mprogressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password2)
               .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           user user=new user(name,age,email);
                           FirebaseDatabase.getInstance().getReference("user")
                                   .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                   .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()) {

                                           Toast.makeText(Register.this, "Registration Succesfull", Toast.LENGTH_SHORT).show();
                                   }

                                   else {
                                       Toast.makeText(Register.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                                   }
                                   mprogressBar.setVisibility(View.GONE);
                               }
                           });

                       }
                   }
               });





    }
}