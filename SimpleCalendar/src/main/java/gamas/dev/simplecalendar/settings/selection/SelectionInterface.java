package gamas.dev.simplecalendar.settings.selection;

import gamas.dev.simplecalendar.utils.SelectionType;

public interface SelectionInterface {

    @SelectionType
    int getSelectionType();

    void setSelectionType(@SelectionType int selectionType);
}
