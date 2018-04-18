package akai.com.todolistapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SettingsActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> myList;
    private final int SECTION = 0, ELEMENT = 1;

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public View mView;

        public ViewHolder1(View v) {
            super(v);
            mView = v;
            mTextView = mView.findViewById(R.id.Settings_Element_View_text1);
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public View mView;

        public ViewHolder2(View v) {
            super(v);
            mView = v;
            mTextView = mView.findViewById(R.id.Settings_Section_View_text1);
        }
    }


    public SettingsActivityAdapter(List<Object> myDataset) {
        myList = myDataset;
    }

    @Override
    public int getItemViewType(int position) {
        if (myList.get(position) instanceof SettingsElement) {
            return ELEMENT;
        } else if (myList.get(position) instanceof String) {
            return SECTION;
        }
        return -1;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch(viewType){
            case ELEMENT:
                View v1 = inflater.inflate(R.layout.settings_element_layout, parent, false);
                vh = new ViewHolder1(v1);
                break;
            case SECTION:
                View v2 = inflater.inflate(R.layout.settings_section_layout, parent, false);
                vh = new ViewHolder2(v2);
                break;
            default:
                View v = inflater.inflate(R.layout.settings_element_layout, parent, false);
                vh = new ViewHolder1(v);
                break;
        }

        return vh;
    }

    private void configureViewHolder1(ViewHolder1 vh1, int position) {
        final SettingsElement element = (SettingsElement) myList.get(position);
        if (element != null) {
            vh1.mTextView.setText(element.getName());
            vh1.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    element.start();
                }
            });
        }
    }

    private void configureViewHolder2(ViewHolder2 vh2, int position) {
        String section = (String) myList.get(position);
        if (section != null) {
            vh2.mTextView.setText(section);
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("VH", String.valueOf(position));
        Log.d("VH", String.valueOf(holder.getItemViewType()));
        switch (holder.getItemViewType()) {
            case ELEMENT:
                ViewHolder1 vh1 = (ViewHolder1) holder;
                configureViewHolder1(vh1, position);
                break;
            case SECTION:
                ViewHolder2 vh2 = (ViewHolder2) holder;
                configureViewHolder2(vh2, position);
                break;
            default:
                ViewHolder1 vh = (ViewHolder1) holder;
                configureViewHolder1(vh, position);
                break;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.myList.size();
    }
}
