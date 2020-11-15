package com.vad.calk;

import androidx.room.Database;

import androidx.room.RoomDatabase;

@Database(entities = Exercise.class, version = 1, exportSchema = false)
public abstract class ExercisesDatabase extends RoomDatabase {

}
