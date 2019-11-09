package com.example.tourviet;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.os.AsyncTask;
import java.io.*;

public class DownloadImagesTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;

    public DownloadImagesTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap myImage = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            myImage = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myImage;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
