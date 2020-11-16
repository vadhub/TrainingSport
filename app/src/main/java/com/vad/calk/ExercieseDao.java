package com.vad.calk;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExercieseDao {

    @Query("SELECT * FROM exercises ORDER BY date")
    LiveData<List<Exercise>> getAllExercises();

    @Insert
    void insertExercise(Exercise exercise);

    @Delete
    void deleteExercise(Exercise exercise);

    @Query("DELETE FROM exercises")
    void deleteAllExercises();
}
