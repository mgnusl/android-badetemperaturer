package no.kreativo.badevann;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import no.kreativo.badevann.data.County;
import no.kreativo.badevann.data.Place;

import java.util.ArrayList;

public class KartFragment extends Fragment {

    private ArrayList<County> listOfCounties;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            listOfCounties = bundle.getParcelableArrayList("counties");
        }

        // Get a handle to the Map Fragment
        GoogleMap map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        LatLng defaultPosition = new LatLng(61.7150, 9.1753);

        map.setMyLocationEnabled(false);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultPosition, 5));

        for (County c : listOfCounties) {
            for (Place p : c.getListOfPlaces()) {
                LatLng place = new LatLng(p.getGeo_lat(), p.getGeo_long());
                map.addMarker(new MarkerOptions()
                                .title(p.getShortName())
                                .snippet(p.getLongName())
                                .position(place)
                );
            }
        }

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
