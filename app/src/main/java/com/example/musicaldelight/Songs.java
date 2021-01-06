package com.example.musicaldelight;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Songs extends Fragment {
    //to send intent
    Activity frag;

    Context context;
    private String[] itemsAll;
    private ListView mSongsList;

    //audio


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Pass your layout xml to the inflater and assign it to rootView.
        View rootView = inflater.inflate(R.layout.activity_smart_player, container, false);
        context = rootView.getContext(); // Assign your rootView to context

//        Button yourButton = (Button) rootView.findViewById(R.id.button);
//            yourButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Pass the context and the Activity class you need to open from the Fragment Class, to the Intent
//                Intent intent = new Intent(context, The smart player activity.class);
//                startActivity(intent);
//            }
//        });
        frag=getActivity();
        mSongsList=rootView.findViewById(R.id.songsList);
        appExternalStoragePermission();


        //audio
        return rootView;

    }


    public void appExternalStoragePermission()
    {
        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response)
                    {
                        displayAudioSongsName();
                    }

                    @Override public void onPermissionDenied(PermissionDeniedResponse response)
                    {

                    }

                    @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token)
                    {
                        token.continuePermissionRequest();
                    }
                }).check();
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

   public void displayAudioSongsName()
    {
        final ArrayList<File> audiosongs= readOnlyAudioSongs(Environment.getExternalStorageDirectory());
        itemsAll=new String[audiosongs.size()];

        for(int songcounter=0;songcounter<audiosongs.size();songcounter++)
        {
            itemsAll[songcounter]=audiosongs.get(songcounter).getName();
        }

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(frag,android.R.layout.simple_list_item_1,itemsAll);
        mSongsList.setAdapter(arrayAdapter);


        mSongsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String songName=mSongsList.getItemAtPosition(position).toString();
                Intent intent=new Intent(frag,PlayMusic.class);
                intent.putExtra("song",audiosongs);
                intent.putExtra("name",songName);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);

    }



}