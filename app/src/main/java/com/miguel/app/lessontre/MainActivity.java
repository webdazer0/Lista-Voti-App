package com.miguel.app.lessontre;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.miguel.app.lessontre.model.DBHelper;
import com.miguel.app.lessontre.model.Student;
import com.miguel.app.lessontre.model.StudentDB;
import com.miguel.app.lessontre.view.adapter.CustomAdapter;
import com.miguel.app.lessontre.view.DetailsActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    TextView testo;
    Button addBtn;
    ListView myList;
    List<Student> students;
    CustomAdapter adapter;
    Activity activity;

    List<Double> votes = new ArrayList<>();
    Double media;

    Context ctx;

    DBHelper dbHelper;

    TextView name;
    TextView lastname;
    TextView birthdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = this;
        activity = MainActivity.this;

        addBtn = (Button) findViewById(R.id.btnAggiungi);
        myList = (ListView) findViewById(R.id.lvElenco);

        students = new ArrayList<>();

        dbHelper = new DBHelper(ctx);
//        addSample();
        getStudents();
        adapter = new CustomAdapter(ctx, students);
        myList.setAdapter(adapter);

        addBtn.setOnClickListener(addBtnEvent); // Aggiungi studenti
        myList.setOnItemClickListener(myListItemEvent); // Modifica studente in un'altra Activity
    }

    private final AdapterView.OnItemClickListener myListItemEvent = (parent, view, position, id) -> {
        Log.i("MITO DEBUG", "elemento: " + String.valueOf(position) + ", " + String.valueOf(id));

        Intent intent = new Intent(view.getContext(), DetailsActivity.class);
        intent.putExtra("name", students.get(position).getNome());
        intent.putExtra("lastname", students.get(position).getCognome());

        view.getContext().startActivity(intent);
    };

    private final View.OnClickListener addBtnEvent = vista -> {
        name = ((TextView) findViewById(R.id.txtNome));
        lastname = (TextView) findViewById(R.id.txtCognome);
        birthdate = (TextView) findViewById(R.id.txtEta);

        if (name.getText().length() != 0 & lastname.getText().length() != 0 & birthdate.getText().length() != 0) {

            try {

                long new_Id = addStudent();
                Log.i("MITO DEBUG", "ultimo ID: " + new_Id);
                getStudents();
                reloadmyList(); // Ricarica la lista di studenti (nella Listview)

                clearInputs(name, lastname, birthdate);
                birthdate.clearFocus();

            } catch (Exception error) {
                Log.e("MITO_DEBUG: ", "errore: " + error.getMessage());
            }

//            students.add(new Student(name.getText().toString(), lastname.getText().toString(), Integer.parseInt(birthdate.getText().toString())));


            try {
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

            students.add(new Student(tmpName, tmpLastname, Integer.parseInt(tmpBirthdate)));
        }
    }

    private void reloadmyList() {
        adapter.loadData(students);
        myList.invalidateViews();
        myList.refreshDrawableState();
    }

    private Double getMedia(TextView age) {
        double result;
        Double total = 0.0;
        votes.add(Double.parseDouble(age.getText().toString()));
        for (Double vote : votes) total += vote;
        result = total / (votes.size());
        return result;
    }

    private void clearInputs(TextView... myListDati) {
        for (TextView dato : myListDati) dato.setText("");
    }

    private void myToast(CharSequence messaggio) {
        Toast.makeText(ctx, messaggio, Toast.LENGTH_SHORT).show();
    }

    private void addSample() {
        students.add(new Student("Mario", "Rossi", 25));
        students.add(new Student("Marta", "Cessi", 35));
        students.add(new Student("Harry", "Ronaldo", 35));
    }

}