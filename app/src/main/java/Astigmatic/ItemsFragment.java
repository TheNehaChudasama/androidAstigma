package Astigmatic;


import android.icu.text.Edits;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;

import Models.Item;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsFragment extends Fragment {



    String category;
    Button btnItemsList;
    TextToSpeech tts;
    public static ArrayList<Item> cartList=new ArrayList<Item>();
    Item item=new Item();
    int currentcategory=0,j=0,currentItem=0;
    public ItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_items, container, false);

        btnItemsList = (Button) view.findViewById(R.id.btnItemsList);

//        btnItemsList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               Toast.makeText(getContext(),CategoriesFragment.categoriesMap.get(CategoriesFragment.currentCategory == -1 ? 0 : CategoriesFragment.currentCategory),Toast.LENGTH_LONG).show();
//            }
//        }

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("item", "onResume: " + CategoriesFragment.currentCategory);
    Toast.makeText(getContext(),CategoriesFragment.categoriesList.get(CategoriesFragment.currentCategory == -1 ? 0 : CategoriesFragment.currentCategory),Toast.LENGTH_LONG).show();
        category=CategoriesFragment.categoriesList.get(CategoriesFragment.currentCategory==-1?0:CategoriesFragment.currentCategory);

        initializeTextToSpeech();


        Iterator<Item>iter2=null;
        Iterator<Item>iter3=null;
        Iterator<Item>iter1= null;

        if(null != CategoriesFragment.list1 && !CategoriesFragment.list1.isEmpty()) {
            iter1 = CategoriesFragment.list1.iterator();
            final ArrayList<Item> arrayList = CategoriesFragment.list1;

                btnItemsList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        j++;

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (j == 1) {

                                    if (currentItem >= (arrayList.size() - 1)) {
                                        speak("no more items");
//                                        ++currentItem;

                                    } else {
                                        item=arrayList.get(++currentItem);

                                        Log.d("item_frag","onclick");

                                        speak(item.getName());

                                        speak(""+item.getId());
                                        speak(""+item.getQuantity());
                                        speak(""+item.getPrice());


                                    }
                                } else if (j == 2) {
                                    if (currentItem <= 0) {
                                        speak("invalid");
//                                        currentItem--;
                                    } else {
                                        item=arrayList.get(--currentItem);
                                        speak(item.getName());
                                        speak(""+item.getId());
                                        speak(""+item.getQuantity());
                                        speak(""+item.getPrice());

                                    }

                                } else if (j == 3) {
                                    item=arrayList.get(currentItem);

                                    cartList.add(item);

                                }


                                j=0;




                            }
                        }, 800);


                    }
                });

            }

        if(null != CategoriesFragment.list2) {
            iter2 = CategoriesFragment.list2.iterator();
        }
        if(null != CategoriesFragment.list3) {
            iter3 = CategoriesFragment.list3.iterator();
        }



        String msg="";


        if(category.equalsIgnoreCase("Category : 1")|| CategoriesFragment.currentCategory==-1)
        {
            Toast.makeText(getContext(),""+category,Toast.LENGTH_SHORT).show();

            if(iter1!=null) {
                while (iter1.hasNext()) {
                    item = iter1.next();

                    msg = msg + "\n" + item.getId() + "\t" + item.getName() + "\t" + item.getQuantity() + "\t" + item.getPrice() + "\n";

                }
            }
            currentcategory=1;
            btnItemsList.setText(msg);
        }
        else if(category.equalsIgnoreCase("Category : 2"))
        {
            Toast.makeText(getContext(),""+category,Toast.LENGTH_SHORT).show();

            if(iter2!=null) {
                while (iter2.hasNext()) {
                    item = iter2.next();

                    msg = msg + "\n" + item.getId() + "\t" + item.getName() + "\t" + item.getQuantity() + "\t" + item.getPrice() + "\n";

                }
            }
            currentcategory=2;
           btnItemsList.setText(msg);


        }
        else if(category.equalsIgnoreCase("Category : 3"))
        {
            Toast.makeText(getContext(),""+category,Toast.LENGTH_SHORT).show();
            if(iter3!=null) {
                while (iter3.hasNext()) {
                    item = iter3.next();

                    msg = msg + "\n" + item.getId() + "\t" + item.getName() + "\t" + item.getQuantity() + "\t" + item.getPrice() + "\n";

                }
            }
            currentcategory=3;
           btnItemsList.setText(msg);

        }
        else
        {

        }
        btnItemsList.setText(msg);

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


    public static ItemsFragment newInstance() {
        ItemsFragment fragment = new ItemsFragment();
        return fragment;
    }
}
