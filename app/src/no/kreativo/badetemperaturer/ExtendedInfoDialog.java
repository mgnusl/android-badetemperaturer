package no.kreativo.badetemperaturer;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import no.kreativo.badetemperaturer.data.Place;
import no.kreativo.badetemperaturer.database.DatabaseHelper;

public class ExtendedInfoDialog extends Dialog {

    private Place place;
    private Activity activity;

    public ExtendedInfoDialog(Activity a, Place place) {
        super(a);
        activity = a;
        this.place = place;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.overview_info_window);

        ((TextView) findViewById(R.id.windowPlaceShort)).setText(place.getShortName());
        ((TextView) findViewById(R.id.windowPlaceLong)).setText(place.getLongName());

        if (!place.getAirTemp().equals(""))
            ((TextView) findViewById(R.id.windowAirTemp)).setText(Html.fromHtml("<b>Lufttemperatur: </b>" + place.getAirTemp() + "°C"));
        else
            ((TextView) findViewById(R.id.windowAirTemp)).setText(Html.fromHtml("<b>Lufttemperatur: </b> -"));

        if (!place.getWeatherDescription().equals(""))
            ((TextView) findViewById(R.id.windowWeather)).setText(Html.fromHtml("<b>Vær: </b>" + place.getWeatherDescription()));
        else
            ((TextView) findViewById(R.id.windowWeather)).setText(Html.fromHtml("<b>Vær: </b> - "));

        ((TextView) findViewById(R.id.windowTemp)).setText(Integer.toString(place.getWaterTemp()) + "°C");
        ((TextView) findViewById(R.id.windowKommune)).setText(Html.fromHtml("<b>Kommune: </b>" + place.getMunicipality()));
        ((TextView) findViewById(R.id.windowLastUpdate)).setText(Html.fromHtml("<b>Sist oppdatert: </b>" + place.getTimeElapsedFromLastUpdate()));
        ((Button) findViewById(R.id.closeButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // Favorites
        final DatabaseHelper dbHelper = DatabaseHelper.getInstance(activity.getApplicationContext());

        final ImageView favButton = (ImageView) findViewById(R.id.favButton);
        if (place.isFavorite())
            favButton.setImageResource(R.drawable.ic_action_rating_important);
        else
            favButton.setImageResource(R.drawable.ic_action_rating_not_important);

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (place.isFavorite()) {
                    dbHelper.removeFavorite(place);
                    favButton.setImageResource(R.drawable.ic_action_rating_not_important);
                    place.setFavorite(false);
                } else {
                    dbHelper.addFavorite(place);
                    favButton.setImageResource(R.drawable.ic_action_rating_important);
                    place.setFavorite(true);
                }

            }
        });

    }


}
