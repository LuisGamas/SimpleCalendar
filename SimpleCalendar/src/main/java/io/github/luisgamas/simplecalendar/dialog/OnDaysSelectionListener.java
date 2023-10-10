package io.github.luisgamas.simplecalendar.dialog;

import java.util.List;

import io.github.luisgamas.simplecalendar.model.Day;

public interface OnDaysSelectionListener {
    void onDaysSelected(List<Day> selectedDays);
}
