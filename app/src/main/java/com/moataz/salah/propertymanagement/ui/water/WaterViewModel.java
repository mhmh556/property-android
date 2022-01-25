package com.moataz.salah.propertymanagement.ui.water;

import android.content.Context;
import android.os.Bundle;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.adapter.ExpensesAdapter;
import com.moataz.salah.propertymanagement.databinding.WaterFragmentBinding;
import com.moataz.salah.propertymanagement.model.expenses.ExpensesModel;

import java.util.List;

public class WaterViewModel extends ViewModel {

    WaterFragmentBinding binding;
    Context context;
    NavController navController;
    List<ExpensesModel> list ;

    public void intUi(WaterFragmentBinding binding , Context context , NavController navController , List<ExpensesModel> list ){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.list = list ;
        getData();
    }

    public void getData(){
        binding.waterRecycler.setLayoutManager(new GridLayoutManager(context , 2));
        ExpensesAdapter adapter = new ExpensesAdapter(context , list);
        binding.waterRecycler.setAdapter(adapter);
    }

}
