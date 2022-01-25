package com.moataz.salah.propertymanagement.ui.Others;

import android.content.Context;
import android.os.Bundle;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.adapter.ExpensesAdapter;
import com.moataz.salah.propertymanagement.databinding.OthersFragmentBinding;
import com.moataz.salah.propertymanagement.model.expenses.ExpensesModel;

import java.util.List;

public class OthersViewModel extends ViewModel {

    OthersFragmentBinding binding;
    Context context;
    NavController navController;
    List<ExpensesModel> list ;

    public void intUi(OthersFragmentBinding binding , Context context , NavController navController , List<ExpensesModel> list ){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.list = list ;
        getData();
    }

    public void getData(){
        binding.otherRecycler.setLayoutManager(new GridLayoutManager(context , 2));
        ExpensesAdapter adapter = new ExpensesAdapter(context , list);
//        adapter.setOnClick(this);
        binding.otherRecycler.setAdapter(adapter);

    }

//    @Override
//    public void onClickListener() {
//        Bundle b = new Bundle();
//        b.putString("type" , "other");
//        navController.navigate(R.id.nav_counter_details_fragment , b);
//    }
}
