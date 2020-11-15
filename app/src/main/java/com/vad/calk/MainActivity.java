package com.vad.calk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements Datable{

    private ImageView addSport;
    private DialogNameSport dialogNameSport;
    private RecyclerView recyclerView;
    ArrayList<Exercise> exercises = new ArrayList<>();
    private TrainingAdapter trainingAdapter;
    private LayoutInflater layoutInflater;
    private TrainingDBHelper dbHelper;
    private SQLiteDatabase database;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        addSport = (ImageView) findViewById(R.id.btn_add);
        dialogNameSport = new DialogNameSport();
        dbHelper = new TrainingDBHelper(this);
        database = dbHelper.getWritableDatabase();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        getData();

        trainingAdapter = new TrainingAdapter();
        trainingAdapter.setExercises(exercises);
        trainingAdapter.setOnItemTrainingListener(new TrainingAdapter.OnItemTrainingListener() {
            @Override
            public void onItemTrainingClick(int position) {
                intoMain(position);
            }

        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                remove(viewHolder.getAdapterPosition());

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(trainingAdapter);

        itemTouchHelper.attachToRecyclerView(recyclerView);

        addSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void remove(int position){

        int id = exercises.get(position).getId();
        String whereSportType = TrainingContract.TrainingSportType._ID + " = ?";
        String[] whereArgsSportType = new String[]{Integer.toString(id)};

        String nameSport = exercises.get(position).getName();
        String whereTraining = TrainingContract.TrainingEntry.COLUMN_TYPE_SPORT + " = ?";
        String[] whereArgsTraining = new String[]{nameSport};

        database.delete(TrainingContract.TrainingSportType.TABLE_NAME, whereSportType, whereArgsSportType);
        database.delete(TrainingContract.TrainingEntry.TABLE_NAME, whereTraining, whereArgsTraining);

        exercises.remove(position);
        getData();

        trainingAdapter.notifyDataSetChanged();
    }

    private void getData(){
        exercises.clear();
        Cursor cursor = database.query(TrainingContract.TrainingSportType.TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(TrainingContract.TrainingSportType._ID));
            String type_sport = cursor.getString(cursor.getColumnIndex(TrainingContract.TrainingSportType.COLUMN_TYPE_SPORT));
            long date = cursor.getLong(cursor.getColumnIndex(TrainingContract.TrainingSportType.COLUMN_DATE_SPORT_TYPE));
            Exercise exercise = new Exercise(id,type_sport, date);
            exercises.add(exercise);
        }
        cursor.close();
    }

    public void showDialog(){
        dialogNameSport.show(getSupportFragmentManager(), "TypeSport");
    }

    @Override
    public void nameSport(String text) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(TrainingContract.TrainingSportType.COLUMN_TYPE_SPORT, text);
        long i = calendar.getTimeInMillis();

        System.out.println(i);
        contentValues.put(TrainingContract.TrainingSportType.COLUMN_DATE_SPORT_TYPE, i);

        database.insert(TrainingContract.TrainingSportType.TABLE_NAME, null, contentValues);
        layoutInflater = LayoutInflater.from(getApplicationContext());
    }

    @Override
    public void recombination() {
        getData();
        trainingAdapter.setExercises(exercises);
    }

    private void intoMain(int position){
        Intent intent = new Intent(this ,Training.class);
        intent.putExtra("nameSport", trainingAdapter.exercises.get(position).getName());
        startActivity(intent);
    }
}


