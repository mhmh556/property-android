package com.moataz.salah.propertymanagement.ui.reservedApartmentsDetails;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.adapter.ReservedFlatAdapter;
import com.moataz.salah.propertymanagement.adapter.SliderAdapterExample;
import com.moataz.salah.propertymanagement.databinding.ReservedApartmentsDetailsFragmentBinding;
import com.moataz.salah.propertymanagement.model.MessageResponse;
import com.moataz.salah.propertymanagement.model.Pic;
import com.moataz.salah.propertymanagement.model.UpdateResponse;
import com.moataz.salah.propertymanagement.model.addFlat.AddNewFlatResponse;
import com.moataz.salah.propertymanagement.model.reserve.ReservationListResponse;
import com.moataz.salah.propertymanagement.model.reserve.ReservationModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import com.moataz.salah.propertymanagement.ui.reservedFlat.ReservedFlatViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservedApartmentsDetailsFragment extends Fragment {

    private String TAG = "HomeFragment";

    ReservedApartmentsDetailsModelFactory factory;
    ReservedApartmentsDetailsViewModel homeViewModel;
    ReservedApartmentsDetailsFragmentBinding binding;
    NavController navController  = null ;
    BottomNavigationView bottom_nav;
    ImageView back , logo;
    TextView toolbar_title;
    Toolbar toolbar;
    String currentLang = Locale.getDefault().getLanguage();
    UserPreference userPreference ;
    ReservationModel reservationModel ;
    int reservation_id ;
    Dialog dialog ;
    String key = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getResources().getBoolean(R.bool.only_landscape)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else if(getResources().getBoolean(R.bool.only_portarite)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        binding = ReservedApartmentsDetailsFragmentBinding.inflate(inflater,container,false);
        fullScreen();
        back = getActivity().findViewById(R.id.back_bt);
        bottom_nav = getActivity().findViewById(R.id.bottom_nav);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar = getActivity().findViewById(R.id.toolbar);
        logo = getActivity().findViewById(R.id.logo);
        userPreference = new UserPreference(getContext());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        factory = new ReservedApartmentsDetailsModelFactory(requireContext(),binding);
        homeViewModel = new ViewModelProvider(this,factory).get(ReservedApartmentsDetailsViewModel.class);
        navController = Navigation.findNavController(binding.getRoot());
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        toolbar_title.setLayoutParams(params);
        toolbar_title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        bottom_nav.setVisibility(View.VISIBLE);
        back.setImageResource(R.drawable.ic_back);
        logo.setVisibility(View.GONE);
//        toolbar_title.setText(navController.getCurrentDestination().getLabel());
        toolbar.setNavigationIcon(null);
        toolbar.setBackgroundColor(getActivity().getResources().getColor(R.color.ddd));

        Bundle b = getArguments();
        if (b != null){
            key = b.getString("key");
            reservationModel = (ReservationModel) b.getSerializable("data");
            Log.e("flat" , String.valueOf(reservationModel.getAptNum()));
            toolbar_title.setText(getContext().getResources().getString(R.string.flat_30) + " " + String.valueOf(reservationModel.getAptNum()));
            reservation_id = reservationModel.getReservationId();
            binding.peopleNumberText.setText(String.valueOf(reservationModel.getNumPerson()));
            binding.bookingDateText.setText(reservationModel.getCheckInDate().substring(0, 10));
            binding.leavingDateText.setText(reservationModel.getCheckOutDate().substring(0, 10));
        }else {
            reservationModel = new ReservationModel();
        }
        SliderAdapterExample adapter = new SliderAdapterExample(getContext());
        adapter.addItem(new Pic(R.drawable.pic2));
        adapter.addItem(new Pic(R.drawable.pic2));
        adapter.addItem(new Pic(R.drawable.pic2));
        adapter.addItem(new Pic(R.drawable.pic2));
        binding.imageSlider.setSliderAdapter(adapter);
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        binding.imageSlider.setScrollTimeInSec(3);
        binding.imageSlider.setAutoCycle(true);

        if (userPreference.getApp().getReservationEdit() == 0){
            binding.add.setVisibility(View.GONE);
        }else if (userPreference.getApp().getReservationEdit() == 1){
            binding.add.setVisibility(View.VISIBLE);
        }

        homeViewModel.intUi(binding,getContext(),navController,currentLang);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_reserved_flat_fragment);
            }
        });

        binding.extendBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    public void showAddDialog(){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.add_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView title = dialog.findViewById(R.id.title_text);
        title.setText(R.string.expand_date);
        TextView subTitle = dialog.findViewById(R.id.text);
        subTitle.setVisibility(View.GONE);
        ImageView close = dialog.findViewById(R.id.close_bt);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        EditText editText = dialog.findViewById(R.id.name_text);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    pickDate(editText);
                }else {
                    editText.setFocusable(true);
                }
            }
        });
        Button add = dialog.findViewById(R.id.add_bt);
        add.setText(R.string.expand);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validation(editText)){
                    return;
                }else {
                    extensionDate(editText.getText().toString() , reservation_id , userPreference.getApiKey() , userPreference.getToken());
                }
                dialog.dismiss();
            }
        });

        dialog.show();
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
            date_text.setText(year_text +"/"+ month_text +"/"+ day);
            dialog.dismiss();
            date_text.setFocusable(true);
        });
        dialog.show();
    }

    private Boolean validation(EditText editText){
        String text = editText.getText().toString().trim();
        if (text.isEmpty()){
            editText.setError("Field can't be empty");
            return false;
        }else {
            editText.setError(null);
            return true;
        }
    }

    public void extensionDate(String date , int id , String api_key , String token){
        binding.loader.setVisibility(View.VISIBLE);
        String mod = "update-check-out";

        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("check_out_date" , date);
            jsonObj_.put("reservation_id" , id);
            jsonObj_.put("app_api_key", api_key);
            jsonObj_.put("access_token", token);
            jsonObj_.put("mod", mod);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiInterface apiInterface = RetrofitClient2.getInstance().getApi();
        Call<MessageResponse> call = apiInterface.updateCheckOut("application/json" , gsonObject);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null) {
//                        if (response.body().getStatus() == 0) {
                            binding.loader.setVisibility(View.GONE);
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
//                        showDialog(response.body().getMessage() , getContext());
                            if (key.equals("add")){
                                Bundle b = new Bundle();
                                b.putString("key" , "add");
                                navController.navigate(R.id.nav_reserved_apartments_fragment , b);
                            }else {
                                navController.navigate(R.id.nav_reserved_flat_fragment);
                            }
//                        }else if (response.body().getStatus() == 1){
//                            binding.loader.setVisibility(View.GONE);
//                            showDialog(response.body().getMessage() , getContext());
//                        }else {
//                            binding.loader.setVisibility(View.GONE);
//                            showDialog(response.body().getMessage() , getContext());
//                        }
                    }else {
                        binding.loader.setVisibility(View.GONE);
                        Toast.makeText(getContext() , response.body().getMessage() , Toast.LENGTH_SHORT).show();
                        showDialog(response.message() , getContext());
                        dialog.dismiss();
                    }
                }else {
                    binding.loader.setVisibility(View.GONE);
                    Toast.makeText(getContext() , response.message() , Toast.LENGTH_SHORT).show();
                    showDialog(response.message() , getContext());
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
                showDialog(t.getMessage() , getContext());
            }
        });
    }

    public void showDialog(String message, Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView error = dialog.findViewById(R.id.message);
        error.setText(message);
        Button ok = dialog.findViewById(R.id.ok_bt);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}