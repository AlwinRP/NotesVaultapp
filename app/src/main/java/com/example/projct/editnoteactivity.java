package com.example.projct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.okhttp.internal.Internal;

import java.util.HashMap;
import java.util.Map;

public class editnoteactivity extends AppCompatActivity {
    Internal data;
    EditText medittitle,meditcontnt;
    FloatingActionButton meditnote;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnoteactivity);
        medittitle=findViewById(R.id.edittitle);
        meditcontnt=findViewById(R.id.editcontent);
        meditnote=findViewById(R.id.savenote);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();


        Toolbar toolbar=findViewById(R.id.toolbarofeditnote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        meditnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"saved",Toast.LENGTH_SHORT).show();
                String newtitle=medittitle.getText().toString();
                String newcontent=meditcontnt.getText().toString();

                if(newtitle.isEmpty()||newcontent.isEmpty()){
                    Toast.makeText(getApplicationContext(),"File is Empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    DocumentReference documentReference=firebaseFirestore.collection("Notes").document(firebaseUser.getUid()).collection("my_notes").document();
                    Map<String,Object> note=new HashMap<>();
                    note.put("title",newtitle);
                    note.put("content",newcontent);
                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(),"Note is updated",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(editnoteactivity.this,notes.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Note is not updated",Toast.LENGTH_SHORT).show();


                        }
                    });
                }
            }
        });
        Intent data=getIntent();

        String notetitle=data.getStringExtra("title");
        String notecontent=data.getStringExtra("content");
        meditcontnt.setText(notecontent);
        medittitle.setText(notetitle);



    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}