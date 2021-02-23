package com.miguel.app.lessontre.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miguel.app.lessontre.R;
import com.miguel.app.lessontre.model.Student;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context ctx;
    List<Student> studenti;
    LayoutInflater inflater;

    public CustomAdapter(Context context, List<Student> studenti) {
        this.ctx = context;
        this.studenti = studenti;
        this.inflater = LayoutInflater.from(ctx);
    }

    public void loadData(List<Student> studenti) {
       this.studenti = studenti;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return studenti.size();
    }

    @Override
    public Object getItem(int position) {
        //return null;
        return studenti.get(position);
    }

    @Override
    public long getItemId(int position) {
        return studenti.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.student_element, null);

        TextView fullname = (TextView)convertView.findViewById(R.id.lviNominativo);
        TextView age = (TextView)convertView.findViewById(R.id.lviEta);

        fullname.setText(studenti.get(position).getNominativo());
        age.setText(studenti.get(position).getEtaString());

        return convertView;
    }
}
