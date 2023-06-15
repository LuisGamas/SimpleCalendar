package gamas.dev.simplecalendar.selection;


import androidx.annotation.NonNull;

import gamas.dev.simplecalendar.model.Day;

public class NoneSelectionManager extends BaseSelectionManager {

    @Override
    public void toggleDay(@NonNull Day day) {

    }

    @Override
    public boolean isDaySelected(@NonNull Day day) {
        return false;
    }

    @Override
    public void clearSelections() {

    }
}
