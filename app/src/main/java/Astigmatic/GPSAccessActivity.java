package Astigmatic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.loginactivity.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class GPSAccessActivity extends AppCompatActivity
{
    ListView lvLocation;
    List<String> Locationlist;
    TextToSpeech tts;
    String Locations="";
    Button btnTap;
    Double lat = 0.0d,longt = 0.0d;
    Location gpsLoc = null , netLoc=null , finalLoc = null;
    int j;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsaccess);
        initializeTextToSpeech();
        btnTap = findViewById(R.id.btnLocationTap);
        disableButton();

        Locationlist = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Locationlist.add("Location : " + i);
            Locations += "Location " + i;
        }

        lvLocation = findViewById(R.id.lvLocations);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Locationlist);
        lvLocation.setAdapter(arrayAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                speak("Access location  from this list. " + Locations);
                while (true) {
                    Log.d(TAG, "onCreate: inside while");
                    if (tts.isSpeaking()) {
                        Log.d(TAG, "onCreate: speaking");
                    } else {
                        enableButton();
                        break;
                    }
                }

                speak("tap now ");
            }
        },700);


        btnTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableButton();
                j++;
//                String msg = "Select Access location.";
//                tts.speak(msg, TextToSpeech.QUEUE_ADD, null, null);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        Intent ii = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//                        ii.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//                        startActivityForResult(ii, 121);
                        if(j==1)
                        {
                            Uri gmmIntentUri = Uri.parse("google.navigation:q=22.3297282,73.2052667");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                        }
                        if(j==2)
                        {

                        }
                        if(j==3)
                        {

                        }

                    }
                }, 1200);
            }
        });




//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//
//        if(locationManager != null) {
//            gpsLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            netLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//
//            if(netLoc != null) {
//                finalLoc = netLoc;
//                lat = finalLoc.getLatitude();
//                longt = finalLoc.getLongitude();
//            } else if(gpsLoc != null) {
//                finalLoc = gpsLoc;
//                lat = finalLoc.getLatitude();
//                longt = finalLoc.getLongitude();
//            }
//
//            Uri gmmIntentUri = Uri.parse("google.navigation:q=22.3297282,73.2052667");
//            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//            mapIntent.setPackage("com.google.android.apps.maps");
//            startActivity(mapIntent);
//
//            Toast.makeText(this, ""+ lat + "," + finalLoc.getLongitude(), Toast.LENGTH_SHORT).show();
//        }




    }

    private void initializeTextToSpeech() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (tts.getEngines().size() == 0) {
                    Toast.makeText(GPSAccessActivity.this, "there is no tts engine on your device ", Toast.LENGTH_LONG).show();
                    finish(); //to exit the app

                } else {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });
    }

    private void disableButton() {
        btnTap.setClickable(false);
        btnTap.setEnabled(false);
    }


    private void enableButton() {
        btnTap.setClickable(true);
        btnTap.setEnabled(true);
    }

    private void speak(String s) {
        if (Build.VERSION.SDK_INT >= 21) {
            tts.speak(s, TextToSpeech.QUEUE_ADD, null, null);
        } else {
            tts.speak(s, TextToSpeech.QUEUE_ADD, null);
        }
    }


}
