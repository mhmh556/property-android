package com.moataz.salah.propertymanagement.ui.counter;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import com.moataz.salah.propertymanagement.adapter.TestAdapter;
import com.moataz.salah.propertymanagement.databinding.CounterFragmentBinding;
import java.util.List;

public class CounterViewModel extends ViewModel {

    CounterFragmentBinding binding;
    Context context;
    NavController navController;
    List<String> newsDataModelList;
    String currentLang ;

    public void intUi(CounterFragmentBinding binding , Context context , NavController navController , List<String> newsDataModelList , String currentLang ){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.newsDataModelList = newsDataModelList;
        this.currentLang = currentLang ;
        getData();
    }

    public void getData(){
        TestAdapter apartmentAdapter = new TestAdapter(context , newsDataModelList);
        binding.recyclerView4.setLayoutManager(new GridLayoutManager(context , 1));
        binding.recyclerView4.setAdapter(apartmentAdapter);
    }

}
