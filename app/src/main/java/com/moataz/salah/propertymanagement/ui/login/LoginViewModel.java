package com.moataz.salah.propertymanagement.ui.login;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.databinding.ActivityLoginBinding;
import com.moataz.salah.propertymanagement.model.login.LoginResponse;
import com.moataz.salah.propertymanagement.model.login.User;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClientLogin;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel{
    ActivityLoginBinding binding;
    Context context;
    NavController navController;
    ApiInterface apiInterface;
    UserPreference userPreference;

    public void intUi(ActivityLoginBinding binding , Context context , NavController navController,
                      UserPreference userPreference){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.userPreference = userPreference;
    }

    public void login(){
        binding.loader.setVisibility(View.VISIBLE);
        if (!validationUserName() || !validationUserPass()){
            binding.loader.setVisibility(View.GONE);
            return;
        }else {
            String user_name = binding.userName.getText().toString();
            String user_pass = binding.userPass.getText().toString();
            String api_key = "0F3zf54Nut73EaXsfGvykEkn6CfKFVT57494kJ10" ;
            JsonObject gsonObject = new JsonObject();
            try {
                JSONObject jsonObj_ = new JSONObject();
                jsonObj_.put("user_name", user_name);
                jsonObj_.put("password", user_pass);
                jsonObj_.put("mod", "property-login");
                JsonParser jsonParser = new JsonParser();
                gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
                Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            apiInterface = RetrofitClientLogin.getInstance().getApi();
            Call<LoginResponse> call = apiInterface.login("application/json", api_key , gsonObject);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getData() != null) {
                                binding.loader.setVisibility(View.GONE);
                                User user = response.body().getData();
//                                userPreference.saveUser(user);
                                userPreference.saveUserData(user);
                                userPreference.saveToken(user.getAccessToken());
                                userPreference.saveUserInfo(user_name, user_pass);
                                if (context.getResources().getBoolean(R.bool.isTablet)) {
                                    userPreference.writeSizeStatue(true);
                                } else if (!context.getResources().getBoolean(R.bool.isTablet)) {
                                    userPreference.writeSizeStatue(false);
                                }
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("data", user);
                                navController.navigate(R.id.nav_app_fragment, bundle);
                            }else {
                                binding.loader.setVisibility(View.GONE);
                                showDialog(response.body().getMessage() , context);
                            }
                        } else {
                            binding.loader.setVisibility(View.GONE);
                            showDialog(context.getResources().getString(R.string.wrong), context);
                        }
                    } else if (response.code() == 204){
                        binding.loader.setVisibility(View.GONE);
                        showDialog(response.message(), context);
                    }else {
                        binding.loader.setVisibility(View.GONE);
                        showDialog(response.message(), context);
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    binding.loader.setVisibility(View.GONE);
                    showDialog(t.getMessage(), context);
                }
            });
        }
    }

    public void loginByFingerprint(String name , String pass){
        binding.loader.setVisibility(View.VISIBLE);
        String api_key = "0F3zf54Nut73EaXsfGvykEkn6CfKFVT57494kJ10" ;
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("user_name", name);
            jsonObj_.put("password", pass);
            jsonObj_.put("mod", "property-login");
            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiInterface = RetrofitClientLogin.getInstance().getApi();
            Call<LoginResponse> call = apiInterface.login("application/json", api_key , gsonObject);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            binding.loader.setVisibility(View.GONE);
                            User user = response.body().getData();
//                                userPreference.saveUser(user);
                            userPreference.saveUserData(user);
                            userPreference.saveToken(user.getAccessToken());
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("data" , user);
                            navController.navigate(R.id.nav_app_fragment , bundle);
                        } else {
                            binding.loader.setVisibility(View.GONE);
                            showDialog(context.getResources().getString(R.string.wrong), context);
                        }
                    } else if (response.code() == 204){
                        binding.loader.setVisibility(View.GONE);
                        showDialog(response.message(), context);
                    }else {
                        binding.loader.setVisibility(View.GONE);
                        showDialog(response.message(), context);
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    binding.loader.setVisibility(View.GONE);
                    showDialog(t.getMessage(), context);
                }
            });


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

    private Boolean validationUserName(){
        String userNameInput = binding.userName.getText().toString().trim();
        if (userNameInput.isEmpty()){
            binding.userName.setError("Field can't be empty");
            return false;
        }else {
            binding.userPass.setError(null);
            return true;
        }
    }

    private Boolean validationUserPass(){
        String userPassInput = binding.userPass.getText().toString().trim();
        if (userPassInput.isEmpty()){
            binding.userPass.setError("Field can't be empty");
            return false;
        }else {
            binding.userPass.setError(null);
            return true;
        }
    }

}
