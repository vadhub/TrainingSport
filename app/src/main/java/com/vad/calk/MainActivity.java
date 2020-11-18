package com.vad.calk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
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
import java.util.List;

public class MainActivity extends AppCompatActivity implements Datable{

    private ImageView addSport;
    private DialogNameSport dialogNameSport;
    private RecyclerView recyclerView;
    private TrainingAdapter trainingAdapter;
    private LayoutInflater layoutInflater;

    private MainViewModel viewModel;

    private Calendar calendar = Calendar.getInstance();

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        addSport = (ImageView) findViewById(R.id.btn_add);
        dialogNameSport = new DialogNameSport();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        trainingAdapter = new TrainingAdapter();

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        getData();

        //viewModel = new ViewModelProvider(this).get(MainViewModel.class);


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
        Exercise exercise = trainingAdapter.getExercises().get(position);
        viewModel.deleteExercise(exercise);
    }

    private void getData(){
        LiveData<List<Exercise>> exerciesFromDB = viewModel.getExercises();
        exerciesFromDB.observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                if(exercises!=null&&exercises.size()>0){
                    trainingAdapter.setExercises(exercises);
                }

            }
        });
    }

    public void showDialog(){
        dialogNameSport.show(getSupportFragmentManager(), "TypeSport");
    }

    @Override
    public void nameSport(String text) {
        long date = calendar.getTimeInMillis();

        if(text!=null){
            Exercise exercise = new Exercise(text, date);
            viewModel.insertExercise(exercise);
        }
        layoutInflater = LayoutInflater.from(getApplicationContext());
    }


    private void intoMain(int position){
        Intent intent = new Intent(this ,Training.class);
        intent.putExtra("nameSport", trainingAdapter.exercises.get(position).getName());
        startActivity(intent);
    }
}


