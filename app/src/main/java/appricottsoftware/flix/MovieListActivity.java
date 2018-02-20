package appricottsoftware.flix;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import appricottsoftware.flix.Adapters.MovieAdapter;
import appricottsoftware.flix.Models.Config;
import appricottsoftware.flix.Models.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {

    private final static String TAG = "MovieListActivity";

    @BindView(R.id.rvMovies) RecyclerView rvMovies;

    AsyncHttpClient client;
    ArrayList<Movie> movies; // List of currently playing movies
    MovieAdapter adapter; // Adapter for recycler view
    Config config; // Image config

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        client = new AsyncHttpClient(); // Initialize the client
        movies = new ArrayList<>(); // Initialize the list of movies

//        movies.size();
//        int extra = 999;
//        int numer = 4;
//        int denom = 0;
//        try {
//            int sol = numer/denom;
//        } catch (Exception e) {
//            Log.e(TAG, "onCreate" + extra, e);
//            //e.printStackTrace();
//        }


        // Resolve the recycler view and connect a layout manager and the adapter
        adapter = new MovieAdapter(movies); // Initialize the adapter - movies cannot be reinitialized after this point
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);

        // Get the configuration on app creation
        getConfiguration();
    }

    // Get the list of currently playing movies from the API
    private void getNowPlaying() {
        String url = getString(R.string.API_BASE_URL) + "/movie/now_playing"; // Create the URL

        RequestParams params = new RequestParams(); // Set the request parameters
        params.put(getString(R.string.API_KEY_PARAM), getString(R.string.api_key));

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try { // Load the result into movies list
                    JSONArray results = response.getJSONArray("results");

                    // Iterate through the result set and create Movie objects
                    for(int i = 0; i < results.length(); i++) {
                        Movie movie = new Movie(results.getJSONObject(i));
                        movies.add(movie);

                        // Notify adapter that a row was added
                        adapter.notifyItemInserted(movies.size() - 1);
                    }
                    Log.i(TAG, String.format("Loaded %s movies", results.length()));
                } catch (JSONException e) {
                    logError("Failed to parse now playing movies", e, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed to get data from now_playing endpoint", throwable, true);
            }
        });
    }

    // Get the configuration from the API
    private void getConfiguration() {
        String url = getString(R.string.API_BASE_URL) + "/configuration"; // Create the URL

        RequestParams params = new RequestParams(); // Set the request parameters
        params.put(getString(R.string.API_KEY_PARAM), getString(R.string.api_key));

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    config = new Config(response);
                    Log.d(TAG, String.format("Loaded configuration with imageBaseUrl %s and posterSize %s",
                            config.getImageBaseUrl(),
                            config.getPosterSize()));

                    // Pass config to adapter
                    adapter.setConfig(config);

                    // Get the now playing movie list
                    getNowPlaying();
                } catch (JSONException e) {
                    logError("Failed parsing configuration", e, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed getting configuration", throwable, true);
            }
        });
    }

    private void logError(String message, Throwable error, boolean alertUser) {
        Log.e(TAG, message, error);
        if(alertUser) {
            // Show a long toast with the error message
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}

