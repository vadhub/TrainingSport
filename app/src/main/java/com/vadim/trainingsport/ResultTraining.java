package com.vadim.trainingsport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ResultTraining extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TrainingResultAdapter adapter;
    private ArrayList<TableResult> tableResults = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private TrainingDBHelper dbHelper;
    private SQLiteDatabase database;

    private Bundle args;
    private String nameSport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_training);

        args = getIntent().getExtras();
        nameSport = args.get("nameSport_").toString();

        dbHelper = new TrainingDBHelper(this);
        database = dbHelper.getWritableDatabase();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_results);
        adapter = new TrainingResultAdapter();
        adapter.setTableResults(tableResults);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        tableResults.clear();

        Cursor cursor = database.query(TrainingContract.TrainingEntry.TABLE_NAME, null, TrainingContract.TrainingEntry.COLUMN_TYPE_SPORT+" = ?", new String[]{nameSport}, null, null, TrainingContract.TrainingEntry.COLUMN_DATE+" DESC");
        Date temp=null;
        while (cursor.moveToNext()){
            if(!cursor.isNull(cursor.getColumnIndex(TrainingContract.TrainingEntry.COLUMN_DATE))){

                Calendar calendar = Calendar.getInstance();
                long l = cursor.getLong(cursor.getColumnIndex(TrainingContract.TrainingEntry.COLUMN_DATE));
                calendar.setTimeInMillis(l);
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                String dateTemp = dateFormat.format(calendar.getTime());
                Date d = null;
                try {
                    d = dateFormat.parse(dateTemp);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(!d.equals(temp)){
                    temp = d;
                    tableResults.add(new TableResult(0,cursor.getLong(cursor.getColumnIndex(TrainingContract.TrainingEntry.COLUMN_DATE))));
                    System.out.println(!d.equals(temp));
                }
                tableResults.add(new TableResult(1, String.valueOf(cursor.getInt(cursor.getColumnIndex(TrainingContract.TrainingEntry.COLUMN_LVL))), String.valueOf(cursor.getInt(cursor.getColumnIndex(TrainingContract.TrainingEntry.COLUMN_STAT))), String.valueOf(cursor.getInt(cursor.getColumnIndex(TrainingContract.TrainingEntry.COLUMN_NUMB)))));

            }
        }
        cursor.close();
    }

    public void onClickToHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}