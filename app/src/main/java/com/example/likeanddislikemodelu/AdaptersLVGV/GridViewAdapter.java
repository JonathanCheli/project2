package com.example.likeanddislikemodelu.AdaptersLVGV;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import android.widget.ImageView;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.example.likeanddislikemodelu.R;
import com.example.likeanddislikemodelu.Model.ImagesResponse;

public class GridViewAdapter extends ArrayAdapter<ImagesResponse>{

public GridViewAdapter(Context context, int resource, List<ImagesResponse> imagesResponseList) {
        super(context, resource,imagesResponseList);
        }


@NonNull
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_grid,null);


        }

        ImagesResponse imagesResponse = getItem(position);
        ImageView imageView2 = view.findViewById(R.id.id_standarImage2);


        imageView2.setImageResource(imagesResponse.getImages());


        return view;
        }

        }