package com.example.a4fish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {

    private OnEventClickListener mOnEventClickListener;
    private List<Event> mEvents; // Cached copy of words
    private final LayoutInflater mInflater;

    EventListAdapter(Context context, OnEventClickListener onEventClickListener) {
        mInflater = LayoutInflater.from(context);
        this.mOnEventClickListener = onEventClickListener;
    }


    class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView eventItemView;
        private final TextView eventDate;
        OnEventClickListener onEventClickListener;

        private EventViewHolder(View itemView, OnEventClickListener onEventClickListener) {
            super(itemView);
            eventItemView = itemView.findViewById(R.id.event_text_view);
            eventDate = itemView.findViewById(R.id.event_date_text_view);
            this.onEventClickListener = onEventClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onEventClickListener.onEventClick(getAdapterPosition());
        }
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new EventViewHolder(itemView, mOnEventClickListener);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        if (mEvents != null) {
            Event current = mEvents.get(position);
            holder.eventItemView.setText(current.getEventTitle());
            holder.eventDate.setText(current.getEventDate());
        } else {
            // Covers the case of data not being ready yet.
            holder.eventItemView.setText("No Event");
        }
    }

    void setEvents(List<Event> events){
        mEvents = events;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mEvents != null)
            return mEvents.size();
        else return 0;
    }

    public interface OnEventClickListener {
        void onEventClick (int position);
    }
}
