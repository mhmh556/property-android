package com.moataz.salah.propertymanagement.ui.addCounter;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.adapter.FlatsAdapter;
import com.moataz.salah.propertymanagement.databinding.AddCounterFragmentBinding;
import com.moataz.salah.propertymanagement.model.FlatModel;
import com.moataz.salah.propertymanagement.model.electrical.ElectricalModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddCounterFragment extends Fragment{

    private String TAG = "HomeFragment";

    AddCounterModelFactory factory;
    AddCounterViewModel homeViewModel;
    AddCounterFragmentBinding binding;
    NavController navController  = null ;
    BottomNavigationView bottom_nav;
    ImageView back , logo;
    List<FlatModel> list;
    TextView toolbar_title;
    Toolbar toolbar;
    String currentLang = Locale.getDefault().getLanguage();
    String type ;
    ApiInterface apiInterface ;
    UserPreference userPreference ;
    String action ;
    ElectricalModel electricalModel ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = AddCounterFragmentBinding.inflate(inflater,container,false);
        fullScreen();
        back = getActivity().findViewById(R.id.back_bt);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar = getActivity().findViewById(R.id.toolbar);
        logo = getActivity().findViewById(R.id.logo);
        bottom_nav = getActivity().findViewById(R.id.bottom_nav);
        userPreference = new UserPreference(getContext());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        factory = new AddCounterModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(AddCounterViewModel.class);
        navController = Navigation.findNavController(binding.getRoot());
        bottom_nav.setVisibility(View.GONE);
        logo.setVisibility(View.GONE);
        toolbar_title.setVisibility(View.VISIBLE);
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        toolbar_title.setLayoutParams(params);
        back.setVisibility(View.VISIBLE);
        back.setImageResource(R.drawable.ic_back);
        toolbar_title.setText(navController.getCurrentDestination().getLabel());
        toolbar.setNavigationIcon(null);
        toolbar.setBackgroundColor(getActivity().getResources().getColor(R.color.ddd));

        Bundle b = getArguments();
        if (b != null){
            type = b.getString("type");
            Log.e("type" , type);
            action = b.getString("action");
        }
        if (action != null) {
            if (action.equals("add")) {
                binding.accountInput.setText("");
                binding.addressInput.setText("");
                binding.addBt.setText(R.string.add);
                toolbar_title.setText(navController.getCurrentDestination().getLabel());
            } else if (action.equals("edit")) {
                electricalModel = (ElectricalModel) b.getSerializable("data");
                binding.accountInput.setText(String.valueOf(electricalModel.getAccountNum()));
                binding.addressInput.setText(electricalModel.getAddress());
                binding.addBt.setText(R.string.edit);
                toolbar_title.setText(R.string.edit);
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_general_expenses_fragment);
            }
        });

        homeViewModel.intUi(binding,getContext(),navController,currentLang , apiInterface , userPreference);

        binding.counterFlatBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFlatsDialog();
            }
        });

        binding.addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type != null) {
                    if (type.equals("electric")) {
                        if (action.equals("add")) {
                            homeViewModel.addCounter("electrical_device");
                        }else if (action.equals("edit")){
                            homeViewModel.editElectricCounter(electricalModel.getElectricalAccountId() , "electrical_device");
                        }
                    }else if (type.equals("water")){
                        if (action.equals("add")) {
                            homeViewModel.addCounter("water_device");
                        }else if (action.equals("edit")){
                            homeViewModel.editElectricCounter(electricalModel.getElectricalAccountId() , "water_device");
                        }
                    }
                }
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

    public void pickDate(EditText date_text){

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.calender_dialog);
        dialog.setCancelable(true);

        CalendarView calendarView = dialog.findViewById(R.id.calander_view);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {

            String day = String.valueOf(dayOfMonth);
            String month_text = String.valueOf(month+1);
            String year_text = String.valueOf(year);

            date_text.setText(year_text +"-"+ month_text +"-"+ day);
            dialog.dismiss();
            date_text.setFocusable(true);
        });

        dialog.show();

    }

    public void showFlatsDialog(){
        Dialog dialog2 = new Dialog(getContext());
        dialog2.setContentView(R.layout.flat_dialog);
        dialog2.setCancelable(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView close = dialog2.findViewById(R.id.close_bt);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        
        Button add = dialog2.findViewById(R.id.add_bt);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });

        list = new ArrayList<>();
        list.add(new FlatModel("Flat - 1" , true));
        list.add(new FlatModel("Flat - 2" , false));
        list.add(new FlatModel("Flat - 3" , true));
        list.add(new FlatModel("Flat - 4" , false));
        list.add(new FlatModel("Flat - 5" , true));
        list.add(new FlatModel("Flat - 6" , false));
        list.add(new FlatModel("Flat - 7" , false));
        RecyclerView recyclerView = dialog2.findViewById(R.id.flats_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext() , 1));
        FlatsAdapter adapter = new FlatsAdapter(getContext() , list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        dialog2.show();
    }


}