// ActivityAdapter.java
package com.example.schoolroutineapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {

    private List<ActivityItem> activityList;

    public ActivityAdapter(List<ActivityItem> activityList) {
        this.activityList = activityList;
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_activity, parent, false);
        return new ActivityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {
        ActivityItem activityItem = activityList.get(position);
        holder.titleTextView.setText(activityItem.getTitle());
        holder.timeTextView.setText(activityItem.getStartTime() + " - " + activityItem.getEndTime());
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    public void updateActivityList(List<ActivityItem> newActivityList) {
        this.activityList = newActivityList;
        notifyDataSetChanged();
    }

    static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView timeTextView;

        public ActivityViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textViewTitle);
            timeTextView = itemView.findViewById(R.id.textViewTime);
        }
    }
}

