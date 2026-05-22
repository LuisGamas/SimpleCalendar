package io.github.luisgamas.simplecalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import io.github.luisgamas.simplecalendar.model.Day;
import io.github.luisgamas.simplecalendar.settings.lists.DisabledDaysCriteria;
import io.github.luisgamas.simplecalendar.settings.lists.DisabledDaysCriteriaType;
import io.github.luisgamas.simplecalendar.utils.CalendarUtils;
import io.github.luisgamas.simplecalendar.utils.Constants;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class CalendarUtilsTest {

    @Test
    public void createWeekDayTitles_returnsSevenTitles() {
        var titles = CalendarUtils.createWeekDayTitles(Calendar.MONDAY);
        assertNotNull(titles);
        assertEquals(Constants.DAYS_IN_WEEK, titles.size());
    }

    @Test
    public void getSelectedDayListForMultipleMode_returnsEmpty_forEmptyList() {
        var result = CalendarUtils.getSelectedDayListForMultipleMode(java.util.Collections.emptyList());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void isDayInSet_returnsTrue_forMatchingDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 15);
        var day = new Day(calendar);

        java.util.Calendar matchingCal = java.util.Calendar.getInstance();
        matchingCal.set(2024, Calendar.MARCH, 15, 0, 0, 0);
        matchingCal.set(java.util.Calendar.MILLISECOND, 0);
        java.util.Set<Long> daySet = new java.util.HashSet<>();
        daySet.add(matchingCal.getTimeInMillis());

        assertTrue(CalendarUtils.isDayInSet(day, daySet));
    }

    @Test
    public void isDayInSet_returnsFalse_forNonMatchingDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 15);
        var day = new Day(calendar);

        java.util.Set<Long> daySet = new java.util.HashSet<>();
        daySet.add(0L);

        assertFalse(CalendarUtils.isDayInSet(day, daySet));
    }

    @Test
    public void isDayDisabledByCriteria_withDaysOfMonth_returnsTrue() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 15);
        var day = new Day(calendar);

        var criteria = new DisabledDaysCriteria(
                new HashSet<>(Arrays.asList(15)),
                DisabledDaysCriteriaType.DAYS_OF_MONTH
        );

        assertTrue(CalendarUtils.isDayDisabledByCriteria(day, criteria));
    }
}
