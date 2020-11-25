package com.example.likeanddislikemodelu.AdaptersLVGV;

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
import com.example.likeanddislikemodelu.Model.ImagesResponse;
import com.example.likeanddislikemodelu.R;

public class ListViewAdapter extends ArrayAdapter<ImagesResponse>{
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

        TextView txt = view.findViewById(R.id.id_standard_txt);
        ImageView imageView = view.findViewById(R.id.id_standarImage);


        imageView.setImageResource(imagesResponse.getImages());
        txt.setText(imagesResponse.getTitle());


        return view;

    }
}