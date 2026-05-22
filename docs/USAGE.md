# SimpleCalendar — Usage Guide

## Overview

SimpleCalendar is an Android library widget that provides a fully customizable calendar with multiple selection modes, connected days, disabled days, and infinite scrolling. Built as a fork of CosmoCalendar, it targets modern Android SDK versions and Java 17.

---

## Installation

Add the dependency to your module's `build.gradle`:

```groovy
dependencies {
    implementation 'io.github.luisgamas:simplecalendar:{version}'
}
```

Minimum SDK: **26**  
Compile SDK: **36**

---

## Quick Start

### 1. Add to XML layout

```xml
<io.github.luisgamas.simplecalendar.view.CalendarView
    android:id="@+id/calendar_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

### 2. Get selected days programmatically

```java
CalendarView calendarView = findViewById(R.id.calendar_view);
List<Day> selectedDays = calendarView.getSelectedDays();
List<Calendar> selectedDates = calendarView.getSelectedDates();
```

### 3. Clear all selections

```java
calendarView.clearSelections();
```

---

## XML Attributes

All customization is available via XML attributes on the `CalendarView` tag.

### Common

| Attribute | Type | Description |
|-----------|------|-------------|
| `orientation` | `horizontal` / `vertical` | Calendar scroll direction. **Default:** `vertical` |
| `calendarBackgroundColor` | `color` | Background color of the whole calendar widget |
| `monthTextColor` | `color` | Color of the month title text (e.g. "May 2026") |
| `dayTextColor` | `color` | Color of day numbers |
| `otherDayTextColor` | `color` | Color of days that belong to adjacent months |
| `weekDayTitleTextColor` | `color` | Color of weekday column headers (M, T, W, …) |
| `firstDayOfTheWeek` | `sunday` / `monday` / … / `saturday` | First column of the grid. **Default:** `monday` |
| `showDaysOfWeek` | `boolean` | Show day-of-week row in each month card (auto-managed by orientation) |
| `showDaysOfWeekTitle` | `boolean` | Show day-of-week row above the entire calendar (auto-managed by orientation) |

### Selection

| Attribute | Type | Description |
|-----------|------|-------------|
| `selectionType` | `single` / `multiple` / `range` / `none` | Selection behaviour. **Default:** `single` |
| `selectedDayTextColor` | `color` | Text color of a selected day |
| `selectedDayBackgroundColor` | `color` | Fill color of a selected day (single & multiple) |
| `selectedDayBackgroundStartColor` | `color` | Start color of range selection gradient |
| `selectedDayBackgroundEndColor` | `color` | End color of range selection gradient |
| `selectionBarMonthTextColor` | `color` | Text color of the month label in bottom selection bar |

### Current Day

| Attribute | Type | Description |
|-----------|------|-------------|
| `currentDayTextColor` | `color` | Text color of today's date |
| `currentDayIconRes` | `reference` | Drawable icon for today (default: colored triangle) |
| `currentDaySelectedIconRes` | `reference` | Drawable icon for today when selected (default: white triangle) |

### Weekend Days

| Attribute | Type | Description |
|-----------|------|-------------|
| `weekendDays` | `flag` | Bitmask: `none`, `monday`, `tuesday`, … `sunday` (e.g. `saturday\|sunday`) |
| `weekendDayTextColor` | `color` | Text color of weekend days |

### Connected Days

| Attribute | Type | Description |
|-----------|------|-------------|
| `connectedDayIconRes` | `reference` | Drawable icon for a connected day |
| `connectedDaySelectedIconRes` | `reference` | Drawable icon for a connected day when selected |
| `connectedDayIconPosition` | `top` / `bottom` | Icon placement relative to the day number |

### Disabled Days

| Attribute | Type | Description |
|-----------|------|-------------|
| `disabledDayTextColor` | `color` | Text color of disabled days |

### Navigation Buttons

| Attribute | Type | Description |
|-----------|------|-------------|
| `previousMonthIconRes` | `reference` | Drawable for "previous month" button (horizontal mode) |
| `nextMonthIconRes` | `reference` | Drawable for "next month" button (horizontal mode) |

---

## Selection Modes

Switch selection mode at any time:

```java
import io.github.luisgamas.simplecalendar.utils.SelectionType;

calendarView.setSelectionType(SelectionType.SINGLE);    // one day
calendarView.setSelectionType(SelectionType.MULTIPLE);  // multiple days
calendarView.setSelectionType(SelectionType.RANGE);     // start-to-end range
calendarView.setSelectionType(SelectionType.NONE);      // no selection
```

### Multiple Selection — criteria-based

You can auto-select all days matching a criterion:

```java
MultipleSelectionManager manager =
    (MultipleSelectionManager) calendarView.getSelectionManager();

