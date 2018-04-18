package akai.com.todolistapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SettingsActivityAdapter extends RecyclerView.Adapter<SettingsActivityAdapter.ViewHolder> {
    private List<SettingsSection> ListDataset;

    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;;
        public View mView;
        //public RecyclerView mRecycler;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            mTextView = mView.findViewById(R.id.Settings_Section_View_text1);
            //mRecycler = mView.findViewById(R.id.settings_section_recycler_view);
        }
    }


    public SettingsActivityAdapter(List<SettingsSection> myDataset) {
        ListDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SettingsActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_section_layout, parent, false);
        SettingsActivityAdapter.ViewHolder vh = new SettingsActivityAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SettingsActivityAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(ListDataset.get(position).getSectionName());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ListDataset.size();
    }
}
