package no.kreativo.badevann;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import no.kreativo.badevann.data.County;
import no.kreativo.badevann.data.Place;

import java.util.ArrayList;
import java.util.HashMap;

public class KartFragment extends Fragment {

    private ArrayList<County> listOfCounties;
    private HashMap<Marker, Place> markerMap;
    private GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            listOfCounties = bundle.getParcelableArrayList("counties");
        }

        markerMap = new HashMap<Marker, Place>();

        // Get a handle to the Map Fragment
        map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        LatLng defaultPosition = new LatLng(61.7150, 9.1753);

        map.setMyLocationEnabled(false);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultPosition, 5));
        map.setInfoWindowAdapter(new MyInfoWindowAdapter());

        for (County c : listOfCounties) {
            for (Place p : c.getListOfPlaces()) {
                LatLng place = new LatLng(p.getGeo_lat(), p.getGeo_long());
                Marker marker = map.addMarker(new MarkerOptions()
                        .title(p.getShortName())
                        .snippet(p.getLongName())
                        .position(place));
                markerMap.put(marker, p);

            }
        }

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Place place = markerMap.get(marker);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(place.getGeo_lat(), place.getGeo_long()), 14));
            }
        });

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onDestroyView ()
    {
        try{
            SupportMapFragment fragment = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map));
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.remove(fragment);
            ft.commit();
        }catch(Exception e){
        }
        super.onDestroyView();
    }

    private class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View contentsView;

        MyInfoWindowAdapter() {
            contentsView = getActivity().getLayoutInflater().inflate(R.layout.maps_info_window, null);
        }

        @Override
        public View getInfoContents(Marker marker) {
            Place place = markerMap.get(marker);
            TextView shortName = (TextView) contentsView.findViewById(R.id.windowPlaceShort);
            TextView longName = (TextView) contentsView.findViewById(R.id.windowPlaceLong);
            TextView airTemp = (TextView) contentsView.findViewById(R.id.windowAirTemp);
            TextView temp = (TextView) contentsView.findViewById(R.id.windowTemp);
            TextView weather = (TextView) contentsView.findViewById(R.id.windowWeather);
            TextView kommune = (TextView) contentsView.findViewById(R.id.windowKommune);
            TextView lastUpdate = (TextView) contentsView.findViewById(R.id.windowLastUpdate);
            shortName.setText(place.getShortName());
            longName.setText(place.getLongName());
            airTemp.setText(Html.fromHtml("<b>Lufttemperatur: </b>" + place.getAirTemp() + "°C"));
            temp.setText(Integer.toString(place.getWaterTemp()) + "°C");
            weather.setText(Html.fromHtml("<b>Vær: </b>" + place.getWeatherDescription()));
            kommune.setText(Html.fromHtml("<b>Kommune: </b>" + place.getMunicipality()));
            lastUpdate.setText(Html.fromHtml("<b>Sist oppdatert: </b>" + place.getLastUpdated()));

            return contentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

    }
}
