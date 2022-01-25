package com.moataz.salah.propertymanagement.ui.chartDetails;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
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
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.adapter.ChartPagerAdapter;
import com.moataz.salah.propertymanagement.databinding.ChartDetailsFragmentBinding;
import com.moataz.salah.propertymanagement.model.electrical.ElectricalModel;
import java.util.Locale;

public class ChartDetailsFragment extends Fragment {

    private String TAG = "HomeFragment";

    ChartDetailsFragmentModelFactory factory;
    ChartDetailsViewModel homeViewModel;
    ChartDetailsFragmentBinding binding;
    NavController navController  = null ;
    BottomNavigationView bottom_nav;
    ImageView back , logo;
    TextView toolbar_title;
    Toolbar toolbar;
    String currentLang = Locale.getDefault().getLanguage();
    ChartPagerAdapter chartPagerAdapter;
    String type = "";
    ElectricalModel electricalModel ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = ChartDetailsFragmentBinding.inflate(inflater,container,false);
        fullScreen();
        back = getActivity().findViewById(R.id.back_bt);
        bottom_nav = getActivity().findViewById(R.id.bottom_nav);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar = getActivity().findViewById(R.id.toolbar);
        logo = getActivity().findViewById(R.id.logo);
        chartPagerAdapter = new ChartPagerAdapter(getChildFragmentManager() , getContext() ,1);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        factory = new ChartDetailsFragmentModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(ChartDetailsViewModel.class);
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

        Bundle b = getArguments();
        if (b != null){
            type = b.getString("type");
            electricalModel = (ElectricalModel) b.getSerializable("data");
        }
        if (type !=null) {
            if (type.equals("electric")) {
                toolbar_title.setText(getContext().getResources().getString(R.string.electric_meter) + "24");
            } else if (type.equals("water")) {
                toolbar_title.setText(getContext().getResources().getString(R.string.water_meter) + "24");
            } else if (type.equals("other")) {
                toolbar_title.setText(getContext().getResources().getString(R.string.electric_meter) + "24");
            }
        }

        binding.chartViewPager.setOffscreenPageLimit(1);
        binding.chartViewPager.setAdapter(chartPagerAdapter);

        binding.tabLayout.setupWithViewPager(binding.chartViewPager);

        binding.chartViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        homeViewModel.intUi(binding,getContext(),navController,currentLang);

        binding.graphsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b1 = new Bundle();
                if (type.equals("electric")){
                    b1.putString("type" , "electric");
                    b1.putSerializable("data" , electricalModel);
                }else if (type.equals("water")){
                    b1.putString("type" , "water");
                    b1.putSerializable("data" , electricalModel);
                }else if (type.equals("other")){
                    b1.putString("type" , "other");
                    b1.putSerializable("data" , electricalModel);
                }
                navController.navigate(R.id.nav_counter_details_fragment , b1);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b2 = new Bundle();
                if (type.equals("electric")){
                    b2.putString("type" , "electric");
                }else if (type.equals("water")){
                    b2.putString("type" , "water");
                }else if (type.equals("other")){
                    b2.putString("type" , "other");
                }
                navController.navigate(R.id.nav_counter_details_fragment , b2);
            }
        });
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