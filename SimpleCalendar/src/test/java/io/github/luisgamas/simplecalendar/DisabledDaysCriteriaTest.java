package io.github.luisgamas.simplecalendar;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import io.github.luisgamas.simplecalendar.settings.lists.DisabledDaysCriteria;
import io.github.luisgamas.simplecalendar.settings.lists.DisabledDaysCriteriaType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DisabledDaysCriteriaTest {

    @Test
    public void constructor_withRange_createsDaysSet() {
        DisabledDaysCriteria criteria = new DisabledDaysCriteria(1, 5,
                DisabledDaysCriteriaType.DAYS_OF_MONTH);
        assertEquals(5, criteria.getDays().size());
        assertTrue(criteria.getDays().contains(1));
        assertTrue(criteria.getDays().contains(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_withInvalidRange_throwsException() {
        new DisabledDaysCriteria(5, 3, DisabledDaysCriteriaType.DAYS_OF_MONTH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_withNegativeStart_throwsException() {
        new DisabledDaysCriteria(-1, 5, DisabledDaysCriteriaType.DAYS_OF_MONTH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_withDayExceedingMax_throwsException() {
        Set<Integer> days = new HashSet<>(Arrays.asList(32));
        new DisabledDaysCriteria(days, DisabledDaysCriteriaType.DAYS_OF_MONTH);
    }

    @Test
    public void constructor_withDaysOfWeek_exceedingMax_throwsException() {
        Set<Integer> days = new HashSet<>(Arrays.asList(8));
        try {
            new DisabledDaysCriteria(days, DisabledDaysCriteriaType.DAYS_OF_WEEK);
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    @Test
    public void constructor_withDaysSet_createsCriteria() {
        Set<Integer> days = new HashSet<>(Arrays.asList(1, 15, 20));
        DisabledDaysCriteria criteria = new DisabledDaysCriteria(days,
                DisabledDaysCriteriaType.DAYS_OF_MONTH);
        assertEquals(3, criteria.getDays().size());
    }

    @Test
    public void getCriteriaType_returnsType() {
        DisabledDaysCriteria criteria = new DisabledDaysCriteria(
                new HashSet<Integer>(), DisabledDaysCriteriaType.DAYS_OF_WEEK);
        assertEquals(DisabledDaysCriteriaType.DAYS_OF_WEEK, criteria.getCriteriaType());
    }

    @Test
    public void setDays_withSet_updatesDays() {
        DisabledDaysCriteria criteria = new DisabledDaysCriteria(
                new HashSet<Integer>(), DisabledDaysCriteriaType.DAYS_OF_MONTH);
        Set<Integer> newDays = new HashSet<>(Arrays.asList(5, 10, 15));
        criteria.setDays(newDays, DisabledDaysCriteriaType.DAYS_OF_MONTH);
        assertEquals(3, criteria.getDays().size());
    }
}
