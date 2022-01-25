package com.moataz.salah.propertymanagement.ui.addEmployee;

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
import com.moataz.salah.propertymanagement.databinding.AddEmployeeFragmentBinding;
import com.moataz.salah.propertymanagement.model.customer.CustomerModel;
import com.moataz.salah.propertymanagement.model.staff.StaffModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class AddEmployeeFragment extends Fragment{

    private String TAG = "HomeFragment";

    AddEmployeeModelFactory factory;
    AddEmployeeViewModel homeViewModel;
    AddEmployeeFragmentBinding binding;
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
    StaffModel staffModel ;
    String baseUrl = "https://login-system-users-bucket.s3.amazonaws.com/images";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = AddEmployeeFragmentBinding.inflate(inflater,container,false);
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
        factory = new AddEmployeeModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(AddEmployeeViewModel.class);
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
        if (type != null) {
            if (type.equals("add")){
                if (from.equals("staff")) {
                    toolbar_title.setText(R.string.new_employee);
                    binding.fullNameInput.setText("");
                    binding.addressInput.setText("");
                    binding.phoneInput.setText("");
                    binding.idNumberInput.setText("");
                    binding.addBt.setText(R.string.add);
                }else{
                    toolbar_title.setText(R.string.new_customer);
                    binding.fullNameInput.setText("");
                    binding.addressInput.setText("");
                    binding.phoneInput.setText("");
                    binding.idNumberInput.setText("");
                    binding.addBt.setText(R.string.add);
                }
            }else if (type.equals("edit")){
                if (from.equals("staff")){
                    toolbar_title.setText(R.string.edit_employee);
                    staffModel = (StaffModel) b.getSerializable("data");
                    if (staffModel != null) {
                        binding.fullNameInput.setText(staffModel.getUserFullName());
                        binding.phoneInput.setText(String.valueOf(((int) staffModel.getUserPhone())));
                        binding.addressInput.setText(staffModel.getUserAddress());
                        binding.idNumberInput.setText(String.valueOf(staffModel.getUserNationalId()));
                        binding.userNameInput.setText(staffModel.getUserName());
                        binding.jobInput.setText(staffModel.getUserJob());
                        binding.mailInput.setText(staffModel.getUserEmail());
                        binding.addBt.setText(R.string.edit);
                        if (staffModel.getUserImage() == null || staffModel.getUserImage().equals("default") ||
                                staffModel.getUserImage().equals("")){
                            binding.employeePic.setImageResource(R.drawable.default_user_pic);
                        }else {
                            Picasso.get().load(baseUrl + staffModel.getUserImage() + "_S.png")
                                    .into(binding.employeePic);
                        }                    }
                }else {
                    toolbar_title.setText(R.string.edit_customer);
                    customerModel = (CustomerModel) b.getSerializable("data");
                    if (customerModel != null) {
                        binding.fullNameInput.setText(customerModel.getName());
                        binding.phoneInput.setText(customerModel.getPhone());
                        binding.addressInput.setText(customerModel.getAddress());
                        binding.idNumberInput.setText(customerModel.getNationallyId());
                        binding.addBt.setText(R.string.edit);
                    }
                }
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from.equals("dialog")) {
                    navController.navigate(R.id.nav_reservation_fragment);
                } else if (from.equals("fragment")){
                    navController.navigate(R.id.nav_customer_fragment);
                }else if (from.equals("staff")){
                    navController.navigate(R.id.nav_employees_fragment);
                }
            }
        });

        homeViewModel.intUi(binding,getContext(),navController,list,currentLang , userPreference , apiInterface , api_key , from);

        binding.employeePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.initImage();
            }
        });

        binding.addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("add")) {
                    homeViewModel.addCustomer();
                }else if (type.equals("edit")){
                    homeViewModel.updateUserInfo(staffModel);
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
}