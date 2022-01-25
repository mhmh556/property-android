package com.moataz.salah.propertymanagement.ui.Others;

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
import com.moataz.salah.propertymanagement.databinding.OthersFragmentBinding;
import com.moataz.salah.propertymanagement.model.expenses.ExpensesModel;

import java.util.ArrayList;
import java.util.List;

public class OthersFragment extends Fragment {

    private String TAG = "HomeFragment";

    OthersFragmentModelFactory factory;
    OthersViewModel homeViewModel;
    OthersFragmentBinding binding;
    NavController navController  = null ;
    List<ExpensesModel> list ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = OthersFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        factory = new OthersFragmentModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(OthersViewModel.class);
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