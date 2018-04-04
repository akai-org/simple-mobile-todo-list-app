package akai.com.todolistapp;

/**
 * Created by Wojtek
 *
 * This class is responsible for data preparation
 */

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Task> ListDataset;

    private boolean multiSelect = false;
    private List<Task> selectedItems = new ArrayList<>();

    private OnLongClickListener onLongClickListener;
    private int selectedPosition = -1;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public TextView date;
        public CheckBox mStar;
        public View mView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            mTextView = mView.findViewById(R.id.Rec_View_text1);
            date = mView.findViewById(R.id.Rec_View_date);
            mStar = mView.findViewById(R.id.priorityStar);
        }
    }

    interface OnLongClickListener {
        void onClick(int position);
    }


    public MyAdapter(List<Task> myDataset) {
        ListDataset = myDataset;
    }

    public void setMultiSelect(boolean multiSelect) {
        this.multiSelect = multiSelect;
        if(!multiSelect) {
            selectedItems = new ArrayList<>();
        }
    }

    public void setOnLongClickListener(OnLongClickListener listener) {
        this.onLongClickListener = listener;
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(ListDataset.get(position).getTitle());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        holder.date.setText(sdf.format(ListDataset.get(position).getDate().getTime()));

        holder.mStar.setChecked(ListDataset.get(position).getPriority());

        if((multiSelect && selectedItems.contains(ListDataset.get(position))) || selectedPosition == position) {
            holder.mView.setBackgroundColor(Color.GRAY);
        }
        else {
            holder.mView.setBackgroundColor(Color.WHITE);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task clickedTask = ListDataset.get(holder.getAdapterPosition());
                if(multiSelect) {
                    if(selectedItems.contains(clickedTask)) {
                        selectedItems.remove(clickedTask);
                        view.setBackgroundColor(Color.WHITE);
                    }
                    else {
                        selectedItems.add(clickedTask);
                        view.setBackgroundColor(Color.GRAY);
                    }
                }
            }
        });

        if(onLongClickListener != null) {
            holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(selectedPosition == -1) {
                        view.setBackgroundColor(Color.GRAY);
                        setSelectedPosition(holder.getAdapterPosition());
                        onLongClickListener.onClick(holder.getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ListDataset.size();
    }

    public List<Task> getSelectedItems() {
        return selectedItems;
    }
}
