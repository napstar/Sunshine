package com.example.android.sunshine.app;

/**
 * Created by nokunna on 2/24/2016.
 */

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public  class ForecastFragment extends Fragment {

    public ForecastFragment() {
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater)
    {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id= item.getItemId();
        if(id==R.id.action_refresh)
        {
            FetchWeatherTask weatherTask= new FetchWeatherTask();
            weatherTask.execute("94043,us");
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        List<String> weekForcast= new ArrayList<String>(Arrays.asList(data));

        //add dummy data
        //create array adapter
        //populate listview

        mForecastAdapter= new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                weekForcast

        );

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView= (ListView)rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);

        setHasOptionsMenu(true);


        return rootView;

  //end of on creat view

    }

    public class FetchWeatherTask extends AsyncTask<String,Void,Void>
    {
      private final String LOG_TAG=FetchWeatherTask.class.getSimpleName();


        @Override
        protected Void doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.

            if (params.length==0)
            {
                return null;
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;
            String format="json";
            String units="metric";
            int numDays=7;

            try
            {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
               // String baseUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7";
               // String apiKey = "&APPID=" + BuildConfig.OPEN_WEATHER_MAP_API_KEY;

               // String apiKey="&APPID="+"9b2da7ef3011dd609c6851f8e472f138";;
                //URL url = new URL(baseUrl.concat(apiKey));

               // URL builtUri= new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");
                final String FORECAST_BASE_URL ="http://api.openweathermap.org/data/2.5/forecast/daily?";
                final String QUERY_PARAM = "q";
                final String FORMAT_PARAM = "mode";
                final String UNITS_PARAM = "units";
                final String DAYS_PARAM = "cnt";
                final String APPID_PARAM = "APPID";


                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, params[0])
                        .appendQueryParameter(FORMAT_PARAM, format)
                        .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                        .appendQueryParameter(APPID_PARAM, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
                        .build();
                URL url = new URL(builtUri.toString());
                //log url
                Log.v(LOG_TAG, "Built URI " + builtUri.toString());
                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
              //  URL url = new URL(baseUrl.concat(apiKey));

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
               Log.e(LOG_TAG,"Forecast JSon String:"+forecastJsonStr);



            }
            catch(IOException e)
            {
                Log.e(LOG_TAG, "Error ", e);
            }
            finally
            {
                if(urlConnection !=null)
                {
                    urlConnection.disconnect();
                }
                if(reader !=null)
                {
                    try
                    {
                        reader.close();
                    }
                    catch(final IOException e)
                    {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            return null;
        }
    }


}


