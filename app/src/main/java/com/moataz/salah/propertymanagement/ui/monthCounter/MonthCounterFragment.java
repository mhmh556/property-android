package com.moataz.salah.propertymanagement.ui.monthCounter;

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
import com.moataz.salah.propertymanagement.databinding.MonthConterDetailsFragmentBinding;
import com.moataz.salah.propertymanagement.model.bill.BillModel;
import java.util.ArrayList;
import java.util.List;

public class MonthCounterFragment extends Fragment {

    private String TAG = "HomeFragment";

    MonthCounterFragmentModelFactory factory;
    MonthCounterViewModel homeViewModel;
    MonthConterDetailsFragmentBinding binding;
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
        binding = MonthConterDetailsFragmentBinding.inflate(inflater,container,false);
        userPreference = new UserPreference(getContext());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        factory = new MonthCounterFragmentModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(MonthCounterViewModel.class);
        navController = Navigation.findNavController(binding.getRoot());

        list = new ArrayList<>();

        homeViewModel.intUi(binding , getContext() , navController , list , userPreference);


    }

}