package com.vadim.trainingsport;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TrainingResultAdapter extends RecyclerView.Adapter<TrainingResultAdapter.TrainingResultViewHolder> {
    private static final int DATE = 0;
    private static final int TABLE = 1;

    private ArrayList<TableResult> tableResults;

    public TrainingResultAdapter() {
    }

    public ArrayList<TableResult> getTableResults() {
        return tableResults;
    }

    public void setTableResults(ArrayList<TableResult> tableResults) {
        notifyDataSetChanged();
        this.tableResults = tableResults;
    }

    @Override
    public int getItemViewType(int position) {
        if(tableResults.get(position).getType() == 0){
            return DATE;
        }else{
            return TABLE;
        }

    }

    @NonNull
    @Override
    public TrainingResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == DATE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result_numb, parent, false);
        }
        return new TrainingResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingResultViewHolder holder, int position) {
        int type = getItemViewType(position);

        switch (type){
            case DATE:
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                String dateText = dateFormat.format(tableResults.get(position).getDate());
                holder.dateText.setText(dateText);
                break;
            case TABLE:
                holder.lvl.setText(tableResults.get(position).getLvlRes());
                holder.stat.setText(tableResults.get(position).getStatRes());
                holder.numb.setText(tableResults.get(position).getNumbRes());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return tableResults.size();
    }

    public class TrainingResultViewHolder extends RecyclerView.ViewHolder {

        TextView dateText;

        TextView lvl;
        TextView stat;
        TextView numb;
        public TrainingResultViewHolder(@NonNull View itemView) {
            super(itemView);

            dateText = (TextView) itemView.findViewById(R.id.date_training);

            lvl = (TextView) itemView.findViewById(R.id.lvl_result);
            stat = (TextView) itemView.findViewById(R.id.stat_result);
            numb = (TextView) itemView.findViewById(R.id.numb_result);
        }
    }
}

