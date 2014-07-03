package no.kreativo.badevann;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import no.kreativo.badevann.data.County;
import no.kreativo.badevann.data.Place;
import no.kreativo.badevann.utils.Utils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private ArrayList<County> listOfCounties;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        listOfCounties = new ArrayList<County>();

        new AsyncHandleXML().execute("http://om.yr.no/badetemperatur/badetemperatur.xml");

    }

    private class AsyncHandleXML extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Working...");

            try {
                URL url = new URL(params[0]);

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                // Get the XML from an input stream
                xpp.setInput(Utils.getInputStream(url), "UTF_8");

                Place place = new Place();
                County county = new County();

                // Returns the type of current event
                int eventType = xpp.getEventType();

                // Loop through all elements as long as they are not END_DOCUMENT
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {

                        if (xpp.getName().equalsIgnoreCase("county")) {
                            county = new County();
                            county.setName(xpp.getAttributeValue(null, "name"));
                        } else if (xpp.getName().equalsIgnoreCase("place")) {
                            // Inside place
                            place = new Place();
                            place.setPlaceID(xpp.getAttributeValue(null, "id"));
                            place.setShortName(xpp.getAttributeValue(null, "shortname"));
                            place.setLongName(xpp.getAttributeValue(null, "longname"));
                        } else if (xpp.getName().equalsIgnoreCase("municipality")) {
                            place.setMunicipality(xpp.nextText());
                        } else if (xpp.getName().equalsIgnoreCase("geo_lat")) {
                            place.setGeo_lat(Double.parseDouble(xpp.nextText()));
                        } else if (xpp.getName().equalsIgnoreCase("geo_long")) {
                            place.setGeo_long(Double.parseDouble(xpp.nextText()));
                        } else if (xpp.getName().equalsIgnoreCase("temperature")) {
                            place.setAirTemp(xpp.getAttributeValue(null, "air"));
                            place.setWaterTemp(Integer.parseInt(xpp.getAttributeValue(null, "water")));
                        } else if (xpp.getName().equalsIgnoreCase("weather")) {
                            if (!xpp.isEmptyElementTag()) {
                                place.setWeatherDescription(xpp.nextText());
                            }

                        } else if (xpp.getName().equalsIgnoreCase("updated")) {
                            place.setLastUpdated(xpp.nextText());
                        }

                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("county")) {
                        listOfCounties.add(county);
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("place")) {
                        county.addPlace(place);
                    }

                    eventType = xpp.next(); //move to next element

                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            pDialog.dismiss();
            int counter = 0;
            for(County c : listOfCounties) {
                for(Place p : c.getListOfPlaces())
                    counter++;
            }

            Log.d("APP", "Number of counties " + listOfCounties.size());
            Log.d("APP", "Number of places " + counter);
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Working...");
            pDialog.show();
        }
    }
}
