package com.example.android.sunshine.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.util.Log;
import android.widget.ListView;

import java.util.*;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new PlaceholderFragment())
                        .commit();
            }
            else {
                Log.e("MainActivity:onCreate", "Main is null");
            }
        }
        catch(Exception ex)
        {
            Log.e("MainActivity:onCreate", ex.getMessage());
            Log.e("MainActivity:onCreate", ex.toString());
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        //array adpter
        ArrayAdapter<String> mForecastAdapter;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            String[] data={
                            "Today -Sunny -88/63"
                            ,"Tomorrow -Foggy -70/40"
                            ,"Wed -Cloudy -72/63"
                              ,"Thurs -Asteroids -75/65"
                    ,"Fri -Heavy Rain -65/56"
                    ,"Sat - HELP TRAPPED IN WEATHERSTATION -60/51"
                    ,"Sun -Sunny -80/68"

                    };

           List<String>  weekForcast= new ArrayList<String>(Arrays.asList(data));

            //add dummy data
            //create array adapter
            //populate listview

            mForecastAdapter= new ArrayAdapter<String>(
                    getActivity(),
                    R.layout.list_item_forecast,
                    R.id.list_item_forecast_textview,
                    weekForcast

            );
         //   setListAdapter(mForecastAdapter);
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            ListView listView= (ListView)rootView.findViewById(R.id.listview_forecast);
            listView.setAdapter(mForecastAdapter);

            return rootView;



        }
    }





}
