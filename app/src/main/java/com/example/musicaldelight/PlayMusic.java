package com.example.musicaldelight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlayMusic extends AppCompatActivity {

    private RelativeLayout parentRelativeLayout;

    private TextToSpeech mytexttospeech;
    private SpeechRecognizer speechRecognition;
  //  Intent speechRecognizerIntent;
  //  private String keeper="";

    private ImageView playPauseBtn, nextBtn, previousBtn;
    private TextView songNameTxt;

    private ImageView imageView;
    private RelativeLayout lowerRelativeLayout;
  //  private Button voiceEnabledBtn;
   // private String mode="ON";

    private SeekBar mseekBar;
    private MediaPlayer mymMediaPlayer;
    private int position;
    private ArrayList<File> mySongs;
    private String mSongName;

    private Handler mHandler;
    private Runnable mUpdateSeekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_music);

//        checkVoiceCommandPermission();


        playPauseBtn=findViewById(R.id.play_pause_btn);
        nextBtn=findViewById(R.id.next_btn);
        previousBtn=findViewById(R.id.previous_btn);
        imageView=findViewById(R.id.logo);

        lowerRelativeLayout=findViewById(R.id.lower);
//        voiceEnabledBtn=findViewById(R.id.voice_enabled_btn);
        songNameTxt=findViewById(R.id.songName);
        mseekBar=findViewById(R.id.seek_bar);


        parentRelativeLayout=findViewById(R.id.parentRelativeLayout);
//        speechRecognizer=SpeechRecognizer.createSpeechRecognizer(PlayMusic.this);
//        speechRecognizerIntent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());


        validateReceiveValuesAndStartPlaying();
        imageView.setImageResource(R.drawable.crop);

//        speechRecognizer.setRecognitionListener(new RecognitionListener() {
//            @Override
//            public void onReadyForSpeech(Bundle params) {
//
//            }
//
//            @Override
//            public void onBeginningOfSpeech() {
//
//            }
//
//            @Override
//            public void onRmsChanged(float rmsdB) {
//
//            }
//
//            @Override
//            public void onBufferReceived(byte[] buffer) {
//
//            }
//
//            @Override
//            public void onEndOfSpeech() {
//
//            }
//
//            @Override
//            public void onError(int error) {
//
//            }
//
//            @Override
//            public void onResults(Bundle results) {
//
//                ArrayList<String> matchesFound=results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//                if(matchesFound!=null)
//                {
//                    if(mode.equals("ON"))
//                    {
//                        keeper=matchesFound.get(0);
//
//                        if(keeper.equals("play the song"))
//                        {
//                            playPauseSong();
//                            Toast.makeText(PlayMusic.this,"Command = "+keeper,Toast.LENGTH_LONG).show();
//                        }
//                        //Toast.makeText(MainActivity.this,"Result = "+keeper,Toast.LENGTH_LONG).show();
//                        else if(keeper.equals("stop the song"))
//                        {
//                            playPauseSong();
//                            Toast.makeText(PlayMusic.this,"Command = "+keeper,Toast.LENGTH_LONG).show();
//                        }
//
//                        else if(keeper.equals("play next song"))
//                        {
//                            playNextSong();
//                            Toast.makeText(PlayMusic.this,"Command = "+keeper,Toast.LENGTH_LONG).show();
//                        }
//
//                        else if(keeper.equals("play previous song"))
//                        {
//                            playPreviousSong();
//                            Toast.makeText(PlayMusic.this,"Command = "+keeper,Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//
//                }
//
//            }
//
//            @Override
//            public void onPartialResults(Bundle partialResults) {
//
//            }
//
//            @Override
//            public void onEvent(int eventType, Bundle params) {
//
//            }
//        });

//        parentRelativeLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch(event.getAction())
//                {
//                    case MotionEvent.ACTION_DOWN:
//                        speechRecognizer.startListening(speechRecognizerIntent);
//                        keeper="";
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        speechRecognizer.stopListening();
//                        break;
//
//                }
//                return false;
//            }
//        });

//        voiceEnabledBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mode.equals("ON"))
//                {
//                    mode="OFF";
//                    voiceEnabledBtn.setText("Voice Enabled Button- OFF");
//                    lowerRelativeLayout.setVisibility(View.VISIBLE);
//                }
//                else
//                {
//                    mode="ON";
//                    voiceEnabledBtn.setText("Voice Enabled Button- ON");
//                    lowerRelativeLayout.setVisibility(View.GONE);
//                }
//            }
//        });


        mHandler=new Handler();
//        mUpdateSeekBar=new Runnable() {
//            @Override
//            public void run() {
//                if(mymMediaPlayer!=null)
//                {
//                    int mCurrentPosition=mymMediaPlayer.getCurrentPosition()/1000;
//                    mseekBar.setProgress(mCurrentPosition);
//                }
//                mHandler.postDelayed(this,1000);
//
//            }
//        };



        playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPauseSong();
            }
        });

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mymMediaPlayer.getCurrentPosition()>0)
                {
                    playPreviousSong();
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mymMediaPlayer.getCurrentPosition()>0)
                {
                    playNextSong();
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
                speechRecognition.startListening(intent);

                initializeTextToSpeech();
                initializeSpeechRecognizer();

            }
        });

        mseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser)
                    mymMediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        initializeTextToSpeech();
        initializeSpeechRecognizer();
    }

    private void validateReceiveValuesAndStartPlaying()
    {

        releaseMediaPlayer();
        Intent intent=getIntent();

        Bundle bundle=intent.getExtras();

        mySongs=(ArrayList)bundle.getParcelableArrayList("song");
        mSongName=mySongs.get(position).getName();
        String songName=intent.getStringExtra("name");

        songNameTxt.setText(songName);
        songNameTxt.setSelected(true);

        position=bundle.getInt("position",0);

        Uri uri=Uri.parse(mySongs.get(position).toString());

        mymMediaPlayer=MediaPlayer.create(PlayMusic.this,uri);

        mymMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mseekBar.setMax(mymMediaPlayer.getDuration());
                mymMediaPlayer.start();
                changeSeekBar();
            }
        });
