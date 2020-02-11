package Astigmatic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginactivity.GestureListener;
import com.example.loginactivity.R;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ShoppingActivity extends Activity {


    TextToSpeech tts;
    TextView txtshop;
    Button btnTap;
    int j = 0, count;
    public int currentCategory = -1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        initializeTextToSpeech();


        txtshop = findViewById(R.id.txtshop);
        StringBuilder sb = new StringBuilder();
        final ArrayList<String> categoriesList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            categoriesList.add("Category : " + i);
            sb.append("Category : " + i).append("\n");
            //categories += "Category " + i;
        }

        txtshop.setText(sb.toString());

        txtshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j++;
                Toast.makeText(ShoppingActivity.this, "asd", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(j==1)
                        {

                            if(currentCategory>(categoriesList.size()-2))
                            {
                                speak("no more categories");
                                ++currentCategory;

                            }
                            else
                                speak(categoriesList.get(++currentCategory));
                        } else if(j==2)
                        {
                            if(currentCategory==0)
                            {
                                speak("invalid");
                                currentCategory--;
                            }

                            else
                                speak(categoriesList.get(--currentCategory));
                        }else if(j==3)
                        {
//                            Intent ii1 = new Intent(getApplicationContext(), Category1Activity.class);
//                            startActivity(ii1);
                        }
                        j=0;
                    }
                }, 800);
            }
        });

//        lvCategory = findViewById(R.id.lvCategories);
//        lvCategory.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, categoriesList);
//        lvCategory.setAdapter(arrayAdapter);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                speak("You are now in shopping menu. Select from the available categories. " + categories);
//                while (true) {
//                    Log.d(TAG, "onCreate: inside while");
//                    if (tts.isSpeaking()) {
//                        Log.d(TAG, "onCreate: speaking");
//                    } else {
//                        enableButton();
//                        break;
//                    }
//                }
//
//                speak("tap now ");
//            }
//        },700);
//
//
//        btnTap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                disableButton();
//                j++;
//                String msg = "Select Category.";
//                tts.speak(msg, TextToSpeech.QUEUE_ADD, null, null);
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
////                        Intent ii = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
////                        ii.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
////                        startActivityForResult(ii, 121);
//
//                        if(j==1)
//                        {
//                            Toast.makeText(ShoppingActivity.this, "1", Toast.LENGTH_SHORT).show();
//                        }
//                        if(j==2)
//                        {
//                            Toast.makeText(ShoppingActivity.this, "2", Toast.LENGTH_SHORT).show();
//                        }
//                        if(j==3){
//                            Toast.makeText(ShoppingActivity.this, "3", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                }, 1200);
//            }
//        });
    }
    //
//    private void disableButton() {
//        btnTap.setClickable(false);
//        btnTap.setEnabled(false);
//    }
//
//    private void enableButton() {
//        btnTap.setClickable(true);
//        btnTap.setEnabled(true);
//    }
//
    private void initializeTextToSpeech() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (tts.getEngines().size() == 0) {
                    Toast.makeText(ShoppingActivity.this, "there is no tts engine on your device ", Toast.LENGTH_LONG).show();
                    finish(); //to exit the app

                } else {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentCategory=-1;
    }

    private void speak(String s) {
        if (Build.VERSION.SDK_INT >= 21) {
            tts.speak(s, TextToSpeech.QUEUE_ADD, null, null);
        } else {
            tts.speak(s, TextToSpeech.QUEUE_ADD, null);
        }
    }
    //
    @Override
    public void onPause() {
        super.onPause();
        tts.shutdown();
    }
}
