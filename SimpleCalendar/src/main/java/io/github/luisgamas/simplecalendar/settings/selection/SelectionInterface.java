package io.github.luisgamas.simplecalendar.settings.selection;

import io.github.luisgamas.simplecalendar.utils.SelectionType;

public interface SelectionInterface {

    @SelectionType
    int getSelectionType();

    void setSelectionType(@SelectionType int selectionType);
}
