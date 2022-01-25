package com.moataz.salah.propertymanagement.ui.code;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import com.moataz.salah.propertymanagement.databinding.ActivityCodeBinding;

public class CodeViewModel extends ViewModel{
    ActivityCodeBinding binding;
    Context context;
    NavController navController;


    public void intUi(ActivityCodeBinding binding , Context context , NavController navController){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;

    }

    public void getData(){



    }

}
