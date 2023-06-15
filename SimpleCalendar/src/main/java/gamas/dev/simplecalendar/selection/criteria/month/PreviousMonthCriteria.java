package gamas.dev.simplecalendar.selection.criteria.month;

import java.util.Calendar;

import gamas.dev.simplecalendar.utils.DateUtils;

public class PreviousMonthCriteria extends BaseMonthCriteria {

    private long currentTimeInMillis;

    public PreviousMonthCriteria() {
        currentTimeInMillis = System.currentTimeMillis();
    }

    @Override
    protected int getMonth() {
        Calendar calendar = DateUtils.getCalendar(currentTimeInMillis);
        calendar.add(Calendar.MONTH, -1);
        return calendar.get(Calendar.MONTH);
    }

    @Override
    protected int getYear() {
        Calendar calendar = DateUtils.getCalendar(currentTimeInMillis);
        calendar.add(Calendar.MONTH, -1);
        return calendar.get(Calendar.YEAR);
    }
}
