package com.moataz.salah.propertymanagement.ui.products;

import android.app.Activity;
import android.app.Dialog;
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
import com.moataz.salah.propertymanagement.adapter.ProductsAdapter;
import com.moataz.salah.propertymanagement.databinding.ProductsFragmentBinding;
import com.moataz.salah.propertymanagement.model.productsResponse.ProductsModel;
import com.moataz.salah.propertymanagement.model.property.PropertyModel;
import com.moataz.salah.propertymanagement.model.property.PropertyPriceModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductsFragment extends Fragment implements ProductsAdapter.onEditClick , ProductsAdapter.onDeleteClick {

    private String TAG = "HomeFragment";

    ProductsModelFactory factory;
    ProductsViewModel homeViewModel;
    ProductsFragmentBinding binding;
    NavController navController  = null ;
    BottomNavigationView bottom_nav;
    ImageView back , logo;
    List<ProductsModel> list;
    List<PropertyPriceModel> price_list ;
    TextView toolbar_title;
    Toolbar toolbar;
    String currentLang = Locale.getDefault().getLanguage();
    Dialog dialog2 , dialog3;
    ApiInterface apiInterface;
    String token ;
    UserPreference userPreference ;
    PropertyModel propertyModel ;
    int property_id;
    String api_key ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = ProductsFragmentBinding.inflate(inflater,container,false);
        fullScreen();
        back = getActivity().findViewById(R.id.back_bt);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar = getActivity().findViewById(R.id.toolbar);
        logo = getActivity().findViewById(R.id.logo);
        bottom_nav = getActivity().findViewById(R.id.bottom_nav);
        userPreference = new UserPreference(getContext());
        dialog2 = new Dialog(getContext());
        dialog2.setContentView(R.layout.customer_dialog);
        dialog3 = new Dialog(getContext());
        dialog3.setContentView(R.layout.customer_dialog);
        list = new ArrayList<>();

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        factory = new ProductsModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(ProductsViewModel.class);
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
        token = userPreference.getUser().getAccessToken();
        api_key = userPreference.getApiKey();
        Log.e("token" , token);

        if (userPreference.getApp().getProductAdd() == 0){
            binding.barcode.setVisibility(View.GONE);
        }else if (userPreference.getApp().getProductAdd() == 1){
            binding.barcode.setVisibility(View.VISIBLE);
        }

        Bundle b = getArguments();
        if (b != null){
            propertyModel = (PropertyModel) b.getSerializable("data");
            if (propertyModel != null){
                property_id = propertyModel.getPropertyId();
                Log.e("property_id" , String.valueOf(property_id));
            }
        }
        homeViewModel.intUi(binding , getContext() , navController , price_list , currentLang , list , userPreference);

        binding.barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("type" , "add");
                navController.navigate(R.id.nav_add_product_fragment ,b);
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

    @Override
    public void onDeleteClickListener(ProductsModel productsModel) {
//        homeViewModel.delete_product(propertyModel.getPropertyId());
    }

    @Override
    public void onEditClickListener(ProductsModel productsModel) {
//        Bundle b = new Bundle();
//        b.putString("type" , "edit");
//        b.putSerializable("data" , productsModel);
//        navController.navigate(R.id.nav_add_product_fragment , b);
    }
}