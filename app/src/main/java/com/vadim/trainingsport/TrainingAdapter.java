package com.vadim.trainingsport;

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

    ArrayList<SportType> sportTypes;

    private OnItemTrainingListener onItemTrainingListener;

    public ArrayList<SportType> getSportTypes() {
        return sportTypes;
    }

    interface OnItemTrainingListener{
        void onItemTrainingClick(int position);
    }

    public void setOnItemTrainingListener(OnItemTrainingListener onItemTrainingListener) {
        this.onItemTrainingListener = onItemTrainingListener;
    }

    public void setSportTypes(ArrayList<SportType> sportTypes) {
        notifyDataSetChanged();
        this.sportTypes = sportTypes;

    }

    public TrainingAdapter() {
        this.sportTypes = sportTypes;
    }

    @NonNull
    @Override
    public TrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sport, parent, false);

        return new TrainingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TrainingViewHolder holder, final int position) {
        holder.nameSport.setText(sportTypes.get(position).getName());
        Calendar calendar = Calendar.getInstance();
        long l = sportTypes.get(position).getDate();
        calendar.setTimeInMillis(l);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(calendar.getTime());
        System.out.println(sportTypes.get(position).getDate());

        holder.date.setText(dateText);
    }


    @Override
    public int getItemCount() {
        return sportTypes.size();
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
