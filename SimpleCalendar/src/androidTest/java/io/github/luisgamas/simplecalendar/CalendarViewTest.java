package io.github.luisgamas.simplecalendar;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.luisgamas.simplecalendar.utils.SelectionType;
import io.github.luisgamas.simplecalendar.view.CalendarView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class CalendarViewTest {

    @Test
    public void calendarView_instantiates() {
        Context context = ApplicationProvider.getApplicationContext();
        CalendarView calendarView = new CalendarView(context);
        assertNotNull(calendarView);
    }

    @Test
    public void defaultSelectionType_isSingle() {
        Context context = ApplicationProvider.getApplicationContext();
        CalendarView calendarView = new CalendarView(context);
        assertEquals(SelectionType.SINGLE, calendarView.getSelectionType());
    }

    @Test
    public void setSelectionType_updatesType() {
        Context context = ApplicationProvider.getApplicationContext();
        CalendarView calendarView = new CalendarView(context);
        calendarView.setSelectionType(SelectionType.MULTIPLE);
        assertEquals(SelectionType.MULTIPLE, calendarView.getSelectionType());
    }

    @Test
    public void setCalendarOrientation_updatesOrientation() {
        Context context = ApplicationProvider.getApplicationContext();
        CalendarView calendarView = new CalendarView(context);
        calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
        assertEquals(LinearLayoutManager.HORIZONTAL, calendarView.getCalendarOrientation());
    }

    @Test
    public void clearSelections_clearsSelectedDays() {
        Context context = ApplicationProvider.getApplicationContext();
        CalendarView calendarView = new CalendarView(context);
        calendarView.clearSelections();
        assertNotNull(calendarView.getSelectedDays());
        assertEquals(0, calendarView.getSelectedDays().size());
    }
}
