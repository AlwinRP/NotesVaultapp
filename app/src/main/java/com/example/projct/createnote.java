package com.example.projct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class createnote extends AppCompatActivity {

    EditText mcreatetitle,mcreatecontent;
    FloatingActionButton msave;
    FirebaseAuth mAuth;
    FirebaseUser mFirebase_user;
    FirebaseFirestore Mfirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnote);
        msave=findViewById(R.id.createnote);
        mcreatecontent=findViewById(R.id.createcontent);
        mcreatetitle=findViewById(R.id.createtitle);

        Toolbar toolbar=findViewById(R.id.toolbarofcreatenote);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        mAuth=FirebaseAuth.getInstance();
        mFirebase_user=FirebaseAuth.getInstance().getCurrentUser();
        Mfirestore=FirebaseFirestore.getInstance();

        msave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=mcreatetitle.getText().toString();
                String content=mcreatecontent.getText().toString();
                if(title.isEmpty()|| content.isEmpty()){

                    Toast.makeText(getApplicationContext(),"Both fields are required",Toast.LENGTH_SHORT).show();
                }
                else {

                    DocumentReference documentReference=Mfirestore.collection("Notes").document(mFirebase_user.getUid()).collection("my_notes").document();

                    Map<String,Object> note=new HashMap<>();
                    note.put("title",title);
                    note.put("content",content);
                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"Note Created Succesfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(createnote.this,notes.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(createnote.this, "Failed to Create Note", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            onBackPressed();
        }

            return super.onOptionsItemSelected(item);
        }


}