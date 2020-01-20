package com.example.diplom1.Classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diplom1.Activities.MainActivity;
import com.example.diplom1.R;

import java.util.List;

public class MaterialAdapter extends ArrayAdapter<Material> {

    private final String TAG = "MaterialAdapter";

    Context context;
    int resource;
    List<Material> materialList;
    String type;
    int id;

    public MaterialAdapter(Context context, int resource, List<Material> materialList, String type, int id) {
        super(context, resource, materialList);

        this.context = context;
        this.resource = resource;
        this.materialList = materialList;
        this.type = type;
        this.id = id;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textViewTitle = view.findViewById(R.id.textViewTitle);
        TextView textViewAbout = view.findViewById(R.id.textViewAbout);
        TextView textViewPrice = view.findViewById(R.id.textViewPrice);

        Material material = materialList.get(position);

        textViewTitle.setText(material.getTitle());
        textViewAbout.setText(material.getAbout());
        textViewPrice.setText("Цена: от " + Double.toString(material.getPrice()) + " р.");

        imageView.setImageDrawable(context.getResources().getDrawable(material.getImage()));

        view.findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Material mat = materialList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("type", type);
                bundle.putString("title", mat.getTitle());
                bundle.putDouble("price", mat.getPrice());
                bundle.putInt("id", id);
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("translation", bundle);
                context.startActivity(intent);
//                некритичный но косяк
                ((Activity) context).finishAndRemoveTask();
//                Log.e(TAG, mat.getTitle() + " " + Double.toString(mat.getPrice()));
            }
        });

        return view;

    }
}
