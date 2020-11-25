package com.example.likeanddislikemodelu;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.util.Arrays;

import com.example.likeanddislikemodelu.AdapterSS.SelectionScreenAdapter;
import com.example.likeanddislikemodelu.GlidLibExtraSource.MyAppGlideModule;
import com.example.likeanddislikemodelu.Model.ImagesResponse;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;


public class SelectionScreen extends AppCompatActivity {

    private ArrayList<String> al;
    private ArrayAdapter<String> arrayAdapter;
    private int i;
    SwipeFlingAdapterView flingContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_screen);



        al = new ArrayList<>();
        al.add("blabla");
        al.add("blabla");
        al.add("blabla");
        al.add("blabla");
        al.add("blabla");
        al.add("blabla");
        al.add("blabla");
        al.add("blabla");

        arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.title_id, al );


        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                al.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(SelectionScreen.this, "Left!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRightCardExit(Object dataObject){
                Toast.makeText(SelectionScreen.this, "Right!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // this function is implemented to make a loop through the updates of new pictures, and never Exhausting//
              /*  al.add("XML ".concat(String.valueOf(i)));
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++; */
                Toast.makeText(SelectionScreen.this, "You just ran out of Products, try later, thank you!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onScroll(float scrollProgressPercent) {
             }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(SelectionScreen.this, "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void left(View view) {
        flingContainer.getTopCardListener().selectLeft();
    }

    public void right(View view) {
        flingContainer.getTopCardListener().selectRight();
    }

    public void navToReviewScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), ReviewScreen.class);
        startActivity(intent);


    }
}
