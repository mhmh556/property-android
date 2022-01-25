package com.moataz.salah.propertymanagement.ui.customer;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.adapter.CustomerParentAdapter;
import com.moataz.salah.propertymanagement.databinding.CustomerFragmentBinding;
import com.moataz.salah.propertymanagement.model.addFlat.AddNewFlatResponse;
import com.moataz.salah.propertymanagement.model.customer.CustomerModel;
import com.moataz.salah.propertymanagement.model.customer.ResponseCustomer;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerViewModel extends ViewModel implements CustomerParentAdapter.onDeleteClick , CustomerParentAdapter.onEditClick , CustomerParentAdapter.onItemClick{

    CustomerFragmentBinding binding;
    Context context;
    NavController navController;
    String currentLang ;
    ApiInterface apiInterface ;
    UserPreference preference;
    List<CustomerModel> list ;

    public void intUi(CustomerFragmentBinding binding , Context context , NavController navController , String currentLang ,
                      List<CustomerModel> list , UserPreference preference){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.currentLang = currentLang ;
        this.preference = preference;
        this.list = list ;
        getData();
    }

    public void getData(){
        binding.loader.setVisibility(View.VISIBLE);
        String api_key = preference.getApiKey();
        String token = preference.getToken();
        String mod = "get-customers" ;
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
        Call<ResponseCustomer> call = apiInterface.getCustomer("application/json" , gsonObject);
        call.enqueue(new Callback<ResponseCustomer>() {
            @Override
            public void onResponse(Call<ResponseCustomer> call, Response<ResponseCustomer> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        binding.loader.setVisibility(View.GONE);
                        list = response.body().getData();
                        if (list == null){
                            showDialog(response.body().getMessage() , context);
                        }else {
                            binding.customerRecycler.setLayoutManager(new GridLayoutManager(context, 1));
                            CustomerParentAdapter adapter = new CustomerParentAdapter(context, list);
                            binding.customerRecycler.setAdapter(adapter);
                            adapter.setOnItemClick(CustomerViewModel.this);
                            adapter.setOnDeleteClick(CustomerViewModel.this);
                            adapter.setOnEditClick(CustomerViewModel.this);
                            adapter.notifyDataSetChanged();
                        }
                    }else {
                        binding.loader.setVisibility(View.GONE);
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        showDialog(response.message() , context);
                    }
                }else {
                    binding.loader.setVisibility(View.GONE);
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                    showDialog(response.message() , context);
                }
            }

            @Override
            public void onFailure(Call<ResponseCustomer> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                showDialog(t.getMessage() , context);
            }
        });
    }

    public void delete_customer(int customer_id){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.confirm_dialog);
        dialog.setCancelable(true);
        Button ok = dialog.findViewById(R.id.delete_bt);
        Button cancel = dialog.findViewById(R.id.cancel_bt);
        TextView title = dialog.findViewById(R.id.title_text);
        TextView message = dialog.findViewById(R.id.message_text);

        title.setText(R.string.delete);
        message.setText(R.string.are_you_want_delete_customer);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String api_key = preference.getApiKey();
                String token = preference.getToken();
                String mod = "delete-customer" ;
                JsonObject gsonObject = new JsonObject();
                try {
                    JSONObject jsonObj_ = new JSONObject();
                    jsonObj_.put("app_api_key", api_key);
                    jsonObj_.put("customer_id", customer_id);
                    jsonObj_.put("access_token", token);
                    jsonObj_.put("mod", mod);

                    JsonParser jsonParser = new JsonParser();
                    gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
                    Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                apiInterface = RetrofitClient2.getInstance().getApi();
                Call<AddNewFlatResponse> call = apiInterface.deleteProduct("application/json" , gsonObject);
                call.enqueue(new Callback<AddNewFlatResponse>() {
                    @Override
                    public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null){
                                dialog.dismiss();
                                getData();
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
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onClickListener(CustomerModel item) {

    }

    @Override
    public void onDeleteClickListener(CustomerModel item) {
        if (preference.getApp().getCustmerDelete() == 0){
            showDialog("you dont have permission to delete" , context);
        }else if (preference.getApp().getCustmerDelete() == 1) {
            delete_customer(item.getCustomerId());
        }
    }

    @Override
    public void onEditClickListener(CustomerModel item) {
        if (preference.getApp().getCustmerEdit() == 0){
            showDialog("you dont have permission to update" , context);
        }else if (preference.getApp().getCustmerEdit() == 1) {
            Bundle b = new Bundle();
            b.putString("from", "fragment");
            b.putString("type", "edit");
            b.putSerializable("data", item);
            navController.navigate(R.id.nav_add_customer_fragment, b);
        }
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
