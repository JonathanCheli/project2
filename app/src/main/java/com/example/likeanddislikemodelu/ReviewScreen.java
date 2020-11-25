package com.example.likeanddislikemodelu;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.ListView;

import com.example.likeanddislikemodelu.AdaptersLVGV.GridViewAdapter;
import com.example.likeanddislikemodelu.AdaptersLVGV.ListViewAdapter;
import com.example.likeanddislikemodelu.Model.ImagesResponse;

import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class ReviewScreen extends AppCompatActivity {

    private ListView listView;
    private GridView myGrid;
    private ViewStub stubListView;
    private ViewStub stubGrid;
    private ListViewAdapter listViewAdapter;
    private GridViewAdapter gridViewAdapter;
    private List<ImagesResponse> imageRList = new ArrayList<>();
    private int currentViewMode = 0;

    static final int VIEW_LISTVIEW = 0;
    static final int VIEW_GRIDVIEW = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.review_screen);


        stubGrid = (ViewStub) findViewById(R.id.id_sutb_gridview);
        stubListView = (ViewStub) findViewById(R.id.id_sutb_listview);

        stubListView.inflate();
        stubGrid.inflate();

        listView = findViewById(R.id.id_itemListView_layout);
        myGrid = findViewById(R.id.id_itemGrid_layout);


        getImageRList();


        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_LISTVIEW);


        listView.setOnItemClickListener(onItemClickListener);
        myGrid.setOnItemClickListener(onItemClickListener);

        switchView();

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

            listViewAdapter = new ListViewAdapter(ReviewScreen.this, R.layout.item_listview_layout, imageRList );
            listView.setAdapter(listViewAdapter);


        } else {
            gridViewAdapter = new GridViewAdapter(ReviewScreen.this,R.layout.item_grid_layout, imageRList);
            myGrid.setAdapter(gridViewAdapter);
        }
    }

    public List<ImagesResponse> getImageRList() {
        imageRList = new ArrayList<>();
        imageRList.add(new ImagesResponse(R.drawable.matratzenbezug_smood_webstoff, "Matratzenbezug smood webstoff"));
        imageRList.add(new ImagesResponse(R.drawable.premium_komfort_matratze2smood, "Premium komfort matratze smood"));
        imageRList.add(new ImagesResponse(R.drawable.premium_komfort_matratze_smood, "Premium komfort matratze smood"));
        imageRList.add(new ImagesResponse(R.drawable.premium_komfort_matratze_smood2, "Premium komfort matratze smood"));
        imageRList.add(new ImagesResponse(R.drawable.premium_komfort_matratze_smood3, "Premium komfort matratze smood3"));
        imageRList.add(new ImagesResponse(R.drawable.smood_spring_bett_i_webstoff_eiche_massiv, "Smood spring bett i webstoff eiche massiv"));
        imageRList.add(new ImagesResponse(R.drawable.smoodspring_bett_i_webstoff_eiche_massiv2, "Smood spring bett i webstoff eiche massiv"));
        imageRList.add(new ImagesResponse(R.drawable.smoodspring_bett_i_webstoff_eiche_massiv3, "Smood spring bett i webstoff eiche massiv"));
        imageRList.add(new ImagesResponse(R.drawable.smoodspring_bett_i_webstoff_eiche_massiv4, "Smood spring bett i webstoff eiche massiv"));
        return imageRList;
    }


        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), imageRList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        };


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
            return true;
        }
    }



