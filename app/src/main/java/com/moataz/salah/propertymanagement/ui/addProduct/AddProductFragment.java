package com.moataz.salah.propertymanagement.ui.addProduct;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.databinding.AddProductFragmentBinding;
import com.moataz.salah.propertymanagement.model.productsResponse.ProductsModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class AddProductFragment extends Fragment{

    private String TAG = "HomeFragment";

    AddProductModelFactory factory;
    AddProductViewModel homeViewModel;
    AddProductFragmentBinding binding;
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
    String type = "";
    ProductsModel productsModel ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = AddProductFragmentBinding.inflate(inflater,container,false);
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
        factory = new AddProductModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(AddProductViewModel.class);
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
        binding.loader.setVisibility(View.VISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_reservation_fragment);
            }
        });

        Bundle b = getArguments();
        if (b != null){
            type = b.getString("type");
        }
        if (type != null) {
            if (type.equals("add")){
                binding.loader.setVisibility(View.GONE);
                toolbar_title.setText(R.string.add_product);
                binding.nameInput.setText("");
                binding.quantityInput.setText("");
                binding.priceInput.setText("");
                binding.salePriceInput.setText("");
                binding.addBt.setText(R.string.add);
            }else if (type.equals("edit")){
                String baseUrl = "https://login-system-users-bucket.s3.amazonaws.com/images";
                toolbar_title.setText(R.string.edit_product);
                productsModel = (ProductsModel) b.getSerializable("data");
                binding.nameInput.setText(productsModel.getName());
                binding.quantityInput.setText(String.valueOf(productsModel.getQty()));
                binding.priceInput.setText(String.valueOf(productsModel.getPrice()));
                binding.salePriceInput.setText(String.valueOf(productsModel.getSalePrice()));
                binding.addBt.setText(R.string.edit);
                binding.loader.setVisibility(View.GONE);
                if (productsModel.getImage() == null || productsModel.getImage().equals("default") ||
                        productsModel.getImage().equals("")){
                    binding.photoId.setImageResource(R.drawable.ic_image);
                }else {
                    Picasso.get().load(baseUrl + productsModel.getImage() + "_S.png")
                            .into(binding.photoId);
                }
            }
        }

        homeViewModel.intUi(binding,getContext(),navController,list,currentLang , userPreference , apiInterface , api_key);

        binding.addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("add")) {
                    homeViewModel.addCustomer();
                }else if (type.equals("edit")){
                    homeViewModel.editProduct(productsModel.getProductId());
                }
            }
        });

        binding.photoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("add")) {
                    homeViewModel.initImage();
                }else if (type.equals("edit")){
                    homeViewModel.updateImage(productsModel);
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
}