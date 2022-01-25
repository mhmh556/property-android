package com.moataz.salah.propertymanagement.ui.addPrice;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.databinding.AddPriceFragmentBinding;
import com.moataz.salah.propertymanagement.model.apt.AptTypeModel;
import com.moataz.salah.propertymanagement.model.price.AddPriceResponse;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPriceViewModel extends ViewModel{

    AddPriceFragmentBinding binding;
    Context context;
    NavController navController;
    List<AptTypeModel> list;
    ApiInterface apiInterface;
    String currentLang ;
    String token ;
    Dialog dialog2 , dialog;
    ProgressBar loader ;
    String api_key ;

    public void intUi(AddPriceFragmentBinding binding , Context context , NavController navController , List<AptTypeModel> list , String currentLang
                        , String token , String api_key){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.list = list;
        this.currentLang = currentLang ;
        this.token = token ;
        this.api_key = api_key ;
        getData();
    }

    public void getData(){

    }

    public void addPrice (int property_id , String price_name , int price){
        binding.loader.setVisibility(View.VISIBLE);
        int active = 1;
        String mod = "add-property-price";
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("property_id", property_id);
            jsonObj_.put("price", Integer.valueOf(price));
            jsonObj_.put("price_name", price_name);
            jsonObj_.put("active", active);
            jsonObj_.put("access_token", token);
            jsonObj_.put("mod", mod);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiInterface = RetrofitClient2.getInstance().getApi();
        Call<AddPriceResponse> call = apiInterface.addPropertiesPrice("application/json", gsonObject);
        call.enqueue(new Callback<AddPriceResponse>() {
            @Override
            public void onResponse(Call<AddPriceResponse> call, Response<AddPriceResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        binding.loader.setVisibility(View.GONE);
                        Log.e("message" , response.body().getMessage());
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.nav_vacant_fragment);
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
            public void onFailure(Call<AddPriceResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Boolean validationAreaNum(EditText editText){
        String areaNumInput = editText.getText().toString().trim();
        if (areaNumInput.isEmpty()){
            editText.setError("Field can't be empty");
            return false;
        }else {
            editText.setError(null);
            return true;
        }
    }
}
