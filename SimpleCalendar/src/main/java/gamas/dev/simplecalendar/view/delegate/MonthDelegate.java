package gamas.dev.simplecalendar.view.delegate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gamas.dev.simplecalendar.R;
import gamas.dev.simplecalendar.adapter.DaysAdapter;
import gamas.dev.simplecalendar.adapter.viewholder.MonthHolder;
import gamas.dev.simplecalendar.model.Month;
import gamas.dev.simplecalendar.settings.SettingsManager;

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
