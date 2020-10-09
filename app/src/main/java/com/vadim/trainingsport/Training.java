package com.vadim.trainingsport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class Training extends AppCompatActivity {
    private ImageView addLevel;
    private ImageView addStat;
    private ImageView addNumb;


    private TextView typeSport;
    private TextView lvl;
    private TextView stat;
    private TextView numb;

    private int lvlCount, statCount, numbCount;

    private TrainingDBHelper dbHelper;

    private Button training;
    private SQLiteDatabase db;

    private Bundle args;
    private String nameSport;

    private int seconds;
    private boolean isRunning = false;

    private TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        dbHelper = new TrainingDBHelper(this);

        db = dbHelper.getWritableDatabase();

        addLevel = (ImageView) findViewById(R.id.addLvl);
        addStat = (ImageView) findViewById(R.id.addstat);
        addNumb = (ImageView) findViewById(R.id.addNumb);


        typeSport = (TextView) findViewById(R.id.type_sport);
        lvl = (TextView) findViewById(R.id.lvl);
        stat = (TextView) findViewById(R.id.stat);
        numb = (TextView) findViewById(R.id.numb);

        training = (Button) findViewById(R.id.home);
        timer = (TextView) findViewById(R.id.timer_text);

        runTimer();

        args = getIntent().getExtras();
        nameSport = args.get("nameSport").toString();

        Cursor cursor = db.query(TrainingContract.TrainingEntry.TABLE_NAME, new String[]{TrainingContract.TrainingEntry.COLUMN_TYPE_SPORT,TrainingContract.TrainingEntry.COLUMN_LVL, TrainingContract.TrainingEntry.COLUMN_STAT, TrainingContract.TrainingEntry.COLUMN_NUMB, TrainingContract.TrainingEntry.COLUMN_DATE}, TrainingContract.TrainingEntry.COLUMN_TYPE_SPORT+"=?", new String[]{nameSport}, null, null, TrainingContract.TrainingEntry.COLUMN_DATE+" ASC");

        while (cursor.moveToNext()){
            if(!cursor.isNull(cursor.getColumnIndex(TrainingContract.TrainingEntry.COLUMN_LVL))){
                lvlCount=cursor.getInt(cursor.getColumnIndex(TrainingContract.TrainingEntry.COLUMN_LVL));
                lvl.setText(String.valueOf(lvlCount));

            }else{
                lvl.setText("0");
            }

            if(!cursor.isNull(cursor.getColumnIndex(TrainingContract.TrainingEntry.COLUMN_STAT))){
                statCount=cursor.getInt(cursor.getColumnIndex(TrainingContract.TrainingEntry.COLUMN_STAT));
                stat.setText(String.valueOf(statCount));
            }else{
                stat.setText("0");
            }

            if(!cursor.isNull(cursor.getColumnIndex(TrainingContract.TrainingEntry.COLUMN_NUMB))){
                numbCount=cursor.getInt(cursor.getColumnIndex(TrainingContract.TrainingEntry.COLUMN_NUMB));
                numb.setText(String.valueOf(numbCount));
            }else{
                numb.setText("0");
            }

        }
        cursor.close();

        typeSport.setText(nameSport);

        addLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvlCount++;
                ContentValues contentValues = new ContentValues();
                contentValues.put(TrainingContract.TrainingEntry.COLUMN_TYPE_SPORT, nameSport);
                contentValues.put(TrainingContract.TrainingEntry.COLUMN_LVL, lvlCount);
                contentValues.put(TrainingContract.TrainingEntry.COLUMN_STAT, 1);
                contentValues.put(TrainingContract.TrainingEntry.COLUMN_NUMB, 0);
                contentValues.put(TrainingContract.TrainingEntry.COLUMN_DATE,System.currentTimeMillis());

                statCount=1;
                numbCount=0;

                stat.setText("1");
                numb.setText("0");

                lvl.setText(String.valueOf(lvlCount));
                db.insert(TrainingContract.TrainingEntry.TABLE_NAME, null, contentValues);
            }
        });

        addStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statCount++;
                ContentValues contentValues = new ContentValues();
                contentValues.put(TrainingContract.TrainingEntry.COLUMN_TYPE_SPORT, nameSport);
                contentValues.put(TrainingContract.TrainingEntry.COLUMN_LVL, lvlCount);
                contentValues.put(TrainingContract.TrainingEntry.COLUMN_STAT, statCount);
                contentValues.put(TrainingContract.TrainingEntry.COLUMN_NUMB, 0);
                contentValues.put(TrainingContract.TrainingEntry.COLUMN_DATE, System.currentTimeMillis());

                db.insert(TrainingContract.TrainingEntry.TABLE_NAME, null, contentValues);
                stat.setText(String.valueOf(statCount));
                numbCount=0;
                numb.setText("0");
            }
        });

        addNumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numbCount++;
                ContentValues contentValues = new ContentValues();
                contentValues.put(TrainingContract.TrainingEntry.COLUMN_TYPE_SPORT, nameSport);
                contentValues.put(TrainingContract.TrainingEntry.COLUMN_NUMB, numbCount);
                contentValues.put(TrainingContract.TrainingEntry.COLUMN_DATE, System.currentTimeMillis());

                db.update(TrainingContract.TrainingEntry.TABLE_NAME, contentValues, TrainingContract.TrainingEntry.COLUMN_TYPE_SPORT+" =? AND "+ TrainingContract.TrainingEntry.COLUMN_STAT+" =?", new String[]{nameSport, String.valueOf(statCount)});
                numb.setText(String.valueOf(numbCount));
            }
        });

        training.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                training();
            }
        });
    }

    private void training(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void onClickStart(View view) {
        isRunning=true;
    }

    public void onClickPause(View view) {
        isRunning=false;
    }

    public void onClickReset(View view) {
        isRunning=false;
        seconds=0;
    }

    private void runTimer(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = (seconds%3600)/60;
                int secnds = seconds%60;
                int milisec = secnds/1000;
                String format = String.format(Locale.getDefault(), "%d:%02d:%02d", minutes,secnds, milisec);
                timer.setText(format);
                if(isRunning){
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });


    }

    public void onClickFinish(View view) {
        Intent intent = new Intent(this, ResultTraining.class);
        intent.putExtra("nameSport_", nameSport);
        startActivity(intent);

    }
}