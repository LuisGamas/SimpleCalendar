package io.github.luisgamas.simplecalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Calendar;
import java.util.Date;

import io.github.luisgamas.simplecalendar.model.Day;
import io.github.luisgamas.simplecalendar.utils.DateUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class DateUtilsTest {

    @Test
    public void getCalendar_fromDate_returnsCalendar() {
        Date now = new Date();
        Calendar calendar = DateUtils.getCalendar(now);
        assertNotNull(calendar);
        assertEquals(now.getTime(), calendar.getTimeInMillis());
    }

    @Test
    public void getCalendar_fromMillis_returnsCalendar() {
        long now = System.currentTimeMillis();
        Calendar calendar = DateUtils.getCalendar(now);
        assertNotNull(calendar);
        assertEquals(now, calendar.getTimeInMillis());
    }

    @Test
    public void getFirstDayOfMonth_returnsFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 15);
        Date firstDay = DateUtils.getFirstDayOfMonth(calendar.getTime());
        Calendar result = DateUtils.getCalendar(firstDay);
        assertEquals(1, result.get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.MARCH, result.get(Calendar.MONTH));
        assertEquals(2024, result.get(Calendar.YEAR));
    }

    @Test
    public void getLastDayOfMonth_returnsLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.FEBRUARY, 15);
        Date lastDay = DateUtils.getLastDayOfMonth(calendar.getTime());
        Calendar result = DateUtils.getCalendar(lastDay);
        assertEquals(29, result.get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.FEBRUARY, result.get(Calendar.MONTH));
    }

    @Test
    public void getFirstDayOfWeek_returnsMonday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 15);
        Date firstDay = DateUtils.getFirstDayOfWeek(calendar.getTime(), Calendar.MONDAY);
        Calendar result = DateUtils.getCalendar(firstDay);
        assertEquals(Calendar.MONDAY, result.get(Calendar.DAY_OF_WEEK));
    }

    @Test
    public void getLastDayOfWeek_returnsSunday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 15);
        Date lastDay = DateUtils.getLastDayOfWeek(calendar.getTime());
        Calendar result = DateUtils.getCalendar(lastDay);
        assertEquals(Calendar.SUNDAY, result.get(Calendar.DAY_OF_WEEK));
    }

    @Test
    public void isSameMonth_returnsTrue_forSameMonth() {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(2024, Calendar.MARCH, 1);
        Calendar cal2 = Calendar.getInstance();
        cal2.set(2024, Calendar.MARCH, 31);
        assertTrue(DateUtils.isSameMonth(cal1, cal2));
    }

    @Test
    public void isSameMonth_returnsFalse_forDifferentMonth() {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(2024, Calendar.MARCH, 1);
        Calendar cal2 = Calendar.getInstance();
        cal2.set(2024, Calendar.APRIL, 1);
        assertFalse(DateUtils.isSameMonth(cal1, cal2));
    }

    @Test
    public void isSameDayOfMonth_returnsTrue_forSameDay() {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(2024, Calendar.MARCH, 15);
        Calendar cal2 = Calendar.getInstance();
        cal2.set(2024, Calendar.APRIL, 15);
        assertTrue(DateUtils.isSameDayOfMonth(cal1, cal2));
    }

    @Test
    public void addMonth_incrementsMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 1);
        DateUtils.addMonth(calendar);
        assertEquals(Calendar.FEBRUARY, calendar.get(Calendar.MONTH));
    }

    @Test
    public void addDay_incrementsDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 31);
        DateUtils.addDay(calendar);
        assertEquals(1, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.FEBRUARY, calendar.get(Calendar.MONTH));
    }

    @Test
    public void isCurrentDate_returnsFalse_forNull() {
        assertFalse(DateUtils.isCurrentDate(null));
    }

    @Test
    public void isDayInRange_returnsTrue_forDayInRange() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.MARCH, 15);
        Day middle = new Day(cal.getTime());

        cal.set(2024, Calendar.MARCH, 1);
        Day start = new Day(cal.getTime());

        cal.set(2024, Calendar.MARCH, 31);
        Day end = new Day(cal.getTime());

        assertTrue(DateUtils.isDayInRange(middle, start, end));
    }

    @Test
    public void isDayInRange_returnsFalse_forDayOutsideRange() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.APRIL, 1);
        Day outside = new Day(cal.getTime());

        cal.set(2024, Calendar.MARCH, 1);
        Day start = new Day(cal.getTime());

        cal.set(2024, Calendar.MARCH, 31);
        Day end = new Day(cal.getTime());

        assertFalse(DateUtils.isDayInRange(outside, start, end));
    }
}
