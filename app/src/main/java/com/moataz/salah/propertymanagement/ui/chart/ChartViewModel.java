package com.moataz.salah.propertymanagement.ui.chart;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.ChartFragmentBinding;
import java.util.ArrayList;

public class ChartViewModel extends ViewModel {

    ChartFragmentBinding binding;
    Context context;
    NavController navController;
    String currentLang ;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList<BarEntry> barEntries;

    public void intUi(ChartFragmentBinding binding , Context context , NavController navController , String currentLang ){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.currentLang = currentLang ;
        getData();
    }

    public void getData(){
//        setData();
        barDataSet = new BarDataSet(barEntries , "");
        barData = new BarData(barDataSet);
        binding.barGraph.setData(barData);
        barDataSet.setColor(context.getResources().getColor(R.color.ddd));
        barDataSet.setValueTextColor(context.getResources().getColor(R.color.ddd));
        barDataSet.setValueTextSize(10f);
        barDataSet.setLabel(context.getResources().getString(R.string.label));

    }

//    public void setData(){
//        barEntries = new ArrayList<BarEntry>();
//        barEntries.add(new BarEntry(1f , 200));
//        barEntries.add(new BarEntry(2f , 800));
//        barEntries.add(new BarEntry(3f , 600));
//        barEntries.add(new BarEntry(4f , 400));
//        barEntries.add(new BarEntry(5f , 2000));
//        barEntries.add(new BarEntry(6f , 1200));
//        barEntries.add(new BarEntry(7f , 2200));
//        barEntries.add(new BarEntry(8f , 1600));
//        barEntries.add(new BarEntry(9f , 1800));
//        barEntries.add(new BarEntry(10f , 1000));
//        barEntries.add(new BarEntry(11f , 1400));
//        barEntries.add(new BarEntry(12f , 2400));
//
//
//    }

    private void setData(int count, float range) {

        float start = 1f;

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = (int) start; i < start + count; i++) {
            float val = (float) (Math.random() * (range + 1));

            if (Math.random() * 100 < 25) {
                values.add(new BarEntry(i, val, context.getResources().getDrawable(R.drawable.add_shape)));
            } else {
                values.add(new BarEntry(i, val));
            }
        }

        BarDataSet set1;

        if (binding.barGraph.getData() != null &&
                binding.barGraph.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) binding.barGraph.getData().getDataSetByIndex(0);
            set1.setValues(values);
            binding.barGraph.getData().notifyDataChanged();
            binding.barGraph.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(values, "The year 2017");

            set1.setDrawIcons(false);

//            int startColor1 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
//            int startColor2 = ContextCompat.getColor(this, android.R.color.holo_blue_light);
//            int startColor3 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
//            int startColor4 = ContextCompat.getColor(this, android.R.color.holo_green_light);
//            int startColor5 = ContextCompat.getColor(this, android.R.color.holo_red_light);
//            int endColor1 = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
//            int endColor2 = ContextCompat.getColor(this, android.R.color.holo_purple);
//            int endColor3 = ContextCompat.getColor(this, android.R.color.holo_green_dark);
//            int endColor4 = ContextCompat.getColor(this, android.R.color.holo_red_dark);
//            int endColor5 = ContextCompat.getColor(this, android.R.color.holo_orange_dark);
//
//            List<Fill> gradientFills = new ArrayList<>();
//            gradientFills.add(new Fill(startColor1, endColor1));
//            gradientFills.add(new Fill(startColor2, endColor2));
//            gradientFills.add(new Fill(startColor3, endColor3));
//            gradientFills.add(new Fill(startColor4, endColor4));
//            gradientFills.add(new Fill(startColor5, endColor5));
//
//            set1.setFills(gradientFills);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
//            data.setValueTypeface(tfLight);
            data.setBarWidth(0.9f);

            binding.barGraph.setData(data);
        }
    }
}
