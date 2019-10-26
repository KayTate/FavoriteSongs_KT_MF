package com.example.favoritesongs_kt_mf;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DeleteItemActivity extends AppCompatActivity {
    private static final String ACTIVITY_TAG = "DeleteActivity";
    private RecordManager recMgr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recMgr = new RecordManager(this);
        Log.w(ACTIVITY_TAG, "onCreate method");
        updateView();
    }

    // Build a View programmatically
    private void updateView() {
        Log.w(ACTIVITY_TAG, "updateView method");

        ArrayList<Song> records = recMgr.selectAll();
        Log.w(ACTIVITY_TAG, "\tretrieved ArrayList of records");

        RelativeLayout relLayout = new RelativeLayout(this);
        ScrollView scrollView = new ScrollView(this);
        RadioGroup radGroup = new RadioGroup(this);
        Log.w(ACTIVITY_TAG, "\tcreated components");

        for (Song nmEmRec : records) {
            RadioButton rb = new RadioButton(this);
            rb.setId(nmEmRec.getId());
            rb.setText(nmEmRec.toString());
            radGroup.addView(rb);
        }
        Log.w(ACTIVITY_TAG, "\tcreated radio buttons");

        // set up event handling
        RadioButtonHandler rbh = new RadioButtonHandler();
        radGroup.setOnCheckedChangeListener(rbh);
        Log.w(ACTIVITY_TAG, "\tset up event handling");

        scrollView.addView(radGroup);
        relLayout.addView(scrollView);
        Log.w(ACTIVITY_TAG, "\tadded radio buttons to scroll, added scroll to layout");

        setContentView(relLayout);
    }

    private class RadioButtonHandler implements RadioGroup.OnCheckedChangeListener {
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // delete record from database
            recMgr.deleteById(checkedId);
            Toast.makeText(DeleteItemActivity.this, "Song deleted",
                    Toast.LENGTH_SHORT).show();

            // update screen
            updateView();
        }
    }
}
