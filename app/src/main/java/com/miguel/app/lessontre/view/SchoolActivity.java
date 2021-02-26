package com.miguel.app.lessontre.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.miguel.app.lessontre.R;
import com.miguel.app.lessontre.model.DBHelper;
import com.miguel.app.lessontre.model.Student;
import com.miguel.app.lessontre.model.StudentDB;
import com.miguel.app.lessontre.model.VoteDB;
import com.miguel.app.lessontre.view.adapter.StudentAdapter;

import java.util.ArrayList;
import java.util.List;


public class SchoolActivity extends AppCompatActivity {

    TextView testo;
    Button addBtn;
    ListView myList;
    List<Student> students;
    StudentAdapter adapter;
    Activity activity;

    List<Double> votes = new ArrayList<>();
    Double media;

    Context ctx;

    DBHelper dbHelper;

    TextView name;
    TextView lastname;
    TextView birthdate; // Qui dovrà apparire un Datepicker per scegliere la data di nascita

    FloatingActionButton fab;
    boolean isClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);

        ctx = this;
        activity = SchoolActivity.this;

        addBtn = (Button) findViewById(R.id.btnAggiungi);
        myList = (ListView) findViewById(R.id.lvElenco);

        fab = findViewById(R.id.fab);

        students = new ArrayList<>();
        dbHelper = new DBHelper(ctx);

        try {
//            addSample();
//            getStudents();
            getStudentsAndVotes(); // Chiamata sql

            adapter = new StudentAdapter(ctx, students);
            myList.setAdapter(adapter);

            addBtn.setOnClickListener(addBtnEvent); // Aggiungi studenti
            myList.setOnItemClickListener(myListItemEvent); // Modifica studente in un'altra Activity
        } catch (Exception error) {
            Log.e("MITO DEBUG", "Errore: " + error.getMessage());
        }


        fab.setOnClickListener(v -> {
            if(!isClicked) {
                myToast("Ciaooo, prova ad inserire uno studente 👀");
                isClicked = !isClicked;
            } else {
                myToast("Se hai già inserito uno, clicacci sul suo nome e dali un voto 🙄");
                isClicked = !isClicked;
            }
        });
    }

    private final AdapterView.OnItemClickListener myListItemEvent = (parent, view, position, id) -> {
        Log.i("MITO DEBUG", "elemento: " + position + ", " + id);

        Intent intent = new Intent(ctx, DetailsActivity.class);
        intent.putExtra("DB_Id", id + "");

        startActivity(intent);
    };

    private final View.OnClickListener addBtnEvent = vista -> {
        name = ((TextView) findViewById(R.id.txtNome));
        lastname = (TextView) findViewById(R.id.txtCognome);
        birthdate = (TextView) findViewById(R.id.txtEta);

        if (name.getText().length() != 0 & lastname.getText().length() != 0 & birthdate.getText().length() != 0) {

            try {

                long new_Id = addStudent();
                Log.i("MITO DEBUG", "ultimo ID: " + new_Id);

                getStudentsAndVotes();

//                getStudents();
                reloadmyList(); // Ricarica la lista di studenti (nella Listview)

                clearInputs(name, lastname, birthdate);
                birthdate.clearFocus();

                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            } catch (Exception error) {
                Log.e("MITO DEBUG", "Errore: " + error.getMessage());
            }
            myToast("Dati inseriti correttamente ✅");
        } else {
            myToast("Devi compilare tutti i campi ❌");
        }
    };

    private long addStudent() {
        String tmpName = name.getText().toString();
        String tmpLastname = lastname.getText().toString();
        String tmpBirthdate = birthdate.getText().toString();
        return dbHelper.insert(tmpName, tmpLastname, tmpBirthdate);
    }

    private void cleanMyList() {
        students.clear();
    }

    private void getStudents() {
        cleanMyList();
        Cursor cursor = dbHelper.select();
        while (cursor.moveToNext()) {
            String tmpName = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_NAME));
            String tmpLastname = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_LASTNAME));
            String tmpBirthdate = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_BIRTHDATE));
            String tmpID = cursor.getString(cursor.getColumnIndex(StudentDB.Data._ID));

            students.add(new Student(tmpName, tmpLastname, Integer.parseInt(tmpBirthdate), Integer.parseInt(tmpID)));
        }
    }

    private void getStudentsAndVotes() {
        cleanMyList();
        Cursor cursor = dbHelper.selectAll();
        while (cursor.moveToNext()) {
            String tmpName = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_NAME));
            String tmpLastname = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_LASTNAME));
            String tmpVote = cursor.getString(cursor.getColumnIndex(VoteDB.Data.COL_VOTE));
            String tmpID = cursor.getString(cursor.getColumnIndex(StudentDB.Data._ID));

            if (tmpVote == null)
                tmpVote = "-1"; // per gli studenti che non hanno ancora nessun voto

            students.add(new Student(tmpName, tmpLastname, Double.parseDouble(tmpVote), Integer.parseInt(tmpID)));
        }
    }

    private void reloadmyList() {
        adapter.loadData(students);
        myList.invalidateViews();
        myList.refreshDrawableState();
    }

    private void clearInputs(TextView... myListDati) {
        for (TextView dato : myListDati) dato.setText("");
    }

    private void myToast(CharSequence messaggio) {
        Toast.makeText(ctx, messaggio, Toast.LENGTH_SHORT).show();

//        Toast toast = new Toast(ctx);
//
//
//        TextView tv = new TextView(ctx);
//        tv.setBackgroundColor(Color.BLUE);
//        tv.setTextColor(Color.RED);
//
//        toast.setText(messaggio);
//        toast.setDuration(Toast.LENGTH_LONG);
//
//        toast.setView(tv);
//        toast.show();
    }

    private Double getMedia(TextView age) {
        double result;
        Double total = 0.0;
        votes.add(Double.parseDouble(age.getText().toString()));
        for (Double vote : votes) total += vote;
        result = total / (votes.size());
        return result;
    }

    private void addSample() {
        students.add(new Student("Mario", "Rossi", 25, 11));
        students.add(new Student("Marta", "Cessi", 35, 12));
        students.add(new Student("Harry", "Ronaldo", 35, 13));
    }

}