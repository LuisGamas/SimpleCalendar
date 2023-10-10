package io.github.luisgamas.simplecalendar.selection.criteria.month;

import java.util.Calendar;

import io.github.luisgamas.simplecalendar.model.Day;
import io.github.luisgamas.simplecalendar.selection.criteria.BaseCriteria;

public abstract class BaseMonthCriteria extends BaseCriteria {

    protected abstract int getMonth();

    protected abstract int getYear();

    @Override
    public boolean isCriteriaPassed(Day day) {
        return day.getCalendar().get(Calendar.MONTH) == getMonth()
                && day.getCalendar().get(Calendar.YEAR) == getYear();
    }
}
