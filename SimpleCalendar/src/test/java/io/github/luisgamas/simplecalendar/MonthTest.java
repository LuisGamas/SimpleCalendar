package io.github.luisgamas.simplecalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.github.luisgamas.simplecalendar.model.Day;
import io.github.luisgamas.simplecalendar.model.DayOfWeek;
import io.github.luisgamas.simplecalendar.model.Month;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class MonthTest {

    @Test
    public void constructor_setsFirstDay() {
        Calendar calendar = Calendar.getInstance();
        Day firstDay = new Day(calendar);
        List<Day> days = new ArrayList<>();
        Month month = new Month(firstDay, days);
        assertEquals(firstDay, month.getFirstDay());
    }

    @Test
    public void getDays_returnsProvidedDays() {
        Day day = new Day(Calendar.getInstance());
        List<Day> days = new ArrayList<>();
        days.add(day);
        Month month = new Month(day, days);
        assertEquals(1, month.getDays().size());
        assertTrue(month.getDays().contains(day));
    }

    @Test
    public void getDaysWithoutTitlesAndOnlyCurrent_filtersCorrectly() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.MARCH, 1);
        Day firstDay = new Day(cal);

        List<Day> days = new ArrayList<>();
        days.add(new DayOfWeek(cal.getTime()));

        cal.set(2024, Calendar.MARCH, 15);
        days.add(new Day(cal));

        cal.set(2024, Calendar.FEBRUARY, 28);
        days.add(new Day(cal));

        Month month = new Month(firstDay, days);
        List<Day> filtered = month.getDaysWithoutTitlesAndOnlyCurrent();

        assertEquals(1, filtered.size());
    }

    @Test
    public void getMonthName_returnsFormattedName() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 1);
        Day firstDay = new Day(calendar);
        Month month = new Month(firstDay, new ArrayList<Day>());
        String name = month.getMonthName();
        assertNotNull(name);
        assertTrue(name.contains("2024"));
    }

    @Test
    public void setFirstDay_updatesFirstDay() {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(2024, Calendar.MARCH, 1);
        Day day1 = new Day(cal1);

        Calendar cal2 = Calendar.getInstance();
        cal2.set(2024, Calendar.APRIL, 1);
        Day day2 = new Day(cal2);

        Month month = new Month(day1, new ArrayList<Day>());
        month.setFirstDay(day2);
        assertEquals(day2, month.getFirstDay());
    }
}
