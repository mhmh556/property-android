package com.moataz.salah.propertymanagement.ui.addCounter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.databinding.AddCounterFragmentBinding;
import com.moataz.salah.propertymanagement.model.addFlat.AddNewFlatResponse;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCounterViewModel extends ViewModel {

    AddCounterFragmentBinding binding;
    Context context;
    NavController navController;
    String currentLang ;
    ApiInterface apiInterface ;
    UserPreference userPreference ;

    public void intUi(AddCounterFragmentBinding binding , Context context , NavController navController , String currentLang , ApiInterface apiInterface ,
                      UserPreference userPreference){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.currentLang = currentLang ;
        this.apiInterface = apiInterface ;
        this.userPreference = userPreference ;
        getData();
    }

    public void getData(){
        binding.setNumber("26");
    }

    public void addCounter(String type){
        binding.loader.setVisibility(View.VISIBLE);
        if (!validationInput(binding.accountInput) || !validationInput(binding.addressInput)){
            binding.loader.setVisibility(View.GONE);
            return;
        }else {
            String api_key = userPreference.getApiKey();
            String name = binding.nameInput.getText().toString();
            int device_num = Integer.parseInt(binding.deviceInput.getText().toString());
            int account_num = Integer.parseInt(binding.accountInput.getText().toString());
            String address = binding.addressInput.getText().toString();
            String token = userPreference.getToken();
            String mod = "add-electrical-account";

            JsonObject gsonObject = new JsonObject();
            try {
                JSONObject jsonObj_ = new JSONObject();
                jsonObj_.put("app_api_key", api_key);
                jsonObj_.put("device_num", device_num);
                jsonObj_.put("account_num", account_num);
                jsonObj_.put("name", name);
                jsonObj_.put("multiplication_factor", 1);
                jsonObj_.put("type", type);
                jsonObj_.put("address", address);
                jsonObj_.put("access_token", token);
                jsonObj_.put("mod", mod);

                JsonParser jsonParser = new JsonParser();
                gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
                Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            apiInterface = RetrofitClient2.getInstance().getApi();
            Call<AddNewFlatResponse> call = apiInterface.addElectricCounter("application/json", gsonObject);
            call.enqueue(new Callback<AddNewFlatResponse>() {
                @Override
                public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                    if (response.isSuccessful()){
                        if (response.code() == 200){
                            binding.loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            navController.navigate(R.id.nav_general_expenses_fragment);
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
                public void onFailure(Call<AddNewFlatResponse> call, Throwable t) {
                    binding.loader.setVisibility(View.GONE);
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void editElectricCounter(int id , String type){
        binding.loader.setVisibility(View.VISIBLE);
        if (!validationInput(binding.accountInput) || !validationInput(binding.addressInput)){
            binding.loader.setVisibility(View.GONE);
            return;
        }else {
            String api_key = userPreference.getApiKey();
            String name = binding.nameInput.getText().toString();
            int device_num = Integer.parseInt(binding.deviceInput.getText().toString());
            int account_num = Integer.parseInt(binding.accountInput.getText().toString());
            String address = binding.addressInput.getText().toString();
            String token = userPreference.getToken();
            String mod = "update-electrical-account";

            JsonObject gsonObject = new JsonObject();
            try {
                JSONObject jsonObj_ = new JSONObject();
                jsonObj_.put("app_api_key", api_key);
                jsonObj_.put("device_num", device_num);
                jsonObj_.put("account_num", account_num);
                jsonObj_.put("name", name);
                jsonObj_.put("electrical_account_id" , id);
                jsonObj_.put("address", address);
                jsonObj_.put("multiplication_factor", 1);
                jsonObj_.put("type", type);
                jsonObj_.put("access_token", token);
                jsonObj_.put("mod", mod);

                JsonParser jsonParser = new JsonParser();
                gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
                Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            apiInterface = RetrofitClient2.getInstance().getApi();
            Call<AddNewFlatResponse> call = apiInterface.editElectricCounter("application/json", gsonObject);
            call.enqueue(new Callback<AddNewFlatResponse>() {
                @Override
                public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                    if (response.isSuccessful()){
                        if (response.code() == 200){
                            binding.loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            navController.navigate(R.id.nav_general_expenses_fragment);
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
                public void onFailure(Call<AddNewFlatResponse> call, Throwable t) {
                    binding.loader.setVisibility(View.GONE);
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
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
