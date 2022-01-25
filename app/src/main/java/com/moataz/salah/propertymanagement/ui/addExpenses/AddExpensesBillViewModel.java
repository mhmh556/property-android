package com.moataz.salah.propertymanagement.ui.addExpenses;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.AddExpensesBillFragmentBinding;
import com.moataz.salah.propertymanagement.model.addFlat.AddNewFlatResponse;
import com.moataz.salah.propertymanagement.model.apt.AptTypeModel;
import com.moataz.salah.propertymanagement.model.expenses.ExpensesModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddExpensesBillViewModel extends ViewModel{

    AddExpensesBillFragmentBinding binding;
    Context context;
    NavController navController;
    List<AptTypeModel> list;
    ApiInterface apiInterface;
    String currentLang ;
    String token ;
    Dialog dialog2 , dialog;
    String api_key ;

    public void intUi(AddExpensesBillFragmentBinding binding , Context context , NavController navController , List<AptTypeModel> list , String currentLang
                        , String token , String api_key) {
        this.binding = binding;
        this.context = context;
        this.navController = navController;
        this.list = list;
        this.currentLang = currentLang;
        this.token = token;
        this.api_key = api_key;
    }

    public void addNewbill(){
        binding.loader.setVisibility(View.VISIBLE);
        if (!validationName() || !validationDetails()){
            binding.loader.setVisibility(View.GONE);
            return;
        }else {
            String name = binding.nameInput.getText().toString();
            String description = binding.accountInput.getText().toString();
            int price = 135;
            String mod = "add-expense";
            JsonObject gsonObject = new JsonObject();
            try {
                JSONObject jsonObj_ = new JSONObject();
                jsonObj_.put("app_api_key", api_key);
                jsonObj_.put("title", name);
                jsonObj_.put("description", description);
                jsonObj_.put("price", price);
                jsonObj_.put("access_token", token);
                jsonObj_.put("mod", mod);

                JsonParser jsonParser = new JsonParser();
                gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
                Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            apiInterface = RetrofitClient2.getInstance().getApi();
            Call<AddNewFlatResponse> call = apiInterface.addExpense("application/json", gsonObject);
            call.enqueue(new Callback<AddNewFlatResponse>() {
                @Override
                public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                    if (response.isSuccessful()) {
                        Log.e("response_message" , response.message());
                        if (response.body() != null) {
                                binding.loader.setVisibility(View.GONE);
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("message", response.body().getMessage());
                                navController.navigate(R.id.nav_general_expenses_fragment);
                        } else {
                            binding.loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
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

    public void updatebill(int expense_id , ExpensesModel model){
        binding.loader.setVisibility(View.VISIBLE);
        if (!validationName() || !validationDetails()){
            binding.loader.setVisibility(View.GONE);
            return;
        }else {
            String title = binding.nameInput.getText().toString();
            String dec = binding.accountInput.getText().toString();
            String mod = "update-expense";
            JsonObject gsonObject = new JsonObject();
            try {
                JSONObject jsonObj_ = new JSONObject();
                jsonObj_.put("title", title);
                jsonObj_.put("description", dec);
                jsonObj_.put("price", 180);
                jsonObj_.put("expense_id" , expense_id);
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
            Call<AddNewFlatResponse> call = apiInterface.updateProperty("application/json", gsonObject);
            call.enqueue(new Callback<AddNewFlatResponse>() {
                @Override
                public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                    if (response.isSuccessful()) {
                        Log.e("response_message" , response.message());
                        if (response.body() != null) {
                            binding.loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("message", response.body().getMessage());
//                            Bundle b = new Bundle();
//                            b.putSerializable("item" , model);
                            navController.navigate(R.id.nav_general_expenses_fragment);
                        } else {
                            binding.loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
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

    public void deletebill(int expense_id){
        binding.loader.setVisibility(View.VISIBLE);
        if (!validationName() || !validationDetails()){
            binding.loader.setVisibility(View.GONE);
            return;
        }else {
            String mod = "update-expense";
            JsonObject gsonObject = new JsonObject();
            try {
                JSONObject jsonObj_ = new JSONObject();
                jsonObj_.put("expense_id" , expense_id);
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
            Call<AddNewFlatResponse> call = apiInterface.updateProperty("application/json", gsonObject);
            call.enqueue(new Callback<AddNewFlatResponse>() {
                @Override
                public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                    if (response.isSuccessful()) {
                        Log.e("response_message" , response.message());
                        if (response.body() != null) {
                            binding.loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("message", response.body().getMessage());
                            navController.navigate(R.id.nav_general_expenses_fragment);
                        } else {
                            binding.loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
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

    public void showAddDialog(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView close = dialog.findViewById(R.id.close_bt);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        EditText name_input = dialog.findViewById(R.id.name_text);
        Button add = dialog.findViewById(R.id.add_bt);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        dialog.show();
    }

    private Boolean validationName(){
        String nameInput = binding.nameInput.getText().toString().trim();
        if (nameInput.isEmpty()){
            binding.nameInput.setError("Field can't be empty");
            return false;
        }else {
            binding.nameInput.setError(null);
            return true;
        }
    }
    private Boolean validationDetails(){
        String detailsInput = binding.accountInput.getText().toString().trim();
        if (detailsInput.isEmpty()){
            binding.accountInput.setError("Field can't be empty");
            return false;
        }else {
            binding.accountInput.setError(null);
            return true;
        }
    }
}
