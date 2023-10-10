package io.github.luisgamas.simplecalendar.sample;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.github.luisgamas.simplecalendar.selection.MultipleSelectionManager;
import io.github.luisgamas.simplecalendar.selection.criteria.BaseCriteria;
import io.github.luisgamas.simplecalendar.selection.criteria.WeekDayCriteria;
import io.github.luisgamas.simplecalendar.selection.criteria.month.CurrentMonthCriteria;
import io.github.luisgamas.simplecalendar.selection.criteria.month.NextMonthCriteria;
import io.github.luisgamas.simplecalendar.selection.criteria.month.PreviousMonthCriteria;
import io.github.luisgamas.simplecalendar.utils.SelectionType;
import io.github.luisgamas.simplecalendar.view.CalendarView;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private CalendarView calendarView;

    private List<BaseCriteria> threeMonthsCriteriaList;
    private WeekDayCriteria fridayCriteria;

    private boolean fridayCriteriaEnabled;
    private boolean threeMonthsCriteriaEnabled;

    private MenuItem menuFridays;
    private MenuItem menuThreeMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.toolbar));

        initViews();
        createCriterias();
    }

    public void initViews() {
        calendarView = findViewById(R.id.calendar_view);
        ((RadioGroup) findViewById(R.id.rg_orientation)).setOnCheckedChangeListener(this);
        ((RadioGroup) findViewById(R.id.rg_selection_type)).setOnCheckedChangeListener(this);
    }

    private void createCriterias() {
        fridayCriteria = new WeekDayCriteria(Calendar.FRIDAY);

        threeMonthsCriteriaList = new ArrayList<>();
        threeMonthsCriteriaList.add(new CurrentMonthCriteria());
        threeMonthsCriteriaList.add(new NextMonthCriteria());
        threeMonthsCriteriaList.add(new PreviousMonthCriteria());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_default_calendar_activity, menu);
        menuFridays = menu.findItem(R.id.select_all_fridays);
        menuThreeMonth = menu.findItem(R.id.select_three_months);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.select_all_fridays) {
            fridayMenuClick();
        } else if (item.getItemId() == R.id.select_three_months) {
            threeMonthsMenuClick();
        } else if (item.getItemId() == R.id.clear_selections) {
            clearSelectionsMenuClick();
        } else if (item.getItemId() == R.id.log_selected_days) {
            logSelectedDaysMenuClick();
        }

        return super.onOptionsItemSelected(item);
    }

    private void fridayMenuClick() {
        if (fridayCriteriaEnabled) {
            menuFridays.setTitle(getString(R.string.select_all_fridays));
            unselectAllFridays();
        } else {
            menuFridays.setTitle(getString(R.string.unselect_all_fridays));
            selectAllFridays();
        }
        fridayCriteriaEnabled = !fridayCriteriaEnabled;
    }

    private void threeMonthsMenuClick() {
        if (threeMonthsCriteriaEnabled) {
            menuThreeMonth.setTitle(getString(R.string.select_three_months));
            unselectThreeMonths();
        } else {
            menuThreeMonth.setTitle(getString(R.string.unselect_three_months));
            selectThreeMonths();
        }
        threeMonthsCriteriaEnabled = !threeMonthsCriteriaEnabled;
    }

    private void selectAllFridays() {
        if (calendarView.getSelectionManager() instanceof MultipleSelectionManager) {
            ((MultipleSelectionManager) calendarView.getSelectionManager()).addCriteria(fridayCriteria);
        }
        calendarView.update();
    }

    private void unselectAllFridays() {
        if (calendarView.getSelectionManager() instanceof MultipleSelectionManager) {
            ((MultipleSelectionManager) calendarView.getSelectionManager()).removeCriteria(fridayCriteria);
        }
        calendarView.update();
    }

    private void selectThreeMonths() {
        if (calendarView.getSelectionManager() instanceof MultipleSelectionManager) {
            ((MultipleSelectionManager) calendarView.getSelectionManager()).addCriteriaList(threeMonthsCriteriaList);
        }
        calendarView.update();
    }

    private void unselectThreeMonths() {
        if (calendarView.getSelectionManager() instanceof MultipleSelectionManager) {
            ((MultipleSelectionManager) calendarView.getSelectionManager()).removeCriteriaList(threeMonthsCriteriaList);
        }
        calendarView.update();
    }

    private void clearSelectionsMenuClick() {
        calendarView.clearSelections();

        fridayCriteriaEnabled = false;
        threeMonthsCriteriaEnabled = false;
        menuFridays.setTitle(getString(R.string.select_all_fridays));
        menuThreeMonth.setTitle(getString(R.string.select_three_months));
    }


    private void logSelectedDaysMenuClick() {
        Toast.makeText(this, "Selected " + calendarView.getSelectedDays().size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        clearSelectionsMenuClick();

        if (checkedId == R.id.rb_horizontal) {
            calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
        } else if (checkedId == R.id.rb_vertical) {
            calendarView.setCalendarOrientation(OrientationHelper.VERTICAL);
        } else if (checkedId == R.id.rb_single) {
            calendarView.setSelectionType(SelectionType.SINGLE);
        } else if (checkedId == R.id.rb_multiple) {
            calendarView.setSelectionType(SelectionType.MULTIPLE);
        } else if (checkedId == R.id.rb_range) {
            calendarView.setSelectionType(SelectionType.RANGE);
        } else {
            calendarView.setSelectionType(SelectionType.NONE);
        }
    }
}