package no.kreativo.badevann;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import com.astuetz.PagerSlidingTabStrip;
import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;
import eu.inmite.android.lib.dialogs.SimpleDialogFragment;
import no.kreativo.badevann.adapter.ViewPagerAdapter;
import no.kreativo.badevann.data.County;
import no.kreativo.badevann.data.Place;
import no.kreativo.badevann.utils.Utils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private ArrayList<County> listOfCounties;
    private ArrayList<Place> listOfFavorites;
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        listOfCounties = new ArrayList<County>();
        listOfFavorites = new ArrayList<Place>();

        new AsyncHandleXML().execute("http://om.yr.no/badetemperatur/badetemperatur.xml");

    }

    private class AsyncHandleXML extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Working...");

            try {
                URL url = new URL(params[0]);

                DateTimeFormatter parser = ISODateTimeFormat.dateTimeNoMillis();

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
                            county.setId(Integer.parseInt(xpp.getAttributeValue(null, "id")));
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
                            DateTime dateTime = parser.parseDateTime(xpp.nextText() + "+0200");
                            place.setLastUpdated(dateTime);
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
            initializeFragments();
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Henter data...");
            pDialog.show();
        }
    }

    public void initializeFragments() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("counties", listOfCounties);

        Bundle favBundle = new Bundle();
        favBundle.putParcelableArrayList("favorites", listOfFavorites);

        List<Fragment> fragments = new ArrayList<Fragment>();

        OverviewListFragment listFragment = new OverviewListFragment();
        //KartFragment kartFragment = new KartFragment();
        FavoritesFragment favoritesFragment = new FavoritesFragment();
        listFragment.setArguments(bundle);
        //kartFragment.setArguments(bundle);
        favoritesFragment.setArguments(favBundle);

        //fragments.add(kartFragment);
        fragments.add(listFragment);
        fragments.add(favoritesFragment);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new ViewPagerAdapter(fragmentManager, fragments));
        viewPager.setCurrentItem(1);

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(viewPager);

        //Style tabs
        tabs.setIndicatorColor(getResources().getColor(R.color.viewpager_indicator_color));
        tabs.setShouldExpand(false);

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }

    }

    private void showAboutPopup() {
        SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
                .setTitle(R.string.about)
                .setMessage(R.string.about_message)
                .setPositiveButtonText("Lukk")
                .show();


    }

    private void showLicensesPopup() {
        final Notices notices = new Notices();
        notices.addNotice(new Notice("LicensesDialog", "https://github.com/PSDev/LicensesDialogg", "Philip Schiffer", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("HeaderListView", "https://github.com/applidium/HeaderListView", "applidium", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("android-styled-dialogs", "https://github.com/inmite/android-styled-dialogs", "inmite", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("discreet-app-rate", "https://github.com/PomepuyN/discreet-app-rate", "PomepuyN", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Joda-Time", "http://www.joda.org/joda-time/", "JodaOrg", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("PagerSlidingTabStrip", "https://github.com/astuetz/PagerSlidingTabStrip", "astuetz", new ApacheSoftwareLicense20()));
        new LicensesDialog(this, notices, false, false).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                new AsyncHandleXML().execute("http://om.yr.no/badetemperatur/badetemperatur.xml");
                return true;
            case R.id.action_about:
                showAboutPopup();
            case R.id.action_licenses:
                showLicensesPopup();
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
