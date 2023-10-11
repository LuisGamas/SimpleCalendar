package io.github.luisgamas.simplecalendar.view.delegate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.luisgamas.simplecalendar.R;
import io.github.luisgamas.simplecalendar.adapter.DaysAdapter;
import io.github.luisgamas.simplecalendar.adapter.viewholder.MonthHolder;
import io.github.luisgamas.simplecalendar.model.Month;
import io.github.luisgamas.simplecalendar.settings.SettingsManager;

public class MonthDelegate {

    private SettingsManager appearanceModel;

    public MonthDelegate(SettingsManager appearanceModel) {
        this.appearanceModel = appearanceModel;
    }

    public MonthHolder onCreateMonthHolder(DaysAdapter adapter, ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.view_month, parent, false);
        final MonthHolder holder = new MonthHolder(view, appearanceModel);
        holder.setDayAdapter(adapter);
        return holder;
    }

    public void onBindMonthHolder(Month month, MonthHolder holder, int position) {
        holder.bind(month);
    }
}