//        mseekBar.setMax(mymMediaPlayer.getDuration());
//        mymMediaPlayer.start();

    }

    private void changeSeekBar()
    {
        if(mymMediaPlayer!=null){
        mseekBar.setProgress(mymMediaPlayer.getCurrentPosition());
        if(mymMediaPlayer.isPlaying()){
            mUpdateSeekBar=new Runnable() {
                @Override
                public void run() {
                    changeSeekBar();
                }
            };
            mHandler.postDelayed(mUpdateSeekBar,1000);
        }
        }
    }


    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mymMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mymMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mymMediaPlayer = null;
        }
    }

//    private void checkVoiceCommandPermission()
//    {
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
//        {
//            if(!(ContextCompat.checkSelfPermission(PlayMusic.this, Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED))
//            {
//                Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:"+getPackageName()));
//                startActivity(intent);
//                finish();
//            }
//        }
//
//    }

    public void playPauseSong()
    {
        imageView.setBackgroundResource(R.drawable.four);
        if(mymMediaPlayer.isPlaying())
        {
            Log.d("Test","inside");
            playPauseBtn.setImageResource(R.drawable.play);
            mymMediaPlayer.pause();
           // mHandler.removeCallbacks(mUpdateSeekBar);
        }
        else
        {
            Log.d("Test","inside");
            playPauseBtn.setImageResource(R.drawable.pause);
            mymMediaPlayer.start();
           // mHandler.postDelayed(mUpdateSeekBar,0);
            imageView.setBackgroundResource(R.drawable.four);
        }
    }

    @Override
    public void onBackPressed() {
        releaseMediaPlayer();
        super.onBackPressed();
    }


    public void playNextSong()
    {
        mymMediaPlayer.pause();
        mymMediaPlayer.stop();
        mymMediaPlayer.release();

        Log.d("Test","inside");
        position=((position+1)%mySongs.size());

        Uri uri=Uri.parse(mySongs.get(position).toString());

        mymMediaPlayer=MediaPlayer.create(PlayMusic.this,uri);

        mSongName=mySongs.get(position).toString();
        songNameTxt.setText(mSongName);
        mymMediaPlayer.start();

        imageView.setBackgroundResource(R.drawable.three);

        if(mymMediaPlayer.isPlaying())
        {
            playPauseBtn.setImageResource(R.drawable.pause);
        }
        else
        {
            playPauseBtn.setImageResource(R.drawable.play);
            imageView.setBackgroundResource(R.drawable.five);
        }

    }

    public void playPreviousSong()
    {
        mymMediaPlayer.pause();
        mymMediaPlayer.stop();
        mymMediaPlayer.release();

        Log.d("Test","inside");
        position=((position-1)<0?(mySongs.size()-1):(position-1));

        Uri uri=Uri.parse(mySongs.get(position).toString());

        mymMediaPlayer=MediaPlayer.create(PlayMusic.this,uri);

        mSongName=mySongs.get(position).toString();
        songNameTxt.setText(mSongName);
        mymMediaPlayer.start();

        imageView.setBackgroundResource(R.drawable.two);

        if(mymMediaPlayer.isPlaying())
        {
            playPauseBtn.setImageResource(R.drawable.pause);
        }
        else
        {
            playPauseBtn.setImageResource(R.drawable.play);
            imageView.setBackgroundResource(R.drawable.five);
        }

    }



    ////////////////////////////////////////////
    public void initializeSpeechRecognizer() {
        if(SpeechRecognizer.isRecognitionAvailable(this))
        {
            speechRecognition=SpeechRecognizer.createSpeechRecognizer(this);
            speechRecognition.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle results) {

                    List<String> result=results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    //recognizer returns multiple results
                    processResult(result.get(0));
                    //pick result with highest score
                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }

    public void processResult(String command) {
        command=command.toLowerCase();

        //what is your name?
        //what is the time?
        //open the browser
        if(command.indexOf("play")!=-1){

            playPauseSong();

        }

        if(command.indexOf("stop")!=-1){

            mymMediaPlayer.pause();
            playPauseBtn.setImageResource(R.drawable.play);

        }

        if(command.indexOf("next")!=-1){

            playNextSong();
        }

        if(command.indexOf("previous")!=-1){

            playPreviousSong();
        }




    }


    public void initializeTextToSpeech() {

        mytexttospeech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(mytexttospeech.getEngines().size()==0)
                {
                    Toast.makeText(PlayMusic.this,"There is no TTS engine on your device",Toast.LENGTH_LONG).show();
                    finish();
                }
                else
                {
                    mytexttospeech.setLanguage(Locale.US);
//                    speak("Hello! I am ready");
                }
            }
        });
    }

//    public void speak(String s) {
//
//        if(Build.VERSION.SDK_INT>=21)
//        {
//            mytexttospeech.speak(s,TextToSpeech.QUEUE_FLUSH,null,null);
//        }
//        else
//        {
//            mytexttospeech.speak(s,TextToSpeech.QUEUE_FLUSH,null);
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onPause() {
        super.onPause();
        mytexttospeech.shutdown();
    }

    @Override
    protected void onDestroy() {


        //Close the Text to Speech Library
        if(mytexttospeech != null) {

            mytexttospeech.stop();
            mytexttospeech.shutdown();

        }
        super.onDestroy();
    }


}
