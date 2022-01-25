package com.moataz.salah.propertymanagement.ui.vacant;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.adapter.PropertyAdapter;
import com.moataz.salah.propertymanagement.databinding.VacantFragemntBinding;
import com.moataz.salah.propertymanagement.model.property.PropertyModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VacantFragment extends Fragment {

    private String TAG = "HomeFragment";

    VacantFragmentModelFactory factory;
    VacantViewModel homeViewModel;
    VacantFragemntBinding binding;
    BottomNavigationView bottom_nav;
    NavController navController  = null ;
    ImageView back , logo;
    List<PropertyModel> list;
    TextView toolbar_title;
    Toolbar toolbar;
    String currentLang = Locale.getDefault().getLanguage();
    UserPreference userPreference;
    String token ;
    String api_key ;
    int property_view , reservation_view ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = VacantFragemntBinding.inflate(inflater,container,false);
        back = getActivity().findViewById(R.id.back_bt);
        logo = getActivity().findViewById(R.id.logo);
        bottom_nav = getActivity().findViewById(R.id.bottom_nav);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar = getActivity().findViewById(R.id.toolbar);
        userPreference = new UserPreference(getContext());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        factory = new VacantFragmentModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(VacantViewModel.class);
        navController = Navigation.findNavController(binding.getRoot());
        toolbar_title.setVisibility(View.GONE);
        logo.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        bottom_nav.setVisibility(View.VISIBLE);
        back.setImageResource(R.drawable.ic_iconly_light_notification);
        toolbar_title.setText(navController.getCurrentDestination().getLabel());
        toolbar.setNavigationIcon(R.drawable.ic_icon_menu);
        toolbar.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        token = userPreference.getToken();
        api_key = userPreference.getApiKey();
        property_view = userPreference.getApp().getPropertyView();
        list = new ArrayList<>();

        Log.e("add" , String.valueOf(userPreference.getApp().getPropertyAdd()));

        if (userPreference.getApp().getPropertyAdd() == 0){
            binding.addNewBt.setVisibility(View.GONE);
        }else if (userPreference.getApp().getPropertyAdd() == 1){
            binding.addNewBt.setVisibility(View.VISIBLE);
        }

        binding.addNewBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("key" , "new");
                b.putString("type" , "vacant");
                navController.navigate(R.id.nav_add_new_flat_fragment , b);
            }
        });

        homeViewModel.intUi(binding,getContext(),navController,list,currentLang , token , api_key);
        homeViewModel.getData();

    }
}