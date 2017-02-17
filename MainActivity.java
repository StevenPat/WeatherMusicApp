package com.example.steve.weathermusicapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.steve.weathermusicapp.Grid.GridViewAdapter;
import com.example.steve.weathermusicapp.Grid.Mood;

import java.util.ArrayList;
import java.util.List;

import static com.example.steve.weathermusicapp.R.id.myGridView;

public class MainActivity extends AppCompatActivity {
        private ViewStub stubGrid;
        private GridView gridView;
        private GridViewAdapter gridViewAdapter;
        private List<Mood> moodList;
    //rubber baby buggy bumpers

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            stubGrid = (ViewStub) findViewById(R.id.stub_grid);
            stubGrid.inflate();
            gridView = (GridView) findViewById(myGridView);
            getMoodList();
            gridView.setOnItemClickListener(onItemClick);

            setAdapters();
        }

        private void setAdapters() {
            gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, moodList);
            gridView.setAdapter(gridViewAdapter);
        }

        public List<Mood> getMoodList() {

            moodList = new ArrayList<>();
            moodList.add(new Mood(R.drawable.joy, "Happy"));
            moodList.add(new Mood(R.drawable.sad, "Sad"));;
            moodList.add(new Mood(R.drawable.chill, "Chill"));;
            moodList.add(new Mood(R.drawable.anger, "Angry"));
            moodList.add(new Mood(R.drawable.high, "High"));
            moodList.add(new Mood(R.drawable.relaxed, "Relaxed"));



            return moodList;
        }

        AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent weather = new Intent(MainActivity.this, WeatherMusic.class);
                startActivity(weather);
            }
        };

    }



