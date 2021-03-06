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
            moodList.add(new Mood(R.drawable.sad, "Sad"));
            moodList.add(new Mood(R.drawable.chill, "Chill"));
            moodList.add(new Mood(R.drawable.anger, "Angry"));
            moodList.add(new Mood(R.drawable.high, "Confused"));
            moodList.add(new Mood(R.drawable.relaxed, "Excited"));



            return moodList;
        }

        AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (position == 0) {
                Intent i = new Intent(MainActivity.this, Choice.class);
                i.putExtra("layout", R.layout.activity_play_audio_happy);
                startActivity(i);
            }
            else if (position == 1){
                Intent i = new Intent(MainActivity.this, Choice.class);
                i.putExtra("layout", R.layout.activity_play_audio_sad);
                startActivity(i);
            }
            else if (position == 2){
                Intent i = new Intent(MainActivity.this, Choice.class);
                i.putExtra("layout", R.layout.activity_play_audio_relax);
                startActivity(i);
            }
            else if (position == 3){
                Intent i = new Intent(MainActivity.this, Choice.class);
                i.putExtra("layout", R.layout.activity_play_audio_angry);
                startActivity(i);
            }
            else if (position == 4){
                Intent i = new Intent(MainActivity.this, Choice.class);
                i.putExtra("layout", R.layout.activity_play_audio_confused);
                startActivity(i);
            }
            else if (position == 5){
                Intent i = new Intent(MainActivity.this, Choice.class);
                i.putExtra("layout", R.layout.activity_play_audio_excited);
                startActivity(i);
            }
            else {
                Intent i = new Intent(MainActivity.this, Choice.class);
                i.putExtra("layout", R.layout.activity_play_audio_example);
                startActivity(i);
            }
            }
        };

    }



