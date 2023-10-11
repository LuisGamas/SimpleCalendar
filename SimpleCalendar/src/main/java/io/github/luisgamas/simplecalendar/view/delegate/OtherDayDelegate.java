package io.github.luisgamas.simplecalendar.view.delegate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.luisgamas.simplecalendar.R;
import io.github.luisgamas.simplecalendar.adapter.viewholder.OtherDayHolder;
import io.github.luisgamas.simplecalendar.model.Day;
import io.github.luisgamas.simplecalendar.view.CalendarView;

public class OtherDayDelegate {

    private CalendarView calendarView;

    public OtherDayDelegate(CalendarView calendarView) {
        this.calendarView = calendarView;
    }

    public OtherDayHolder onCreateDayHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_other_day, parent, false);
        return new OtherDayHolder(view, calendarView);
    }

    public void onBindDayHolder(Day day, OtherDayHolder holder, int position) {
        holder.bind(day);
    }
}
