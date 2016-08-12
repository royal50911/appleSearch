package com.example.denniskim.crisp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {

    protected SearchView search;
    private ListView results;
    ArrayList<String> arrayApples;
    private ArrayAdapter<String> adapter;
    private Cursor apples;
    private MyDatabase db;
    String currentTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActionBar bar=getSupportActionBar();
        bar.hide();

        Bundle appleData = getIntent().getExtras();
        if (appleData == null)
            return;
        currentTable = appleData.getString("username");
        Log.e("current", currentTable);

        search = (SearchView)findViewById(R.id.searchView);
        results = (ListView)findViewById(R.id.results);
        arrayApples = new ArrayList<String>();
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayApples);
        results.setAdapter(adapter);

        db = new MyDatabase(this);

        apples = db.getApple("");
        if (apples.moveToFirst() == false)
            return;

        while (true) {
            arrayApples.add(apples.getString(0));
            if (apples.moveToNext() == false)
                break;
        }
        adapter.notifyDataSetChanged();
        apples.close();


        search.setOnCloseListener(new SearchView.OnCloseListener() {
                                      @Override
                                      public boolean onClose() {
                                          // to clear out array of apples for new search
                                          int size = arrayApples.size();
                                          if (size != 0) {
                                              for (int i = 0; i < size; i++) {
                                                  arrayApples.remove(0);
                                              }

                                              // repopulate the list
                                              apples = db.getApple("");
                                              if (apples.moveToFirst() == false)
                                                  return false;

                                              while (true) {
                                                  arrayApples.add(apples.getString(0));
                                                  if (apples.moveToNext() == false)
                                                      break;
                                              }

                                          }
                                          //this updates the listView
                                          adapter.notifyDataSetChanged();
                                          apples.close();
                                          return false;
                                      }
                                  }
        );
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {

                apples = db.getApple(text);  // search by month first
                if (apples.moveToFirst() == false)
                    return false;

                int size = arrayApples.size();
                // to clear out array of apples for new search
                if (size != 0) {
                    for (int i = 0; i < size; i++) {
                        arrayApples.remove(0);
                    }
                }

                while (true) {
                    arrayApples.add(apples.getString(0));
                    if (apples.moveToNext() == false)
                        break;
                }
                //this updates the listView
                adapter.notifyDataSetChanged();
                apples.close();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                // adapter.getFilter().filter(text);
                return false;
            }
        });

        results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String apple = String.valueOf(parent.getItemAtPosition(position));
                Intent myIntent = new Intent(SearchActivity.this, ApplesProfile.class);
                myIntent.putExtra("Apple name", apple);
                myIntent.putExtra("username", currentTable);
                startActivity(myIntent);
            }
        });

    }


}
