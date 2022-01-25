package com.moataz.salah.propertymanagement.ui.home;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import com.moataz.salah.propertymanagement.databinding.HomeFragmentBinding;

public class HomeViewModel extends ViewModel {

    HomeFragmentBinding binding;
    Context context;
    NavController navController;
    String currentLang ;

    public void intUi(HomeFragmentBinding binding , Context context , NavController navController , String currentLang ){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.currentLang = currentLang ;
    }

}
