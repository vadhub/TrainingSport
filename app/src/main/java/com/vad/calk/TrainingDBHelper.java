package com.vad.calk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TrainingDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "training_app.db";
    private static final int DB_VERSION = 1;

    public TrainingDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TrainingContract.TrainingEntry.CREATE_COMMAND);
        db.execSQL(TrainingContract.TrainingSportType.CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TrainingContract.TrainingEntry.DROP_COMMAND);
        db.execSQL(TrainingContract.TrainingSportType.DROP_COMMAND);
        onCreate(db);
    }
}
