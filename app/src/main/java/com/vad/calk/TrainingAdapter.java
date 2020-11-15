package com.vad.calk;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.TrainingViewHolder> {

    ArrayList<Exercise> exercises;

    private OnItemTrainingListener onItemTrainingListener;

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    interface OnItemTrainingListener{
        void onItemTrainingClick(int position);
    }

    public void setOnItemTrainingListener(OnItemTrainingListener onItemTrainingListener) {
        this.onItemTrainingListener = onItemTrainingListener;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        notifyDataSetChanged();
        this.exercises = exercises;

    }

    public TrainingAdapter() {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public TrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sport, parent, false);

        return new TrainingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TrainingViewHolder holder, final int position) {
        holder.nameSport.setText(exercises.get(position).getName());
        Calendar calendar = Calendar.getInstance();
        long l = exercises.get(position).getDate();
        calendar.setTimeInMillis(l);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(calendar.getTime());
        System.out.println(exercises.get(position).getDate());

        holder.date.setText(dateText);
    }


    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class TrainingViewHolder extends RecyclerView.ViewHolder {

        TextView nameSport;
        TextView date;

        @SuppressLint("ResourceType")
        public TrainingViewHolder(@NonNull View itemView) {
            super(itemView);

            nameSport = (TextView) itemView.findViewById(R.id.name_sport_);
            date = (TextView) itemView.findViewById(R.id.date_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemTrainingListener != null){
                        onItemTrainingListener.onItemTrainingClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
