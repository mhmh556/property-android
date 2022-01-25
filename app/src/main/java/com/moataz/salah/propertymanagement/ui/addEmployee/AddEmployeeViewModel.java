package com.moataz.salah.propertymanagement.ui.addEmployee;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.MediaLoader;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.databinding.AddEmployeeFragmentBinding;
import com.moataz.salah.propertymanagement.model.MessageResponse;
import com.moataz.salah.propertymanagement.model.staff.AddUserModel;
import com.moataz.salah.propertymanagement.model.staff.AddUserResponse;
import com.moataz.salah.propertymanagement.model.staff.GetPermissionResponse;
import com.moataz.salah.propertymanagement.model.staff.StaffModel;
import com.moataz.salah.propertymanagement.model.staff.UserPermissionModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClientLogin;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEmployeeViewModel extends ViewModel {

    AddEmployeeFragmentBinding binding;
    Context context;
    NavController navController;
    List<String> newsDataModelList;
    String currentLang ;
    UserPreference userPreference;
    ApiInterface apiInterface ;
    String api_key ;
    String from ;
    String image_body;
    String path;
    String file_name ;

    public void intUi(AddEmployeeFragmentBinding binding , Context context , NavController navController , List<String> newsDataModelList , String currentLang ,
                      UserPreference userPreference , ApiInterface apiInterface , String api_key , String from){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.newsDataModelList = newsDataModelList;
        this.currentLang = currentLang ;
        this.userPreference = userPreference ;
        this.apiInterface = apiInterface ;
        this.api_key = api_key ;
        this.from = from ;
    }

    public void addCustomer(){
        binding.loader.setVisibility(View.VISIBLE);
        if (!validationInput(binding.fullNameInput) || !validationInput(binding.userNameInput) || !validationInput(binding.jobInput)
            || !validationInput(binding.mailInput) || !validationInput(binding.phoneInput) || !validationInput(binding.idNumberInput)
            || !validationInput(binding.passwordInput) || !validationInput(binding.rePasswordInput) || !validationInput(binding.addressInput)){
            binding.loader.setVisibility(View.GONE);
            return;
        }else {
            String fullName = binding.fullNameInput.getText().toString();
            String userName = binding.userNameInput.getText().toString();
            String job = binding.jobInput.getText().toString();
            String mail = binding.mailInput.getText().toString();
            int idNumber = Integer.parseInt(binding.idNumberInput.getText().toString());
            int phone = Integer.parseInt(binding.phoneInput.getText().toString());
            String password = binding.passwordInput.getText().toString();
            String x_api = "0F3zf54Nut73EaXsfGvykEkn6CfKFVT57494kJ10";
            String address = binding.addressInput.getText().toString();
            String token = userPreference.getToken();
            String image = "data:image/png;base64," + image_body  ;
            String mod = "register-user";

            JsonObject gsonObject = new JsonObject();
            try {
                JSONObject jsonObj_ = new JSONObject();
                jsonObj_.put("app_api_key", api_key);
                jsonObj_.put("user_full_name", fullName);
                jsonObj_.put("user_job", job);
                jsonObj_.put("user_name", userName);
                jsonObj_.put("user_pass", password);
                jsonObj_.put("user_phone", phone);
                jsonObj_.put("city", "ksa");
                jsonObj_.put("user_address", address);
                jsonObj_.put("user_email", mail);
                jsonObj_.put("user_national_id", idNumber);
                jsonObj_.put("image_file_name", file_name);
                jsonObj_.put("image", image);
                jsonObj_.put("access_token", token);
                jsonObj_.put("mod", mod);

                JsonParser jsonParser = new JsonParser();
                gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
                Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            apiInterface = RetrofitClientLogin.getInstance().getApi();
            Call<AddUserResponse> call = apiInterface.addEmployee("application/json", x_api , gsonObject);
            call.enqueue(new Callback<AddUserResponse>() {
                @Override
                public void onResponse(Call<AddUserResponse> call, Response<AddUserResponse> response) {
                    if (response.isSuccessful()){
                        if (response.code() == 200){
                            if (response.body().getUser() != null) {
                                binding.loader.setVisibility(View.GONE);
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                AddUserModel userModel = response.body().getUser();
                                Bundle b = new Bundle();
                                b.putString("type", "new");
                                b.putSerializable("user", userModel);
                                navController.navigate(R.id.nav_permission_fragment, b);
                            }else {
                                binding.loader.setVisibility(View.GONE);
                                showDialog(response.body().getMessage() , context);
                            }
                        }else {
                            binding.loader.setVisibility(View.GONE);
                            showDialog(response.body().getMessage() , context);
                        }
                    }else {
                        binding.loader.setVisibility(View.GONE);
                        showDialog(response.message() , context);
                    }
                }

                @Override
                public void onFailure(Call<AddUserResponse> call, Throwable t) {
                    binding.loader.setVisibility(View.GONE);
                    showDialog(t.getMessage() , context);
                }
            });

        }
    }


    public void updateUserInfo(StaffModel staffModel){
        binding.loader.setVisibility(View.VISIBLE);
        if (!validationInput(binding.fullNameInput) || !validationInput(binding.userNameInput) || !validationInput(binding.jobInput)
                || !validationInput(binding.mailInput) || !validationInput(binding.phoneInput) || !validationInput(binding.idNumberInput)
                || !validationInput(binding.passwordInput) || !validationInput(binding.rePasswordInput) || !validationInput(binding.addressInput)){
            binding.loader.setVisibility(View.GONE);
            return;
        }else {
            String fullName = binding.fullNameInput.getText().toString();
            String userName = binding.userNameInput.getText().toString();
            String job = binding.jobInput.getText().toString();
            String mail = binding.mailInput.getText().toString();
            int idNumber = Integer.parseInt(binding.idNumberInput.getText().toString());
            int phone = Integer.parseInt(String.valueOf(binding.phoneInput.getText())) ;
            String password = binding.passwordInput.getText().toString();
            String x_api = "0F3zf54Nut73EaXsfGvykEkn6CfKFVT57494kJ10";
            String address = binding.addressInput.getText().toString();
            String token = userPreference.getToken();
            int user_id = staffModel.getUserId();
            String mod = "update-user-profile";

            JsonObject gsonObject = new JsonObject();
            try {

                JSONObject jsonObj_ = new JSONObject();
                jsonObj_.put("user_full_name", fullName);
                jsonObj_.put("user_job", job);
                jsonObj_.put("user_phone", phone);
                jsonObj_.put("city", "ksa");
                jsonObj_.put("user_address", address);
                jsonObj_.put("user_email", mail);
                jsonObj_.put("user_name", userName);
                jsonObj_.put("user_national_id", idNumber);
                jsonObj_.put("app_active", 1);
                jsonObj_.put("user_id", user_id);
                jsonObj_.put("access_token", token);
                jsonObj_.put("mod", mod);

                JsonParser jsonParser = new JsonParser();
                gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
                Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            apiInterface = RetrofitClientLogin.getInstance().getApi();
            Call<MessageResponse> call = apiInterface.updateEmployeeInfo("application/json", x_api , gsonObject);
            call.enqueue(new Callback<MessageResponse>() {
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                    if (response.isSuccessful()){
                        if (response.code() == 200){
                            getUserPermission(staffModel , staffModel.getUserApiKey() , userPreference.getApiKey());
                        }else {
                            binding.loader.setVisibility(View.GONE);
                            showDialog(response.body().getMessage() , context);
                        }
                    }else {
                        binding.loader.setVisibility(View.GONE);
                        showDialog(response.message() , context);
                    }
                }

                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) {
                    binding.loader.setVisibility(View.GONE);
                    showDialog(t.getMessage() , context);
                }
            });

        }
    }

    public void getUserPermission(StaffModel item , String user_api_key , String app_api_key){
        String token = userPreference.getToken();
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
                        UserPermissionModel userPermissionModel = response.body().getData();
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

    public void initImage() {
        Album.initialize(AlbumConfig.newBuilder(context)
                .setAlbumLoader(new MediaLoader())
                .build());
        Album.image(context) // Image selection.
                .singleChoice()
                .camera(true)
                .columnCount(3)
                .onResult(result -> {
                    path = result.get(0).getPath();
                    File file = new File(path);
                    file_name = file.getName();
                    Bitmap bm = BitmapFactory.decodeFile(path);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.PNG, 100, baos); // bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    image_body = Base64.encodeToString(b , Base64.NO_WRAP);
                    Log.e("image" , "data:image/png;base64," + image_body);
                    Log.e("path" , path);
                    Log.e("file_name" , file_name);
                    onLoadImageFromUrl(binding.employeePic, path, context);
                })
                .onCancel(result -> {
                })
                .start();
    }

    public static void onLoadImageFromUrl(ImageView imageView, String URl, Context context) {
        Glide.with(context)
                .load(URl)
                .into(imageView);
    }
}
