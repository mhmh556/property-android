package com.moataz.salah.propertymanagement.ui.electricity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.adapter.ElectricAdapter;
import com.moataz.salah.propertymanagement.databinding.ElectricityFragmentBinding;
import com.moataz.salah.propertymanagement.model.electrical.ElectricalModel;
import com.moataz.salah.propertymanagement.model.electrical.ElectricalResponse;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElectricityViewModel extends ViewModel implements ElectricAdapter.onClick{

    ElectricityFragmentBinding binding;
    Context context;
    NavController navController;
    ApiInterface apiInterface ;
    UserPreference userPreference ;
    List<ElectricalModel> list ;
    ElectricAdapter adapter ;

    public void intUi(ElectricityFragmentBinding binding , Context context , NavController navController , ApiInterface apiInterface , UserPreference userPreference ,
                      List<ElectricalModel> list , ElectricAdapter adapter){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.apiInterface =apiInterface ;
        this.userPreference = userPreference ;
        this.list = list ;
        this.adapter = adapter;
        getData();
    }

    public void getData(){
        binding.loader.setVisibility(View.VISIBLE);
        String api_key = userPreference.getApiKey();
        String token = userPreference.getToken();
        String mod = "get-electricals-accounts" ;
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
        Call<ElectricalResponse> call = apiInterface.getElectricity("application/json" , gsonObject);
        call.enqueue(new Callback<ElectricalResponse>() {
            @Override
            public void onResponse(Call<ElectricalResponse> call, Response<ElectricalResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        binding.loader.setVisibility(View.GONE);
                        list = response.body().getData();
                        binding.electricityRecycler.setLayoutManager(new GridLayoutManager(context , 2));
                        adapter = new ElectricAdapter(context , list);
                        binding.electricityRecycler.setAdapter(adapter);
                        adapter.setOnClick(ElectricityViewModel.this);
                        adapter.notifyDataSetChanged();
                    }else {
                        binding.loader.setVisibility(View.GONE);
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    binding.loader.setVisibility(View.GONE);
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ElectricalResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickListener(ElectricalModel item) {
        userPreference.saveItem(item);
        Bundle b = new Bundle();
        b.putSerializable("data" , item);
        b.putString("type" , "electric");
        navController.navigate(R.id.nav_counter_details_fragment , b);
    }
}
