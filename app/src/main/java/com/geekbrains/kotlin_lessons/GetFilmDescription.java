package com.geekbrains.kotlin_lessons;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GetFilmDescription {

    private static final Gson gson = new Gson();

    public static void getFilm() {
        try {
            URL url = new URL("https://api.themoviedb.org/3/movie/550?api_key="+BuildConfig.FILM_API_KEY);
            //Handler handler = new Handler(Looper.getMainLooper());
            new Thread(() -> {
                HttpsURLConnection urlConnection;
                try {
                    urlConnection = (HttpsURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(1000);
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String result = getLines(in);

                    //FilmRequest filmRequest = gson.fromJson(result, FilmRequest.class);
                    System.out.println(result);
                    //handler.post(() -> parent.displayWeather(weatherRequest));
                    urlConnection.disconnect();
                } catch (IOException e) {
                    //handler.post(parent::showError);
                    e.printStackTrace();
                }
            }).start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static String getLines(BufferedReader reader) {
        StringBuilder rawData = new StringBuilder(1024);
        String tempVariable;

        while (true) {
            try {
                tempVariable = reader.readLine();
                if (tempVariable == null) break;
                rawData.append(tempVariable).append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rawData.toString();
    }
}
