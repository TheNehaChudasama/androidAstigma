package Astigmatic;

import android.app.Activity;
import android.content.Intent;
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

public class MainActivity extends Activity implements TextToSpeech.OnInitListener {

    Button btnTap;
    TextToSpeech tts;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                speak("Tap now.");
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
        }, 2000);

        btnTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (i == 1) {
                            Toast.makeText(MainActivity.this, "One", Toast.LENGTH_SHORT).show();
                            Intent i1=new Intent(getApplicationContext(), ShoppingTabActivity.class);
                            startActivity(i1);
                        } else if (i == 2) {
                            Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
                            Intent i1=new Intent(getApplicationContext(),GPSActivity.class);
                           startActivity(i1);
                        } else if (i == 3) {
                            Toast.makeText(MainActivity.this, "3", Toast.LENGTH_SHORT).show();
                            Intent i1=new Intent(getApplicationContext(),ReadingActivity.class);
                        startActivity(i1);
                        } else if (i == 4) {
                            Toast.makeText(MainActivity.this, "4", Toast.LENGTH_SHORT).show();
                        }
                        i = 0;
                    }
                }, 800);
            }
        });
    }

    private void enableButton() {
        btnTap.setClickable(true);
        btnTap.setEnabled(true);
    }


    private void initializeTextToSpeech() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (tts.getEngines().size() == 0) {
                    Toast.makeText(MainActivity.this, "there is no tts engine on your device ", Toast.LENGTH_LONG).show();
                    finish(); //to exit the app

                } else {
                    tts.setLanguage(Locale.US);
//                    speak("hello");


                }
            }
        });
    }

    private void speak(String s) {
        disableButton();
        if (Build.VERSION.SDK_INT >= 21) {
            tts.speak(s, TextToSpeech.QUEUE_ADD, null, null);
        } else {
            tts.speak(s, TextToSpeech.QUEUE_ADD, null);
        }

    }

    private void disableButton() {
        btnTap.setClickable(false);
        btnTap.setEnabled(false);
    }

    @Override
    public void onInit(int i) {

    }
    @Override
    public void onPause()
    {
        super.onPause();
        tts.shutdown();

    }
}
