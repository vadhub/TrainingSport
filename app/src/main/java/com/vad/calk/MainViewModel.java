package com.vad.calk;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static ExercisesDatabase database;

    private LiveData<List<Exercise>> exercises;

    public MainViewModel(@NonNull Application application) {
        super(application);

        database = ExercisesDatabase.getInstance(application);
        exercises = database.exercieseDao().getAllExercises();
    }

    public LiveData<List<Exercise>> getExercises() {
        return exercises;
    }

    public void setExercises(LiveData<List<Exercise>> exercises) {
        this.exercises = exercises;
    }

    public void insertExercise(Exercise exercise){
        new InsertTask().execute(exercise);
    }

    public void deleteExercise(Exercise exercise){
        new DeleteTask().execute(exercise);
    }

    public void deleteAllExercise(){

        new DeleteAllTask().execute();
    }

    private static class InsertTask extends AsyncTask<Exercise, Void, Void> {

        @Override
        protected Void doInBackground(Exercise... exercises) {

            if(exercises!=null&&exercises.length>0){
                database.exercieseDao().insertExercise(exercises[0]);
            }

            return null;
        }
    }

    private static class DeleteTask extends AsyncTask<Exercise, Void, Void>{
        @Override
        protected Void doInBackground(Exercise... exercises) {
            if(exercises!=null&&exercises.length>0){
                database.exercieseDao().deleteExercise(exercises[0]);
            }
            return null;
        }
    }

    private static class DeleteAllTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            database.exercieseDao().deleteAllExercises();
            return null;
        }
    }



}
