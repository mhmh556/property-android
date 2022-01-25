package com.moataz.salah.propertymanagement.ui.chartDetails;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import com.moataz.salah.propertymanagement.databinding.ChartDetailsFragmentBinding;

public class ChartDetailsViewModel extends ViewModel {

    ChartDetailsFragmentBinding binding;
    Context context;
    NavController navController;
    String currentLang ;

    public void intUi(ChartDetailsFragmentBinding binding , Context context , NavController navController , String currentLang ){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.currentLang = currentLang ;
        getData();
    }

    public void getData(){

    }

}
