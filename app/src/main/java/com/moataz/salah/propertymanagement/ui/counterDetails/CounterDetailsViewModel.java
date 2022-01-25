package com.moataz.salah.propertymanagement.ui.counterDetails;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.databinding.CounterDetailsFragmentBinding;
import com.moataz.salah.propertymanagement.model.addFlat.AddNewFlatResponse;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CounterDetailsViewModel extends ViewModel {

    CounterDetailsFragmentBinding binding;
    Context context;
    NavController navController;
    String currentLang ;
    ApiInterface apiInterface ;
    UserPreference preference ;

    public void intUi(CounterDetailsFragmentBinding binding , Context context , NavController navController , String currentLang  , ApiInterface apiInterface
            , UserPreference preference){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.currentLang = currentLang ;
        this.apiInterface = apiInterface ;
        this.preference = preference ;
        getData();
    }

    public void getData(){

    }

    public void deleteElectric(int id){
        String api_key = preference.getApiKey();
        String token = preference.getToken();
        String mod = "delete-electrical-account" ;
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("app_api_key", api_key);
            jsonObj_.put("electrical_account_id", id);
            jsonObj_.put("access_token", token);
            jsonObj_.put("mod", mod);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiInterface = RetrofitClient2.getInstance().getApi();
        Call<AddNewFlatResponse> call = apiInterface.deleteElectric("application/json" , gsonObject);
        call.enqueue(new Callback<AddNewFlatResponse>() {
            @Override
            public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.nav_general_expenses_fragment);
                    }else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddNewFlatResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
