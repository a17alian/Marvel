package com.example.aliceanglesjo.marvel;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//import java.time.Year;
import java.util.ArrayList;
import java.util.List;


/** TODO
 * FIX SORTING ON BACK BUTTON
 * ADD GRIDVIEW?
 * ADD VIDEOVIEW
 * **/

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayAdapter adapter;
    private List<Film> listData = new ArrayList<>();

    FilmReaderDbHelper alice;
    boolean isName = false;
    boolean isDirector = false;
    boolean isYear = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new FetchData().execute();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This is an app about the Marvel Cinematic Universe. Created by Alice Anglesjö.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    /*  RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager); */

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FilmAdapter(listData, new FilmAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Film item) {
              Intent details = new Intent(getApplicationContext(),Details.class);
                details.putExtra("heading", item.heading());
                details.putExtra("sub", item.subtile());
                details.putExtra("sub2", item.subtile2());
                details.putExtra("storyline", item.story());
                details.putExtra("image", item.imgInfo());
                startActivity(details);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        // DATABASE
        alice = new FilmReaderDbHelper(getApplicationContext()) {
        };

    }

    public void fetchDb() {
       listData.clear();
        SQLiteDatabase dbRead = alice.getReadableDatabase();
        String[] projection = {
                FilmReaderContract.MountainEntry.COLUMN_NAME_NAME,
                FilmReaderContract.MountainEntry.COLUMN_NAME_YEAR,
                FilmReaderContract.MountainEntry.COLUMN_NAME_DIRECTOR,
                FilmReaderContract.MountainEntry.COLUMN_NAME_STORY,
                FilmReaderContract.MountainEntry.COLUMN_NAME_IMDB,
                FilmReaderContract.MountainEntry.COLUMN_NAME_IMG_URL
        };

        String sortOrder =
                FilmReaderContract.MountainEntry.COLUMN_NAME_NAME + " DESC ";
        if(isDirector){
            sortOrder = FilmReaderContract.MountainEntry.COLUMN_NAME_DIRECTOR + " ASC ";
        } else if(isName){
            sortOrder = FilmReaderContract.MountainEntry.COLUMN_NAME_NAME + " ASC ";
        } else if(isYear){
            sortOrder = FilmReaderContract.MountainEntry.COLUMN_NAME_YEAR + " ASC ";
        }

        Cursor cursor = dbRead.query(
                FilmReaderContract.MountainEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        while (cursor.moveToNext()) {
            String mName = cursor.getString(cursor.getColumnIndexOrThrow(FilmReaderContract.MountainEntry.COLUMN_NAME_NAME));
            String mYear = cursor.getString(cursor.getColumnIndexOrThrow(FilmReaderContract.MountainEntry.COLUMN_NAME_YEAR));
            String mDirector = cursor.getString(cursor.getColumnIndexOrThrow(FilmReaderContract.MountainEntry.COLUMN_NAME_DIRECTOR));
            String mStory = cursor.getString(cursor.getColumnIndexOrThrow(FilmReaderContract.MountainEntry.COLUMN_NAME_STORY));
            String mImage = cursor.getString(cursor.getColumnIndexOrThrow(FilmReaderContract.MountainEntry.COLUMN_NAME_IMG_URL));
            String mImdb = cursor.getString(cursor.getColumnIndexOrThrow(FilmReaderContract.MountainEntry.COLUMN_NAME_IMDB));

            Film mfilm = new Film(mName, mDirector, mYear, mStory, mImage, mImdb);

           // Log.d("story", mStory);
            listData.add(mfilm);

        }
        cursor.close();
        mRecyclerView.setAdapter(mAdapter);

    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.byName:
                isName = true;
                isDirector = false;
                isYear = false;
                fetchDb();
                return true;
            case R.id.byDirector:
                isName = false;
                isYear = false;
                isDirector = true;
                fetchDb();
                return true;
            case R.id.byYear:
                isName = false;
                isDirector = false;
                isYear = true;
                fetchDb();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private class FetchData extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... params) {
            // These two variables need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a Java string.
            String jsonStr = null;

            try {
                // Construct the URL for the Internet service
                URL url = new URL("https://api.myjson.com/bins/hk1xq");

                // Create the request to the PHP-service, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

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
                jsonStr = buffer.toString();
                return jsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in
                // attempting to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Network error", "Error closing stream", e);
                    }
                }
            }
        }
        @Override
        protected void onPostExecute(String o) {

            super.onPostExecute(o);
           // Log.d("alicehej", "data" + o);

            try {
                mRecyclerView.setAdapter(null);
                listData.clear();
                // Ditt JSON-objekt som Java
                JSONArray json1 = new JSONArray(o);

                SQLiteDatabase dbWrite = alice.getWritableDatabase();
                if (json1 != null)
                for(int i = 0; i < json1.length(); i++) {

                    JSONObject film = json1.getJSONObject(i);
                    //Log.d("alicehej", "filmer" + film.toString());

                    String ID = film.getString("ID");
                    String name = film.getString("name");
                    String status = film.getString("status");
                    String year = film.getString("year");
                    String imdb = film.getString("imdb");
                    String runtime = film.getString("runtime");
                    String director = film.getString("director");
                    String storyline = film.getString("storyline");

                    JSONObject aData = new JSONObject(film.getString("auxdata"));
                    String imgUrl = aData.getString("img");
                   // Log.d("story", storyline.toString());

                    ContentValues values = new ContentValues();
                    values.put(FilmReaderContract.MountainEntry.COLUMN_NAME_NAME, name);
                    values.put(FilmReaderContract.MountainEntry.COLUMN_NAME_YEAR, year);
                    values.put(FilmReaderContract.MountainEntry.COLUMN_NAME_DIRECTOR, director);
                    values.put(FilmReaderContract.MountainEntry.COLUMN_NAME_STORY, storyline);
                    values.put(FilmReaderContract.MountainEntry.COLUMN_NAME_IMG_URL, imgUrl);
                    values.put(FilmReaderContract.MountainEntry.COLUMN_NAME_IMDB, imdb);

                    dbWrite.insertWithOnConflict(FilmReaderContract.MountainEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);

                    Film m = new Film(name, status , year, imdb, runtime, director, storyline ,imgUrl);

                    listData.add(m);

                    mRecyclerView.setAdapter(new FilmAdapter(listData, new FilmAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Film item) {
                            Intent details = new Intent(getApplicationContext(),Details.class);
                            details.putExtra("heading", item.heading());
                            details.putExtra("sub", item.subtile());
                            details.putExtra("sub2", item.subtile2());
                            details.putExtra("storyline", item.story());
                            details.putExtra("image", item.imgInfo());

                            startActivity(details);
                        }
                    }));

                }
                fetchDb();

            } catch (JSONException e) {
                Log.e("brom","E:"+e.getMessage());
            }



        }
    }
}

