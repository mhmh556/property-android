package com.moataz.salah.propertymanagement.ui.products;

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
import com.moataz.salah.propertymanagement.adapter.ProductsAdapter;
import com.moataz.salah.propertymanagement.databinding.ProductsFragmentBinding;
import com.moataz.salah.propertymanagement.model.addFlat.AddNewFlatResponse;
import com.moataz.salah.propertymanagement.model.productsResponse.ProductsModel;
import com.moataz.salah.propertymanagement.model.productsResponse.ProductsResponse;
import com.moataz.salah.propertymanagement.model.property.PropertyPriceModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsViewModel extends ViewModel implements ProductsAdapter.onDeleteClick , ProductsAdapter.onEditClick{

    ProductsFragmentBinding binding;
    Context context;
    NavController navController;
    List<PropertyPriceModel> price_list;
    String currentLang ;
    ApiInterface apiInterface ;
    UserPreference preference;
    List<ProductsModel> list ;

    public void intUi(ProductsFragmentBinding binding , Context context , NavController navController , List<PropertyPriceModel> price_list , String currentLang ,
                      List<ProductsModel> list , UserPreference preference){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.price_list = price_list;
        this.currentLang = currentLang ;
        this.preference = preference;
        this.list = list ;
        getData();
    }

    public void getData(){
        binding.loader.setVisibility(View.VISIBLE);
        String api_key = preference.getApiKey();
        String token = preference.getToken();
        String mod = "get-products" ;
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
        Call<ProductsResponse> call = apiInterface.getProducts("application/json" , gsonObject);
        call.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        binding.loader.setVisibility(View.GONE);
                        list = response.body().getData();
                        binding.inventoryRecycler.setLayoutManager(new GridLayoutManager(context , 1));
                        ProductsAdapter adapter = new ProductsAdapter(context , list);
                        binding.inventoryRecycler.setAdapter(adapter);
                        adapter.setOnDeleteClick(ProductsViewModel.this);
                        adapter.setOnEditClick(ProductsViewModel.this);
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
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void delete_product(int product_id){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.confirm_dialog);
        dialog.setCancelable(true);
        Button ok = dialog.findViewById(R.id.delete_bt);
        Button cancel = dialog.findViewById(R.id.cancel_bt);
        TextView title = dialog.findViewById(R.id.title_text);
        TextView message = dialog.findViewById(R.id.message_text);

        title.setText(R.string.delete);
        message.setText(R.string.are_you_want_delete_product);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String api_key = preference.getApiKey();
                String token = preference.getToken();
                String mod = "delete-product" ;
                JsonObject gsonObject = new JsonObject();
                try {
                    JSONObject jsonObj_ = new JSONObject();
                    jsonObj_.put("app_api_key", api_key);
                    jsonObj_.put("product_id", product_id);
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
    public void onDeleteClickListener(ProductsModel productsModel) {
        if (preference.getApp().getProductDelete() == 0){
            showDialog("no permission to delete" , context);
        }else if (preference.getApp().getProductDelete() == 1) {
            delete_product(productsModel.getProductId());
        }
    }

    @Override
    public void onEditClickListener(ProductsModel productsModel) {
        if (preference.getApp().getProductEdit() == 0){
            showDialog("no permission to edit" , context);
        }else if (preference.getApp().getProductEdit() == 1) {
            Bundle b = new Bundle();
            b.putString("type", "edit");
            b.putSerializable("data", productsModel);
            navController.navigate(R.id.nav_add_product_fragment, b);
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
