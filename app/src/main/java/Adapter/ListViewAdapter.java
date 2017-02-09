package Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cloud.demo.ajk_riset.demo_cloud.R;

/**
 * Created by AJK-Riset on 2/9/2017.
 */

public class ListViewAdapter extends ArrayAdapter<String> {
    private String[] names;
    private String[] desc;
    private Integer[] imageid;
    private Activity context;

    public ListViewAdapter(Activity context, String[] names, String[] desc) {
        super(context, R.layout.layoutlvadapterpengguna, names);
        this.context = context;
        this.names = names;
        this.desc = desc;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layoutlvadapterpengguna, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.tid);
        TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.tusername);

        textViewName.setText(names[position]);
        textViewDesc.setText(desc[position]);
        return  listViewItem;
    }

}
