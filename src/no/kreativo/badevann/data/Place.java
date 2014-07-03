package no.kreativo.badevann.data;

public class Place {

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

    public String getShortName() {
        return shortName;
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
}
