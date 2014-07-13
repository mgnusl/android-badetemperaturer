package no.kreativo.badetemperaturer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import no.kreativo.badetemperaturer.adapter.FavoritesListAdapter;
import no.kreativo.badetemperaturer.adapter.HighLowListAdapter;
import no.kreativo.badetemperaturer.data.Place;

import java.util.ArrayList;

public class HighLowFragment extends Fragment {

    private ArrayList<Place> listOfPlaces;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.highlow_fragment, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            listOfPlaces = bundle.getParcelableArrayList("allplaces");
        }

        ListView listView = (ListView) view.findViewById(R.id.highlowListView);
        listView.setAdapter(new HighLowListAdapter(getActivity(), R.layout.row_overview_item, listOfPlaces));

        return view;
    }
}
