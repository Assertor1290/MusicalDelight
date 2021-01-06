package com.example.musicaldelight;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DesActivity extends AppCompatActivity {

    private String[] itemsAll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_des);


        final ListView lvStatus = (ListView) findViewById(R.id.songsListdes);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);


        final ArrayList<File> audiosongs=readOnlyAudioSongs(Environment.getExternalStorageDirectory());
        itemsAll=new String[audiosongs.size()];

        for(int songcounter=0;songcounter<audiosongs.size();songcounter++)
        {
            itemsAll[songcounter]=audiosongs.get(songcounter).getName();
        }



        String[] strings={"Display "+getIntent().getStringExtra("string")};

        lvStatus.setAdapter(new ArrayAdapter<String>(DesActivity.this,
                android.R.layout.simple_list_item_1, itemsAll));


        lvStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String songName=lvStatus.getItemAtPosition(position).toString();
                Intent intent=new Intent(DesActivity.this,PlayMusic.class);
                intent.putExtra("song",audiosongs);
                intent.putExtra("name",songName);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }
    public ArrayList<File> readOnlyAudioSongs(File file)
    {
        ArrayList<File> arrayList=new ArrayList<>();

        File[] allFiles=file.listFiles();
        for(File individualFile :allFiles)
        {

            if(individualFile.isDirectory() && !individualFile.isHidden())
            {
                arrayList.addAll(readOnlyAudioSongs(individualFile));
            }
            else
            {
                if(individualFile.getName().endsWith(".mp3") || individualFile.getName().endsWith(".aac") || individualFile.getName().endsWith(".wav") || individualFile.getName().endsWith(".wma"))
                {
                    arrayList.add(individualFile);
                }
            }
        }

        return arrayList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //to go back to fragment
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            super.onBackPressed(); //replaced
        }
    }
}