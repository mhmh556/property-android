package com.moataz.salah.propertymanagement.ui.forgetName;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import com.moataz.salah.propertymanagement.databinding.ActivityForgetUserNameBinding;

public class ForgetNameViewModel extends ViewModel{
    ActivityForgetUserNameBinding binding;
    Context context;
    NavController navController;


    public void intUi(ActivityForgetUserNameBinding binding , Context context , NavController navController){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;

    }

    public void getData(){



    }

}
