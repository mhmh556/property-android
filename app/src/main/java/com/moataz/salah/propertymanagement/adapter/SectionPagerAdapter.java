package com.moataz.salah.propertymanagement.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.ui.Others.OthersFragment;
import com.moataz.salah.propertymanagement.ui.electricity.ElectricityFragment;
import com.moataz.salah.propertymanagement.ui.water.WaterFragment;

public class SectionPagerAdapter extends FragmentPagerAdapter {

    ElectricityFragment electricityFragment = new ElectricityFragment();
    WaterFragment waterFragment = new WaterFragment();
    OthersFragment othersFragment = new OthersFragment();
    Context context ;

    public SectionPagerAdapter(FragmentManager fm , Context  context , int behavior) {
        super(fm , behavior);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return electricityFragment;
            case 1:
            default:
                return waterFragment;
            case 2:
                return othersFragment;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getResources().getString(R.string.electricity);
            case 1:
                return context.getResources().getString(R.string.water);
            case 2:
                return context.getResources().getString(R.string.other);

        }
        return super.getPageTitle(position);
    }
}