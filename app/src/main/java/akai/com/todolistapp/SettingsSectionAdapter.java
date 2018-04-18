package akai.com.todolistapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SettingsSectionAdapter  extends RecyclerView.Adapter<SettingsSectionAdapter.ViewHolder> {
    private List<SettingsElement> ListDataset;

    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public View mView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            mTextView = mView.findViewById(R.id.Settings_Element_View_text1);
        }
    }


    public SettingsSectionAdapter(List<SettingsElement> myDataset) {
        ListDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SettingsSectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_element_layout, parent, false);
        SettingsSectionAdapter.ViewHolder vh = new SettingsSectionAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SettingsSectionAdapter.ViewHolder holder,int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(ListDataset.get(position).getName());
//        holder.mView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                ListDataset.get(position).start();
//            }
//        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ListDataset.size();
    }
}
