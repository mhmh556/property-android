package com.moataz.salah.propertymanagement.ui.yearCounter;

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
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.databinding.YearCounterDetailsFragmentBinding;
import com.moataz.salah.propertymanagement.model.bill.BillModel;
import java.util.ArrayList;
import java.util.List;

public class YearCounterFragment extends Fragment {

    private String TAG = "HomeFragment";

    YearCounterFragmentModelFactory factory;
    YearCounterViewModel homeViewModel;
    YearCounterDetailsFragmentBinding binding;
    List<BillModel> list ;
    NavController navController  = null ;
    UserPreference userPreference ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = YearCounterDetailsFragmentBinding.inflate(inflater,container,false);
        userPreference = new UserPreference(getContext());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        factory = new YearCounterFragmentModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(YearCounterViewModel.class);
        navController = Navigation.findNavController(binding.getRoot());

        list = new ArrayList<>();

        homeViewModel.intUi(binding , getContext() , navController , list , userPreference);


    }

}