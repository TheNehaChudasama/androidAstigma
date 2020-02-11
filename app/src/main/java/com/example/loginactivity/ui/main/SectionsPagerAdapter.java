package com.example.loginactivity.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

//import com.example.loginactivity.Category;
import com.example.loginactivity.R;

import java.util.HashMap;
import java.util.Map;

import Astigmatic.CartFragment;
///import Astigmatic.Categories;
import Astigmatic.CategoriesFragment;
import Astigmatic.ItemsFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;
    public static int fragPosition = -1;
    public  Map<Integer, String> mFragmentTags = new HashMap<>();
    public FragmentManager mFragmentManager;


    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
//        return PlaceholderFragment.newInstance(position + 1);
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment = CategoriesFragment.newInstance();
                break;
            case 1:
                fragment= new ItemsFragment();
                break;
            case 2:
                fragment= new CartFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Object obj = super.instantiateItem(container,position);
        if(obj instanceof  Fragment) {

            Fragment fragment=(Fragment)obj;
            String tag= fragment.getTag();
            mFragmentTags.put(position,tag);

        }
        return obj;
    }
    public Fragment getFragment(int position)
    {
        String tag=mFragmentTags.get(position);
        if(tag==null)
            return null;
        return mFragmentManager.findFragmentByTag(tag);
    }
}