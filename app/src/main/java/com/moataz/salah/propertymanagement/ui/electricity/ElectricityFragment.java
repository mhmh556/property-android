package com.moataz.salah.propertymanagement.ui.electricity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.adapter.ElectricAdapter;
import com.moataz.salah.propertymanagement.databinding.ElectricityFragmentBinding;
import com.moataz.salah.propertymanagement.model.electrical.ElectricalModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.ui.generalExpenses.GeneralExpensesFragment;
import java.util.ArrayList;
import java.util.List;

public class ElectricityFragment extends Fragment {

    private String TAG = "HomeFragment";

    ElectricityFragmentModelFactory factory;
    ElectricityViewModel homeViewModel;
    ElectricityFragmentBinding binding;
    List<ElectricalModel> list ;
    NavController navController  = null ;
    ApiInterface apiInterface ;
    ElectricAdapter adapter ;
    UserPreference preference ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = ElectricityFragmentBinding.inflate(inflater,container,false);
        preference = new UserPreference(getContext());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        factory = new ElectricityFragmentModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(ElectricityViewModel.class);
        navController = Navigation.findNavController(binding.getRoot());

        if (preference.getApp().getDeviceMeterAdd() == 0){
            binding.addNewBt.setVisibility(View.GONE);
        }else if (preference.getApp().getDeviceMeterAdd() == 1){
            binding.addNewBt.setVisibility(View.VISIBLE);
        }

        list = new ArrayList<>();
        adapter = new ElectricAdapter(getContext() , list);
        homeViewModel.intUi(binding , getContext() , navController , apiInterface , preference , list , adapter);


        GeneralExpensesFragment navHostFragment = (GeneralExpensesFragment) getParentFragment();
        Fragment parent = (Fragment) navHostFragment.getParentFragment();
        SearchView searchView = parent.getView().findViewById(R.id.search_input);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("query" , query);
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        binding.addNewBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle b = new Bundle();
//                b.putString("type" , "electric");
//                b.putString("action" , "add");
//                navController.navigate(R.id.nav_add_counter_fragment , b);
            }
        });

    }

}