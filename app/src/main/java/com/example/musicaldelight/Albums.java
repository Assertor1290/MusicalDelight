package com.example.musicaldelight;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Albums extends Fragment {

    RecyclerView recyclerView;
    Activity frag;
    private ListView mSongsList;
    private String[] itemsAll;
    Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.album_name, container, false);
        context = rootView.getContext();
        frag=getActivity();
        mSongsList=rootView.findViewById(R.id.songsList);
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = Objects.requireNonNull(getActivity()).findViewById(R.id.recyclerView_Album);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        //Log.d("Sense","Album Called");

        String[] projection = {
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.ALBUM_ART,
        };

        ContentResolver content = getActivity().getContentResolver();
        Cursor media_cursor = content.query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                projection,
                null,//Selection Statement
                null,//Selection Arguments replacement for ? in where id=?
                MediaStore.Audio.Albums.ALBUM + "");

        recyclerView.setAdapter(new AlbumAdapter(getContext(), media_cursor));


        AlbumAdapter albumAdapter=new AlbumAdapter(getContext(),media_cursor);
        albumAdapter.setOnItemClickListener(new AlbumAdapter.ClickListener(){
            @Override
            public void onItemClick(int position, View v) {
                Log.d(TAG, "onItemClick position: " + position);
                displaySongsInAlbum();

            }

            @Override
            public void onItemLongClick(int position, View v) {
                Log.d(TAG, "onItemLongClick pos = " + position);
            }
        });
    }

    private void displaySongsInAlbum() {

        Cursor cursor = null;
        String selection = "is_music != 0";

        String[] projection1 = {
                MediaStore.Audio.Media.ALBUM_ID
        };
        cursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection1, null, null, null);


        int albumId = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
        if (albumId > 0) {
            selection = selection + " and album_id = " + albumId;
        }


        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID
        };
        final String sortOrder = MediaStore.Audio.AudioColumns.DISPLAY_NAME + " COLLATE LOCALIZED ASC";



        try {
            Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            cursor = getActivity().getContentResolver().query(uri, projection, selection, null, sortOrder);
            if (cursor != null) {
                cursor.moveToFirst();

                while (!cursor.isAfterLast()) {
                    Log.d("Vipul",
                            cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));

                    String val=Integer.toString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

                    Log.d("Test",val);
                    cursor.moveToNext();
                }
                Intent intent=new Intent(frag,DesActivity.class);
                intent.putExtra("string", MediaStore.Audio.Media.ALBUM_ID);
                startActivity(intent);
            }

        } catch (Exception e) {
            Log.e("Media", e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set Action Bar title
//        ((MainActivity) Objects.requireNonNull(getActivity())).setActionBarTitle("Albums");
         //((MainActivity) getActivity()).getSupportActionBar().setTitle("Albums");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);

    }
}
