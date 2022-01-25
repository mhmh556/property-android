package com.moataz.salah.propertymanagement.ui.reservedApartments;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.moataz.salah.propertymanagement.databinding.VacantFragemntBinding;
import com.moataz.salah.propertymanagement.model.reserve.ReservationModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReservedApartmentsFragment extends Fragment {

    private String TAG = "HomeFragment";

    ReservedApartmentsModelFactory factory;
    ReservedApartmentsViewModel homeViewModel;
    VacantFragemntBinding binding;
    BottomNavigationView bottom_nav;
    NavController navController  = null ;
    ImageView back , logo;
    List<ReservationModel> list;
    TextView toolbar_title;
    Toolbar toolbar;
    String currentLang = Locale.getDefault().getLanguage();
    String token;
    UserPreference userPreference ;
    String api_key ;
    String key ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        fullScreen();
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
        factory = new ReservedApartmentsModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(ReservedApartmentsViewModel.class);
        navController = Navigation.findNavController(binding.getRoot());
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        toolbar_title.setLayoutParams(params);
        toolbar_title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        bottom_nav.setVisibility(View.VISIBLE);
        back.setImageResource(R.drawable.ic_back);
        logo.setVisibility(View.GONE);
        toolbar_title.setText(navController.getCurrentDestination().getLabel());
        toolbar.setNavigationIcon(null);
        toolbar.setBackgroundColor(getActivity().getResources().getColor(R.color.ddd));
        binding.textView3.setText(R.string.reserved_apartments);
        token = userPreference.getToken();
        api_key = userPreference.getApiKey() ;

        Log.e("add reservation" , String.valueOf(userPreference.getApp().getReservationAdd()));

        if (userPreference.getApp().getReservationAdd() == 0){
            binding.addNewBt.setVisibility(View.GONE);
        }else if (userPreference.getApp().getReservationAdd() == 1){
            binding.addNewBt.setVisibility(View.VISIBLE);
        }

        Bundle b = getArguments();
        if (b!=null){
            key = b.getString("key");
        }

        list = new ArrayList<>();

        if (key.equals("print")){
            binding.addNewBt.setText(R.string.print);
        }else if (key.equals("add")){
            binding.addNewBt.setText(R.string.new_apartment);
        }

        binding.addNewBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (key.equals("print")){
                    navController.navigate(R.id.nav_print_fragment);
                }else if (key.equals("add")){
                    Bundle b = new Bundle();
                    b.putString("key" , "new");
                    navController.navigate(R.id.nav_add_new_flat_fragment , b);
                }
            }
        });

        homeViewModel.intUi(binding , getContext() , navController , list , currentLang , token , api_key);
    }

    public void fullScreen(){
        //make translucent statusBar on kitkat devices
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(getActivity(), WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(getActivity(), WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getActivity().getWindow().setStatusBarColor(getActivity().getResources().getColor(R.color.ddd));
        }
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}