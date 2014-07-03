package no.kreativo.badevann.data;

import java.util.ArrayList;

public class County {

    private String name;
    private ArrayList<Place> listOfPlaces;

    public County() {
        listOfPlaces = new ArrayList<Place>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Place> getListOfPlaces() {
        return listOfPlaces;
    }

    public void addPlace(Place p) {
        listOfPlaces.add(p);
    }
}
