package com.miguel.app.lessontre;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.miguel.app.lessontre.model.Student;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = this;
        activity = MainActivity.this;

        addBtn = (Button) findViewById(R.id.btnAggiungi);
        myList = (ListView) findViewById(R.id.lvElenco);

        students = new ArrayList<>();
        addSample();
        adapter = new CustomAdapter(ctx, students);
        myList.setAdapter(adapter);

        addBtn.setOnClickListener(addBtnEvent); // Aggiungi studenti
        myList.setOnItemClickListener(myListItemEvent); // Modifica studente in un'altra Activity
    }

    private AdapterView.OnItemClickListener myListItemEvent = (parent, view, position, id) -> {
        Log.i("MITO DEBUG", "elemento: " + String.valueOf(position) + ", " + String.valueOf(id));

        Intent intent = new Intent(view.getContext(), DetailsActivity.class);
        intent.putExtra("name", students.get(position).getNome());
        intent.putExtra("lastname", students.get(position).getCognome());

        view.getContext().startActivity(intent);
    };

    private View.OnClickListener addBtnEvent = vista -> {
        TextView name = ((TextView) findViewById(R.id.txtNome));
        TextView lastname = (TextView) findViewById(R.id.txtCognome);
        TextView vote = (TextView) findViewById(R.id.txtEta);

        if (name.getText().length() != 0 & lastname.getText().length() != 0 & vote.getText().length() != 0) {

            students.add(new Student(name.getText().toString(), lastname.getText().toString(), Integer.valueOf(vote.getText().toString())));
            adapter.loadData(students);
            myList.invalidateViews();
            myList.refreshDrawableState();

            clearInputs(name, lastname, vote);
            vote.clearFocus();

            try {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            } catch (Exception error) {
                Log.i("MITO DEBUG", "Errore: " + error.getMessage());
            }
            myToast("Dati inseriti correttamente ✅");
        } else {
            myToast("Devi compilare tutti i campi ❌");
        }

    };


    private Double getMedia(TextView age) {
        Double result;
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