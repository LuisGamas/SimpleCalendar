package io.github.luisgamas.simplecalendar.selection.selectionbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import gamas.dev.simplecalendar.R;
import io.github.luisgamas.simplecalendar.model.Day;
import io.github.luisgamas.simplecalendar.view.CalendarView;
import io.github.luisgamas.simplecalendar.view.customviews.CircleAnimationTextView;

public class MultipleSelectionBarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int VIEW_TYPE_TITLE = 0;
    private final static int VIEW_TYPE_CONTENT = 1;
    private List<SelectionBarItem> items;
    private CalendarView calendarView;
    private ListItemClickListener listItemClickListener;

    public MultipleSelectionBarAdapter(CalendarView calendarView, ListItemClickListener listItemClickListener) {
        items = new ArrayList<>();
        this.calendarView = calendarView;
        this.listItemClickListener = listItemClickListener;
    }

    public void setData(List<SelectionBarItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setListItemClickListener(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_TITLE) {
            return new TitleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_multiple_selection_bar_title, parent, false));
        } else {
            return new ContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_multiple_selection_bar_content, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof SelectionBarTitleItem) {
            return VIEW_TYPE_TITLE;
        } else {
            return VIEW_TYPE_CONTENT;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_TITLE) {
            ((TitleViewHolder) holder).bind(position);
        } else {
            ((ContentViewHolder) holder).bind(position);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public interface ListItemClickListener {
        void onMultipleSelectionListItemClick(Day day);
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {

        final TextView tvTitle;

        public TitleViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }

        public void bind(int position) {
            SelectionBarTitleItem selectionBarTitleItem = (SelectionBarTitleItem) items.get(position);
            tvTitle.setText(selectionBarTitleItem.getTitle());
            tvTitle.setTextColor(calendarView.getSelectionBarMonthTextColor());
        }
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        final CircleAnimationTextView catvDay;

        public ContentViewHolder(View itemView) {
            super(itemView);
            catvDay = (CircleAnimationTextView) itemView.findViewById(R.id.catv_day);
        }

        public void bind(int position) {
            final SelectionBarContentItem selectionBarContentItem = (SelectionBarContentItem) items.get(position);
            catvDay.setText(String.valueOf(selectionBarContentItem.getDay().getDayNumber()));
            catvDay.setTextColor(calendarView.getSelectedDayTextColor());
            catvDay.showAsSingleCircle(calendarView);
            itemView.setOnClickListener(v -> {
                if (listItemClickListener != null) {
                    listItemClickListener.onMultipleSelectionListItemClick(selectionBarContentItem.getDay());
                }
            });
        }
    }
}
