package com.example.favoritesongs_kt_mf;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {
    private static final String ACTIVITY_TAG = "AddItemActivity";
    private RecordManager recMgr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recMgr = new RecordManager(this);
        setContentView(R.layout.activity_add);
    }

    public void addItem(View v) {
        Log.w(ACTIVITY_TAG, "addItem method");

        EditText nameEdtTxt = findViewById(R.id.name_editText);
        EditText artistEdtTxt = findViewById(R.id.artist_editText);
        EditText ratingEdtTxt = findViewById(R.id.rating_editText);
        Log.w(ACTIVITY_TAG, "\tretrieved handles to GUI components");

        String name = nameEdtTxt.getText().toString();
        String artist = artistEdtTxt.getText().toString();
        int rating = Integer.parseInt(ratingEdtTxt.getText().toString());
        Log.w(ACTIVITY_TAG, "\tretrieved data from GUI components");

        Song songRec = new Song(0, name, artist, rating);
        Log.w(ACTIVITY_TAG, "\tabout to make record manager call");
        recMgr.insert(songRec);
        Log.w(ACTIVITY_TAG, "\trecord manager call complete");
        Toast.makeText(this, "Song added", Toast.LENGTH_SHORT).show();
    }
}
