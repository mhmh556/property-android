package com.moataz.salah.propertymanagement.ui.addCustomer;

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
import com.moataz.salah.propertymanagement.databinding.AddCustomerFragmentBinding;
import com.moataz.salah.propertymanagement.model.addFlat.AddNewFlatResponse;
import com.moataz.salah.propertymanagement.model.property.PropertyModel;
import com.moataz.salah.propertymanagement.model.task.TaskModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCustomerViewModel extends ViewModel {

    AddCustomerFragmentBinding binding;
    Context context;
    NavController navController;
    List<String> newsDataModelList;
    String currentLang ;
    UserPreference userPreference;
    ApiInterface apiInterface ;
    String api_key ;
    String from ;
    PropertyModel propertyModel ;

    public void intUi(AddCustomerFragmentBinding binding , Context context , NavController navController , List<String> newsDataModelList , String currentLang ,
                      UserPreference userPreference , ApiInterface apiInterface , String api_key , String from , PropertyModel propertyModel){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.newsDataModelList = newsDataModelList;
        this.currentLang = currentLang ;
        this.userPreference = userPreference ;
        this.apiInterface = apiInterface ;
        this.api_key = api_key ;
        this.from = from ;
        this.propertyModel = propertyModel ;
    }

    public void addCustomer(String type , TaskModel taskModel){
        binding.loader.setVisibility(View.VISIBLE);
        if (!validationInput(binding.nameInput) || !validationInput(binding.addressInput) || !validationInput(binding.idNumberInput)
            || !validationInput(binding.phoneInput)){
            binding.loader.setVisibility(View.GONE);
            return;
        }else {
            String name = binding.nameInput.getText().toString();
            String address = binding.addressInput.getText().toString();
            int phone = Integer.parseInt(binding.phoneInput.getText().toString());
            int national_id = Integer.parseInt(binding.idNumberInput.getText().toString());
            String token = userPreference.getToken();
            String mod = "add-customer";

            JsonObject gsonObject = new JsonObject();
            try {
                JSONObject jsonObj_ = new JSONObject();
                jsonObj_.put("app_api_key", api_key);
                jsonObj_.put("name", name);
                jsonObj_.put("address", address);
                jsonObj_.put("phone", phone);
                jsonObj_.put("nationally_id", national_id);
                jsonObj_.put("access_token", token);
                jsonObj_.put("mod", mod);

                JsonParser jsonParser = new JsonParser();
                gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
                Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            apiInterface = RetrofitClient2.getInstance().getApi();
            Call<AddNewFlatResponse> call = apiInterface.addCustomer("application/json", gsonObject);
            call.enqueue(new Callback<AddNewFlatResponse>() {
                @Override
                public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                    if (response.isSuccessful()){
                        if (response.code() == 200){
                            binding.loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            if (from.equals("dialog")) {
                                Bundle b = new Bundle();
                                b.putSerializable("data" , propertyModel);
                                navController.navigate(R.id.nav_reservation_fragment , b);
                            } else if (from.equals("fragment")){
                                navController.navigate(R.id.nav_customer_fragment);
                            }else if (from.equals("task")){
                                Bundle b = new Bundle();
                                b.putString("action" , type);
                                b.putSerializable("data" , taskModel);
                                navController.navigate(R.id.nav_add_task_fragment , b);
                            }
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

    public void editCustomer(int customer_id){
        binding.loader.setVisibility(View.VISIBLE);
        if (!validationInput(binding.nameInput) || !validationInput(binding.addressInput) || !validationInput(binding.phoneInput)
                || !validationInput(binding.idNumberInput)){
            binding.loader.setVisibility(View.GONE);
            return;
        }else {
            String api_key = userPreference.getApiKey();
            String name = binding.nameInput.getText().toString();
            String address = binding.addressInput.getText().toString();
            int phone = Integer.parseInt(binding.phoneInput.getText().toString());
            int national_id = Integer.parseInt(binding.idNumberInput.getText().toString());
            String token = userPreference.getToken();
            String mod = "update-customer";

            JsonObject gsonObject = new JsonObject();
            try {
                JSONObject jsonObj_ = new JSONObject();
                jsonObj_.put("name", name);
                jsonObj_.put("address", address);
                jsonObj_.put("phone", phone);
                jsonObj_.put("nationally_id", national_id);
                jsonObj_.put("customer_id", customer_id);
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
            Call<AddNewFlatResponse> call = apiInterface.updateProduct("application/json", gsonObject);
            call.enqueue(new Callback<AddNewFlatResponse>() {
                @Override
                public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                    if (response.isSuccessful()){
                        if (response.code() == 200){
                            binding.loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            navController.navigate(R.id.nav_customer_fragment);
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
