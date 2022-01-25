package com.moataz.salah.propertymanagement.ui.yearCounter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.adapter.CounterAdapter;
import com.moataz.salah.propertymanagement.databinding.YearCounterDetailsFragmentBinding;
import com.moataz.salah.propertymanagement.model.bill.BillModel;
import com.moataz.salah.propertymanagement.model.bill.BillResponse;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YearCounterViewModel extends ViewModel implements CounterAdapter.onClick{

    YearCounterDetailsFragmentBinding binding;
    Context context;
    NavController navController;
    List<BillModel> list ;
    UserPreference userPreference ;

    public void intUi(YearCounterDetailsFragmentBinding binding , Context context , NavController navController , List<BillModel> list , UserPreference userPreference){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.list = list ;
        this.userPreference = userPreference ;
        getData();
    }

    public void getData(){
        binding.loader.setVisibility(View.VISIBLE);
        String api_key = userPreference.getApiKey();
        String token = userPreference.getToken();
        String mod = "get-electicals-bills" ;
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

        ApiInterface apiInterface = RetrofitClient2.getInstance().getApi();
        Call<BillResponse> call = apiInterface.getBill("application/json" , gsonObject);
        call.enqueue(new Callback<BillResponse>() {
            @Override
            public void onResponse(Call<BillResponse> call, Response<BillResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        binding.loader.setVisibility(View.GONE);
                        list = response.body().getData();
                        binding.yearRecycler.setLayoutManager(new GridLayoutManager(context , 1));
                        CounterAdapter adapter = new CounterAdapter(context , list);
                        binding.yearRecycler.setAdapter(adapter);
                        adapter.setOnClick(YearCounterViewModel.this);
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
            public void onFailure(Call<BillResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.yearRecycler.setLayoutManager(new GridLayoutManager(context , 1));
        CounterAdapter adapter = new CounterAdapter(context , list);
        binding.yearRecycler.setAdapter(adapter);

    }

    @Override
    public void onClickListener(BillModel item) {

    }
}
