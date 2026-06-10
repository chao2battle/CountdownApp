package com.example.countdownapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CountdownAdapter extends RecyclerView.Adapter<CountdownAdapter.CountdownViewHolder> {

    private List<CountdownItem> countdownList;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener {
        public void onEditClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public CountdownAdapter(Context context, List<CountdownItem> countdownList) {
        this.context = context;
        this.countdownList = countdownList;
    }

    @NonNull
    @Override
    public CountdownViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.countdown, parent, false);
        return new CountdownViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountdownViewHolder holder, int position) {
        CountdownItem countdownItem = countdownList.get(position);

        holder.countdownName.setText(countdownItem.getName());
        holder.countdownDate.setText(countdownItem.getDate());
        holder.daysLeft.setText(countdownItem.getTimeLeft());
    }

    @Override
    public int getItemCount() {
        return countdownList.size();
    }

    class CountdownViewHolder extends RecyclerView.ViewHolder {
        TextView countdownName, countdownDate, daysLeft;
        Button editButt;

        public CountdownViewHolder(@NonNull View itemView) {
            super(itemView);
            countdownName = itemView.findViewById(R.id.countdownName);
            countdownDate = itemView.findViewById(R.id.countdownDate);
            daysLeft = itemView.findViewById(R.id.daysLeft);
            editButt = itemView.findViewById(R.id.editButt);

            editButt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getBindingAdapterPosition();
                        if (position != -1) {
                            listener.onEditClick(position);
                        }
                    }
                }
            });
        }
    }
}
