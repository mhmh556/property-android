package com.moataz.salah.propertymanagement.ui.print;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.adapter.ExpensesAdapter;
import com.moataz.salah.propertymanagement.adapter.ReservedApartmentsAdapter;
import com.moataz.salah.propertymanagement.databinding.OthersFragmentBinding;
import com.moataz.salah.propertymanagement.databinding.PrintFragmentBinding;
import com.moataz.salah.propertymanagement.model.expenses.ExpensesModel;
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

public class PrintViewModel extends ViewModel {

    PrintFragmentBinding binding;
    Context context;
    NavController navController;
    List<ReservationModel> list;
    String currentLang ;
    String token ;
    ApiInterface apiInterface ;
    String api_key ;

    public void intUi(PrintFragmentBinding binding , Context context , NavController navController , List<ReservationModel> list , String currentLang
            , String token , String api_key){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.list = list ;
        this.currentLang = currentLang ;
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
                        ReservedApartmentsAdapter apartmentAdapter = new ReservedApartmentsAdapter(context, list);
                        binding.itemList.setLayoutManager(new GridLayoutManager(context, 2));
                        binding.itemList.setAdapter(apartmentAdapter);
                    }else {
                        binding.loader.setVisibility(View.GONE);
                        Toast.makeText(context , response.body().getMessage() , Toast.LENGTH_SHORT).show();
                    }
                }else {
                    binding.loader.setVisibility(View.GONE);
                    Toast.makeText(context , response.message() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReservationListResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toast.makeText(context , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

    }
}