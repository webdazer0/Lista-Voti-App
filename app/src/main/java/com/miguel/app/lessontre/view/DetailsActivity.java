package com.miguel.app.lessontre.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.miguel.app.lessontre.MainActivity;
import com.miguel.app.lessontre.R;
import com.miguel.app.lessontre.model.DBHelper;
import com.miguel.app.lessontre.model.StudentDB;

public class DetailsActivity extends AppCompatActivity {

    Context context;
    Bundle intent;
    DBHelper dbHelper;

    TextView name;
    TextView lastname;
    TextView vote;

    ListView myListVoti;
    ArrayAdapter<String> adapter;

    int DB_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        context = this;

        try {
            intent = getIntent().getExtras();

            DB_Id = Integer.valueOf(intent.getString("DB_Id"));
            Log.i("MITO_DEBUG", "dd " + DB_Id);

            dbHelper = new DBHelper(context);
        } catch (Exception error) {
            Log.e("MITO_DEBUG", "errror: " + error.getMessage());
        }

        name = (TextView) findViewById(R.id.txtDettagliNominativo);
        lastname = (TextView) findViewById(R.id.txtDettagliEta);
        vote = findViewById(R.id.txtVoto);


        myListVoti = findViewById(R.id.lvElencoVoti);

        getStudent(DB_Id);

        adapter = new ArrayAdapter(context, R.layout.votes_element, R.id.lviVoto, new String[]{"53", "3232"} );
        myListVoti.setAdapter(adapter);

        Button btnUpdate = findViewById(R.id.btnUpdate);

        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnAddVote = findViewById(R.id.btnAddVoto);

        btnDelete.setOnClickListener(v -> {
            long success = dbHelper.delete(DB_Id);
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        });

        btnAddVote.setOnClickListener(v -> {
            long success = dbHelper.insertVote(DB_Id, vote.getText().toString());
            Log.i("MITO_DEBUG", "addvote: " + success);
        });

    }

    private void getStudent(int id) {
        Cursor cursor = dbHelper.selectById(id);
        while (cursor.moveToNext()) {
            String tmpName = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_NAME));
            String tmpLastname = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_LASTNAME));
//            String tmpBirthdate = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_BIRTHDATE));

            name.setText(tmpName);
            lastname.setText(tmpLastname);
        }
    }

}