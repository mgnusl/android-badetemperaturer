package no.kreativo.badevann;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private Button southButton, northButton, westButton, eastButton;

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

        final LatLng shortcutSouth = new LatLng(58.351685, 8.543667);
        final LatLng shortcutNorth = new LatLng(68.596451, 18.747696);
        final LatLng shortcutWest = new LatLng(60.810763, 5.266706);
        final LatLng shortcutEast = new LatLng(59.9402, 10.7629);

        map.setMyLocationEnabled(true);
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
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(place.getGeo_lat(), place.getGeo_long()), 13));
            }
        });

        // Set button listeners
        southButton = (Button)view.findViewById(R.id.sorButton);
        northButton = (Button)view.findViewById(R.id.nordButton);
        westButton = (Button)view.findViewById(R.id.vestButton);
        eastButton = (Button)view.findViewById(R.id.ostButton);

        southButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCameraTo(shortcutSouth, 7);
            }
        });

        northButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCameraTo(shortcutNorth, 5);
            }
        });

        eastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCameraTo(shortcutEast, 7);
            }
        });

        westButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCameraTo(shortcutWest, 6);
            }
        });

        return view;

    }

    private void moveCameraTo(LatLng pos, int zoom) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, zoom));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onDestroyView() {
        try {
            SupportMapFragment fragment = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map));
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.remove(fragment);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
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

            if(!place.getAirTemp().equals(""))
                airTemp.setText(Html.fromHtml("<b>Lufttemperatur: </b>" + place.getAirTemp() + "°C"));
            else
                airTemp.setText(Html.fromHtml("<b>Lufttemperatur: </b> -"));

            if(!place.getWeatherDescription().equals(""))
                weather.setText(Html.fromHtml("<b>Vær: </b>" + place.getWeatherDescription()));
            else
                weather.setText(Html.fromHtml("<b>Vær: </b> - "));

            temp.setText(Integer.toString(place.getWaterTemp()) + "°C");
            kommune.setText(Html.fromHtml("<b>Kommune: </b>" + place.getMunicipality()));
            lastUpdate.setText(Html.fromHtml("<b>Sist oppdatert: </b>" + place.getTimeElapsedFromLastUpdate()));

            return contentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

    }
}
