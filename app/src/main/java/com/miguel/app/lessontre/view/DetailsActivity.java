package com.miguel.app.lessontre.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.miguel.app.lessontre.R;

public class DetailsActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        intent = getIntent();
        String name = intent.getStringExtra("name");
        String lastname = intent.getStringExtra("lastname");

        TextView nameDetails = (TextView) findViewById(R.id.txtDettagliNominativo);
        TextView lastnameDetails = (TextView) findViewById(R.id.txtDettagliEta);

        nameDetails.setText(name);
        lastnameDetails.setText(lastname);

    }
}