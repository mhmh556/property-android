package com.moataz.salah.propertymanagement.ui.addContactUs;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.adapter.TaskJobAdapter;
import com.moataz.salah.propertymanagement.databinding.AddContactUsFragmentBinding;
import com.moataz.salah.propertymanagement.databinding.AddTaskFragmentBinding;
import com.moataz.salah.propertymanagement.model.contactType.ContactTypeModel;
import com.moataz.salah.propertymanagement.model.contactUs.ContactUsModel;
import com.moataz.salah.propertymanagement.model.property.PropertyModel;
import com.moataz.salah.propertymanagement.model.taskJob.TaskJobModel;
import com.moataz.salah.propertymanagement.model.taskJob.TaskJobResponse;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContactUsFragment extends Fragment {

    private String TAG = "HomeFragment";

    AddContactUsModelFactory factory;
    AddContactUsViewModel homeViewModel;
    AddContactUsFragmentBinding binding;
    NavController navController  = null ;
    BottomNavigationView bottom_nav;
    ImageView back , logo;
    List<ContactTypeModel> list;
    TextView toolbar_title;
    Toolbar toolbar;
    String currentLang = Locale.getDefault().getLanguage();
    UserPreference userPreference;
    String token ;
    String api_key ;
    String key ;
    ContactUsModel model ;
    Dialog dialog2 ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = AddContactUsFragmentBinding.inflate(inflater,container,false);
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
        factory = new AddContactUsModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(AddContactUsViewModel.class);
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
        token = userPreference.getUser().getAccessToken();
        Log.e("token" , token);
        Log.e("phone" , userPreference.getUser().getUserPhone().toString());

        Bundle b  = getArguments();
        if (b == null){
            return;
        }else {
            model = (ContactUsModel) b.getSerializable("data");
            key = (String) b.get("action");
            if (key.equals("add")){
                toolbar_title.setText(R.string.contact_us);
                binding.addBt.setText(R.string.add);
            }else if (key.equals("update")){
                toolbar_title.setText(R.string.contact_us);
                binding.addBt.setText(R.string.update);
            }
        }

        if (model != null ){
            binding.durationInput.setText(model.getTitle());
            binding.notesInput.setText(model.getContactText());
        }

        binding.administratorSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.getContactType();
            }
        });

        homeViewModel.intUi(binding,getContext(),navController,list,currentLang , userPreference);

//        binding.phoneInput.setText(userPreference.getUser().getUserPhone().toString());

        binding.addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (key.equals("add")){
                    homeViewModel.addNewContactUs();
                }else if (key.equals("update")){
                    if (model != null) {
                        homeViewModel.update_item(model.getContactTypeId() , model.getContactUserId() , model.getContactUsId() , model.getName());
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
}