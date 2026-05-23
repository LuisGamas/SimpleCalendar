package io.github.luisgamas.simplecalendar;

import org.junit.Test;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import io.github.luisgamas.simplecalendar.settings.lists.CalendarListsModel;
import io.github.luisgamas.simplecalendar.settings.lists.connected_days.ConnectedDays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

public class CalendarListsModelTest {

    @Test
    public void eachInstance_hasOwnConnectedDaysManager() {
        CalendarListsModel model1 = new CalendarListsModel();
        CalendarListsModel model2 = new CalendarListsModel();

        assertNotSame(
                "Each CalendarListsModel should have its own ConnectedDaysManager",
                model1.getConnectedDaysManager(),
                model2.getConnectedDaysManager()
        );
    }

    @Test
    public void connectedDaysManager_isInitiallyEmpty() {
        CalendarListsModel model = new CalendarListsModel();

        assertFalse(
                "New manager should have no connected days",
                model.getConnectedDaysManager().isAnyConnectedDays()
        );
    }

    @Test
    public void addConnectedDays_onlyAffectsCallingInstance() {
        CalendarListsModel model1 = new CalendarListsModel();
        CalendarListsModel model2 = new CalendarListsModel();

        Set<Long> days = new HashSet<>();
        days.add(Calendar.getInstance().getTimeInMillis());
        ConnectedDays cd = new ConnectedDays(days, 0xFF0000);

        model1.addConnectedDays(cd);

        assertTrue("model1 should have connected days",
                model1.getConnectedDaysManager().isAnyConnectedDays());
        assertFalse("model2 should NOT have connected days",
                model2.getConnectedDaysManager().isAnyConnectedDays());
    }
}
