package com.moataz.salah.propertymanagement.ui.chartYear;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.ChartYearFragmentBinding;
import java.util.ArrayList;

public class ChartYearViewModel extends ViewModel {

    ChartYearFragmentBinding binding;
    Context context;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList<BarEntry> barEntries;

    public void intUi(ChartYearFragmentBinding binding , Context context ){
        this.binding = binding;
        this.context = context ;
        getData();
    }

    public void getData(){
        setData();
        barDataSet = new BarDataSet(barEntries , "");
        barData = new BarData(barDataSet);
        binding.barGraph.setData(barData);
        barDataSet.setColor(context.getResources().getColor(R.color.ddd));
        barDataSet.setValueTextColor(context.getResources().getColor(R.color.ddd));
        barDataSet.setValueTextSize(10f);
        barDataSet.setLabel(context.getResources().getString(R.string.label));

    }

    public void setData(){
        barEntries = new ArrayList<BarEntry>();
        barEntries.add(new BarEntry(1f , 200));
        barEntries.add(new BarEntry(2f , 800));
        barEntries.add(new BarEntry(3f , 600));
        barEntries.add(new BarEntry(4f , 400));
        barEntries.add(new BarEntry(5f , 2000));
        barEntries.add(new BarEntry(6f , 1200));
        barEntries.add(new BarEntry(7f , 2200));
        barEntries.add(new BarEntry(8f , 1600));
        barEntries.add(new BarEntry(9f , 1800));
        barEntries.add(new BarEntry(10f , 1000));
        barEntries.add(new BarEntry(11f , 1400));
        barEntries.add(new BarEntry(12f , 2400));

    }

}
