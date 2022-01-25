package com.moataz.salah.propertymanagement.ui.generalExpenses;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.adapter.ExpensesAdapter;
import com.moataz.salah.propertymanagement.databinding.ExpensesFragmentBinding;
import com.moataz.salah.propertymanagement.model.expenses.ExpensesModel;
import com.moataz.salah.propertymanagement.model.expenses.GetExpensesResponse;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneralExpensesViewModel extends ViewModel implements ExpensesAdapter.onItemClick{

    ExpensesFragmentBinding binding;
    Context context;
    NavController navController;
    String currentLang ;
    ApiInterface apiInterface ;
    UserPreference preference ;
    List<ExpensesModel> list ;
    ExpensesAdapter adapter ;

    public void intUi(ExpensesFragmentBinding binding , Context context , NavController navController , String currentLang , ApiInterface apiInterface ,
                      UserPreference preference , List<ExpensesModel> list){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.currentLang = currentLang ;
        this.apiInterface = apiInterface ;
        this.preference = preference ;
        this.list = list ;
        getData();
    }

    public void getData(){
        binding.loader.setVisibility(View.VISIBLE);
        String api_key = preference.getApiKey();
        String token = preference.getToken();
        Log.e("api_key" , api_key);
        Log.e("token" , token);
        String mod = "get-expenses" ;
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
        Call<GetExpensesResponse> call = apiInterface.getExpenses("application/json" , gsonObject);
        call.enqueue(new Callback<GetExpensesResponse>() {
            @Override
            public void onResponse(Call<GetExpensesResponse> call, Response<GetExpensesResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        binding.loader.setVisibility(View.GONE);
                        list = response.body().getData();
                        Log.e("response" , response.body().getMessage());
                        binding.viewPager.setLayoutManager(new GridLayoutManager(context , 1));
                        adapter = new ExpensesAdapter(context , list);
                        adapter.setOnItemClick(GeneralExpensesViewModel.this);
                        binding.viewPager.setAdapter(adapter);
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
            public void onFailure(Call<GetExpensesResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.searchInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void filter(String type){
        adapter.getFilter().filter(type);
    }

    @Override
    public void onClickListener(ExpensesModel item) {
        Bundle b = new Bundle();
        b.putSerializable("item" , item);
        navController.navigate(R.id.nav_expense_details_fragment , b);
    }

//    @Override
//    public void onClickListener(ElectricalModel item) {
//        preference.saveItem(item);
//        Bundle b = new Bundle();
//        b.putSerializable("data" , item);
//        b.putString("type" , "electric");
//        navController.navigate(R.id.nav_counter_details_fragment , b);
//
//    }
}
