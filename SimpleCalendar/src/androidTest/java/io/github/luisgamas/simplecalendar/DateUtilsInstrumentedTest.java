package io.github.luisgamas.simplecalendar;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

import io.github.luisgamas.simplecalendar.utils.DateUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class DateUtilsInstrumentedTest {

    @Test
    public void getFirstDayOfWeek_returnsExpectedDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JUNE, 15);
        Date firstDay = DateUtils.getFirstDayOfWeek(calendar.getTime(), Calendar.MONDAY);
        Calendar result = Calendar.getInstance();
        result.setTime(firstDay);
        assertEquals(Calendar.MONDAY, result.get(Calendar.DAY_OF_WEEK));
    }

    @Test
    public void getLastDayOfWeek_returnsExpectedDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JUNE, 15);
        Date lastDay = DateUtils.getLastDayOfWeek(calendar.getTime());
        Calendar result = Calendar.getInstance();
        result.setTime(lastDay);
        assertEquals(Calendar.SUNDAY, result.get(Calendar.DAY_OF_WEEK));
    }

    @Test
    public void isCurrentDate_checksToday() {
        assertTrue(DateUtils.isCurrentDate(new Date()));
    }

    @Test
    public void isSameMonth_comparison() {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(2025, Calendar.JANUARY, 1);
        Calendar cal2 = Calendar.getInstance();
        cal2.set(2025, Calendar.JANUARY, 31);
        assertTrue(DateUtils.isSameMonth(cal1, cal2));
    }

    @Test
    public void addMonth_advancesCorrectly() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.DECEMBER, 1);
        DateUtils.addMonth(calendar);
        assertEquals(Calendar.JANUARY, calendar.get(Calendar.MONTH));
        assertEquals(2026, calendar.get(Calendar.YEAR));
    }
}
