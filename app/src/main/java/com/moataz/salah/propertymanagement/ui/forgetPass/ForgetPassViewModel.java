package com.moataz.salah.propertymanagement.ui.forgetPass;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import com.moataz.salah.propertymanagement.databinding.ActivityForgetPasswordBinding;

public class ForgetPassViewModel extends ViewModel{
    ActivityForgetPasswordBinding binding;
    Context context;
    NavController navController;


    public void intUi(ActivityForgetPasswordBinding binding , Context context , NavController navController){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;

    }

    public void getData(){



    }

}
