package com.example.schoolroutineapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {

    private List<ActivityItem> activityList;
    private Runnable saveActivitiesCallback;

    public ActivityAdapter(List<ActivityItem> activityList, Runnable saveActivitiesCallback) {
        this.activityList = activityList;
        this.saveActivitiesCallback = saveActivitiesCallback;
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_activity, parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        ActivityItem activity = activityList.get(position);
        holder.textViewTitle.setText(activity.getTitle());
        holder.textViewTime.setText(activity.getStartTime() + " - " + activity.getEndTime());
        holder.checkBoxCompleted.setChecked(activity.isCompleted());

        holder.checkBoxCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            activity.setCompleted(isChecked);
            saveActivitiesCallback.run();  // Сохраняем изменения при изменении статуса выполнения
        });
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewTime;
        CheckBox checkBoxCompleted;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            checkBoxCompleted = itemView.findViewById(R.id.checkBoxCompleted);
        }
    }
}
