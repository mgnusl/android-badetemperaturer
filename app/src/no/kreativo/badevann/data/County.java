package no.kreativo.badevann.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class County implements Parcelable {

    private String name;
    private ArrayList<Place> listOfPlaces;
    private int id;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*
            Parcelable methods
     */

    public static final Parcelable.Creator<County> CREATOR = new Parcelable.Creator<County>() {
        public County createFromParcel(Parcel source) {
            return new County(source);
        }

        public County[] newArray(int size) {
            return new County[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeList(listOfPlaces);
        dest.writeInt(id);
    }

    public County(Parcel in) {
        name = in.readString();
        in.readList(listOfPlaces, null);
        id = in.readInt();
    }
}
