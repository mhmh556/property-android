package com.moataz.salah.propertymanagement.ui.permission;

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
import com.moataz.salah.propertymanagement.databinding.PermissionFragmentBinding;
import com.moataz.salah.propertymanagement.model.productsResponse.ProductsModel;
import com.moataz.salah.propertymanagement.model.property.PropertyModel;
import com.moataz.salah.propertymanagement.model.property.PropertyPriceModel;
import com.moataz.salah.propertymanagement.model.staff.AddUserModel;
import com.moataz.salah.propertymanagement.model.staff.StaffModel;
import com.moataz.salah.propertymanagement.model.staff.UserPermissionModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PermissionFragment extends Fragment implements ProductsAdapter.onEditClick , ProductsAdapter.onDeleteClick {

    private String TAG = "HomeFragment";

    PermissionModelFactory factory;
    PermissionViewModel homeViewModel;
    PermissionFragmentBinding binding;
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
    UserPermissionModel userPermissionModel ;
    StaffModel staffModel;
    String user_api_key ;
//    int property_id;
    String api_key ;
    String type ;

    int property_view = 0 ; int property_delete = 0 ; int property_edit = 0 ; int property_add = 0 ;
    int cleaning_view = 0; int cleaning_delete = 0; int cleaning_edit = 0; int cleaning_add = 0;
    int product_view = 0; int product_delete = 0; int product_edit = 0; int product_add = 0;
    int custmer_view = 0; int custmer_delete = 0; int custmer_edit = 0; int custmer_add = 0;
    int reservation_view = 0; int reservation_delete = 0; int reservation_edit = 0; int reservation_add = 0;
    int purchase_view = 0; int purchase_delete = 0; int purchase_edit = 0; int purchase_add = 0;
    int device_meter_view = 0; int device_meter_delete = 0; int device_meter_edit = 0; int device_meter_add = 0;
    int customer_view = 0; int customer_delete = 0; int customer_edit = 0; int customer_add = 0;
    int report_view = 0;

    AddUserModel userModel ;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = PermissionFragmentBinding.inflate(inflater,container,false);
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
        factory = new PermissionModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(PermissionViewModel.class);
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

        Bundle m = getArguments();
        if (m != null) {
            type = m.getString("type");
            if (type.equals("new")){
                userModel = (AddUserModel) m.getSerializable("user");
                user_api_key = userModel.getUserApiKey();
                binding.name.setText(userModel.getUserName());
                binding.address.setText(userModel.getUserJob());
                binding.created.setText(String.valueOf(userModel.getUserPhone()));
            }else if (type.equals("update")){
                userPermissionModel = (UserPermissionModel) m.getSerializable("item");
                staffModel = (StaffModel) m.getSerializable("obj");
                user_api_key = staffModel.getUserApiKey();
                binding.name.setText(staffModel.getUserName());
                binding.address.setText(staffModel.getUserJob());
                binding.created.setText(String.valueOf(staffModel.getUserPhone()));

            }
        }

        homeViewModel.intUi(binding , getContext() , navController , price_list , currentLang , list , userPreference);

        if (type.equals("update")){
            binding.addBt.setText(R.string.update);
            if (userPermissionModel.getCleaningAdd() == 1){
                binding.addCleaningCheckBox.setChecked(true);
                cleaning_add = 1 ;
            }else {
                binding.addCleaningCheckBox.setChecked(false);
                cleaning_add = 0 ;
            }
            if (userPermissionModel.getCleaningEdit() == 1){
                binding.editCleaningCheckBox.setChecked(true);
                cleaning_edit = 1 ;
            }else {
                binding.editCleaningCheckBox.setChecked(false);
                cleaning_edit = 0 ;
            }
            if (userPermissionModel.getCleaningDelete() == 1){
                binding.deleteCleaningCheckBox.setChecked(true);
                cleaning_delete = 1 ;
            }else {
                binding.deleteCleaningCheckBox.setChecked(false);
                cleaning_delete = 0 ;
            }
            if (userPermissionModel.getCleaningView() == 1){
                binding.reviewCleaningCheckBox.setChecked(true);
                cleaning_view = 1 ;
            }else {
                binding.reviewCleaningCheckBox.setChecked(false);
                cleaning_view = 0 ;
            }
            if (userPermissionModel.getPropertyAdd() == 1){
                binding.addPropertyCheckBox.setChecked(true);
                property_add = 1 ;
            }else {
                binding.addPropertyCheckBox.setChecked(false);
                property_add = 0 ;
            }
            if (userPermissionModel.getPropertyDelete() == 1){
                binding.deletePropertyCheckBox.setChecked(true);
                property_delete = 1 ;
            }else {
                binding.deletePropertyCheckBox.setChecked(false);
                property_delete = 0 ;
            }
            if (userPermissionModel.getPropertyEdit() == 1){
                binding.editPropertyCheckBox.setChecked(true);
                property_edit = 1 ;
            }else {
                binding.editPropertyCheckBox.setChecked(false);
                property_edit = 0 ;
            }
            if (userPermissionModel.getPropertyView() == 1){
                binding.reviewPropertyCheckBox.setChecked(true);
                property_view = 1 ;
            }else {
                binding.reviewPropertyCheckBox.setChecked(false);
                property_view = 0 ;
            }

            if (userPermissionModel.getProductAdd() == 1){
                binding.addProductCheckBox.setChecked(true);
                product_add = 1 ;
            }else {
                binding.addProductCheckBox.setChecked(false);
                product_add = 0 ;
            }
            if (userPermissionModel.getProductDelete() == 1){
                binding.deleteProductCheckBox.setChecked(true);
                product_delete = 1 ;
            }else {
                binding.deleteProductCheckBox.setChecked(false);
                product_delete = 0 ;
            }
            if (userPermissionModel.getProductEdit() == 1){
                binding.editProductCheckBox.setChecked(true);
                product_edit = 1 ;
            }else {
                binding.editProductCheckBox.setChecked(false);
                product_edit = 0 ;
            }
            if (userPermissionModel.getProductView() == 1){
                binding.reviewProductCheckBox.setChecked(true);
                product_view = 1 ;
            }else {
                binding.reviewProductCheckBox.setChecked(false);
                product_view = 0 ;
            }

//            if (userPermissionModel.getCustmerAdd() == 1){
//                binding.addCustmerCheckBox.setChecked(true);
//                custmer_add = 1 ;
//            }else {
//                binding.addCustmerCheckBox.setChecked(false);
//                custmer_add = 0 ;
//            }
//            if (userPermissionModel.getCustmerDelete() == 1){
//                binding.deleteCustmerCheckBox.setChecked(true);
//                custmer_delete = 1 ;
//            }else {
//                binding.deleteCustmerCheckBox.setChecked(false);
//                custmer_delete = 0 ;
//            }
//            if (userPermissionModel.getCustmerEdit() == 1){
//                binding.editCustmerCheckBox.setChecked(true);
//                custmer_edit = 1 ;
//            }else {
//                binding.editCustmerCheckBox.setChecked(false);
//                custmer_edit = 0 ;
//            }
//            if (userPermissionModel.getCustmerView() == 1){
//                binding.reviewCustmerCheckBox.setChecked(true);
//                custmer_view = 1 ;
//            }else {
//                binding.reviewCustmerCheckBox.setChecked(false);
//                custmer_view = 0 ;
//            }

            if (userPermissionModel.getReservationAdd() == 1){
                binding.addReservationCheckBox.setChecked(true);
                reservation_add = 1 ;
            }else {
                binding.addReservationCheckBox.setChecked(false);
                reservation_add = 0 ;
            }
            if (userPermissionModel.getReservationDelete() == 1){
                binding.deleteReservationCheckBox.setChecked(true);
                reservation_delete = 1 ;
            }else {
                binding.deleteReservationCheckBox.setChecked(false);
                reservation_delete = 0 ;
            }
            if (userPermissionModel.getReservationEdit() == 1){
                binding.editReservationCheckBox.setChecked(true);
                reservation_edit = 1 ;
            }else {
                binding.editReservationCheckBox.setChecked(false);
                reservation_edit = 0 ;
            }
            if (userPermissionModel.getReservationView() == 1){
                binding.reviewReservationCheckBox.setChecked(true);
                reservation_view = 1 ;
            }else {
                binding.reviewReservationCheckBox.setChecked(false);
                reservation_view = 0 ;
            }

            if (userPermissionModel.getPurchaseAdd() == 1){
                binding.addPurchaseCheckBox.setChecked(true);
                purchase_add = 1 ;
            }else {
                binding.addPurchaseCheckBox.setChecked(false);
                purchase_add = 0 ;
            }
            if (userPermissionModel.getPurchaseDelete() == 1){
                binding.deletePurchaseCheckBox.setChecked(true);
                purchase_delete = 1 ;
            }else {
                binding.deletePurchaseCheckBox.setChecked(false);
                purchase_delete = 0 ;
            }
            if (userPermissionModel.getPurchaseEdit() == 1){
                binding.editPurchaseCheckBox.setChecked(true);
                purchase_edit = 1 ;
            }else {
                binding.editPurchaseCheckBox.setChecked(false);
                purchase_edit = 0 ;
            }
            if (userPermissionModel.getPurchaseView() == 1){
                binding.reviewPurchaseCheckBox.setChecked(true);
                purchase_view = 1 ;
            }else {
                binding.reviewPurchaseCheckBox.setChecked(false);
                purchase_view = 0 ;
            }

            if (userPermissionModel.getDeviceMeterAdd() == 1){
                binding.addDeviceMeterCheckBox.setChecked(true);
                device_meter_add = 1 ;
            }else {
                binding.addDeviceMeterCheckBox.setChecked(false);
                device_meter_add = 0 ;
            }
            if (userPermissionModel.getDeviceMeterDelete() == 1){
                binding.deleteDeviceMeterCheckBox.setChecked(true);
                device_meter_delete = 1 ;
            }else {
                binding.deleteDeviceMeterCheckBox.setChecked(false);
                device_meter_delete = 0 ;
            }
            if (userPermissionModel.getDeviceMeterEdit() == 1){
                binding.editDeviceMeterCheckBox.setChecked(true);
                device_meter_edit = 1 ;
            }else {
                binding.editDeviceMeterCheckBox.setChecked(false);
                device_meter_edit = 0 ;
            }
            if (userPermissionModel.getDeviceMeterView() == 1){
                binding.reviewDeviceMeterCheckBox.setChecked(true);
                device_meter_view = 1 ;
            }else {
                binding.reviewDeviceMeterCheckBox.setChecked(false);
                device_meter_view = 0 ;
            }

            if (userPermissionModel.getCustomerAdd() == 1){
                binding.addCustomerCheckBox.setChecked(true);
                customer_add = 1 ;
            }else {
                binding.addCustomerCheckBox.setChecked(false);
                customer_add = 0 ;
            }
            if (userPermissionModel.getCustomerDelete() == 1){
                binding.deleteCustomerCheckBox.setChecked(true);
                customer_delete = 1 ;
            }else {
                binding.deleteCustomerCheckBox.setChecked(false);
                customer_delete = 0 ;
            }
            if (userPermissionModel.getCustomerEdit() == 1){
                binding.editCustomerCheckBox.setChecked(true);
                customer_edit = 1 ;
            }else {
                binding.editCustomerCheckBox.setChecked(false);
                customer_edit = 0 ;
            }
            if (userPermissionModel.getCustomerView() == 1){
                binding.reviewCustomerCheckBox.setChecked(true);
                customer_view = 1 ;
            }else {
                binding.reviewCustomerCheckBox.setChecked(false);
                customer_view = 0 ;
            }

            if (userPermissionModel.getReportView() == 1){
                binding.reviewReportCheckBox.setChecked(true);
                report_view = 1 ;
            }else {
                binding.reviewReportCheckBox.setChecked(false);
                report_view = 0 ;
            }

        }else if (type.equals("new")){
            binding.addBt.setText(R.string.add);
            property_view = 0 ; property_delete = 0 ; property_edit = 0 ; property_add = 0 ;
            cleaning_view = 0; cleaning_delete = 0; cleaning_edit = 0; cleaning_add = 0;
            product_view = 0; product_delete = 0; product_edit = 0; product_add = 0;
            custmer_view = 0; custmer_delete = 0; custmer_edit = 0; custmer_add = 0;
            reservation_view = 0; reservation_delete = 0; reservation_edit = 0; reservation_add = 0;
            purchase_view = 0; purchase_delete = 0; purchase_edit = 0; purchase_add = 0;
            device_meter_view = 0; device_meter_delete = 0; device_meter_edit = 0; device_meter_add = 0;
            customer_view = 0; customer_delete = 0; customer_edit = 0; customer_add = 0; report_view = 0;

            binding.addPropertyCheckBox.setChecked(false); binding.deletePropertyCheckBox.setChecked(false);
            binding.editPropertyCheckBox.setChecked(false); binding.reviewPropertyCheckBox.setChecked(false);

            binding.addCleaningCheckBox.setChecked(false); binding.deleteCleaningCheckBox.setChecked(false);
            binding.editCleaningCheckBox.setChecked(false); binding.reviewCleaningCheckBox.setChecked(false);

            binding.addProductCheckBox.setChecked(false); binding.deleteProductCheckBox.setChecked(false);
            binding.editProductCheckBox.setChecked(false); binding.reviewProductCheckBox.setChecked(false);

//            binding.addCustmerCheckBox.setChecked(false); binding.deleteCustmerCheckBox.setChecked(false);
//            binding.editCustmerCheckBox.setChecked(false); binding.reviewCustmerCheckBox.setChecked(false);

            binding.addReservationCheckBox.setChecked(false); binding.deleteReservationCheckBox.setChecked(false);
            binding.editReservationCheckBox.setChecked(false); binding.reviewReservationCheckBox.setChecked(false);

            binding.addPurchaseCheckBox.setChecked(false); binding.deletePurchaseCheckBox.setChecked(false);
            binding.editPurchaseCheckBox.setChecked(false); binding.reviewPurchaseCheckBox.setChecked(false);

            binding.addDeviceMeterCheckBox.setChecked(false); binding.deleteDeviceMeterCheckBox.setChecked(false);
            binding.editDeviceMeterCheckBox.setChecked(false); binding.reviewDeviceMeterCheckBox.setChecked(false);

            binding.addCustomerCheckBox.setChecked(false); binding.deleteCustomerCheckBox.setChecked(false);
            binding.editCustomerCheckBox.setChecked(false); binding.reviewCustomerCheckBox.setChecked(false);

            binding.reportCheckBox.setChecked(false); binding.deletePropertyCheckBox.setChecked(false);
            binding.editPropertyCheckBox.setChecked(false); binding.reviewPropertyCheckBox.setChecked(false);

        }

        binding.propertyCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.propertyCheckBox.isChecked()){
                    binding.propertyCheckBox.setChecked(true); binding.addPropertyCheckBox.setChecked(true); binding.deletePropertyCheckBox.setChecked(true);
                    binding.editPropertyCheckBox.setChecked(true); binding.reviewPropertyCheckBox.setChecked(true);
                    property_add = 1 ; property_delete = 1 ; property_edit = 1 ; property_view = 1 ;
                }else {
                    binding.propertyCheckBox.setChecked(false); binding.addPropertyCheckBox.setChecked(false); binding.deletePropertyCheckBox.setChecked(false);
                    binding.editPropertyCheckBox.setChecked(false); binding.reviewPropertyCheckBox.setChecked(false);
                    property_add = 0 ; property_delete = 0 ; property_edit = 0 ; property_view = 0 ;
                }
            }
        });

        binding.cleaningCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.cleaningCheckBox.isChecked()){
                    binding.cleaningCheckBox.setChecked(true); binding.addCleaningCheckBox.setChecked(true); binding.editCleaningCheckBox.setChecked(true);
                    binding.deleteCleaningCheckBox.setChecked(true); binding.reviewCleaningCheckBox.setChecked(true);
                    cleaning_add = 1 ; cleaning_delete = 1 ; cleaning_edit = 1 ; cleaning_view = 1 ;
                }else {
                    binding.cleaningCheckBox.setChecked(false); binding.addCleaningCheckBox.setChecked(false); binding.editCleaningCheckBox.setChecked(false);
                    binding.deleteCleaningCheckBox.setChecked(false); binding.reviewCleaningCheckBox.setChecked(false);
                    cleaning_add = 0 ; cleaning_delete = 0 ; cleaning_edit = 0 ; cleaning_view = 0 ;
                }
            }
        });

        binding.productCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.productCheckBox.isChecked()){
                    binding.productCheckBox.setChecked(true); binding.addProductCheckBox.setChecked(true); binding.deleteProductCheckBox.setChecked(true);
                    binding.editProductCheckBox.setChecked(true); binding.reviewProductCheckBox.setChecked(true);
                    product_add = 1 ; product_delete = 1 ; product_edit = 1 ; product_view = 1 ;
                }else {
                    binding.productCheckBox.setChecked(false);  binding.addProductCheckBox.setChecked(false); binding.deleteProductCheckBox.setChecked(false);
                    binding.editProductCheckBox.setChecked(false); binding.reviewProductCheckBox.setChecked(false);
                    product_add = 0 ; product_delete = 0 ; product_edit = 0 ; product_view = 0 ;
                }
            }
        });

