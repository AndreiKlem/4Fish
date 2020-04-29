package com.example.a4fish;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    ArrayList<String> events;
    public EventsAdapter (ArrayList<String> events) {
        this.events = events;
    }

    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventsAdapter.ViewHolder holder, int position) {
        holder.eventTextView.setText(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView eventTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            eventTextView = itemView.findViewById(R.id.event_text_view);
        }
    }
}
