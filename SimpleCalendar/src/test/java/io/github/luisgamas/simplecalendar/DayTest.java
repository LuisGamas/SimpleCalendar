package io.github.luisgamas.simplecalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Calendar;

import io.github.luisgamas.simplecalendar.model.Day;
import io.github.luisgamas.simplecalendar.selection.SelectionState;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class DayTest {

    @Test
    public void constructor_setsNotSelected() {
        Day day = new Day(Calendar.getInstance());
        assertFalse(day.isSelected());
    }

    @Test
    public void getDayNumber_returnsCorrectDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 15);
        Day day = new Day(calendar);
        assertEquals(15, day.getDayNumber());
    }

    @Test
    public void setSelected_updatesState() {
        Day day = new Day(Calendar.getInstance());
        day.setSelected(true);
        assertTrue(day.isSelected());
    }

    @Test
    public void setDisabled_updatesState() {
        Day day = new Day(Calendar.getInstance());
        day.setDisabled(true);
        assertTrue(day.isDisabled());
    }

    @Test
    public void setWeekend_updatesState() {
        Day day = new Day(Calendar.getInstance());
        day.setWeekend(true);
        assertTrue(day.isWeekend());
    }

    @Test
    public void setBelongToMonth_updatesState() {
        Day day = new Day(Calendar.getInstance());
        day.setBelongToMonth(true);
        assertTrue(day.isBelongToMonth());
    }

    @Test
    public void setFromConnectedCalendar_updatesState() {
        Day day = new Day(Calendar.getInstance());
        day.setFromConnectedCalendar(true);
        assertTrue(day.isFromConnectedCalendar());
    }

    @Test
    public void setSelectionState_updatesState() {
        Day day = new Day(Calendar.getInstance());
        day.setSelectionState(SelectionState.SINGLE_DAY);
        assertEquals(SelectionState.SINGLE_DAY, day.getSelectionState());
    }

    @Test
    public void setConnectedDaysTextColor_updatesColor() {
        Day day = new Day(Calendar.getInstance());
        day.setConnectedDaysTextColor(0xFF0000);
        assertEquals(0xFF0000, day.getConnectedDaysTextColor());
    }

    @Test
    public void equals_returnsTrue_forSameDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 15);
        Day day1 = new Day(calendar);
        Day day2 = new Day(calendar);
        assertTrue(day1.equals(day2));
    }

    @Test
    public void equals_returnsFalse_forDifferentDay() {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(2024, Calendar.MARCH, 15);
        Calendar cal2 = Calendar.getInstance();
        cal2.set(2024, Calendar.MARCH, 16);
        Day day1 = new Day(cal1);
        Day day2 = new Day(cal2);
        assertFalse(day1.equals(day2));
    }

    @Test
    public void toString_returnsFormattedString() {
        Day day = new Day(Calendar.getInstance());
        assertNotNull(day.toString());
        assertTrue(day.toString().startsWith("Day{"));
    }

    @Test
    public void hashCode_isConsistent() {
        Day day = new Day(Calendar.getInstance());
        int hash1 = day.hashCode();
        int hash2 = day.hashCode();
        assertEquals(hash1, hash2);
    }
}
