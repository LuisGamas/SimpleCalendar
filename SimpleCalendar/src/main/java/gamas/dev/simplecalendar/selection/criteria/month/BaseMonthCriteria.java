package gamas.dev.simplecalendar.selection.criteria.month;

import java.util.Calendar;

import gamas.dev.simplecalendar.model.Day;
import gamas.dev.simplecalendar.selection.criteria.BaseCriteria;

public abstract class BaseMonthCriteria extends BaseCriteria {

    protected abstract int getMonth();

    protected abstract int getYear();

    @Override
    public boolean isCriteriaPassed(Day day) {
        return day.getCalendar().get(Calendar.MONTH) == getMonth()
                && day.getCalendar().get(Calendar.YEAR) == getYear();
    }
}
