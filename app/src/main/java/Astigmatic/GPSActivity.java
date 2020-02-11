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
import android.widget.Button;
import android.widget.Toast;

import com.example.loginactivity.R;

import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class GPSActivity extends AppCompatActivity {
    Button btnTap;
    TextToSpeech tts;
    int i = 0;
    Double lat = 0.0d,longt = 0.0d;
    Location gpsLoc = null , netLoc=null , finalLoc = null;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        initializeTextToSpeech();



        btnTap = findViewById(R.id.btnTap);
        disableButton();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.d(TAG, "onCreate: inside while");
                    if (tts.isSpeaking()) {
                        Log.d(TAG, "onCreate: speaking");
                    } else {
                        Log.d(TAG, "onCreate: break");
                        break;
                    }
                }
                Log.d(TAG, "onCreate: outside while");
                speak(" Tap once for store the current location. Tap twice for access the saved location. Tap now.");
                Log.d(TAG, "onCreate: after speak");

                while (true) {
                    Log.d(TAG, "onCreate: inside while");
                    if (tts.isSpeaking()) {
                        Log.d(TAG, "onCreate: speaking");
                    } else {
                        enableButton();
                        break;
                    }
                }
            }

            private void enableButton() {
                btnTap.setClickable(true);
                btnTap.setEnabled(true);

            }
        }, 2000);

        btnTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (i == 1) {
//                            Toast.makeText(GPSActivity.this, "1", Toast.LENGTH_SHORT).show();
                            /// write the code to store the current location
                            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


                            if(locationManager != null) {
                                gpsLoc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                netLoc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                                if(netLoc != null) {
                                    finalLoc = netLoc;
                                    lat = finalLoc.getLatitude();
                                    longt = finalLoc.getLongitude();
                                } else if(gpsLoc != null) {
                                    finalLoc = gpsLoc;
                                    lat = finalLoc.getLatitude();
                                    longt = finalLoc.getLongitude();
                                }

//                                Uri gmmIntentUri = Uri.parse("google.navigation:q=22.3297282,73.2052667");
//                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                                mapIntent.setPackage("com.google.android.apps.maps");
//                                startActivity(mapIntent);

                               Toast.makeText(getApplicationContext(), ""+ lat + "," + finalLoc.getLongitude(), Toast.LENGTH_SHORT).show();
                            }



                        } else if (i == 2) {
                            Toast.makeText(GPSActivity.this, "2", Toast.LENGTH_SHORT).show();
                           // write code for access location whicha are save in application
                            Intent ii =new Intent(getApplicationContext(),GPSAccessActivity.class);
                            startActivity(ii);
                        }
                        i = 0;
                    }
                }, 800);
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

//


    }

    private void speak(String s) {
        disableButton();
        if (Build.VERSION.SDK_INT >= 21) {
            tts.speak(s, TextToSpeech.QUEUE_ADD, null, null);
        } else {
            tts.speak(s, TextToSpeech.QUEUE_ADD, null);
        }
    }

    private void disableButton()
    {
        btnTap.setClickable(false);
        btnTap.setEnabled(false);
    }

    private void initializeTextToSpeech()
    {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (tts.getEngines().size() == 0) {
                    Toast.makeText(GPSActivity.this, "there is no tts engine on your device ", Toast.LENGTH_LONG).show();
                    finish(); //to exit the app

                } else {
                    tts.setLanguage(Locale.US);
                    speak("welcome in GPS activity");


                }
            }
        });

    }

    @Override
    public void onPause()
    {
        super.onPause();
        tts.shutdown();
    }

}

