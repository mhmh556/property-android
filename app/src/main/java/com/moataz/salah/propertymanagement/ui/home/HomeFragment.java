package com.moataz.salah.propertymanagement.ui.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.databinding.HomeFragmentBinding;
import com.moataz.salah.propertymanagement.model.application.ApplicationModel;
import com.moataz.salah.propertymanagement.model.login.User;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private String TAG = "HomeFragment";

    HomeFragmentModelFactory factory;
    HomeViewModel homeViewModel;
    HomeFragmentBinding binding;
    NavController navController  = null ;
    BottomNavigationView bottom_nav;
    private DrawerLayout drawerLayout;
    ImageView back , logo , info_bt;
    TextView toolbar_title;
    Toolbar toolbar;
    String currentLang = Locale.getDefault().getLanguage();
    UserPreference userPreference ;
    int property_view , reservation_view ;
    ApplicationModel app ;
    User user ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = HomeFragmentBinding.inflate(inflater,container,false);
        fullScreen();
        back = getActivity().findViewById(R.id.back_bt);
        logo = getActivity().findViewById(R.id.logo);
        info_bt = getActivity().findViewById(R.id.info_bt);
        bottom_nav = getActivity().findViewById(R.id.bottom_nav);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar = getActivity().findViewById(R.id.toolbar);
        drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        userPreference = new UserPreference(getContext());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        factory = new HomeFragmentModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(HomeViewModel.class);
        navController = Navigation.findNavController(binding.getRoot());
        toolbar_title.setVisibility(View.GONE);
        logo.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        bottom_nav.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        back.setImageResource(R.drawable.ic_iconly_light_notification);
        toolbar_title.setText(navController.getCurrentDestination().getLabel());
        toolbar.setNavigationIcon(R.drawable.ic_icon_menu);
        toolbar.setBackgroundColor(getActivity().getResources().getColor(R.color.white));

        Log.e("device" , String.valueOf(userPreference.getApp().getReservationView()));

        if (userPreference.getApp().getDeviceMeterView() == 0){
            binding.linearLayout2.setVisibility(View.GONE);
        }else if (userPreference.getApp().getDeviceMeterView() == 1){
            binding.linearLayout2.setVisibility(View.VISIBLE);
        }


        if (getResources().getBoolean(R.bool.only_landscape)) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            //get header view
            View headerView = binding.navHome.getHeaderView(0);
            TextView textView_name = headerView.findViewById(R.id.productNameTV);
            textView_name.setText(userPreference.getUser().getUserFullName());
