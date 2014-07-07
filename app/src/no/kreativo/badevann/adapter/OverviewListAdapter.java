package no.kreativo.badevann.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.applidium.headerlistview.SectionAdapter;
import no.kreativo.badevann.R;
import no.kreativo.badevann.data.County;
import no.kreativo.badevann.data.Place;

import java.util.ArrayList;

public class OverviewListAdapter extends SectionAdapter {

    private Context context;
    private ArrayList<County> data;

    public OverviewListAdapter(Context context, ArrayList<County> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int numberOfSections() {
        return data.size();
    }

    @Override
    public int numberOfRows(int section) {
        return data.get(section).getListOfPlaces().size();
    }

    @Override
    public Object getRowItem(int section, int row) {
        return null;
    }

    @Override
    public boolean hasSectionHeaderView(int section) {
        return true;
    }

    @Override
    public View getRowView(int section, int row, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_overview_item, parent, false);
        }

        Place place = data.get(section).getListOfPlaces().get(row);
        TextView placeTextView = (TextView)convertView.findViewById(R.id.nameTextView);
        placeTextView.setText(place.getShortName());

        TextView tempTextView = (TextView)convertView.findViewById(R.id.tempTextView);
        tempTextView.setText(Integer.toString(place.getWaterTemp()) + "°C");

        return convertView;

    }

    @Override
    public int getSectionHeaderViewTypeCount() {
        return 2;
    }

    @Override
    public int getSectionHeaderItemViewType(int section) {
        return 1;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(context.getResources().getLayout(R.layout.header_row), null);
        }

        TextView text = (TextView) convertView.findViewById(R.id.headerTextView);
        text.setText(data.get(section).getName());

        return convertView;
    }

    @Override
    public void onRowItemClick(AdapterView<?> parent, View view, int section, int row, long id) {
        super.onRowItemClick(parent, view, section, row, id);
        Log.d("APP", "Section: " + Integer.toString(section) + ". Row: " + Integer.toString(row));
    }

}
