package com.moataz.salah.propertymanagement.ui.addPrice;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.databinding.AddPriceFragmentBinding;
import com.moataz.salah.propertymanagement.model.FlatModel;
import com.moataz.salah.propertymanagement.model.addFlat.FlatResponseModel;
import com.moataz.salah.propertymanagement.model.apt.AptTypeModel;
import com.moataz.salah.propertymanagement.model.price.AddPriceResponse;
import com.moataz.salah.propertymanagement.model.property.PropertyModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPriceFragment extends Fragment {

    private String TAG = "HomeFragment";

    AddPriceModelFactory factory;
    AddPriceViewModel homeViewModel;
    AddPriceFragmentBinding binding;
    NavController navController = null;
    BottomNavigationView bottom_nav;
    ImageView back, logo;
    List<AptTypeModel> list;
    TextView toolbar_title;
    Toolbar toolbar;
    String currentLang = Locale.getDefault().getLanguage();
    UserPreference userPreference;
    String token;
    String api_key;
    FlatResponseModel flatModel;
    int property_id ;
    Dialog dialog ;
    String type ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = AddPriceFragmentBinding.inflate(inflater, container, false);
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
        factory = new AddPriceModelFactory(requireContext(), binding);
        homeViewModel = new ViewModelProvider(this, factory).get(AddPriceViewModel.class);
        navController = Navigation.findNavController(binding.getRoot());
        bottom_nav.setVisibility(View.GONE);
        logo.setVisibility(View.GONE);
        toolbar_title.setVisibility(View.VISIBLE);
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        toolbar_title.setLayoutParams(params);
        back.setVisibility(View.VISIBLE);
        back.setImageResource(R.drawable.ic_back);
        toolbar_title.setText(navController.getCurrentDestination().getLabel());
        toolbar.setNavigationIcon(null);
        toolbar.setBackgroundColor(getActivity().getResources().getColor(R.color.ddd));
        api_key = userPreference.getApiKey();
        token = userPreference.getToken();
        binding.loader.setVisibility(View.VISIBLE);
        Log.e("token", token);

        Log.e("add price" , String.valueOf(userPreference.getApp().getPropertyAdd()));

        if (userPreference.getApp().getPropertyAdd() == 0){
            binding.add.setVisibility(View.GONE);
        }else if (userPreference.getApp().getPropertyAdd() == 1){
            binding.add.setVisibility(View.VISIBLE);
        }

        if (userPreference.getApp().getPropertyEdit() == 0){
            binding.editBt.setVisibility(View.GONE);
        }else if (userPreference.getApp().getPropertyEdit() == 1){
            binding.editBt.setVisibility(View.VISIBLE);
        }

        Bundle b = getArguments();
        if (b == null) {
            binding.loader.setVisibility(View.GONE);
            return;
        } else {
            flatModel = (FlatResponseModel) b.getSerializable("data");
            if (flatModel != null) {
                type = b.getString("type");
                binding.setFlatNumber(flatModel.getAptNum());
                binding.n1.setText(String.valueOf(flatModel.getNumBath()));
                binding.n2.setText(String.valueOf(flatModel.getNumRooms()));
                binding.n3.setText(String.valueOf(flatModel.getNumLivingRoom()));
                binding.n4.setText(String.valueOf(flatModel.getNumFloor()));
                property_id = flatModel.getPropertyId();
                binding.loader.setVisibility(View.GONE);
            }
        }
        homeViewModel.intUi(binding,getContext(),navController,list,currentLang,token,api_key);

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNewReserveTypeDialog();
//                homeViewModel.addPrice(property_id , "test add" , 130);
            }
        });
}

    public void showAddDialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.add_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showAddNewReserveTypeDialog(){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.new_reserve_type_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText type = dialog.findViewById(R.id.price_type_input);
        EditText price = dialog.findViewById(R.id.price_value_input);
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
                if (!validation(type) || !validation(price)){
                    return;
                }else {
                    addPrice(property_id , type.getText().toString() , Integer.parseInt(price.getText().toString()));
                }
            }
        });

        dialog.show();
    }

    public void showDeleteDialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.confirm_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView close = dialog.findViewById(R.id.close_bt);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button delete = dialog.findViewById(R.id.delete_bt);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button cancel = dialog.findViewById(R.id.cancel_bt);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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

    private Boolean validation(EditText editText){
        String areaNumInput = editText.getText().toString().trim();
        if (areaNumInput.isEmpty()){
            editText.setError("Field can't be empty");
            return false;
        }else {
            editText.setError(null);
            return true;
        }
    }

    public void addPrice (int property_id , String price_name , int price){
        binding.loader.setVisibility(View.VISIBLE);
        int active = 1;
        String mod = "add-property-price";
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("property_id", property_id);
            jsonObj_.put("price", Integer.valueOf(price));
            jsonObj_.put("price_name", price_name);
            jsonObj_.put("active", active);
            jsonObj_.put("access_token", token);
            jsonObj_.put("mod", mod);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiInterface apiInterface = RetrofitClient2.getInstance().getApi();
        Call<AddPriceResponse> call = apiInterface.addPropertiesPrice("application/json", gsonObject);
        call.enqueue(new Callback<AddPriceResponse>() {
            @Override
            public void onResponse(Call<AddPriceResponse> call, Response<AddPriceResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        binding.loader.setVisibility(View.GONE);
                        Log.e("message" , response.body().getMessage());
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        if (type.equals("vacant")){
                            navController.navigate(R.id.nav_vacant_fragment);
                        }else {
                            Bundle b = new Bundle();
                            b.putString("key", "add");
                            navController.navigate(R.id.nav_reserved_apartments_fragment, b);
                        }
                    } else {
                        binding.loader.setVisibility(View.GONE);
                        Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.loader.setVisibility(View.GONE);
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddPriceResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}