package gamas.dev.simplecalendar.selection.selectionbar;

import gamas.dev.simplecalendar.model.Day;

public class SelectionBarContentItem implements SelectionBarItem {

    private Day day;

    public SelectionBarContentItem(Day day) {
        this.day = day;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}

