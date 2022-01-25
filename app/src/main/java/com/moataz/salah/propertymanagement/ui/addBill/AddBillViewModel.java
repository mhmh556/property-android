package com.moataz.salah.propertymanagement.ui.addBill;

import android.content.Context;
import android.os.Bundle;
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
import com.moataz.salah.propertymanagement.databinding.AddBillFragmentBinding;
import com.moataz.salah.propertymanagement.model.addFlat.AddNewFlatResponse;
import com.moataz.salah.propertymanagement.model.electrical.ElectricalModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBillViewModel extends ViewModel {

    AddBillFragmentBinding binding;
    Context context;
    NavController navController;
    String currentLang ;
    ApiInterface apiInterface ;
    UserPreference userPreference ;

    public void intUi(AddBillFragmentBinding binding , Context context , NavController navController , String currentLang  , ApiInterface apiInterface ,
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

    }

    public void addElectricityBill(ElectricalModel electricalModel){
        binding.loader.setVisibility(View.VISIBLE);
        if (!validationInput(binding.thePreviousReadingInput) || !validationInput(binding.currentReadingInput) || !validationInput(binding.coastInput)){
            binding.loader.setVisibility(View.GONE);
            return;
        }else {
            String api_key = userPreference.getApiKey();
            String day = String.valueOf(binding.datePicker1.getDayOfMonth());
            String month_text = String.valueOf(binding.datePicker1.getMonth()+1);
            String year_text = String.valueOf(binding.datePicker1.getYear());
            String date = year_text +"-"+ month_text +"-"+ day ;
            Log.e("date" , date) ;
            int last_reading = Integer.parseInt(binding.thePreviousReadingInput.getText().toString());
            int current_reading = Integer.parseInt(binding.currentReadingInput.getText().toString());
            int total_price = Integer.parseInt(binding.coastInput.getText().toString());
            String token = userPreference.getToken();
            String mod = "add-electical-bill";

            JsonObject gsonObject = new JsonObject();
            try {
                JSONObject jsonObj_ = new JSONObject();
                jsonObj_.put("electrical_account_id", electricalModel.getElectricalAccountId());
                jsonObj_.put("app_api_key", api_key);
                jsonObj_.put("date_bill", date);
                jsonObj_.put("last_reading", last_reading);
                jsonObj_.put("current_reading", current_reading);
                jsonObj_.put("total_price", total_price);
                jsonObj_.put("access_token", token);
                jsonObj_.put("mod", mod);

                JsonParser jsonParser = new JsonParser();
                gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
                Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            apiInterface = RetrofitClient2.getInstance().getApi();
            Call<AddNewFlatResponse> call = apiInterface.addElectricityBill("application/json", gsonObject);
            call.enqueue(new Callback<AddNewFlatResponse>() {
                @Override
                public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                    if (response.isSuccessful()){
                        if (response.code() == 200){
                            binding.loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Bundle b = new Bundle();
                            b.putSerializable("data" , electricalModel);
                            b.putString("type" , "electric");
                            navController.navigate(R.id.nav_counter_details_fragment , b);
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

    public void updateElectricityBill(ElectricalModel electricalModel , int bill_id){
        binding.loader.setVisibility(View.VISIBLE);
        if (!validationInput(binding.thePreviousReadingInput) || !validationInput(binding.currentReadingInput) || !validationInput(binding.coastInput)){
            binding.loader.setVisibility(View.GONE);
            return;
        }else {
            String api_key = userPreference.getApiKey();
            String day = String.valueOf(binding.datePicker1.getDayOfMonth());
            String month_text = String.valueOf(binding.datePicker1.getMonth()+1);
            String year_text = String.valueOf(binding.datePicker1.getYear());
            String date = year_text +"-"+ month_text +"-"+ day ;
            Log.e("date" , date) ;
            int last_reading = Integer.parseInt(binding.thePreviousReadingInput.getText().toString());
            int current_reading = Integer.parseInt(binding.currentReadingInput.getText().toString());
            int total_price = Integer.parseInt(binding.coastInput.getText().toString());
            String token = userPreference.getToken();
            String mod = "update-electical-bill";

            JsonObject gsonObject = new JsonObject();
            try {
                JSONObject jsonObj_ = new JSONObject();
                jsonObj_.put("electrical_account_id", electricalModel.getElectricalAccountId());
                jsonObj_.put("app_api_key", api_key);
                jsonObj_.put("date_bill", date);
                jsonObj_.put("last_reading", last_reading);
                jsonObj_.put("current_reading", current_reading);
                jsonObj_.put("total_price", total_price);
                jsonObj_.put("electical_bill_id", bill_id);
                jsonObj_.put("access_token", token);
                jsonObj_.put("mod", mod);

                JsonParser jsonParser = new JsonParser();
                gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
                Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            apiInterface = RetrofitClient2.getInstance().getApi();
            Call<AddNewFlatResponse> call = apiInterface.addElectricityBill("application/json", gsonObject);
            call.enqueue(new Callback<AddNewFlatResponse>() {
                @Override
                public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                    if (response.isSuccessful()){
                        if (response.code() == 200){
                            binding.loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Bundle b = new Bundle();
                            b.putSerializable("data" , electricalModel);
                            b.putString("type" , "electric");
                            navController.navigate(R.id.nav_counter_details_fragment , b);
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
