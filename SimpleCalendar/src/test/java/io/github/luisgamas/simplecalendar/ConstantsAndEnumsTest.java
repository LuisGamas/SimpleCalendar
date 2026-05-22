package io.github.luisgamas.simplecalendar;

import org.junit.Test;

import io.github.luisgamas.simplecalendar.selection.SelectionState;
import io.github.luisgamas.simplecalendar.settings.lists.DisabledDaysCriteriaType;
import io.github.luisgamas.simplecalendar.utils.Constants;
import io.github.luisgamas.simplecalendar.utils.SelectionType;
import io.github.luisgamas.simplecalendar.utils.WeekDay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ConstantsAndEnumsTest {

    @Test
    public void constants_areCorrect() {
        assertEquals(7, Constants.DAYS_IN_WEEK);
        assertNotNull(Constants.DAY_NAME_FORMAT);
    }

    @Test
    public void weekDay_constants_areCorrect() {
        assertEquals(0, WeekDay.NONE);
        assertEquals(1, WeekDay.MONDAY);
        assertEquals(2, WeekDay.TUESDAY);
        assertEquals(4, WeekDay.WEDNESDAY);
        assertEquals(8, WeekDay.THURSDAY);
        assertEquals(16, WeekDay.FRIDAY);
        assertEquals(32, WeekDay.SATURDAY);
        assertEquals(64, WeekDay.SUNDAY);
    }

    @Test
    public void selectionType_constants_areCorrect() {
        assertEquals(0, SelectionType.SINGLE);
        assertEquals(1, SelectionType.MULTIPLE);
        assertEquals(2, SelectionType.RANGE);
        assertEquals(3, SelectionType.NONE);
    }

    @Test
    public void selectionState_hasExpectedValues() {
        assertNotNull(SelectionState.valueOf("START_RANGE_DAY_WITHOUT_END"));
        assertNotNull(SelectionState.valueOf("START_RANGE_DAY"));
        assertNotNull(SelectionState.valueOf("END_RANGE_DAY"));
        assertNotNull(SelectionState.valueOf("RANGE_DAY"));
        assertNotNull(SelectionState.valueOf("SINGLE_DAY"));
        assertEquals(5, SelectionState.values().length);
    }

    @Test
    public void disabledDaysCriteriaType_hasExpectedValues() {
        assertNotNull(DisabledDaysCriteriaType.valueOf("DAYS_OF_MONTH"));
        assertNotNull(DisabledDaysCriteriaType.valueOf("DAYS_OF_WEEK"));
        assertEquals(2, DisabledDaysCriteriaType.values().length);
    }
}
