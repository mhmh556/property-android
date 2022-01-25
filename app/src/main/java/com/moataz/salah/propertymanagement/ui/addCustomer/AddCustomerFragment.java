package com.moataz.salah.propertymanagement.ui.addCustomer;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.databinding.AddCustomerFragmentBinding;
import com.moataz.salah.propertymanagement.model.customer.CustomerModel;
import com.moataz.salah.propertymanagement.model.property.PropertyModel;
import com.moataz.salah.propertymanagement.model.task.TaskModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import java.util.List;
import java.util.Locale;

public class AddCustomerFragment extends Fragment{

    private String TAG = "HomeFragment";

    AddCustomerModelFactory factory;
    AddCustomerViewModel homeViewModel;
    AddCustomerFragmentBinding binding;
    NavController navController  = null ;
    BottomNavigationView bottom_nav;
    ImageView back , logo;
    List<String> list;
    TextView toolbar_title;
    Toolbar toolbar;
    String currentLang = Locale.getDefault().getLanguage();
    UserPreference userPreference ;
    ApiInterface apiInterface ;
    String api_key ;
    String from ;
    String type ;
    CustomerModel customerModel ;
    PropertyModel propertyModel ;
    TaskModel taskModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = AddCustomerFragmentBinding.inflate(inflater,container,false);
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
        factory = new AddCustomerModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(AddCustomerViewModel.class);
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
        api_key = userPreference.getApiKey();

        Bundle b = getArguments();
        if (b != null){
            from = b.getString("from");
            type = b.getString("type");
        }
        if (from != null){
            if (from.equals("dialog")){
                propertyModel = (PropertyModel) b.getSerializable("data");
            }else {
                propertyModel = new PropertyModel();
            }
        }

        if (type != null) {
            if (type.equals("add")){
                toolbar_title.setText(R.string.new_customer);
                binding.nameInput.setText("");
                binding.addressInput.setText("");
                binding.phoneInput.setText("");
                binding.idNumberInput.setText("");
                binding.addBt.setText(R.string.add);
                if (from.equals("task")){
                    taskModel = (TaskModel) b.getSerializable("data");
                }else {
                    taskModel = new TaskModel();
                }
            }else if (type.equals("edit")){
                toolbar_title.setText(R.string.edit_customer);
                customerModel = (CustomerModel) b.getSerializable("data");
                if (customerModel != null) {
                    binding.nameInput.setText(customerModel.getName());
                    binding.phoneInput.setText(customerModel.getPhone());
                    binding.addressInput.setText(customerModel.getAddress());
                    binding.idNumberInput.setText(customerModel.getNationallyId());
                    binding.addBt.setText(R.string.edit);
                }
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from.equals("dialog")) {
                    Bundle b = new Bundle();
                    b.putSerializable("data" , propertyModel);
                    navController.navigate(R.id.nav_reservation_fragment , b);
                } else if (from.equals("fragment")){
                    navController.navigate(R.id.nav_customer_fragment);
                }
            }
        });

        homeViewModel.intUi(binding,getContext(),navController,list,currentLang , userPreference , apiInterface , api_key , from , propertyModel);

        binding.addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("add")) {
                    binding.loader.setVisibility(View.VISIBLE);
                    homeViewModel.addCustomer(type , taskModel);
                }else if (type.equals("edit")){
                    binding.loader.setVisibility(View.VISIBLE);
                    homeViewModel.editCustomer(customerModel.getCustomerId());
                }
            }
        });

//        binding.dateOfBirthInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (b){
//                    pickDate(binding.dateOfBirthInput);
//                }else {
//                    binding.dateOfBirthInput.setFocusable(true);
//                }
//            }
//        });
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

//    public void pickDate(EditText date_text){
//        final Dialog dialog = new Dialog(getContext());
//        dialog.setContentView(R.layout.calender_dialog);
//        dialog.setCancelable(true);
//        CalendarView calendarView = dialog.findViewById(R.id.calander_view);
//        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
//            String day = String.valueOf(dayOfMonth);
//            String month_text = String.valueOf(month+1);
//            String year_text = String.valueOf(year);
//            date_text.setText(year_text +"-"+ month_text +"-"+ day);
//            dialog.dismiss();
//            date_text.setFocusable(true);
//        });
//        dialog.show();
//    }
}