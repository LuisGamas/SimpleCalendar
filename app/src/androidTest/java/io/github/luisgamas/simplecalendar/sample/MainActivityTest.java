package io.github.luisgamas.simplecalendar.sample;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void activity_launchesSuccessfully() {
        ActivityScenario<MainActivity> scenario = activityRule.getScenario();
        Espresso.onView(ViewMatchers.withId(R.id.calendar_view))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void radioGroup_horizontal_changesOrientation() {
        Espresso.onView(ViewMatchers.withId(R.id.rb_horizontal))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.calendar_view))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void radioGroup_vertical_changesOrientation() {
        Espresso.onView(ViewMatchers.withId(R.id.rb_vertical))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.calendar_view))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void radioGroup_selectionType_single_works() {
        Espresso.onView(ViewMatchers.withId(R.id.rb_single))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.calendar_view))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void radioGroup_selectionType_multiple_works() {
        Espresso.onView(ViewMatchers.withId(R.id.rb_multiple))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.calendar_view))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void radioGroup_selectionType_range_works() {
        Espresso.onView(ViewMatchers.withId(R.id.rb_range))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.calendar_view))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void radioGroup_selectionType_none_works() {
        Espresso.onView(ViewMatchers.withId(R.id.rb_none))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.calendar_view))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
