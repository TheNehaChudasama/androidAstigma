package Astigmatic;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.loginactivity.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import Models.Item;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    TextToSpeech tts;
    ViewGroup view;
    Button cart;
    String category;
    int j = 0;
    private static int cartno = 0;
    Item item = new Item();
    double total;
    ViewPager viewpager;


    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public ViewGroup onCreateView(LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = (ViewGroup) inflater.inflate(R.layout.fragment_cart, container, false);

        cart = (Button) view.findViewById(R.id.cart);


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        String msg = "";
        initializeTextToSpeech();
        cartno = 0;


        if(!ItemsFragment.cartList.isEmpty()) {
            item=ItemsFragment.cartList.get(cartno) ;


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    speak(item.getName());
                    Log.d("hmm",""+item.getPrice());
                    speak("" + item.getId());
                    speak("" + item.getQuantity());
                    speak("" + item.getPrice());

                }
            },100);


            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    j++;

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (j == 1) {

                                if (cartno >= (ItemsFragment.cartList.size() - 1)) {
                                    speak("no more items");
//                                    ++cartno;
                                    if (!ItemsFragment.cartList.isEmpty())
                                        speak("total is " + total);

                                } else {
                                    item = ItemsFragment.cartList.get(++cartno);

                                    Log.d("item_frag", "onclick");

                                    speak(item.getName());

                                    speak("" + item.getId());
                                    speak("" + item.getQuantity());
                                    speak("" + item.getPrice());


                                }
                            } else if (j == 2) {
                                if (cartno <= 0) {
                                    speak("invalid");
//                                    cartno--;
                                }
//                                else if(cartno==0)
//                                {
//                                    speak("invalid");
//                                    cartno--;
//
//                                }
                                else {
                                    item = ItemsFragment.cartList.get(--cartno);
                                    speak(item.getName());
                                    speak("" + item.getId());
                                    speak("" + item.getQuantity());
                                    speak("" + item.getPrice());

                                }

                            } else if (j == 3) {
                                speak("speak remove or quantity ");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        while (true) {
                                            Log.d(TAG, "onResume: inside while");
                                            if (tts.isSpeaking()) {
                                                Log.d(TAG, "onResume: speaking");
                                            } else {
                                                Intent ii = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                                ii.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                                                startActivityForResult(ii, 101);
                                                Log.d(TAG, "onResume: break");
                                                break;
                                            }
                                        }
                                    }
                                }, 500);
                            }


                            j = 0;


                        }
                    }, 800);


                }
            });


            total = 0;
            for (Item i :
                    ItemsFragment.cartList) {
                msg = msg + "\n" + i.getId() + "\t" + i.getName() + "\t" + i.getQuantity() + "\t" + i.getPrice() + "\n";
                total = total + i.getQuantity() * i.getPrice();
            }
            cart.setText(msg);
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 101) {
            if (resultCode == RESULT_OK && data != null) {

                Log.d("Cart", "before");

                ArrayList<String> list = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Toast.makeText(getContext(), list.get(0), Toast.LENGTH_SHORT).show();
                boolean numeric = true;
                int num=0;


                if (list.get(0).equalsIgnoreCase("remove") || list.get(0).equalsIgnoreCase("delete"))
                    {
                        Log.d("Cartno",""+cartno);
                        ItemsFragment.cartList.remove(cartno);
                        Toast.makeText(getContext(), "inside_1", Toast.LENGTH_SHORT).show();
                    }

                else
                    {
                        try
                        {
                            num = Integer.parseInt(list.get(0));
                        }
                        catch (NumberFormatException e)
                        {
                            numeric = false;
                        }
                        if (numeric)
                        {
                            item = ItemsFragment.cartList.get(cartno);
                            item.setQuantity(num);
                            Toast.makeText(getContext(), "change", Toast.LENGTH_SHORT).show();

                        }
                     }
                }
            }

        }



    private void initializeTextToSpeech() {
        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                    tts.setLanguage(Locale.getDefault());

            }
        });
    }


    private void speak(String s) {
            tts.speak(s, TextToSpeech.QUEUE_ADD, null, null);

    }
}


