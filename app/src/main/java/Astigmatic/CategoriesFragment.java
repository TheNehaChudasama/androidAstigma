package Astigmatic;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.loginactivity.R;
import com.example.loginactivity.ui.main.PlaceholderFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import Models.Item;

import static android.provider.MediaStore.Video.VideoColumns.CATEGORY;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {
    TextToSpeech tts;
    Button txtshop;
    int j = 0;
    public static  int currentCategory = 0;
    public static ArrayList<String> categoriesList = null;
    public static Item item;
    public static ArrayList<Item>list1=null;
    public static ArrayList<Item>list2=null;
    public static ArrayList<Item>list3=null;





    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //     return inflater.inflate(R.layout.fragment_categories2, container, false);
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        initializeTextToSpeech();

        txtshop = (Button) view.findViewById(R.id.txtshop);
        StringBuilder sb = new StringBuilder();
        categoriesList = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            categoriesList.add("Category : " + i);
            sb.append("Category : " + i).append("\n");
//            categories += "Category " + i;
        }

        txtshop.setText(sb.toString());




        txtshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                j++;
                //  Toast.makeText(ShoppingActivity.this, "asd", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (j == 1) {

                            if (currentCategory >= (categoriesList.size() - 1)) {
                                speak("no more categories");
//                                ++currentCategory;

                            } else {
                                speak(categoriesList.get(++currentCategory));
                            }
                        } else if (j == 2)
                        {

                            if (currentCategory <= 0)

                                speak("invalid");



                            else
                                {
                                speak(categoriesList.get(--currentCategory));
                                }

                        }
                        else if (j == 3) {

//                            Intent ii1 = new Intent(getApplicationContext(), Category1Activity.class);
//                            startActivity(ii1);
                        }


                        j=0;


                        if(currentCategory==0)
                        {
                            list1 = new ArrayList<>();

                            list1.add(new Item(1,"dhossa",1,200.0));
                            list1.add(new Item(2,"uttappa",2,90));
                            list1.add(new Item(3,"idli",3,500));

                        }

                        else if(currentCategory==1) {
                            list2 = new ArrayList<>();


                            list2.add(new Item(1,"pizza",10,100));
                          list2.add(new Item(2,"garlic bread",2,90));
                          list2.add(new Item(3,"sandwich",4,60));


                        }

                        else if(currentCategory==2){
                            list3 = new ArrayList<>();

                            list3.add(new Item(1,"dal-dhokdi",2,40));
                           list3.add(new Item(2,"dal-bhat",1,50));
                        }
                        else {

                        }



                    }
                }, 800);






            }
        });




        return view;

    }


    @Override
    public void onResume() {
        super.onResume();
        currentCategory = -1;
        Log.d("Category", "onResume: ");
    }



    private void initializeTextToSpeech() {
        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (tts.getEngines().size() == 0) {
                    //      Toast.makeText(this, "there is no tts engine on your device ", Toast.LENGTH_LONG).show();
                    //finish(); //to exit the app

                } else {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });
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

    public static Fragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
        return fragment;
    }

}