// Select all Fridays
manager.addCriteria(new WeekDayCriteria(Calendar.FRIDAY));

// Select all days of the current month
manager.addCriteria(new CurrentMonthCriteria());
manager.addCriteria(new NextMonthCriteria());
manager.addCriteria(new PreviousMonthCriteria());
```

### Range Selection

The bottom bar displays the selected range start/end when in `HORIZONTAL` mode.

---

## Connected Days

Mark specific dates (e.g. holidays) with custom colors:

```java
import io.github.luisgamas.simplecalendar.settings.lists.connected_days.ConnectedDays;

Calendar calendar = Calendar.getInstance();
Set<Long> days = new TreeSet<>();
days.add(calendar.getTimeInMillis());
// add more days...

ConnectedDays connectedDays = new ConnectedDays(
    days,
    Color.parseColor("#ff0000"),          // normal text color
    Color.parseColor("#ff4000"),          // selected text color
    Color.parseColor("#ff8000")           // disabled text color
);

calendarView.addConnectedDays(connectedDays);
```

You can also pass a simplified constructor:

```java
new ConnectedDays(days, textColor, selectedTextColor);
new ConnectedDays(days, textColor);   // all three colors the same
```

---

## Disabled Days

Prevent specific days from being selected:

```java
Set<Long> disabled = new HashSet<>();
disabled.add(System.currentTimeMillis());
calendarView.setDisabledDays(disabled);
```

### By Criteria

Disable based on day-of-month or day-of-week ranges:

```java
import io.github.luisgamas.simplecalendar.settings.lists.DisabledDaysCriteria;
import io.github.luisgamas.simplecalendar.settings.lists.DisabledDaysCriteriaType;

// Disable days 1–5 of every month
calendarView.setDisabledDaysCriteria(
    new DisabledDaysCriteria(1, 5, DisabledDaysCriteriaType.DAYS_OF_MONTH)
);

// Disable Monday–Friday
calendarView.setDisabledDaysCriteria(
    new DisabledDaysCriteria(
        Calendar.MONDAY, Calendar.FRIDAY,
        DisabledDaysCriteriaType.DAYS_OF_WEEK
    )
);
```

You can also pass an explicit `Set<Integer>`:

```java
Set<Integer> days = new HashSet<>(Arrays.asList(1, 15, 31));
calendarView.setDisabledDaysCriteria(
    new DisabledDaysCriteria(days, DisabledDaysCriteriaType.DAYS_OF_MONTH)
);
```

---

## Navigation

### Programmatic

```java
calendarView.goToPreviousMonth();
calendarView.goToNextMonth();
```

### Orientation

```java
import androidx.recyclerview.widget.LinearLayoutManager;

