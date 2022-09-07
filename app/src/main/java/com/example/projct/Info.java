package com.example.projct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Info extends AppCompatActivity {
    TextView alwin,mansoor,vamsi,criswin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        alwin=findViewById(R.id.alwin);
        mansoor=findViewById(R.id.mansoor);
        vamsi=findViewById(R.id.vamsi);
        criswin=findViewById(R.id.criswin);

        mansoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotourl("https://www.instagram.com/____mr._.dredd____/");
            }
        });
        alwin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotourl("https://www.instagram.com/alwin_rp13/");
            }
        });
        vamsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotourl("https://www.instagram.com/_capzod_/");
            }
        });
        criswin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotourl("https://www.instagram.com/c_ri_s_wi_n/");
            }
        });


    }

    private void gotourl(String s) {
        Uri uri=Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}