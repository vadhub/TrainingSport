package com.vad.calk;

import android.provider.BaseColumns;

public class TrainingContract {
    public static final class TrainingEntry implements BaseColumns {
        public static final String TABLE_NAME = "training";
        public static final String COLUMN_TYPE_SPORT = "type_sport";
        public static final String COLUMN_LVL = "lvl";
        public static final String COLUMN_STAT = "stat";
        public static final String COLUMN_NUMB = "numb";
        public static final String COLUMN_DATE = "date";

        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INT = "INTEGER";

        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+
                "("+_ID+" "+TYPE_INT+" PRIMARY KEY AUTOINCREMENT, " + COLUMN_TYPE_SPORT + " " + TYPE_TEXT + ", " + COLUMN_LVL + " " + TYPE_INT + ", "+
                COLUMN_STAT+" "+TYPE_INT+", "+COLUMN_NUMB+" "+TYPE_INT + ", " + COLUMN_DATE + " " + TYPE_INT+")";

        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS "+TABLE_NAME;

    }

    public static final class TrainingSportType implements BaseColumns{
        public static final String TABLE_NAME = "training_sport_type";
        public static final String COLUMN_TYPE_SPORT = "type_sport_main";
        public static final String COLUMN_DATE_SPORT_TYPE = "date_sport";


        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INT = "INTEGER";
        public static final String TYPE_NUMERIC = "NUMERIC";


        public static final String CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+
                "("+_ID+" "+TYPE_INT+" PRIMARY KEY AUTOINCREMENT, " + COLUMN_TYPE_SPORT + " " + TYPE_TEXT+", "+COLUMN_DATE_SPORT_TYPE+ " " + TYPE_NUMERIC + ")";

        public static final String DROP_COMMAND = "DROP TABLE IF EXISTS "+TABLE_NAME;
    }
}
