package com.example.rezepteapp.weather;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class WeatherCall {

    private LocationManager locationManager;
    private final Context context;
    private final static String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET
    };
    private double lat;
    private double lon;
    private Weather currentWeather = new Weather();
    private WeatherCallback weatherCallback;
    private Thread weatherThread;
    private final Object pauseLock = new Object();
    private boolean isPaused = false;

    public WeatherCall(Context context, WeatherCallback weatherCallback) {
        this.context = context;
        this.weatherCallback = weatherCallback;
        init();
    }

    private void init() {
        locationManager = context.getSystemService(LocationManager.class);
        if (checkPermissions()) {
            updateLocation();
        } else {
            requestPermissions();
        }
    }

    private void updateLocation() {
        LocationListener locationListener = location -> {
            lat = location.getLatitude();
            lon = location.getLongitude();
            startThread();
        };
        if (checkPermissions()) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1, locationListener);
        }
        }

    private boolean checkPermissions() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                (Activity) context,
                REQUIRED_PERMISSIONS,
                1
        );
    }

    public void startThread() {
        weatherThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (pauseLock) {
                    while (isPaused) {
                        try {
                            pauseLock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
                getWeather();
                try {
                    Thread.sleep(10000); // Update every 10 seconds
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        weatherThread.start();
    }

    public void pauseThread() {
        synchronized (pauseLock) {
            isPaused = true;
        }
    }

    public void resumeThread() {
        synchronized (pauseLock) {
            isPaused = false;
            pauseLock.notifyAll();
        }
    }


    public Weather getWeather() {

        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat +
                "&lon=" + lon + "&appid=" + "API Key";

        try {

            HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {

                System.out.println("Response Code is not ok!");

                return null;

            } else {

                String response = new BufferedReader(new InputStreamReader(connection.getInputStream())).lines().collect(Collectors.joining("\n"));
                JSONObject json = new JSONObject(response);
                connection.disconnect();

                currentWeather.setLat(lat);
                currentWeather.setLon(lon);

                if (json.has("main")) {
                    JSONObject main = json.getJSONObject("main");
                    int temp = (int) Math.round(main.getDouble("temp") - 273.15);
                    currentWeather.setTemp(temp + "°C");
                }

                if (json.has("weather")) {
                    JSONObject weather = json.getJSONArray("weather").getJSONObject(0);
                    String icon = weather.getString("icon");
                    currentWeather.setIcon(getWeatherIcon(icon));
                }

                if (weatherCallback != null) {
                    weatherCallback.onWeatherCallback(currentWeather);
                }

                return currentWeather;
            }

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private Bitmap getWeatherIcon(String iconCode) {
        String iconUrl = "https://openweathermap.org/img/wn/" + iconCode + ".png";
        try {
            URL url = new URL(iconUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }
}
