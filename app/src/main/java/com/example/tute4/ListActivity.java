package com.example.tute4;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import Database.DBHelper;

public class ListActivity extends AppCompatActivity {

    private ListView listView;
    private DBHelper dbHelper;
    private ArrayList arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.list);

        dbHelper = new DBHelper(this);
        arrayList = new ArrayList<>();

        viewData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                long id = listView.getItemIdAtPosition(i);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = listView.getItemAtPosition(i).toString();
                //Toast.makeText(ListActivity.this, "" + text, Toast.LENGTH_SHORT).show();

                long id = listView.getItemIdAtPosition(i);

                Intent intent = new Intent(ListActivity.this, DetailsActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    private void viewData() {
        Cursor cursor = dbHelper.readAllInfo();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data to show", Toast.LENGTH_SHORT).show();
        } else {
            if (cursor.moveToFirst()) {
                arrayList.add(cursor.getString(1));

                while (cursor.moveToNext()) {
                    arrayList.add(cursor.getString(1));
                }
            }

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(arrayAdapter);

            //ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
            //istView.setAdapter(arrayAdapter);
        }

        cursor.close();
    }
}