//        CircleImageView profile_image = headerView.findViewById(R.id.profile_image);

            binding.navHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            Menu menu = binding.navHome.getMenu();

            if (userPreference.getUser().getAdmin() == 1){
                menu.findItem(R.id.nav_employees_apartments).setVisible(true);
            }else if (userPreference.getUser().getAdmin() == 0){
                menu.findItem(R.id.nav_employees_apartments).setVisible(false);
            }

            if (userPreference.getApp().getProductView() == 0){
                menu.findItem(R.id.nav_about_us).setVisible(false);
            }else if (userPreference.getApp().getProductView() == 1){
                menu.findItem(R.id.nav_about_us).setVisible(true);
            }

            if (userPreference.getApp().getDeviceMeterView() == 0){
                menu.findItem(R.id.nav_expenses_appointments).setVisible(false);
            }else if (userPreference.getApp().getDeviceMeterView() == 1){
                menu.findItem(R.id.nav_expenses_appointments).setVisible(true);
            }

            binding.navHome.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_counters:
                            navController.navigate(R.id.nav_counters_fragment);
                            break;
                        case R.id.nav_reserved_apartments: {
                            Bundle b = new Bundle();
                            b.putString("key" , "add");
                            navController.navigate(R.id.nav_reserved_apartments_fragment , b);
                            break;
                        }
                        case R.id.nav_vacant_apartments: {
                            navController.navigate(R.id.nav_vacant_fragment);
                            break;
                        }
                        case R.id.nav_printer:{
                            navController.navigate(R.id.nav_printer_fragment);
                            break;
                        }
                        case R.id.nav_expenses_appointments: {
                            navController.navigate(R.id.nav_general_expenses_fragment);
                            break;
                        }
                        case R.id.nav_employees_apartments: {
                            navController.navigate(R.id.nav_employees_fragment);
                            break;
                        }
                        case R.id.nav_cleaning_appointments: {
                            navController.navigate(R.id.nav_customer_fragment);
                            break;
                        }
                        case R.id.nav_about_us: {
                            navController.navigate(R.id.nav_products_fragment);
                            break;
                        }
                        case R.id.nav_sign_out: {
                            navController.navigate(R.id.nav_login_fragment);
                            break;
                        }
                    }
                    return true;
                }
            });
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            NavigationView nav = getActivity().findViewById(R.id.nav_view);
            Menu menu = nav.getMenu();

            if (userPreference.getUser().getAdmin() == 1){
                menu.findItem(R.id.nav_employees_apartments).setVisible(true);
            }else if (userPreference.getUser().getAdmin() == 0){
                menu.findItem(R.id.nav_employees_apartments).setVisible(false);
            }

            if (userPreference.getApp().getProductView() == 0){
                menu.findItem(R.id.nav_about_us).setVisible(false);
            }else if (userPreference.getApp().getProductView() == 1){
                menu.findItem(R.id.nav_about_us).setVisible(true);
            }

            if (userPreference.getApp().getDeviceMeterView() == 0){
                menu.findItem(R.id.nav_expenses_appointments).setVisible(false);
            }else if (userPreference.getApp().getDeviceMeterView() == 1){
                menu.findItem(R.id.nav_expenses_appointments).setVisible(true);
            }
        }

        app = userPreference.getApp();
        user = userPreference.getUser();
        property_view = app.getPropertyView();
        reservation_view = app.getReservationView();

        if (property_view == 0){
            binding.linearLayout.setVisibility(View.GONE);
        }else {
            binding.linearLayout.setVisibility(View.VISIBLE);
        }

        if (reservation_view == 0 ){
            binding.linearLayout2.setVisibility(View.GONE);
            binding.textView.setVisibility(View.GONE);
        }else {
            binding.linearLayout2.setVisibility(View.VISIBLE);
            binding.textView.setVisibility(View.VISIBLE);
        }

        binding.reserved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("key" , "add");
                navController.navigate(R.id.nav_reserved_apartments_fragment , b);
            }
        });

        info_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDialog(app);
            }
        });

        binding.allTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("key" , "all");
                navController.navigate(R.id.nav_task_fragment , b);
            }
        });

        binding.scheduledTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("key" , "scheduled");
                navController.navigate(R.id.nav_task_fragment , b);
            }
        });

        binding.doneTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("key" , "done");
                navController.navigate(R.id.nav_task_fragment , b);
            }
        });

        if (binding.vacant != null) {
            binding.vacant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navController.navigate(R.id.nav_vacant_fragment);
                }
            });
        }

        assert binding.leaving != null;
        binding.leaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_reserved_flat_fragment);
            }
        });

        binding.doneTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_contact_us_fragment);
            }
        });

        binding.monthEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_reserved_flat_fragment);
            }
        });

        homeViewModel.intUi(binding,getContext(),navController,currentLang);

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
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
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

    public void infoDialog(ApplicationModel item) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.info_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView close = dialog.findViewById(R.id.close_bt);

        TextView user_id = dialog.findViewById(R.id.user_id);
        user_id.setText(String.valueOf(user.getUserId()));
        TextView user_full_name = dialog.findViewById(R.id.user_full_name);
        user_full_name.setText(user.getUserFullName());
        TextView user_mail = dialog.findViewById(R.id.user_mail);
        user_mail.setText(user.getUserEmail());
        TextView user_phone = dialog.findViewById(R.id.user_phone);
        user_phone.setText(String.valueOf(user.getUserPhone()));
        TextView user_job = dialog.findViewById(R.id.user_job);
        user_job.setText(user.getUserJob());
        TextView city = dialog.findViewById(R.id.city);
        city.setText(user.getCity());
        TextView user_national_id = dialog.findViewById(R.id.user_national_id);
        user_national_id.setText(String.valueOf(user.getUserNationalId()));
        TextView user_api_key = dialog.findViewById(R.id.user_api_key);
        user_api_key.setText(user.getUserApiKey());

        TextView app_id = dialog.findViewById(R.id.app_id);
        app_id.setText(String.valueOf(app.getApplicationId()));
        TextView app_name = dialog.findViewById(R.id.app_name);
        app_name.setText(app.getAppName());
        TextView app_permission_created = dialog.findViewById(R.id.app_permission_created);
        app_permission_created.setText(app.getApplicationCreated());
        TextView app_permission_updated = dialog.findViewById(R.id.app_permission_updated);
        app_permission_updated.setText(app.getApplicationUpdated());
        CheckBox app_activation = dialog.findViewById(R.id.app_activation);
        if (app.getApplicationActivation().equals("1")){
            app_activation.setChecked(true);
        }else {
            app_activation.setChecked(false);
        }
        TextView app_api_key = dialog.findViewById(R.id.app_api_key);
        app_api_key.setText(app.getApplicationAppApiKey());
        TextView app_created = dialog.findViewById(R.id.app_created);
        app_created.setText(app.getApplicationCreated());
        TextView app_type_id = dialog.findViewById(R.id.app_type_id);
        app_type_id.setText(String.valueOf(app.getAppTypeId()));
        TextView app_updated = dialog.findViewById(R.id.app_updated);
        app_updated.setText(app.getApplicationUpdated());
        CheckBox cleaning_add = dialog.findViewById(R.id.cleaning_add_check);
        CheckBox cleaning_delete = dialog.findViewById(R.id.cleaning_delete_check);
        CheckBox cleaning_edit = dialog.findViewById(R.id.cleaning_edit_check);
        CheckBox cleaning_view = dialog.findViewById(R.id.cleaning_view_check);
        CheckBox custmers_add = dialog.findViewById(R.id.custmers_add_check);
        CheckBox custmers_delete = dialog.findViewById(R.id.custmers_delete_check);
        CheckBox custmers_edit = dialog.findViewById(R.id.custmers_edit_check);
        CheckBox custmers_view = dialog.findViewById(R.id.custmer_view_check);
        CheckBox reservation_add = dialog.findViewById(R.id.reservation_add_check);
        CheckBox reservation_delete = dialog.findViewById(R.id.reservation_delete_check);
        CheckBox reservation_edit = dialog.findViewById(R.id.reservation_edit_check);
        CheckBox reservation_view = dialog.findViewById(R.id.reservation_view_check);
        CheckBox purchase_add = dialog.findViewById(R.id.purchase_add_check);
        CheckBox purchase_delete = dialog.findViewById(R.id.purchase_delete_check);
        CheckBox purchase_edit = dialog.findViewById(R.id.purchase_edit_check);
        CheckBox purchase_view = dialog.findViewById(R.id.purchase_view_check);
        CheckBox device_meter_add = dialog.findViewById(R.id.device_meter_add_check);
        CheckBox device_meter_delete = dialog.findViewById(R.id.device_meter_delete_check);
        CheckBox device_meter_edit = dialog.findViewById(R.id.device_meter_edit_check);
        CheckBox device_meter_view = dialog.findViewById(R.id.device_meter_view_check);
        CheckBox customer_add = dialog.findViewById(R.id.customer_add_check);
        CheckBox customer_delete = dialog.findViewById(R.id.customer_delete_check);
        CheckBox customer_edit = dialog.findViewById(R.id.customer_edit_check);
        CheckBox customer_view = dialog.findViewById(R.id.customer_view_check);
        CheckBox property_add = dialog.findViewById(R.id.property_add_check);
        CheckBox property_delete = dialog.findViewById(R.id.property_delete_check);
        CheckBox property_edit = dialog.findViewById(R.id.property_edit_check);
        CheckBox property_view = dialog.findViewById(R.id.property_view_check);
        CheckBox report_view = dialog.findViewById(R.id.report_view_check);

        if (app.getCleaningAdd() == 1){
            cleaning_add.setChecked(true);
        }else {
            cleaning_add.setChecked(false);
        }

        if (app.getCleaningEdit() == 1){
            cleaning_edit.setChecked(true);
        }else {
            cleaning_edit.setChecked(false);
        }

        if (app.getCleaningDelete() == 1){
            cleaning_delete.setChecked(true);
        }else {
            cleaning_delete.setChecked(false);
        }

        if (app.getCleaningView() == 1){
            cleaning_view.setChecked(true);
        }else {
            cleaning_view.setChecked(false);
        }

        if (app.getCustmerAdd() == 1){
            custmers_add.setChecked(true);
        }else {
            custmers_add.setChecked(false);
        }
        if (app.getCustmerDelete() == 1){
            custmers_delete.setChecked(true);
        }else {
            custmers_delete.setChecked(false);
        }
        if (app.getCustmerEdit() == 1){
            custmers_edit.setChecked(true);
        }else {
            custmers_edit.setChecked(false);
        }
        if (app.getCustmerView() == 1){
            custmers_view.setChecked(true);
        }else {
            custmers_view.setChecked(false);
        }

        if (app.getReservationAdd() == 1){
            reservation_add.setChecked(true);
        }else {
            reservation_add.setChecked(false);
        }

        if (app.getReservationEdit() == 1){
            reservation_edit.setChecked(true);
        }else {
            reservation_edit.setChecked(false);
        }

        if (app.getReservationDelete() == 1){
            reservation_delete.setChecked(true);
        }else {
            reservation_delete.setChecked(false);
        }

        if (app.getReservationView() == 1){
            reservation_view.setChecked(true);
        }else {
            reservation_view.setChecked(false);
        }

        if (app.getPurchaseAdd() == 1){
            purchase_add.setChecked(true);
        }else {
            purchase_add.setChecked(false);
        }
        if (app.getPurchaseDelete() == 1){
            purchase_delete.setChecked(true);
        }else {
            purchase_delete.setChecked(false);
        }
        if (app.getPurchaseEdit() == 1){
            purchase_edit.setChecked(true);
        }else {
            purchase_edit.setChecked(false);
        }
        if (app.getPurchaseView() == 1){
            purchase_view.setChecked(true);
        }else {
            purchase_view.setChecked(false);
        }

        if (app.getDeviceMeterAdd() == 1){
            device_meter_add.setChecked(true);
        }else {
            device_meter_add.setChecked(false);
        }
        if (app.getDeviceMeterDelete() == 1){
            device_meter_delete.setChecked(true);
        }else {
            device_meter_delete.setChecked(false);
        }
        if (app.getDeviceMeterEdit() == 1){
            device_meter_edit.setChecked(true);
        }else {
            device_meter_edit.setChecked(false);
        }
        if (app.getDeviceMeterView() == 1){
            device_meter_view.setChecked(true);
        }else {
            device_meter_view.setChecked(false);
        }

        if (app.getCustomerAdd() == 1){
            customer_add.setChecked(true);
        }else {
            customer_add.setChecked(false);
        }
        if (app.getCustomerDelete() == 1){
            customer_delete.setChecked(true);
        }else {
            customer_delete.setChecked(false);
        }
        if (app.getCustomerEdit() == 1){
            customer_edit.setChecked(true);
        }else {
            customer_edit.setChecked(false);
        }
        if (app.getCustomerView() == 1){
            customer_view.setChecked(true);
        }else {
            customer_view.setChecked(false);
        }

        if (app.getPropertyAdd() == 1){
            property_add.setChecked(true);
        }else {
            property_add.setChecked(false);
        }
        if (app.getPropertyDelete() == 1){
            property_delete.setChecked(true);
        }else {
            property_delete.setChecked(false);
        }
        if (app.getPropertyEdit() == 1){
            property_edit.setChecked(true);
        }else {
            property_edit.setChecked(false);
        }
        if (app.getPropertyView() == 1){
            property_view.setChecked(true);
        }else {
            property_view.setChecked(false);
        }

        if (app.getReportView() == 1){
            report_view.setChecked(true);
        }else {
            report_view.setChecked(false);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


}