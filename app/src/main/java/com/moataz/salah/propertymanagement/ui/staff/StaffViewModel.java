package com.moataz.salah.propertymanagement.ui.staff;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.adapter.StaffAdapter;
import com.moataz.salah.propertymanagement.databinding.StaffFragmentBinding;
import com.moataz.salah.propertymanagement.model.staff.GetPermissionResponse;
import com.moataz.salah.propertymanagement.model.staff.StaffModel;
import com.moataz.salah.propertymanagement.model.staff.StaffResponse;
import com.moataz.salah.propertymanagement.model.staff.UserPermissionModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClientLogin;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffViewModel extends ViewModel implements StaffAdapter.onItemClick , StaffAdapter.onEditClick , StaffAdapter.onDeleteClick{

    StaffFragmentBinding binding;
    Context context;
    NavController navController;
    String currentLang ;
    ApiInterface apiInterface ;
    UserPreference preference;
    List<StaffModel> list ;
    UserPermissionModel userPermissionModel ;

    public void intUi(StaffFragmentBinding binding , Context context , NavController navController , String currentLang ,
                      List<StaffModel> list , UserPreference preference , UserPermissionModel userPermissionModel){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.currentLang = currentLang ;
        this.preference = preference;
        this.list = list ;
        this.userPermissionModel = userPermissionModel ;

        getData();
    }

    public void getData(){
        binding.loader.setVisibility(View.VISIBLE);
        String token = preference.getToken();
        String mod = "list-all-users" ;
        String api_key = "0F3zf54Nut73EaXsfGvykEkn6CfKFVT57494kJ10" ;
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("access_token", token);
            jsonObj_.put("mod", mod);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiInterface apiInterface = RetrofitClientLogin.getInstance().getApi();
        Call<StaffResponse> call = apiInterface.getUser("application/json" , api_key , gsonObject);
        call.enqueue(new Callback<StaffResponse>() {
            @Override
            public void onResponse(Call<StaffResponse> call, Response<StaffResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        Log.e("message" , response.body().getMessage());
                        binding.loader.setVisibility(View.GONE);
                        list = response.body().getData();
                        binding.staffRecycler.setLayoutManager(new GridLayoutManager(context , 1));
                        StaffAdapter adapter = new StaffAdapter(context , list);
                        binding.staffRecycler.setAdapter(adapter);
                        adapter.setOnItemClick(StaffViewModel.this);
                        adapter.setOnEditClick(StaffViewModel.this);
                        adapter.setOnDeleteClick(StaffViewModel.this);
                        adapter.notifyDataSetChanged();
                    }else {
                        binding.loader.setVisibility(View.GONE);
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("message" , response.body().getMessage());
                    }
                }else {
                    binding.loader.setVisibility(View.GONE);
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                    Log.e("message" , response.message());
                }

            }

            @Override
            public void onFailure(Call<StaffResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("failure" , t.getMessage());
            }
        });

    }

    public void getUserPermission(StaffModel item , String user_api_key , String app_api_key){
        String token = preference.getToken();
        String mod = "property-get-user-permission" ;
        String x_api = "0F3zf54Nut73EaXsfGvykEkn6CfKFVT57494kJ10";
        Log.e("user_api_key" , user_api_key);
        Log.e("app_api_key" , app_api_key);
        Log.e("token" , token);
        Log.e("mod" , mod);
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("user_api_key", user_api_key);
            jsonObj_.put("app_api_key", app_api_key);
            jsonObj_.put("access_token", token);
            jsonObj_.put("mod", mod);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiInterface apiInterface = RetrofitClientLogin.getInstance().getApi();
        Call<GetPermissionResponse> call = apiInterface.getUserPermission("application/json" , x_api , gsonObject);
        call.enqueue(new Callback<GetPermissionResponse>() {
            @Override
            public void onResponse(Call<GetPermissionResponse> call, Response<GetPermissionResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        userPermissionModel = response.body().getData();
                        if (userPermissionModel != null) {
                        Bundle b = new Bundle();
                        b.putSerializable("item" , userPermissionModel);
                        b.putSerializable("obj" , item);
                        b.putString("type" , "update");
                        navController.navigate(R.id.nav_permission_fragment , b);
                        }else {
                            Toast.makeText(context, "no permission for this user", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetPermissionResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickListener(StaffModel item) {

    }

    @Override
    public void onDeleteClickListener(StaffModel item) {

    }

    @Override
    public void onEditClickListener(StaffModel item) {
//        getUserPermission(item , item.getUserApiKey() , preference.getApiKey());
        Bundle b = new Bundle();
        b.putSerializable("data" , item);
        b.putString("from" , "staff");
        b.putString("type" , "edit");
        navController.navigate(R.id.nav_add_employees_fragment , b);
//        Bundle b = new Bundle();
//        b.putSerializable("item" , item);
//        b.putString("type" , "update");
//        navController.navigate(R.id.nav_permission_fragment , b);
    }
}
