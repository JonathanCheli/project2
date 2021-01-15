package com.example.likeanddislikemodelu;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.ListView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.likeanddislikemodelu.AdapterSS.CardStackAdapter;
import com.example.likeanddislikemodelu.AdaptersLVGV.GridViewAdapter;
import com.example.likeanddislikemodelu.AdaptersLVGV.ListViewAdapter;
import com.example.likeanddislikemodelu.Model.ImagesResponse;
import com.example.likeanddislikemodelu.Model.ItemModel;

import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReviewScreen extends AppCompatActivity {

    private ListView listView;
    private GridView myGrid;
    private ViewStub stubListView;
    private ViewStub stubGrid;
    private ListViewAdapter listViewAdapter;
    private GridViewAdapter gridViewAdapter;
    private List<ImagesResponse> imagesResponseList;
    private int currentViewMode = 0;

    private RequestQueue requestQueue;
    static final int VIEW_LISTVIEW = 0;
    static final int VIEW_GRIDVIEW = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        stubGrid = (ViewStub) findViewById(R.id.id_sutb_gridview);
        stubListView = (ViewStub) findViewById(R.id.id_sutb_listview);

        stubListView.inflate();
        stubGrid.inflate();

        listView = findViewById(R.id.id_itemListView_layout);
        myGrid = findViewById(R.id.id_itemGrid_layout);



        imagesResponseList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);

        getImageRList();


        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_LISTVIEW);


        /* listView.setOnItemClickListener(onItemClickListener); */
        /* myGrid.setOnItemClickListener(onItemClickListener); */

        switchView();

    }

    private void getImageRList() {
        String url = "https://api-mobile.home24.com/api/v2.0/articles/000000001000062031?appDomain=1&locale=de_DE";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("images");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                ImagesResponse imagesResponse =new ImagesResponse();

                                imagesResponse.setImages(jsonArray.getString(i));

                                imagesResponseList.add(imagesResponse);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void switchView() {
        if (VIEW_LISTVIEW == currentViewMode) {
            stubListView.setVisibility(View.VISIBLE);
            stubGrid.setVisibility(View.GONE);
        } else {
            stubListView.setVisibility(View.GONE);
            stubGrid.setVisibility(View.VISIBLE);
        }

        setAdapters();

    }

    private void setAdapters() {
        if (VIEW_LISTVIEW == currentViewMode) {

            listViewAdapter = new ListViewAdapter(ReviewScreen.this, R.layout.item_listview_layout, imagesResponseList);
            listView.setAdapter(listViewAdapter);


        } else {
            gridViewAdapter = new GridViewAdapter(ReviewScreen.this, R.layout.item_grid_layout, imagesResponseList);
            myGrid.setAdapter(gridViewAdapter);
        }
    }


     /*   AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), imagesResponseList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        }; */


    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case R.id.item_menu1:
                if (VIEW_LISTVIEW == currentViewMode) {
                    currentViewMode = VIEW_GRIDVIEW;
                } else {
                    currentViewMode = VIEW_LISTVIEW;
                }
                switchView();
                SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("currentViewMode", currentViewMode);
                editor.commit();

                break;
        }
        return false;
    }

}