//        binding.custmerCheckBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (binding.custmerCheckBox.isChecked()){
//                    binding.custmerCheckBox.setChecked(true); binding.addCustmerCheckBox.setChecked(true); binding.deleteCustmerCheckBox.setChecked(true);
//                    binding.editCustmerCheckBox.setChecked(true); binding.reviewCustmerCheckBox.setChecked(true);
//                    custmer_add = 1 ; custmer_delete = 1 ; custmer_edit = 1 ; custmer_view = 1 ;
//                }else {
//                    binding.custmerCheckBox.setChecked(false); binding.addCustmerCheckBox.setChecked(false); binding.deleteCustmerCheckBox.setChecked(false);
//                    binding.editCustmerCheckBox.setChecked(false); binding.reviewCustmerCheckBox.setChecked(false);
//                    custmer_add = 0 ; custmer_delete = 0 ; custmer_edit = 0 ; custmer_view = 0 ;
//                }
//            }
//        });

        binding.reservationCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.reservationCheckBox.isChecked()){
                    binding.reservationCheckBox.setChecked(true); binding.addReservationCheckBox.setChecked(true); binding.deleteReservationCheckBox.setChecked(true);
                    binding.editReservationCheckBox.setChecked(true); binding.reviewReservationCheckBox.setChecked(true);
                    reservation_add = 1 ; reservation_delete = 1 ; reservation_edit = 1 ; reservation_view = 1 ;
                }else {
                    binding.reservationCheckBox.setChecked(false); binding.addReservationCheckBox.setChecked(false); binding.deleteReservationCheckBox.setChecked(false);
                    binding.editReservationCheckBox.setChecked(false); binding.reviewReservationCheckBox.setChecked(false);
                    reservation_add = 0 ; reservation_delete = 0 ; reservation_edit = 0 ; reservation_view = 0 ;
                }
            }
        });

        binding.reviewReservationCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.reviewReservationCheckBox.isChecked()){
                    binding.reviewReservationCheckBox.setChecked(true);
                    reservation_view = 1 ;
                }else {
                    binding.reviewReservationCheckBox.setChecked(false);
                    binding.reservationCheckBox.setChecked(false);
                    reservation_view = 0 ;
                }
            }
        });

        binding.editReservationCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.editReservationCheckBox.isChecked()){
                    binding.editReservationCheckBox.setChecked(true);
                    reservation_edit = 1 ;
                }else {
                    binding.editReservationCheckBox.setChecked(false);
                    binding.reservationCheckBox.setChecked(false);
                    reservation_edit = 0 ;
                }
            }
        });

        binding.deleteReservationCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.deleteReservationCheckBox.isChecked()){
                    binding.deleteReservationCheckBox.setChecked(true);
                    reservation_delete = 1 ;
                }else {
                    binding.deleteReservationCheckBox.setChecked(false);
                    binding.reservationCheckBox.setChecked(false);
                    reservation_delete = 0 ;
                }
            }
        });

        binding.addReservationCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.addReservationCheckBox.isChecked()){
                    binding.addReservationCheckBox.setChecked(true);
                    reservation_add = 1 ;
                }else {
                    binding.addReservationCheckBox.setChecked(false);
                    binding.reservationCheckBox.setChecked(false);
                    reservation_add = 0 ;
                }
            }
        });

        binding.reviewPropertyCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.reviewPropertyCheckBox.isChecked()){
                    binding.reviewPropertyCheckBox.setChecked(true);
                    property_view = 1 ;
                }else {
                    binding.reviewPropertyCheckBox.setChecked(false);
                    binding.propertyCheckBox.setChecked(false);
                    property_view = 0 ;
                }
            }
        });

        binding.editPropertyCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.editPropertyCheckBox.isChecked()){
                    binding.editPropertyCheckBox.setChecked(true);
                    property_edit = 1 ;
                }else {
                    binding.editPropertyCheckBox.setChecked(false);
                    binding.propertyCheckBox.setChecked(false);
                    property_edit = 0 ;
                }
            }
        });

        binding.deletePropertyCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.deletePropertyCheckBox.isChecked()){
                    binding.deletePropertyCheckBox.setChecked(true);
                    property_delete = 1 ;
                }else {
                    binding.deletePropertyCheckBox.setChecked(false);
                    binding.propertyCheckBox.setChecked(false);
                    property_delete = 0 ;
                }
            }
        });

        binding.addPropertyCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.addPropertyCheckBox.isChecked()){
                    binding.addPropertyCheckBox.setChecked(true);
                    property_add = 1 ;
                }else {
                    binding.addPropertyCheckBox.setChecked(false);
                    binding.propertyCheckBox.setChecked(false);
                    property_add = 0 ;
                }
            }
        });

        binding.purchaseCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.purchaseCheckBox.isChecked()){
                    binding.purchaseCheckBox.setChecked(true);
                    binding.addPurchaseCheckBox.setChecked(true);
                    binding.deletePurchaseCheckBox.setChecked(true);
                    binding.editPurchaseCheckBox.setChecked(true);
                    binding.reviewPurchaseCheckBox.setChecked(true);
                    purchase_add = 1 ;
                    purchase_delete = 1 ;
                    purchase_edit = 1 ;
                    purchase_view = 1 ;
                }else {
                    binding.purchaseCheckBox.setChecked(false);
                    binding.addPurchaseCheckBox.setChecked(false);
                    binding.deletePurchaseCheckBox.setChecked(false);
                    binding.editPurchaseCheckBox.setChecked(false);
                    binding.reviewPurchaseCheckBox.setChecked(false);
                    purchase_add = 0 ;
                    purchase_delete = 0 ;
                    purchase_edit = 0 ;
                    purchase_view = 0 ;
                }
            }
        });

        binding.deviceMeterCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.deviceMeterCheckBox.isChecked()){
                    binding.deviceMeterCheckBox.setChecked(true);
                    binding.addDeviceMeterCheckBox.setChecked(true);
                    binding.deleteDeviceMeterCheckBox.setChecked(true);
                    binding.editDeviceMeterCheckBox.setChecked(true);
                    binding.reviewDeviceMeterCheckBox.setChecked(true);
                    device_meter_add = 1 ;
                    device_meter_delete = 1 ;
                    device_meter_edit = 1 ;
                    device_meter_view = 1 ;
                }else {
                    binding.deviceMeterCheckBox.setChecked(false);
                    binding.addDeviceMeterCheckBox.setChecked(false);
                    binding.deleteDeviceMeterCheckBox.setChecked(false);
                    binding.editDeviceMeterCheckBox.setChecked(false);
                    binding.reviewDeviceMeterCheckBox.setChecked(false);
                    device_meter_add = 0 ;
                    device_meter_delete = 0 ;
                    device_meter_edit = 0 ;
                    device_meter_view = 0 ;
                }
            }
        });

        binding.customerCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.customerCheckBox.isChecked()){
                    binding.customerCheckBox.setChecked(true);
                    binding.addCustomerCheckBox.setChecked(true);
                    binding.deleteCustomerCheckBox.setChecked(true);
                    binding.editCustomerCheckBox.setChecked(true);
                    binding.reviewCustomerCheckBox.setChecked(true);
                    customer_add = 1 ;
                    customer_delete = 1 ;
                    customer_edit = 1 ;
                    customer_view = 1 ;
                }else {
                    binding.customerCheckBox.setChecked(false);
                    binding.addCustomerCheckBox.setChecked(false);
                    binding.deleteCustomerCheckBox.setChecked(false);
                    binding.editCustomerCheckBox.setChecked(false);
                    binding.reviewCustomerCheckBox.setChecked(false);
                    customer_add = 0 ;
                    customer_delete = 0 ;
                    customer_edit = 0 ;
                    customer_view = 0 ;
                }
            }
        });

        binding.reportCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.reportCheckBox.isChecked()){
                    binding.reportCheckBox.setChecked(true);
                    binding.addReportCheckBox.setChecked(true);
                    binding.deleteReportCheckBox.setChecked(true);
                    binding.editReportCheckBox.setChecked(true);
                    binding.reviewReportCheckBox.setChecked(true);
                    report_view = 1 ;
                }else {
                    binding.reportCheckBox.setChecked(false);
                    binding.addReportCheckBox.setChecked(false);
                    binding.deleteReportCheckBox.setChecked(false);
                    binding.editReportCheckBox.setChecked(false);
                    binding.reviewReportCheckBox.setChecked(false);
                    report_view = 0 ;
                }
            }
        });

        binding.addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("new")) {
                    homeViewModel.setPermission(user_api_key, api_key, property_view, property_delete, property_edit, property_add, cleaning_view, cleaning_delete, cleaning_edit,
                            cleaning_add, product_view, product_delete, product_edit, product_add, custmer_view, custmer_delete, custmer_edit, custmer_add, reservation_view,
                            reservation_delete, reservation_edit, reservation_add, purchase_view, purchase_delete, purchase_edit, purchase_add, device_meter_view,
                            device_meter_delete, device_meter_edit, device_meter_add, customer_view, customer_delete, customer_edit, customer_add, report_view);
                }else if (type.equals("update")){
                    homeViewModel.updatePermission(user_api_key, api_key, property_view, property_delete, property_edit, property_add, cleaning_view, cleaning_delete, cleaning_edit,
                            cleaning_add, product_view, product_delete, product_edit, product_add, custmer_view, custmer_delete, custmer_edit, custmer_add, reservation_view,
                            reservation_delete, reservation_edit, reservation_add, purchase_view, purchase_delete, purchase_edit, purchase_add, device_meter_view,
                            device_meter_delete, device_meter_edit, device_meter_add, customer_view, customer_delete, customer_edit, customer_add, report_view ,
                            userPermissionModel.getUserAppPermissionId());
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