package com.example.confidante;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class SongsActivity extends AppCompatActivity {

    private Button button;
    private TextView song;

    //Declare audio recorder
    private static final String TAG = "@@@@";
    private static String fileName = null;
    MediaRecorder mediaRecorder = new MediaRecorder();

    String fileonserver;
    String getArtist="";
    String getSongName="";
    String streamnow_link="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        button = findViewById(R.id.detect_songs_btn);
        song = findViewById(R.id.song_detected);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recording();
            }
        });
    }

    //Music recognition part

    public void recording()  {

        Button record_btn = findViewById(R.id.detect_songs_btn);
        record_btn.setVisibility(View.GONE);


        // Record to the external cache directory for visibility
        fileName = getExternalCacheDir().getAbsolutePath();

        //Log.d("directory",fileName);
        String random_str = getAlphaNumericString(7);

        fileonserver = random_str +".mp3";

        fileName += "/" + fileonserver;

        mediaRecorder.setOutputFile(fileName);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);


        mediaRecorder.setOutputFile(fileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);


        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.e("asdasdas", "prepare() failed");
        }

        mediaRecorder.start();

    }

    public void stoprecord() throws ExecutionException, InterruptedException, IOException {
        mediaRecorder.stop();
        mediaRecorder.release();


        String url = "https://api.audd.io/?api_token=f54327070d6e835059db20b7dd006e29&url=http://ngocchaugarden.com/finaltesting/finaltesting" + fileonserver;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest Response", response.toString());

                    }
                }
                , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Rest Response", error.toString());
            }
        });

        requestQueue.add(objectRequest);
    }

    // function to generate a random string of length n
    static String getAlphaNumericString(int n){

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public void restartoncreate(View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    }

    /*try {



            JSONObject obj_result = obj.getJSONObject("result");

            String artist = obj_result.getString("artist");
            String title = obj_result.getString("title");
            String song_link = obj_result.getString("song_link");

            getArtist=artist;
            getSongName=title;
            streamnow_link = song_link;

        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + rawJSON + "\"");

        }

        if(getSongName != "") {
            songname.setText(getSongName);
            //singername.setText(getArtist);

        }


        else{
            Toast.makeText(getApplicationContext(), "Can not find a song !", Toast.LENGTH_SHORT).show();
        }


    }*/