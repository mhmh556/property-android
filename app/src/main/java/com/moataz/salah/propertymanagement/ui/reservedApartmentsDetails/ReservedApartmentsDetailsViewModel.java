package com.moataz.salah.propertymanagement.ui.reservedApartmentsDetails;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import com.moataz.salah.propertymanagement.databinding.ReservedApartmentsDetailsFragmentBinding;

public class ReservedApartmentsDetailsViewModel extends ViewModel {

    ReservedApartmentsDetailsFragmentBinding binding;
    Context context;
    NavController navController;
    String currentLang ;

    public void intUi(ReservedApartmentsDetailsFragmentBinding binding , Context context , NavController navController , String currentLang ){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.currentLang = currentLang ;
        getData();
    }

    public void getData(){
    }

}
