package com.moataz.salah.propertymanagement.ui.application;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.adapter.ApplicationAdapter;
import com.moataz.salah.propertymanagement.databinding.AppFragmentBinding;
import com.moataz.salah.propertymanagement.model.application.ApplicationModel;
import com.moataz.salah.propertymanagement.model.login.User;
import java.util.List;
import java.util.Locale;

public class ApplicationFragment extends Fragment implements ApplicationAdapter.onSaleItemClick , ApplicationAdapter.onInfoClick{

    private String TAG = "HomeFragment";

    ApplicationFragmentModelFactory factory;
    ApplicationViewModel homeViewModel;
    AppFragmentBinding binding;
    NavController navController  = null ;
    BottomNavigationView bottom_nav;
    ImageView back , logo;
    TextView toolbar_title;
    Toolbar toolbar;
    String currentLang = Locale.getDefault().getLanguage();
    List<ApplicationModel> list ;
    User user;
    ApplicationAdapter adapter ;
    UserPreference userPreference ;
    private DrawerLayout drawerLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = AppFragmentBinding.inflate(inflater,container,false);
        fullScreen();
        back = getActivity().findViewById(R.id.back_bt);
        bottom_nav = getActivity().findViewById(R.id.bottom_nav);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar = getActivity().findViewById(R.id.toolbar);
        logo = getActivity().findViewById(R.id.logo);
        userPreference = new UserPreference(getContext());
        drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        factory = new ApplicationFragmentModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(ApplicationViewModel.class);
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
        toolbar.setVisibility(View.GONE);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        binding.loader.setVisibility(View.VISIBLE);

        if (userPreference.getUser().getAdmin() == 0){
            binding.createStore.setVisibility(View.GONE);
        }else if (userPreference.getUser().getAdmin() == 1){
            binding.createStore.setVisibility(View.VISIBLE);
        }

        homeViewModel.intUi(binding , getContext() , navController , userPreference , list);
        Bundle b = getArguments();
        if (b != null){
            user = (User) b.getSerializable("data");
            if (user != null ){
                list = user.getApplications();
                adapter = new ApplicationAdapter(getContext() , list);
                adapter.setOnSaleItemClick(this);
                adapter.setOnInfoClick(this);
                if(getResources().getBoolean(R.bool.only_landscape)){
                    binding.recyclerView2.setLayoutManager(new GridLayoutManager(getContext() , 5));
                }else if(getResources().getBoolean(R.bool.only_portarite)){
                    binding.recyclerView2.setLayoutManager(new GridLayoutManager(getContext() , 2));
                }
                binding.recyclerView2.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                binding.loader.setVisibility(View.GONE);
                if (user.getAdmin() == 0){
                    binding.createStore.setVisibility(View.GONE);
                }else {
                    binding.createStore.setVisibility(View.VISIBLE);
                }
            }
        }

        binding.createStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
    }

    public void showAddDialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.add_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ProgressBar loader = dialog.findViewById(R.id.loader);
        loader.setVisibility(View.GONE);
        TextView title = dialog.findViewById(R.id.title_text);
        title.setText(R.string.add_app);
        TextView header = dialog.findViewById(R.id.text);
        header.setText(R.string.name);
        ImageView close = dialog.findViewById(R.id.close_bt);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button add = dialog.findViewById(R.id.add_bt);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loader.setVisibility(View.VISIBLE);
                homeViewModel.addNewApp(dialog , 1);
            }
        });

        dialog.show();
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
    public void onClickListener(ApplicationModel item) {
        userPreference.saveApiKey(item.getApplicationAppApiKey());
        userPreference.saveApp(item);
        navController.navigate(R.id.nav_home_fragment);
    }

    @Override
    public void onInfoClickListener(ApplicationModel item) {

    }
}