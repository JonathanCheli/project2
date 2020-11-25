package com.example.likeanddislikemodelu.AdapterSS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.likeanddislikemodelu.Model.ImagesResponse;
import com.example.likeanddislikemodelu.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class SelectionScreenAdapter extends ArrayAdapter<ImagesResponse>{

    public SelectionScreenAdapter(@NonNull Context context, int resource, @NonNull List<ImagesResponse> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item, null);


        }

        ImagesResponse imagesResponse = getItem(position);

        ImageView imageItem = view.findViewById(R.id.image_id);


        Glide.with(getContext()).load(imagesResponse
                .getImages())
                .into(imageItem);


        return view;

    }

}
