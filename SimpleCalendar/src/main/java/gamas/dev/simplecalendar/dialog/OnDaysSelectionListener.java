package gamas.dev.simplecalendar.dialog;

import java.util.List;

import gamas.dev.simplecalendar.model.Day;

public interface OnDaysSelectionListener {
    void onDaysSelected(List<Day> selectedDays);
}
