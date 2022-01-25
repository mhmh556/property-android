package com.moataz.salah.propertymanagement.ui.reservedFlat;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.adapter.ExpensesAdapter;
import com.moataz.salah.propertymanagement.adapter.ReservedApartmentsAdapter;
import com.moataz.salah.propertymanagement.adapter.ReservedFlatAdapter;
import com.moataz.salah.propertymanagement.databinding.ExpensesFragmentBinding;
import com.moataz.salah.propertymanagement.databinding.ReservedFlatFragmentBinding;
import com.moataz.salah.propertymanagement.model.expenses.ExpensesModel;
import com.moataz.salah.propertymanagement.model.expenses.GetExpensesResponse;
import com.moataz.salah.propertymanagement.model.reserve.ReservationListResponse;
import com.moataz.salah.propertymanagement.model.reserve.ReservationModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import com.moataz.salah.propertymanagement.ui.reservedApartments.ReservedApartmentsViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservedFlatViewModel extends ViewModel implements ReservedFlatAdapter.onItemClick{

    ReservedFlatFragmentBinding binding;
    Context context;
    NavController navController;
    String currentLang ;
    ApiInterface apiInterface ;
    UserPreference preference ;
    List<ReservationModel> list;
    ExpensesAdapter adapter ;
    String token ;
    String api_key ;

    public void intUi(ReservedFlatFragmentBinding binding , Context context , NavController navController , String currentLang , ApiInterface apiInterface ,
                      UserPreference preference , List<ReservationModel> list ,  String token , String api_key){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.currentLang = currentLang ;
        this.apiInterface = apiInterface ;
        this.preference = preference ;
        this.list = list ;
        this.token = token ;
        this.api_key = api_key ;
        getData();
    }

    public void getData(){
        binding.loader.setVisibility(View.VISIBLE);
        String mod = "get-all-reservation";

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
        Call<ReservationListResponse> call = apiInterface.getAllReservation("application/json" , gsonObject);
        call.enqueue(new Callback<ReservationListResponse>() {
            @Override
            public void onResponse(Call<ReservationListResponse> call, Response<ReservationListResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        binding.loader.setVisibility(View.GONE);
                        list = response.body().getData();
                        if (list == null){
                            showDialog(response.body().getMessage() , context);
                        }else {
                            ReservedFlatAdapter adapter = new ReservedFlatAdapter(context , list);
                            adapter.setOnItemClick(ReservedFlatViewModel.this);
                            binding.viewPager.setLayoutManager(new GridLayoutManager(context, 1));
                            binding.viewPager.setAdapter(adapter);
                        }
                    }else {
                        binding.loader.setVisibility(View.GONE);
                        Toast.makeText(context , response.body().getMessage() , Toast.LENGTH_SHORT).show();
                        showDialog(response.message() , context);
                    }
                }else {
                    binding.loader.setVisibility(View.GONE);
                    Toast.makeText(context , response.message() , Toast.LENGTH_SHORT).show();
                    showDialog(response.message() , context);
                }
            }

            @Override
            public void onFailure(Call<ReservationListResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toast.makeText(context , t.getMessage() , Toast.LENGTH_SHORT).show();
                showDialog(t.getMessage() , context);
            }
        });

//        binding.searchInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                adapter.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });
    }

    public void filter(String type){
        adapter.getFilter().filter(type);
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

    @Override
    public void onClickListener(ReservationModel reservationModel) {
        Bundle b = new Bundle();
        b.putSerializable("data" , reservationModel);
        navController.navigate(R.id.nav_reserved_apartments_details_fragment , b);
    }
}
