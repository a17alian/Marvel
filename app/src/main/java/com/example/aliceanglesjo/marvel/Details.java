package com.example.aliceanglesjo.marvel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class Details extends AppCompatActivity {
    ImageView bmImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        String heading = intent.getStringExtra("heading");
        TextView textView = (TextView) findViewById(R.id.primary);
        textView.setText(heading);

        String sub = intent.getStringExtra("sub");
        TextView textView2 = (TextView) findViewById(R.id.sub1);
        textView2.setText(sub);

        String sub2 = intent.getStringExtra("sub2");
        TextView textView3 = (TextView) findViewById(R.id.sub2);
        textView3.setText(sub2);

        String storyline = intent.getStringExtra("storyline");
        TextView textViewStory = (TextView) findViewById(R.id.story);
        textViewStory.setText(storyline);

        String imgUrl = intent.getStringExtra("image");
        new DownloadImageTask((ImageView) findViewById(R.id.cardimg))
               .execute(imgUrl);

    }

// IMAGES
private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    public DownloadImageTask(ImageView inbmImg) {
        bmImage = inbmImg;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}

}
