package gamas.dev.simplecalendar.selection;


import androidx.annotation.NonNull;

import gamas.dev.simplecalendar.model.Day;

public abstract class BaseSelectionManager {

    protected OnDaySelectedListener onDaySelectedListener;

    public BaseSelectionManager() {
    }

    public abstract void toggleDay(@NonNull Day day);

    public abstract boolean isDaySelected(@NonNull Day day);

    public abstract void clearSelections();
}
