package com.moataz.salah.propertymanagement.ui.reserve;

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
import com.moataz.salah.propertymanagement.adapter.CustomerAdapter;
import com.moataz.salah.propertymanagement.databinding.ReservationFragment2Binding;
import com.moataz.salah.propertymanagement.model.customer.CustomerModel;
import com.moataz.salah.propertymanagement.model.customer.ResponseCustomer;
import com.moataz.salah.propertymanagement.model.property.PropertyModel;
import com.moataz.salah.propertymanagement.model.property.PropertyPriceModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationFragment extends Fragment implements CustomerAdapter.onClick{

    private String TAG = "HomeFragment";

    ReservationModelFactory factory;
    ReservationViewModel homeViewModel;
    ReservationFragment2Binding binding;
    NavController navController  = null ;
    BottomNavigationView bottom_nav;
    ImageView back , logo;
    List<CustomerModel> list;
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
    RecyclerView recyclerView ;
    ProgressBar loader;
    String api_key ;
    int customer_id = 0 ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = ReservationFragment2Binding.inflate(inflater,container,false);
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
        factory = new ReservationModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(ReservationViewModel.class);
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
        token = userPreference.getToken();
        api_key = userPreference.getApiKey();
        Log.e("token" , token);

        Bundle b = getArguments();
        if (b != null){
            propertyModel = (PropertyModel) b.getSerializable("data");
            if (propertyModel != null){
                property_id = propertyModel.getPropertyId();
                Log.e("property_id" , String.valueOf(property_id));
            }
        }

        binding.spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinnerDialog();
            }
        });

        binding.bookingDateInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    pickDate(binding.bookingDateInput);
                }else {
                    binding.bookingDateInput.setFocusable(true);
                }
            }
        });

        binding.leavingDateInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    pickDate(binding.leavingDateInput);
                }else {
                    binding.leavingDateInput.setFocusable(true);
                }
            }
        });

        homeViewModel.intUi(binding , getContext() , navController , price_list , currentLang , token , dialog3 , api_key , property_id);

        binding.spinnerPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.showPriceDialog();
            }
        });

        binding.reservationBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.loader.setVisibility(View.VISIBLE);
                homeViewModel.addReservation(propertyModel , customer_id);
            }
        });

    }

    public void showSpinnerDialog(){
        dialog2 = new Dialog(getContext());
        dialog2.setContentView(R.layout.customer_dialog);
        dialog2.setCancelable(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ProgressBar loader = dialog2.findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);
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
                Bundle b = new Bundle();
                b.putString("from" , "dialog");
                b.putString("type" , "add");
                b.putSerializable("data" , propertyModel);
                navController.navigate(R.id.nav_add_customer_fragment , b);
                dialog2.dismiss();
            }
        });

        String mod = "get-customers" ;
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("app_api_key", api_key);
            jsonObj_.put("access_token", token);
            jsonObj_.put("mod", mod);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiInterface = RetrofitClient2.getInstance().getApi();
        Call<ResponseCustomer> call = apiInterface.getCustomer("application/json" , gsonObject);
        call.enqueue(new Callback<ResponseCustomer>() {
            @Override
            public void onResponse(Call<ResponseCustomer> call, Response<ResponseCustomer> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        loader.setVisibility(View.GONE);
                        list = response.body().getData();
                        RecyclerView recyclerView = dialog2.findViewById(R.id.recyclerView3);
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext() , 1));
                        CustomerAdapter adapter = new CustomerAdapter(getContext() , list);
                        adapter.setOnClick(ReservationFragment.this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else {
                        loader.setVisibility(View.GONE);
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    loader.setVisibility(View.GONE);
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCustomer> call, Throwable t) {
                loader.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        dialog2.show();
    }

//    public void showPriceDialog(){
//        dialog3 = new Dialog(getContext());
//        dialog3.setContentView(R.layout.customer_dialog);
//        dialog3.setCancelable(false);
//        dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        TextView title = dialog3.findViewById(R.id.title_text);
//        title.setText(R.string.booking_price);
//        recyclerView = dialog3.findViewById(R.id.recyclerView3);
//        ImageView close = dialog3.findViewById(R.id.close_bt);
//        loader = dialog3.findViewById(R.id.loader);
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog3.dismiss();
//            }
//        });
//        homeViewModel.getPriceList(property_id , recyclerView , loader);
//        Button add = dialog3.findViewById(R.id.add_bt);
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showAddNewReserveTypeDialog();
//            }
//        });
//
//        dialog3.show();
//    }

//    public void showAddNewReserveTypeDialog(){
//        Dialog dialog = new Dialog(getContext());
//        dialog.setContentView(R.layout.new_reserve_type_dialog);
//        dialog.setCancelable(false);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        EditText price_name = dialog.findViewById(R.id.price_type_input);
//        EditText price_value = dialog.findViewById(R.id.price_value_input);
//        ImageView close = dialog.findViewById(R.id.close_bt);
//        recyclerView = dialog2.findViewById(R.id.recyclerView3);
//        loader = dialog2.findViewById(R.id.loader);
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        Button add = dialog.findViewById(R.id.add_bt);
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!validationInput(price_name) || !validationInput(price_value)) {
//                    return;
//                } else {
//                    String priceName = price_name.getText().toString();
//                    String priceValue = price_value.getText().toString();
//                    int active = 1;
//                    String mod = "add-properties-price";
//
//                    JsonObject gsonObject = new JsonObject();
//                    try {
//                        JSONObject jsonObj_ = new JSONObject();
//                        jsonObj_.put("property_id", property_id);
//                        jsonObj_.put("price_name", priceName);
//                        jsonObj_.put("price", Integer.valueOf(priceValue));
//                        jsonObj_.put("active", active);
//                        jsonObj_.put("app_api_key", api_key);
//                        jsonObj_.put("access_token", token);
//                        jsonObj_.put("mod", mod);
//
//                        JsonParser jsonParser = new JsonParser();
//                        gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
//                        Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    apiInterface = RetrofitClient2.getInstance().getApi();
//                    Call<AddPropertyResponse> call = apiInterface.addPropertiesPrice("application/json", gsonObject);
//                    call.enqueue(new Callback<AddPropertyResponse>() {
//                        @Override
//                        public void onResponse(Call<AddPropertyResponse> call, Response<AddPropertyResponse> response) {
//                            if (response.isSuccessful()) {
//                                if (response.body() != null) {
//                                    Log.e("message" , response.body().getMessage());
//                                    homeViewModel.getPriceList(property_id , recyclerView , loader);
//                                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                                    dialog.dismiss();
//                                } else {
//                                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
//                                }
//                            } else {
//                                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<AddPropertyResponse> call, Throwable t) {
//                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        });
//        dialog.show();
//    }


    @Override
    public void onClickListener(CustomerModel item) {
            binding.spinner.setText(item.getName());
            customer_id = item.getCustomerId();
            dialog2.dismiss();
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

    private Boolean validationInput(EditText editText){
        String text = editText.getText().toString().trim();
        if (text.isEmpty()){
            editText.setError("Field can't be empty");
            return false;
        }else {
            editText.setError(null);
            return true;
        }
    }
}