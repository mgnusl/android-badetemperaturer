package no.kreativo.badetemperaturer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import no.kreativo.badetemperaturer.adapter.FavoritesListAdapter;
import no.kreativo.badetemperaturer.data.County;
import no.kreativo.badetemperaturer.data.Place;
import no.kreativo.badetemperaturer.database.DatabaseHelper;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    private ArrayList<Place> listOfFavorites;
    private ArrayList<County> listOfCounties;
    private DatabaseHelper dbHelper;
    private FavoritesListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_fragment, container, false);

        dbHelper = DatabaseHelper.getInstance(getActivity().getApplicationContext());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            listOfFavorites = bundle.getParcelableArrayList("favorites");
            listOfCounties = bundle.getParcelableArrayList("counties");
        }

        adapter = new FavoritesListAdapter(getActivity(), R.layout.row_overview_item, listOfFavorites);
        ListView listView = (ListView) view.findViewById(R.id.favoritesListView);
        listView.setAdapter(adapter);

        if (listOfFavorites.size() > 0) {
            TextView empty1 = (TextView) view.findViewById(R.id.empty1);
            TextView empty2 = (TextView) view.findViewById(R.id.empty2);
            empty1.setVisibility(View.GONE);
            empty2.setVisibility(View.GONE);
        }

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh listOfFavorites
            listOfFavorites.clear();
            for (String s : dbHelper.getAllFavorites()) {
                for (County c : listOfCounties) {
                    for (Place p : c.getListOfPlaces()) {
                        if (p.getShortName().equals(s)) {
                            p.setFavorite(true);
                            listOfFavorites.add(p);
                        }
                    }
                }
            }
        }
    }
}
