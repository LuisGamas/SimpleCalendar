package io.github.luisgamas.simplecalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Calendar;

import io.github.luisgamas.simplecalendar.model.Day;
import io.github.luisgamas.simplecalendar.selection.OnDaySelectedListener;
import io.github.luisgamas.simplecalendar.selection.RangeSelectionManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class RangeSelectionManagerTest {

    private boolean daySelectedCalled;

    @Test
    public void toggleDay_setsTempDay_onFirstSelection() {
        RangeSelectionManager manager = createManager();
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.MARCH, 15);
        Day day = new Day(cal);

        manager.toggleDay(day);

        assertTrue(manager.isDaySelected(day));
    }

    @Test
    public void toggleDay_createsRange_onSecondSelection() {
        RangeSelectionManager manager = createManager();
        Calendar cal1 = Calendar.getInstance();
        cal1.set(2024, Calendar.MARCH, 15);
        Calendar cal2 = Calendar.getInstance();
        cal2.set(2024, Calendar.MARCH, 20);

        manager.toggleDay(new Day(cal1));
        manager.toggleDay(new Day(cal2));

        assertTrue(manager.getDays() != null);
    }

    @Test
    public void toggleDay_usesEquals_notReference() {
        RangeSelectionManager manager = createManager();
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.MARCH, 15);

        Day day1 = new Day(cal);
        Day day2 = new Day(cal);

        manager.toggleDay(day1);
        manager.toggleDay(day2);

        assertNull("Second click on same date should be ignored (equals match)", manager.getDays());
    }

    @Test
    public void toggleDay_ordersRange_startBeforeEnd() {
        RangeSelectionManager manager = createManager();
        Calendar cal1 = Calendar.getInstance();
        cal1.set(2024, Calendar.MARCH, 20);
        Calendar cal2 = Calendar.getInstance();
        cal2.set(2024, Calendar.MARCH, 15);

        manager.toggleDay(new Day(cal1));
        manager.toggleDay(new Day(cal2));

        assertEquals(15, manager.getDays().first.getDayNumber());
        assertEquals(20, manager.getDays().second.getDayNumber());
    }

    @Test
    public void isDaySelected_returnsFalse_forUnselectedDay() {
        RangeSelectionManager manager = createManager();
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.MARCH, 15);
        Calendar calOther = Calendar.getInstance();
        calOther.set(2024, Calendar.MARCH, 16);

        manager.toggleDay(new Day(cal));

        assertFalse(manager.isDaySelected(new Day(calOther)));
    }

    @Test
    public void clearSelections_resetsState() {
        RangeSelectionManager manager = createManager();
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.MARCH, 15);

        manager.toggleDay(new Day(cal));
        manager.clearSelections();

        assertFalse(manager.isDaySelected(new Day(cal)));
        assertNull(manager.getDays());
    }

    @Test
    public void getSelectedState_returnsStartRangeDayWithoutEnd_forSingleTemp() {
        RangeSelectionManager manager = createManager();
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.MARCH, 15);

        manager.toggleDay(new Day(cal));

        assertEquals(
                io.github.luisgamas.simplecalendar.selection.SelectionState.START_RANGE_DAY_WITHOUT_END,
                manager.getSelectedState(new Day(cal))
        );
    }

    private RangeSelectionManager createManager() {
        daySelectedCalled = false;
        return new RangeSelectionManager(new OnDaySelectedListener() {
            @Override
            public void onDaySelected() {
                daySelectedCalled = true;
            }
        });
    }
}
