package com.example.favoritesongs_kt_mf;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final static String ACTIVITY_TAG = "MainActivity";
    private RecordManager recMgr;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recMgr = new RecordManager(this);
        scrollView = findViewById(R.id.mainScroll);
    }

    @Override
    protected void onStart(){
        super.onStart();
        updateView();
    }

    private void updateView(){
        Log.w(ACTIVITY_TAG, "top of updateView");

        ArrayList<Song> songList = recMgr.selectAll();
        int numRecs = songList.size();
        Log.w(ACTIVITY_TAG, "updateView, numRecs is: " + numRecs);

        if(numRecs > 0) {
            scrollView.removeAllViews();
            Log.w(ACTIVITY_TAG, "removed previous sub-views");

            LinearLayout columnOfLabels = new LinearLayout(this);
            columnOfLabels.setOrientation(LinearLayout.VERTICAL);

            TextView[] labels = new TextView[numRecs];
            Log.w(ACTIVITY_TAG, "created array of labels");
            for(int ndx = 0; ndx < songList.size(); ndx++){
                String recStr = songList.get(ndx).toString();
                Log.w(ACTIVITY_TAG, "updateView loop, recStr: " + recStr);
                labels[ndx] = new TextView(this);
                labels[ndx].setText(recStr);
                labels[ndx].setTextSize(24);
                labels[ndx].setPadding(10,10,10,0);
                columnOfLabels.addView(labels[ndx]);
            }
            scrollView.addView(columnOfLabels);
        } else{
            scrollView.removeAllViews();
            Log.w(ACTIVITY_TAG, "removed previous sub-views; numRecs == 0");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add:
                Log.w(ACTIVITY_TAG, "Add selected");
                Intent addItemIntent = new Intent(this, AddItemActivity.class);
                this.startActivity(addItemIntent);
                return true;
            case R.id.action_delete:
                Log.w(ACTIVITY_TAG, "Delete selected");
                Intent deleteIntent = new Intent(this, DeleteItemActivity.class);
                this.startActivity(deleteIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
