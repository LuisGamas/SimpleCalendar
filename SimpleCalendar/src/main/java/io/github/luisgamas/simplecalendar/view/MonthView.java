package io.github.luisgamas.simplecalendar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.github.luisgamas.simplecalendar.adapter.DaysAdapter;
import io.github.luisgamas.simplecalendar.model.Month;
import io.github.luisgamas.simplecalendar.utils.Constants;

public class MonthView extends FrameLayout {

    private RecyclerView rvDays;

    public MonthView(@NonNull Context context) {
        super(context);
        init();
    }

    public MonthView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MonthView(@NonNull Context context,
                     @Nullable AttributeSet attrs,
                     @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MonthView(@NonNull Context context,
                     @Nullable AttributeSet attrs,
                     @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        rvDays = new RecyclerView(getContext());
        rvDays.setHasFixedSize(true);
        rvDays.setNestedScrollingEnabled(false);
        rvDays.setLayoutParams(generateLayoutParams());
        final GridLayoutManager manager = new GridLayoutManager(getContext(), Constants.DAYS_IN_WEEK);
        rvDays.setLayoutManager(manager);
        addView(rvDays);
    }

    public DaysAdapter getAdapter() {
        return (DaysAdapter) rvDays.getAdapter();
    }

    public void setAdapter(DaysAdapter adapter) {
        rvDays.setAdapter(adapter);
    }

    public void initAdapter(Month month) {
        getAdapter().setMonth(month);
    }

    private LayoutParams generateLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
