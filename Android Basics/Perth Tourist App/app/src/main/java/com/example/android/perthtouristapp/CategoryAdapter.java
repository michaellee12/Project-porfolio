package com.example.android.perthtouristapp;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Mike Lee on 1/07/2017.
 */

public class CategoryAdapter extends FragmentPagerAdapter {
    private Context mContext;
    public CategoryAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SightFragment();
            case 1:
                return new FoodFragment();
            case 2:
                return new ShopFragment();
            case 3:
                return new NatureFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.sight);
            case 1:
                return mContext.getString(R.string.food);
            case 2:
                return mContext.getString(R.string.shop);
            case 3:
                return mContext.getString(R.string.nature);
            default:
                return null;

        }
    }

}