calendarView.setCalendarOrientation(LinearLayoutManager.HORIZONTAL);
calendarView.setCalendarOrientation(LinearLayoutManager.VERTICAL);
```

- **Horizontal:** Page through months with prev/next buttons and snap-to-month.
- **Vertical:** Scroll infinitely through months.

---

## Listeners

### Month change

```java
calendarView.setOnMonthChangeListener(new OnMonthChangeListener() {
    @Override
    public void onMonthChanged(Month month) {
        String name = month.getMonthName();  // e.g. "May 2026"
    }
});
```

The `Month` object exposes:

| Method | Returns |
|--------|---------|
| `getMonthName()` | Formatted name (e.g. "May 2026") |
| `getDays()` | All `Day` objects for the month |
| `getDaysWithoutTitlesAndOnlyCurrent()` | Current month days only, excluding headers |
| `getFirstDay()` | First `Day` of the month |

### Day model

The `Day` object exposes:

| Method | Returns |
|--------|---------|
| `getCalendar()` | `Calendar` instance for this day |
| `getDayNumber()` | Day of month (1–31) |
| `isCurrent()` | Whether this is today |
| `isSelected()` | Whether this day is currently selected |
| `isDisabled()` | Whether this day is disabled |
| `isWeekend()` | Whether this day is a weekend day |
| `isBelongToMonth()` | Whether this day belongs to the current visible month |
| `isFromConnectedCalendar()` | Whether this day is a connected/highlighted day |

---

## CalendarDialog

A full-screen dialog with cancel/done actions that wraps a `CalendarView`:

```java
new CalendarDialog(context, new OnDaysSelectionListener() {
    @Override
    public void onDaysSelected(List<Day> selectedDays) {
        // handle selection
    }
}).show();
```

All customization methods available on `CalendarView` are also accessible through `CalendarDialog`:

```java
CalendarDialog dialog = new CalendarDialog(context, listener);
dialog.setSelectionType(SelectionType.RANGE);
dialog.setCalendarBackgroundColor(Color.WHITE);
dialog.show();
```

---

## Programmatic API Reference

### CalendarView public methods

| Method | Description |
|--------|-------------|
| `getSelectedDays()` | Returns `List<Day>` of selected days |
| `getSelectedDates()` | Returns `List<Calendar>` of selected dates |
| `clearSelections()` | Clears all manual and criteria-based selections |
| `update()` | Forces a full redraw |
| `getSelectionManager()` | Returns the current `BaseSelectionManager` |
| `setSelectionManager(BaseSelectionManager)` | Replaces the selection manager |
| `goToPreviousMonth()` | Smooth-scroll to previous month |
| `goToNextMonth()` | Smooth-scroll to next month |
| `getSettingsManager()` | Returns the underlying `SettingsManager` |
| `setSelectionType(@SelectionType int)` | Changes selection mode |
| `setCalendarOrientation(int)` | Toggles horizontal/vertical |
| `setWeekendDays(Set<Long>)` | Sets which days of week are weekends |
| `setDisabledDays(Set<Long>)` | Disables specific dates |
| `setDisabledDaysCriteria(DisabledDaysCriteria)` | Disables by day-of-month or day-of-week |
| `addConnectedDays(ConnectedDays)` | Highlights specific dates |
| `setOnMonthChangeListener(OnMonthChangeListener)` | Month visibility callback |

### Getters/Setters

All XML attributes have corresponding getters and setters on `CalendarView` following the pattern `getXxx()` / `setXxx(...)`:

- `getCalendarBackgroundColor()` / `setCalendarBackgroundColor(int)`
- `getMonthTextColor()` / `setMonthTextColor(int)`
- `getDayTextColor()` / `setDayTextColor(int)`
- `getOtherDayTextColor()` / `setOtherDayTextColor(int)`
- `getWeekendDayTextColor()` / `setWeekendDayTextColor(int)`
- `getWeekDayTitleTextColor()` / `setWeekDayTitleTextColor(int)`
- `getSelectedDayTextColor()` / `setSelectedDayTextColor(int)`
- `getSelectedDayBackgroundColor()` / `setSelectedDayBackgroundColor(int)`
- `getSelectedDayBackgroundStartColor()` / `setSelectedDayBackgroundStartColor(int)`
- `getSelectedDayBackgroundEndColor()` / `setSelectedDayBackgroundEndColor(int)`
- `getCurrentDayTextColor()` / `setCurrentDayTextColor(int)`
- `getCurrentDayIconRes()` / `setCurrentDayIconRes(int)`
- `getCurrentDaySelectedIconRes()` / `setCurrentDaySelectedIconRes(int)`
- `getDisabledDayTextColor()` / `setDisabledDayTextColor(int)`
- `getSelectionBarMonthTextColor()` / `setSelectionBarMonthTextColor(int)`
- `getConnectedDayIconRes()` / `setConnectedDayIconRes(int)`
- `getConnectedDaySelectedIconRes()` / `setConnectedDaySelectedIconRes(int)`
- `getConnectedDayIconPosition()` / `setConnectedDayIconPosition(int)`
- `getPreviousMonthIconRes()` / `setPreviousMonthIconRes(int)`
- `getNextMonthIconRes()` / `setNextMonthIconRes(int)`
- `isShowDaysOfWeek()` / `setShowDaysOfWeek(boolean)`
- `isShowDaysOfWeekTitle()` / `setShowDaysOfWeekTitle(boolean)`
- `getFirstDayOfWeek()` / `setFirstDayOfWeek(int)`

---

## Constants & Enums

| Type | Values |
|------|--------|
| `SelectionType` | `SINGLE(0)`, `MULTIPLE(1)`, `RANGE(2)`, `NONE(3)` |
| `ConnectedDayIconPosition` | `TOP(0)`, `BOTTOM(1)` |
| `DisabledDaysCriteriaType` | `DAYS_OF_MONTH`, `DAYS_OF_WEEK` |
| `WeekDay` (bitmask) | `NONE(0)`, `MONDAY(1)`, `TUESDAY(2)`, `WEDNESDAY(4)`, `THURSDAY(8)`, `FRIDAY(16)`, `SATURDAY(32)`, `SUNDAY(64)` |

---

## Sample App

A complete sample app is available in the `app/` module demonstrating all features including single, multiple, range selection, connected days, and disabled days.
