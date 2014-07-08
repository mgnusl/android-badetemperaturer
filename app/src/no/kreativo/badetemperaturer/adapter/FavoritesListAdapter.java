package no.kreativo.badetemperaturer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import no.kreativo.badetemperaturer.R;
import no.kreativo.badetemperaturer.data.Place;

import java.util.List;

public class FavoritesListAdapter extends ArrayAdapter<Place> {

    private List<Place> favorites;
    private Context context;
    private LayoutInflater inflater;
    private int resource;

    public FavoritesListAdapter(Context context, int resource, List<Place> data) {
        super(context, resource, data);
        favorites = data;
        this.resource = resource;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Log.d("APP", "number of favs " + data.size());

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(resource, null);

            viewHolder = new ViewHolder();
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
            viewHolder.tempTextView = (TextView) convertView.findViewById(R.id.tempTextView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Place place = favorites.get(position);

        viewHolder.nameTextView.setText(place.getShortName());
        viewHolder.tempTextView.setText(Integer.toString(place.getWaterTemp()));

        return convertView;

    }

    static class ViewHolder {
        TextView nameTextView;
        TextView tempTextView;
    }
}
