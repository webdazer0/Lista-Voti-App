package com.miguel.app.lessontre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.miguel.app.lessontre.view.SchoolActivity;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        Context ctx;
        ctx = this;


        Button btnSchool = (Button) findViewById(R.id.btnSchool);

        btnSchool.setOnClickListener(v -> {
            Intent intent = new Intent(ctx, SchoolActivity.class);
            startActivity(intent);
        });
    }
}