package com.example.steve.weathermusicapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.steve.weathermusicapp.Common.Common;
import com.example.steve.weathermusicapp.Helper.Helper;
import com.example.steve.weathermusicapp.Model.OpenWeatherMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;

/**
 * Created by Steve on 2/8/2017.
 */

public class WeatherMusic extends AppCompatActivity implements LocationListener {
    TextView txtCity, txtLastUpdate, txtDescription, txtHumidity, txtTime, txtCelsius;
    Button txtMood, txtPlay, txtRefresh;
    ImageView imageView, imageViewLogo;
    String wDesctiption;
    LinearLayout lL;

    String zip, cc;

    SetLocation s;


    GPSTracker gps;
    LocationManager locationManager;
    LocationListener locationListener;
    String provider;
    static double lat;
    static double lng;
    OpenWeatherMap openWeatherMap = new OpenWeatherMap();

    Location location;
    int MY_PERMISSION = 0;

    MediaPlayer mPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            zip = extras.getString("zip");
            cc = extras.getString("cc");
        }
        setContentView(R.layout.activity_play_audio_example);

        //Control
        txtCity = (TextView) findViewById(R.id.txtCity);

        // txtLastUpdate = (TextView) findViewById(R.id.txtLastUpdate);
        txtDescription = (TextView) findViewById(R.id.txtDescription);

        // txtHumidity = (TextView) findViewById(R.id.txtHumidity);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtCelsius = (TextView) findViewById(R.id.txtCelsius);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageViewLogo = (ImageView) findViewById(R.id.imageViewLogo);




        //implementing LocationListener, Get Coordinates
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(WeatherMusic.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_PERMISSION);

        }

         location = locationManager.getLastKnownLocation(provider);

        if(location != null){
            lat = location.getLatitude();
            lng = location.getLongitude();
        }
        if (location == null) {
            Log.e("TAG", "No Location");
            gps = new GPSTracker(WeatherMusic.this);

            // check if GPS enabled
            if(gps.canGetLocation()){

                lat = gps.getLatitude();
                lng = gps.getLongitude();


            }else{
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }
        }


        if (cc != null && zip != null){
            new GetWeather().execute(Common.apiRequestZip( zip, cc ));

        }else {
            new GetWeather().execute(Common.apiRequest(String.valueOf(lat), String.valueOf(lng)));
        }

        txtMood = (Button) findViewById(R.id.txtMood);
        txtMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickMood = new Intent(WeatherMusic.this, MainActivity.class);
                startActivity(pickMood);
            }
        });

         txtPlay = (Button) findViewById(R.id.txtPlay);
        txtPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mPlayer.start();
            }
        });


        Typeface typeFace=Typeface.createFromAsset(getAssets(),"GeosansLight.ttf");
        txtCity.setTypeface(typeFace);
        txtPlay.setTypeface(typeFace);
        txtMood.setTypeface(typeFace);
        txtCelsius.setTypeface(typeFace);
        txtDescription.setTypeface(typeFace);
        txtTime.setTypeface(typeFace);




    }

    private class GetWeather extends AsyncTask<String, Void, String> {

        ProgressDialog pd = new ProgressDialog(WeatherMusic.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String stream = null;
            String urlString = params[0];

            Helper http = new Helper();
            stream = http.getHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.contains("Error: Not found city")) {
                pd.dismiss();
                return;
            }
            Gson gson = new Gson();
            Type mType = new TypeToken<OpenWeatherMap>() {
            }.getType();
            openWeatherMap = gson.fromJson(s, mType);
            pd.dismiss();

            txtCity.setText(String.format("%s,%s", openWeatherMap.getName(), openWeatherMap.getSys().getCountry()));
//            txtLastUpdate.setText(String.format("Last Updated: %s", Common.getDateNow()));
            txtDescription.setText(String.format("%s", openWeatherMap.getWeather().get(0).getDescription()));
            txtTime.setText(String.format("%s/%s", Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunrise()), Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunrise())));
            txtCelsius.setText(String.format("%.2f °F", openWeatherMap.getMain().getTemp()));
            Picasso.with(WeatherMusic.this)
                    .load(Common.getImage(openWeatherMap.getWeather().get(0).getIcon()))
                    .into(imageView);

            wDesctiption = openWeatherMap.getWeather().get(0).getDescription();

            if (wDesctiption.equals("clear sky")) {
                mPlayer = MediaPlayer.create(WeatherMusic.this, R.raw.happy);
               // mPlayer.start();
                LinearLayout l = (LinearLayout) findViewById(R.id.activity_play_audio_example);
                l.setBackgroundResource(R.drawable.clear);

            } else if ( wDesctiption.equals("rain") || wDesctiption.equals("shower rain")) {
                mPlayer = MediaPlayer.create(WeatherMusic.this, R.raw.sad);
                //mPlayer.start();
                LinearLayout l = (LinearLayout) findViewById(R.id.activity_play_audio_example);
                l.setBackgroundResource(R.drawable.rain);

            } else if ( wDesctiption.equals("thunderstorm") ) {
                mPlayer = MediaPlayer.create(WeatherMusic.this, R.raw.angry);
                //mPlayer.start();
                LinearLayout l = (LinearLayout) findViewById(R.id.activity_play_audio_example);
                l.setBackgroundResource(R.drawable.thunderstorm);

            } else if (wDesctiption.equals("snow")) {
                mPlayer = MediaPlayer.create(WeatherMusic.this, R.raw.sad);
                //mPlayer.start();
                LinearLayout l = (LinearLayout) findViewById(R.id.activity_play_audio_example);
                l.setBackgroundResource(R.drawable.snow);

            } else if (wDesctiption.equals("few clouds") || wDesctiption.equals("scattered clouds") || wDesctiption.equals("broken clouds")) {
                mPlayer = MediaPlayer.create(WeatherMusic.this, R.raw.relaxed);
                //mPlayer.start();
                LinearLayout l = (LinearLayout) findViewById(R.id.activity_play_audio_example);
                l.setBackgroundResource(R.drawable.cloudy);

            }  else if (wDesctiption.equals("overcast clouds")) {
                mPlayer = MediaPlayer.create(WeatherMusic.this, R.raw.relaxed);
                //mPlayer.start();
                LinearLayout l = (LinearLayout) findViewById(R.id.activity_play_audio_example);
                l.setBackgroundResource(R.drawable.overcast);

            }else {
                mPlayer = MediaPlayer.create(WeatherMusic.this, R.raw.sad);
              //  mPlayer.start();

            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WeatherMusic.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_PERMISSION);
        }
        locationManager.removeUpdates(this);
        mPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WeatherMusic.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_PERMISSION);
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);

    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();

        new GetWeather().execute(Common.apiRequest(String.valueOf(lat), String.valueOf(lng)));

    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_audio_example, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_static:
                Intent i = new Intent(WeatherMusic.this, SetLocation.class);
                startActivity(i);



                return true;
            case R.id.action_dynamic:
                lat = location.getLatitude();
                lng = location.getLongitude();

                new GetWeather().execute(Common.apiRequest(String.valueOf(lat), String.valueOf(lng)));
                return true;
            case R.id.menu_static:
                Intent ii = new Intent(WeatherMusic.this, SetLocation.class);
                startActivity(ii);

                return true;
            case R.id.menu_dynamic:
                lat = location.getLatitude();
                lng = location.getLongitude();

                new GetWeather().execute(Common.apiRequest(String.valueOf(lat), String.valueOf(lng)));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
