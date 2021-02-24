package com.miguel.app.lessontre.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.miguel.app.lessontre.MainActivity;
import com.miguel.app.lessontre.R;
import com.miguel.app.lessontre.model.DBHelper;
import com.miguel.app.lessontre.model.StudentDB;
import com.miguel.app.lessontre.model.Vote;
import com.miguel.app.lessontre.model.VoteDB;
import com.miguel.app.lessontre.view.adapter.VoteAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    Context context;
    Bundle intent;
    DBHelper dbHelper;

    TextView name;
    TextView lastname;
    TextView vote;

    ListView myListVoti;
    List<Vote> votes;
    VoteAdapter adapter;
    int DB_Id;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        context = this;

        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnAddVote = findViewById(R.id.btnAddVoto);
        fab = findViewById(R.id.fab);

        try {
            intent = getIntent().getExtras();

            DB_Id = Integer.valueOf(intent.getString("DB_Id"));
            Log.i("MITO_DEBUG", "dd " + DB_Id);

            dbHelper = new DBHelper(context);


            name = (TextView) findViewById(R.id.txtDettagliNominativo);
            lastname = (TextView) findViewById(R.id.txtDettagliEta);
            vote = findViewById(R.id.txtVoto);

            myListVoti = findViewById(R.id.lvElencoVoti);

            votes = new ArrayList<>();

            getStudent(DB_Id);
            getVotes(DB_Id);
            adapter = new VoteAdapter(context, votes);
            myListVoti.setAdapter(adapter);


        } catch (Exception error) {
            Log.e("MITO_DEBUG", "errror: " + error.getMessage());
        }

//      AGGIUNGERE  VOTO

        btnAddVote.setOnClickListener(v -> {
            if (!vote.getText().toString().equals("")) {  // controllo che l'input vote, non sia vuoto
                if (Integer.parseInt(vote.getText().toString()) >= 0 && Integer.parseInt(vote.getText().toString()) <= 30) { // Controllo che l'input sia un voto valido compreso fra 0-30
                    try {
                        long success = dbHelper.insertVote(DB_Id, vote.getText().toString());
                        reloadmyList();
                        clearInputs(vote);

                        myToast("Voto inserito correttamente ✅");
                    } catch (Exception error) {
                        Log.e("MITO_DEBUG", "errore btnAddVote: " + error.getMessage());
                    }
                } else {
                    myToast("Devi inserire un voto valido (0-30) ❌");
                }
            } else {
                myToast("Devi compilare il campo voto ❌");
            }

        });

//        CANCELLARE SINGOLO VOTO
        myListVoti.setOnItemClickListener((parent, view, position, id) -> {
            dbHelper.deleteVote((int) id);
            reloadmyList();
            myToast("Voto rimosso (id: " + id + ") ✅");
        });

        // CANCELLARE STUDENTE
        btnDelete.setOnClickListener(v -> {
            long success = dbHelper.delete(DB_Id);
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        });

        // AGGIORNARE DATI STUDENTE (NOME, COGNOME)

        btnUpdate.setOnClickListener(v -> {
            String tmpName = name.getText().toString();
            String tmpLastname = lastname.getText().toString();

            if (!tmpName.equals("") && !tmpLastname.equals("")) {
                try {
                    dbHelper.updateStudent(DB_Id, tmpName, tmpLastname);

                    myToast("Dati aggiornati correttamente ✅");
                } catch (Exception error) {
                    Log.e("MITO_DEBUG", "Errore " + error.getMessage());
                }
            }
        });

        // Pulsante FloatActionButton per tornare alla HOME
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
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

    private void getVotes(int id) {
        cleanMyList();
        Cursor cursor = dbHelper.selectVotes(id);
        while (cursor.moveToNext()) {
            String tmpVoto = cursor.getString(cursor.getColumnIndex(VoteDB.Data.COL_VOTE));
            String tmpId = cursor.getString(cursor.getColumnIndex(VoteDB.Data._ID));

            votes.add(new Vote(Integer.parseInt(tmpVoto), Integer.parseInt(tmpId)));
        }
    }

    private void reloadmyList() {
        getVotes(DB_Id);
        adapter.loadData(votes);
        myListVoti.invalidateViews();
        myListVoti.refreshDrawableState();
    }

    private void cleanMyList() {
        votes.clear();
    }

    private void clearInputs(TextView... myListDati) {
        for (TextView dato : myListDati) dato.setText("");
    }

    private void myToast(CharSequence messaggio) {
        Toast.makeText(context, messaggio, Toast.LENGTH_SHORT).show();
    }

}