package com.moataz.salah.propertymanagement.ui.permission;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.databinding.PermissionFragmentBinding;
import com.moataz.salah.propertymanagement.model.addFlat.AddNewFlatResponse;
import com.moataz.salah.propertymanagement.model.productsResponse.ProductsModel;
import com.moataz.salah.propertymanagement.model.property.PropertyPriceModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClientLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PermissionViewModel extends ViewModel{

    PermissionFragmentBinding binding;
    Context context;
    NavController navController;
    List<PropertyPriceModel> price_list;
    String currentLang ;
    ApiInterface apiInterface ;
    UserPreference preference;
    List<ProductsModel> list ;

    public void intUi(PermissionFragmentBinding binding , Context context , NavController navController , List<PropertyPriceModel> price_list , String currentLang ,
                      List<ProductsModel> list , UserPreference preference){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.price_list = price_list;
        this.currentLang = currentLang ;
        this.preference = preference;
        this.list = list ;
    }

    public void setPermission(String user_api_key , String app_api_key , int property_view , int property_delete , int property_edit , int property_add ,
            int cleaning_view , int cleaning_delete , int cleaning_edit , int cleaning_add , int product_view , int product_delete , int product_edit ,
            int product_add , int custmer_view , int custmer_delete , int custmer_edit , int custmer_add , int reservation_view , int reservation_delete ,
            int reservation_edit , int reservation_add , int purchase_view , int purchase_delete , int purchase_edit , int purchase_add , int device_meter_view ,
            int device_meter_delete , int device_meter_edit , int device_meter_add , int customer_view , int customer_delete , int customer_edit , int customer_add ,
            int report_view){

        String mod = "add-user-property-permission";
        String token = preference.getToken();
        String x_api = "0F3zf54Nut73EaXsfGvykEkn6CfKFVT57494kJ10";

        Log.e("token" , token);
        Log.e("user_api_key" , user_api_key);
        Log.e("app_api_key" , app_api_key);

        JsonObject gsonObject = new JsonObject();
        try {

            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("user_api_key", user_api_key);
            jsonObj_.put("app_api_key", app_api_key);
            jsonObj_.put("property_view", property_view);
            jsonObj_.put("property_add", property_add);
            jsonObj_.put("property_edit", property_edit);
            jsonObj_.put("property_delete", property_delete);
            jsonObj_.put("cleaning_view", cleaning_view);
            jsonObj_.put("cleaning_add", cleaning_add);
            jsonObj_.put("cleaning_edit", cleaning_edit);
            jsonObj_.put("cleaning_delete", cleaning_delete);
            jsonObj_.put("product_view", product_view);
            jsonObj_.put("product_add", product_add);
            jsonObj_.put("product_edit", product_edit);
            jsonObj_.put("product_delete", product_delete);
            jsonObj_.put("custmer_view", custmer_view);
            jsonObj_.put("custmer_add", custmer_add);
            jsonObj_.put("custmer_edit", custmer_edit);
            jsonObj_.put("custmer_delete", custmer_delete);
            jsonObj_.put("reservation_view", reservation_view);
            jsonObj_.put("reservation_add", reservation_add);
            jsonObj_.put("reservation_edit", reservation_edit);
            jsonObj_.put("reservation_delete", reservation_delete);
            jsonObj_.put("purchase_view", purchase_view);
            jsonObj_.put("purchase_add", purchase_add);
            jsonObj_.put("purchase_edit", purchase_edit);
            jsonObj_.put("purchase_delete", purchase_delete);
            jsonObj_.put("device_meter_view", device_meter_view);
            jsonObj_.put("device_meter_add", device_meter_add);
            jsonObj_.put("device_meter_edit", device_meter_edit);
            jsonObj_.put("device_meter_delete", device_meter_delete);
            jsonObj_.put("customer_view", customer_view);
            jsonObj_.put("customer_add", customer_add);
            jsonObj_.put("customer_edit", customer_edit);
            jsonObj_.put("customer_delete", customer_delete);
            jsonObj_.put("report_view", report_view);
            jsonObj_.put("access_token", token);
            jsonObj_.put("mod", mod);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiInterface = RetrofitClientLogin.getInstance().getApi();
        Call<AddNewFlatResponse> call = apiInterface.addPermission("application/json", x_api , gsonObject);
        call.enqueue(new Callback<AddNewFlatResponse>() {
            @Override
            public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                if (response.isSuccessful()){
                    if (response.code() == 200){
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.nav_employees_fragment);
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

    public void updatePermission(String user_api_key , String app_api_key , int property_view , int property_delete , int property_edit , int property_add ,
                              int cleaning_view , int cleaning_delete , int cleaning_edit , int cleaning_add , int product_view , int product_delete , int product_edit ,
                              int product_add , int custmer_view , int custmer_delete , int custmer_edit , int custmer_add , int reservation_view , int reservation_delete ,
                              int reservation_edit , int reservation_add , int purchase_view , int purchase_delete , int purchase_edit , int purchase_add , int device_meter_view ,
                              int device_meter_delete , int device_meter_edit , int device_meter_add , int customer_view , int customer_delete , int customer_edit , int customer_add ,
                              int report_view , int user_app_permission_id){

        String mod = "update-user-property-permission";
        String token = preference.getToken();
        String x_api = "0F3zf54Nut73EaXsfGvykEkn6CfKFVT57494kJ10";

        Log.e("user_api_key" , user_api_key);
        Log.e("app_api_key" , app_api_key);
        Log.e("user_app_permission_id" , String.valueOf(user_app_permission_id));


        JsonObject gsonObject = new JsonObject();
        try {

            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("user_api_key", user_api_key);
            jsonObj_.put("property_view", property_view);
            jsonObj_.put("property_add", property_add);
            jsonObj_.put("property_edit", property_edit);
            jsonObj_.put("property_delete", property_delete);
            jsonObj_.put("cleaning_view", cleaning_view);
            jsonObj_.put("cleaning_add", cleaning_add);
            jsonObj_.put("cleaning_edit", cleaning_edit);
            jsonObj_.put("cleaning_delete", cleaning_delete);
            jsonObj_.put("product_view", product_view);
            jsonObj_.put("product_add", product_add);
            jsonObj_.put("product_edit", product_edit);
            jsonObj_.put("product_delete", product_delete);
            jsonObj_.put("custmer_view", custmer_view);
            jsonObj_.put("custmer_add", custmer_add);
            jsonObj_.put("custmer_edit", custmer_edit);
            jsonObj_.put("custmer_delete", custmer_delete);
            jsonObj_.put("reservation_view", reservation_view);
            jsonObj_.put("reservation_add", reservation_add);
            jsonObj_.put("reservation_edit", reservation_edit);
            jsonObj_.put("reservation_delete", reservation_delete);
            jsonObj_.put("purchase_view", purchase_view);
            jsonObj_.put("purchase_add", purchase_add);
            jsonObj_.put("purchase_edit", purchase_edit);
            jsonObj_.put("purchase_delete", purchase_delete);
            jsonObj_.put("device_meter_view", device_meter_view);
            jsonObj_.put("device_meter_add", device_meter_add);
            jsonObj_.put("device_meter_edit", device_meter_edit);
            jsonObj_.put("device_meter_delete", device_meter_delete);
            jsonObj_.put("customer_view", customer_view);
            jsonObj_.put("customer_add", customer_add);
            jsonObj_.put("customer_edit", customer_edit);
            jsonObj_.put("customer_delete", customer_delete);
            jsonObj_.put("report_view", report_view);
            jsonObj_.put("user_app_permission_id" , user_app_permission_id);
            jsonObj_.put("app_api_key", app_api_key);
            jsonObj_.put("access_token", token);
            jsonObj_.put("mod", mod);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiInterface = RetrofitClientLogin.getInstance().getApi();
        Call<AddNewFlatResponse> call = apiInterface.addPermission("application/json", x_api , gsonObject);
        call.enqueue(new Callback<AddNewFlatResponse>() {
            @Override
            public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                if (response.isSuccessful()){
                    if (response.code() == 200){
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.nav_employees_fragment);
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
