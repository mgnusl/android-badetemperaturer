package no.kreativo.badetemperaturer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import no.kreativo.badetemperaturer.data.Place;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HighLowFragment extends Fragment {

    private ArrayList<Place> listOfPlaces;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.highlow_fragment, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            listOfPlaces = bundle.getParcelableArrayList("allplaces");
        }

        Collections.sort(listOfPlaces);

        ((TextView) view.findViewById(R.id.cold1)).setText(listOfPlaces.get(0).getShortName());
        ((TextView) view.findViewById(R.id.cold2)).setText(listOfPlaces.get(1).getShortName());
        ((TextView) view.findViewById(R.id.cold3)).setText(listOfPlaces.get(2).getShortName());
        ((TextView) view.findViewById(R.id.warm1)).setText(listOfPlaces.get(listOfPlaces.size()-1).getShortName());
        ((TextView) view.findViewById(R.id.warm2)).setText(listOfPlaces.get(listOfPlaces.size()-2).getShortName());
        ((TextView) view.findViewById(R.id.warm3)).setText(listOfPlaces.get(listOfPlaces.size()-3).getShortName());

        ((TextView) view.findViewById(R.id.cold1Temp)).setText(Integer.toString(listOfPlaces.get(0).getWaterTemp()) + "°C");
        ((TextView) view.findViewById(R.id.cold2Temp)).setText(Integer.toString(listOfPlaces.get(1).getWaterTemp()) + "°C");
        ((TextView) view.findViewById(R.id.cold3Temp)).setText(Integer.toString(listOfPlaces.get(2).getWaterTemp()) + "°C");
        ((TextView) view.findViewById(R.id.warm1Temp)).setText(Integer.toString(listOfPlaces.get(listOfPlaces.size()-1).getWaterTemp()) + "°C");
        ((TextView) view.findViewById(R.id.warm2Temp)).setText(Integer.toString(listOfPlaces.get(listOfPlaces.size()-2).getWaterTemp()) + "°C");
        ((TextView) view.findViewById(R.id.warm3Temp)).setText(Integer.toString(listOfPlaces.get(listOfPlaces.size()-3).getWaterTemp()) + "°C");

        return view;
    }
}
