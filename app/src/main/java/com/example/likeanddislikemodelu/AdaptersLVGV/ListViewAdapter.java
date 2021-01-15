package com.example.likeanddislikemodelu.AdaptersLVGV;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.airbnb.lottie.LottieAnimationView;
import android.animation.Animator;
import java.util.ArrayList;
import java.util.List;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ArrayAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.likeanddislikemodelu.Model.ImagesResponse;
import com.example.likeanddislikemodelu.R;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.example.likeanddislikemodelu.ReviewScreen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ListViewAdapter extends ArrayAdapter<ImagesResponse>{

    private int j=0;
    Boolean likeFav= false;
    List<String > keys;
    DatabaseReference databaseReferenceLikes;
    int numOfLikes = 0;



    public ListViewAdapter(Context context, int resource,List<ImagesResponse> imagesResponseList) {
        super(context, resource, imagesResponseList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_listview,null);

        }


        ImagesResponse imagesResponse = getItem(position);

        ImageView imageView = view.findViewById(R.id.id_standarImage);

        LottieAnimationView lottieImage = view.findViewById(R.id.lottieImage);


        keys = new ArrayList<>();
        databaseReferenceLikes = FirebaseDatabase.getInstance().getReference("images");
        databaseReferenceLikes.keepSynced(true);
        likeFav = true;




        Picasso.get()
                .load(imagesResponse.getImages())
                .fit()
                .centerCrop()
                .into(imageView);
        return view;


    }
}




