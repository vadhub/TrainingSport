package com.vad.calk;

import android.content.Context;

import androidx.room.Database;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Exercise.class, version = 1, exportSchema = false)
public abstract class ExercisesDatabase extends RoomDatabase {

    private static ExercisesDatabase exercisesDatabase;
    private static final String DB_NAME = "exercises.db";

    private static final Object LOCK = new Object();

    public static ExercisesDatabase getInstance(Context context){

        synchronized (LOCK){
            if(exercisesDatabase == null){
                exercisesDatabase = Room.databaseBuilder(context, ExercisesDatabase.class, DB_NAME).build();
            }
        }

        return exercisesDatabase;
    }

    public abstract ExercieseDao exercieseDao();

}
