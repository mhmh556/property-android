package com.moataz.salah.propertymanagement.ui.water;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.WaterFragmentBinding;
import com.moataz.salah.propertymanagement.model.expenses.ExpensesModel;

import java.util.ArrayList;
import java.util.List;

public class WaterFragment extends Fragment {

    private String TAG = "HomeFragment";

    WaterFragmentModelFactory factory;
    WaterViewModel homeViewModel;
    WaterFragmentBinding binding;
    List<ExpensesModel> list ;
    NavController navController  = null ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = WaterFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        factory = new WaterFragmentModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(WaterViewModel.class);
        navController = Navigation.findNavController(binding.getRoot());

        list = new ArrayList<>();
//        list.add("2");
//        list.add("2");
//        list.add("2");
//        list.add("2");
//        list.add("2");
//        list.add("2");
//        list.add("2");

        homeViewModel.intUi(binding , getContext() , navController , list);

    }

}