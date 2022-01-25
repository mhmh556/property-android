package com.moataz.salah.propertymanagement.ui.reservedApartments;

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
import com.moataz.salah.propertymanagement.adapter.ReservedApartmentsAdapter;
import com.moataz.salah.propertymanagement.databinding.VacantFragemntBinding;
import com.moataz.salah.propertymanagement.model.reserve.ReservationListResponse;
import com.moataz.salah.propertymanagement.model.reserve.ReservationModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.BufferUnderflowException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservedApartmentsViewModel extends ViewModel implements ReservedApartmentsAdapter.onClick{

    VacantFragemntBinding binding;
    Context context;
    NavController navController;
    List<ReservationModel> list;
    String currentLang ;
    String token ;
    ApiInterface apiInterface ;
    String api_key ;

    public void intUi(VacantFragemntBinding binding , Context context , NavController navController , List<ReservationModel> list , String currentLang
                        , String token , String api_key){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.list = list;
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
                        if (list == null){
                            showDialog(response.body().getMessage() , context);
                        }else {
                            ReservedApartmentsAdapter apartmentAdapter = new ReservedApartmentsAdapter(context, list);
                            apartmentAdapter.setOnClick(ReservedApartmentsViewModel.this);
                            binding.recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                            binding.recyclerView.setAdapter(apartmentAdapter);
                            binding.searchInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    apartmentAdapter.getFilter().filter(query);
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    apartmentAdapter.getFilter().filter(newText);
                                    return false;
                                }
                            });
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

    }

    @Override
    public void onClickListener(ReservationModel reservationModel) {
        Bundle b = new Bundle();
        b.putSerializable("data" , reservationModel);
        b.putString("key" , "add");
        navController.navigate(R.id.nav_reserved_apartments_details_fragment , b);
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
