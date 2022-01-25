package com.moataz.salah.propertymanagement.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.ui.chartMonth.ChartMonthFragment;
import com.moataz.salah.propertymanagement.ui.chartYear.ChartYearFragment;

public class ChartPagerAdapter extends FragmentPagerAdapter {

    Context context ;

    public ChartPagerAdapter(FragmentManager fm , Context  context , int behavior) {
        super(fm , behavior);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
            default:
                return new ChartMonthFragment();
            case 1:
                return new ChartYearFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getResources().getString(R.string.monthly);
            case 1:
                return context.getResources().getString(R.string.annual);
        }
        return super.getPageTitle(position);
    }
}