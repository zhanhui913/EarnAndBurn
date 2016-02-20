package com.perzhan.earnandburn.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.perzhan.earnandburn.Model.Base;
import com.perzhan.earnandburn.R;
import com.zhan.library.CircularView;

import java.util.List;

/**
 * Created by zhanyap on 2016-01-28.
 */
public class EarnGridViewAdapter extends ArrayAdapter<Base> {

    private Activity activity;
    private List<Base> list;

    static class ViewHolder {
        public TextView name;
        public CircularView circularView;
    }

    public EarnGridViewAdapter(Activity activity, List<Base> list) {
        super(activity, R.layout.item_base, list);
        this.activity = activity;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Avoid un-necessary calls to findViewById() on each row, which is expensive!
        ViewHolder viewHolder;

        /*
         * If convertView is not null, we can reuse it directly, no inflation required!
         * We only inflate a new View when the convertView is null.
         */
        if (convertView == null) {

            // Create a ViewHolder and store references to the two children views
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_grid_base, parent, false);

            viewHolder.name = (TextView) convertView.findViewById(R.id.baseName);
            viewHolder.circularView = (CircularView) convertView.findViewById(R.id.baseIcon);

            // The tag can be any Object, this just happens to be the ViewHolder
            convertView.setTag(viewHolder);
        }else {
            // Get the ViewHolder back to get fast access to the Views
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // getting monthReport data for the row
        Base item = list.get(position);

        viewHolder.name.setText(item.getName());
        viewHolder.circularView.setCircleColor(R.color.colorAccent);
        viewHolder.circularView.setIconResource(R.drawable.ic_person);

        return convertView;
    }
}