package com.moataz.salah.propertymanagement.ui.contactUsDetails;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.databinding.ContactUsDetailsBinding;

public class ContactUsDetailsViewModel extends ViewModel {

    ContactUsDetailsBinding binding;
    Context context;
    NavController navController;
    String currentLang ;
    UserPreference preference ;

    public void intUi(ContactUsDetailsBinding binding , Context context , NavController navController , String currentLang ,
                      UserPreference preference){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.currentLang = currentLang ;
        this.preference = preference ;
    }

}
