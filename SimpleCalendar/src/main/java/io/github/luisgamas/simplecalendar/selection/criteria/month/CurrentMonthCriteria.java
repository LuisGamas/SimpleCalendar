package io.github.luisgamas.simplecalendar.selection.criteria.month;

import java.util.Calendar;

import io.github.luisgamas.simplecalendar.utils.DateUtils;

public class CurrentMonthCriteria extends BaseMonthCriteria {

    private long currentTimeInMillis;

    public CurrentMonthCriteria() {
        currentTimeInMillis = System.currentTimeMillis();
    }

    @Override
    protected int getMonth() {
        return DateUtils.getCalendar(currentTimeInMillis).get(Calendar.MONTH);
    }

    @Override
    protected int getYear() {
        return DateUtils.getCalendar(currentTimeInMillis).get(Calendar.YEAR);
    }
}
