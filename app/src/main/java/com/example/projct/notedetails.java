package com.example.projct;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class notedetails extends AppCompatActivity {
    private TextView mtitle,mcontent;
    FloatingActionButton mgotonotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notedetails);
        mtitle=findViewById(R.id.notetitledetails);
        mcontent=findViewById(R.id.notecontentdetails);
        mgotonotes=findViewById(R.id.gotoedit);
        Toolbar toolbar=findViewById(R.id.toolbarofnotedetails);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mgotonotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(notedetails.this,editnoteactivity.class));
            }
        });

        Intent data=getIntent();
        mgotonotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),editnoteactivity.class);
                intent.putExtra("title",data.getStringExtra("title"));
                intent.putExtra("content",data.getStringExtra("content"));
                view.getContext().startActivity(intent);

            }
        });

        mcontent.setText(data.getStringExtra("content"));
        mtitle.setText(data.getStringExtra("title"));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}