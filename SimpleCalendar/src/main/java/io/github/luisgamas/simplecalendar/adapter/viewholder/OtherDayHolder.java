package io.github.luisgamas.simplecalendar.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import gamas.dev.simplecalendar.R;
import io.github.luisgamas.simplecalendar.model.Day;
import io.github.luisgamas.simplecalendar.view.CalendarView;

public class OtherDayHolder extends BaseDayHolder {

    public OtherDayHolder(View itemView, CalendarView calendarView) {
        super(itemView, calendarView);
        tvDay = (TextView) itemView.findViewById(R.id.tv_day_number);
    }

    public void bind(Day day) {
        tvDay.setText(String.valueOf(day.getDayNumber()));
        tvDay.setTextColor(calendarView.getOtherDayTextColor());
    }
}
