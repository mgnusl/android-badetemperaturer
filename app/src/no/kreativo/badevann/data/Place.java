package no.kreativo.badevann.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Place implements Parcelable {

    private int waterTemp;
    private String shortName, longName, municipality, weatherDescription, lastUpdated, placeID, airTemp;
    private double geo_lat, geo_long;

    public Place() {
    }

    public void setWaterTemp(int waterTemp) {
        this.waterTemp = waterTemp;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public void setAirTemp(String airTemp) {
        this.airTemp = airTemp;
    }

    public void setGeo_lat(double geo_lat) {
        this.geo_lat = geo_lat;
    }

    public void setGeo_long(double geo_long) {
        this.geo_long = geo_long;
    }

    public int getWaterTemp() {
        return waterTemp;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }

    public String getMunicipality() {
        return municipality;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public String getPlaceID() {
        return placeID;
    }

    public String getAirTemp() {
        return airTemp;
    }

    public double getGeo_lat() {
        return geo_lat;
    }

    public double getGeo_long() {
        return geo_long;
    }

    @Override
    public String toString() {
        return "Place{" +
                "waterTemp=" + waterTemp +
                ", shortName='" + shortName + '\'' +
                ", longName='" + longName + '\'' +
                ", municipality='" + municipality + '\'' +
                ", weatherDescription='" + weatherDescription + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", placeID='" + placeID + '\'' +
                ", airTemp='" + airTemp + '\'' +
                ", geo_lat=" + geo_lat +
                ", geo_long=" + geo_long +
                '}';
    }

    /*
            Parcelable methods
     */

    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        public Place createFromParcel(Parcel source) {
            return new Place(source);
        }

        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(waterTemp);
        dest.writeString(shortName);
        dest.writeString(longName);
        dest.writeString(municipality);
        dest.writeString(weatherDescription);
        dest.writeString(lastUpdated);
        dest.writeString(placeID);
        dest.writeString(airTemp);
        dest.writeDouble(geo_lat);
        dest.writeDouble(geo_long);
    }

    public Place(Parcel in) {
        waterTemp = in.readInt();
        shortName = in.readString();
        longName = in.readString();
        municipality = in.readString();
        weatherDescription = in.readString();
        lastUpdated = in.readString();
        placeID = in.readString();
        airTemp = in.readString();
        geo_lat = in.readDouble();
        geo_long = in.readDouble();
    }
}
