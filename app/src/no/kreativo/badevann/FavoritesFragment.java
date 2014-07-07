package no.kreativo.badevann;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import no.kreativo.badevann.adapter.FavoritesListAdapter;
import no.kreativo.badevann.data.Place;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    private ArrayList<Place> favorites;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_fragment, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            favorites = bundle.getParcelableArrayList("favorites");
        }

        ListView listView = (ListView)view.findViewById(R.id.favoritesListView);
        listView.setAdapter(new FavoritesListAdapter(getActivity(), R.layout.row_overview_item, favorites));

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
