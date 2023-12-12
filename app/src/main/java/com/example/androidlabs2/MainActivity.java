package com.example.androidlabs2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ImageView catImageView;
    private ProgressBar PBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        catImageView = findViewById(R.id.catImage);
        PBar = findViewById(R.id.PBar);

        CatImages catImagesTask = new CatImages();
        catImagesTask.execute("https://cataas.com/cat?json=true");
    }

    private class CatImages extends AsyncTask<String, Integer, String> {
        private Bitmap currentBitmap;
        private boolean newCatPictureSelected = false;

        @Override
        protected String doInBackground(String... args) {
            while (true) {
                try {
                    URL url = new URL(args[0]);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream response = urlConnection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    String result = sb.toString();
                    Log.d("CatImages", "JSON Result: " + result);

                    JSONObject catJson = new JSONObject(result);
                    if (catJson.has("_id")) {
                        String baseImageUrl = "https://cataas.com/cat/";
                        String imageURL = baseImageUrl + catJson.getString("_id");

                        String fileName = imageURL.replaceAll("[^a-zA-Z0-9]", "_") + ".jpg";
                        File imageFile = new File(getFilesDir(), fileName);

                        if (imageFile.exists()) {
                            currentBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                        } else {
                            InputStream imageInputStream = new URL(imageURL).openStream();
                            currentBitmap = BitmapFactory.decodeStream(imageInputStream);

                            FileOutputStream fos = new FileOutputStream(imageFile);
                            currentBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.close();
                        }
                        newCatPictureSelected = true;
                        publishProgress(0);

                        for (int i = 0; i < 100; i++) {
                            try {
                                publishProgress(i);
                                Thread.sleep(30);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        Log.w("CatImages", "Warning: 'url' key not found in JSON response");
                    }

                } catch (Exception e) {
                    Log.e("CatImages", "Error in doInBackground", e);
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            PBar.setProgress(values[0]);
            if (newCatPictureSelected) {
                catImageView.setImageBitmap(currentBitmap);
                newCatPictureSelected = false;
            }
        }
    }
}