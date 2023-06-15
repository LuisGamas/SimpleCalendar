package gamas.dev.simplecalendar.settings.lists;

import java.util.Set;

import gamas.dev.simplecalendar.settings.lists.connected_days.ConnectedDays;
import gamas.dev.simplecalendar.settings.lists.connected_days.ConnectedDaysManager;

public interface CalendarListsInterface {

    Set<Long> getDisabledDays();

    void setDisabledDays(Set<Long> disabledDays);

    ConnectedDaysManager getConnectedDaysManager();

    Set<Long> getWeekendDays();

    void setWeekendDays(Set<Long> weekendDays);

    DisabledDaysCriteria getDisabledDaysCriteria();

    void setDisabledDaysCriteria(DisabledDaysCriteria criteria);

    void addConnectedDays(ConnectedDays connectedDays);
}
