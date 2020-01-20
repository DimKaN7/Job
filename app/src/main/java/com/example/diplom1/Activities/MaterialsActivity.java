package com.example.diplom1.Activities;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.diplom1.Classes.Material;
import com.example.diplom1.Classes.MaterialAdapter;
import com.example.diplom1.R;

import java.util.ArrayList;
import java.util.List;

public class MaterialsActivity extends AppCompatActivity {

    private final String TAG = "MaterialsActivity";

    private List<Material> materialList;

    private ListView listView;

    private String type;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materials);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Имеющиеся материалы");
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.listView);

        materialList = new ArrayList<>();
        type = getIntent().getStringExtra("type");
        id = getIntent().getIntExtra("id", -1);

        if (type.equals("seamless")) {
            materialList.add(new Material(R.drawable.seamless1, getResources().getString(R.string.title_seamless_1), getResources().getString(R.string.about_seamless_1), 600));
            materialList.add(new Material(R.drawable.seamless2, getResources().getString(R.string.title_seamless_2), getResources().getString(R.string.about_seamless_2), 400));

            MaterialAdapter adapter = new MaterialAdapter(this, R.layout.material_list_item, materialList, type, id);
            listView.setAdapter(adapter);
        }
        else {
            materialList.add(new Material(R.drawable.tile_1, getResources().getString(R.string.title_tile_1), getResources().getString(R.string.about_tile_1), 1695));
            materialList.add(new Material(R.drawable.tile_2, getResources().getString(R.string.title_tile_2), getResources().getString(R.string.about_tile_2), 1615));
            materialList.add(new Material(R.drawable.tile_3, getResources().getString(R.string.title_tile_3), getResources().getString(R.string.about_tile_3), 1800));
            materialList.add(new Material(R.drawable.tile_4, getResources().getString(R.string.title_tile_4), getResources().getString(R.string.about_tile_4), 1950));
            materialList.add(new Material(R.drawable.tile_5, getResources().getString(R.string.title_tile_5), getResources().getString(R.string.about_tile_5), 1190));
            materialList.add(new Material(R.drawable.tile_6, getResources().getString(R.string.title_tile_6), getResources().getString(R.string.about_tile_6), 1615));
            materialList.add(new Material(R.drawable.tile_7, getResources().getString(R.string.title_tile_7), getResources().getString(R.string.about_tile_7), 1190));
            materialList.add(new Material(R.drawable.tile_8, getResources().getString(R.string.title_tile_8), getResources().getString(R.string.about_tile_8), 1290));
            materialList.add(new Material(R.drawable.tile_9, getResources().getString(R.string.title_tile_9), getResources().getString(R.string.about_tile_9), 1290));
            materialList.add(new Material(R.drawable.tile_10, getResources().getString(R.string.title_tile_10), getResources().getString(R.string.about_tile_10), 1290));

            MaterialAdapter adapter = new MaterialAdapter(this, R.layout.material_list_item, materialList, type, id);
            listView.setAdapter(adapter);
        }
    }
}
