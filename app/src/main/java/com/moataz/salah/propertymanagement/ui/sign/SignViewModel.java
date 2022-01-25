package com.moataz.salah.propertymanagement.ui.sign;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.moataz.salah.propertymanagement.databinding.ActivitySignBinding;
import com.moataz.salah.propertymanagement.model.login.LoginResponse;
import com.moataz.salah.propertymanagement.model.sign.RegisterResponse;
import com.moataz.salah.propertymanagement.model.sign.SignResponse;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient;
import com.moataz.salah.propertymanagement.network.RetrofitClientLogin;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignViewModel extends ViewModel{
    ActivitySignBinding binding;
    Context context;
    NavController navController;
    MultipartBody.Part image_body;
    String path;
    ApiInterface apiInterface;


    public void intUi(ActivitySignBinding binding , Context context , NavController navController, MultipartBody.Part image_body){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.image_body = image_body;
    }

    public void sign(){
        if (!validationFullName() || !validationUserName() || !validationUserPass() || !validationUserConfirmPass()
        || !validationUserMail() || !validationUserPhone() || !validationUserAddress() || !validationUserJob() || !validationUserNationalId()){
            return;
        }else {
            String full_name = binding.fullName.getText().toString();
            String user_name = binding.userName.getText().toString();
            String user_job = binding.userJob.getText().toString();
            String user_pass = (binding.password.getText().toString());
            int user_phone = Integer.parseInt(binding.phoneNumber.getText().toString());
            String user_email = (binding.email.getText().toString());
            int national_id = Integer.parseInt(binding.nationalId.getText().toString());
            String user_address = binding.address.getText().toString();
            String user_city = ("my city");
            String mod = ("register-admin");

            JsonObject gsonObject = new JsonObject();
            try {
                JSONObject jsonObj_ = new JSONObject();
                jsonObj_.put("user_full_name", full_name);
                jsonObj_.put("user_job", user_job);
                jsonObj_.put("user_name", user_name);
                jsonObj_.put("user_pass", user_pass);
                jsonObj_.put("user_phone", user_phone);
                jsonObj_.put("city", user_city);
                jsonObj_.put("user_address", user_address);
                jsonObj_.put("user_email", user_email);
                jsonObj_.put("user_national_id", national_id);
                jsonObj_.put("mod", mod);

                JsonParser jsonParser = new JsonParser();
                gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
                Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            apiInterface = RetrofitClientLogin.getInstance().getApi();
            Call<RegisterResponse> call = apiInterface.registerAdmin("application/json", gsonObject);
                call.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                    binding.signLoader.setVisibility(View.GONE);
                                    showLoginDialog();
                            } else {
                                binding.signLoader.setVisibility(View.GONE);
                                showDialog(response.message(),context);
                            }
                        }else {
                            binding.signLoader.setVisibility(View.GONE);
                            showDialog(response.message(),context);
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        binding.signLoader.setVisibility(View.GONE);
                        showDialog(t.getMessage(),context);
                    }
                });
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

    public void showLoginDialog(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView title = dialog.findViewById(R.id.textView33);
        title.setText("Success");

        TextView error = dialog.findViewById(R.id.message);
        error.setText("please go to Login");

        Button ok = dialog.findViewById(R.id.ok_bt);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_login_fragment);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static RequestBody convertToRequestBody(String part) {
        try {
            if (!part.equals("")) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), part);
                return requestBody;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
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
                    image_body = convertFileToMultipart(path,"image");
                    onLoadImageFromUrl(binding.profileIcon, path, context);
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

    public static MultipartBody.Part convertFileToMultipart(String pathImageFile, String Key) {
        if (pathImageFile != null) {
            File file = new File(pathImageFile);
            RequestBody reqFileselect = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part Imagebody = MultipartBody.Part.createFormData(Key, file.getName()+".jpg", reqFileselect);
            return Imagebody;
        } else {
            return null;
        }
    }


    private Boolean validationFullName(){
        String fullNameInput = binding.fullName.getText().toString().trim();
        if (fullNameInput.isEmpty()){
            binding.fullName.setError("Field can't be empty");
            return false;
        }else {
            binding.fullName.setError(null);
            return true;
        }
    }

    private Boolean validationUserName(){
        String userNameInput = binding.userName.getText().toString().trim();
        if (userNameInput.isEmpty()){
            binding.userName.setError("Field can't be empty");
            return false;
        }else {
            binding.userName.setError(null);
            return true;
        }
    }

    private Boolean validationUserMail(){
        String mailInput = binding.email.getText().toString().trim();
        if (mailInput.isEmpty()){
            binding.email.setError("Field can't be empty");
            return false;
        }else {
            binding.email.setError(null);
            return true;
        }
    }

    private Boolean validationUserPhone(){
        String phoneInput = binding.phoneNumber.getText().toString().trim();
        if (phoneInput.isEmpty()){
            binding.phoneNumber.setError("Field can't be empty");
            return false;
        }else {
            binding.phoneNumber.setError(null);
            return true;
        }
    }

    private Boolean validationUserJob(){
        String userJob = binding.userJob.getText().toString().trim();
        if (userJob.isEmpty()){
            binding.userJob.setError("Field can't be empty");
            return false;
        }else {
            binding.userJob.setError(null);
            return true;
        }
    }

    private Boolean validationUserNationalId(){
        String userNationalId = binding.nationalId.getText().toString().trim();
        if (userNationalId.isEmpty()){
            binding.nationalId.setError("Field can't be empty");
            return false;
        }else {
            binding.nationalId.setError(null);
            return true;
        }
    }

    private Boolean validationUserAddress(){
        String userAddress = binding.address.getText().toString().trim();
        if (userAddress.isEmpty()){
            binding.address.setError("Field can't be empty");
            return false;
        }else {
            binding.address.setError(null);
            return true;
        }
    }

    private Boolean validationUserPass(){
        String passInput = binding.password.getText().toString().trim();
        if (passInput.isEmpty()){
            binding.password.setError("Field can't be empty");
            return false;
        }else {
            binding.password.setError(null);
            return true;
        }
    }

    private Boolean validationUserConfirmPass(){
        String confirmPassInput = binding.rePassword.getText().toString().trim();
        if (confirmPassInput.isEmpty()){
            binding.rePassword.setError("Field can't be empty");
            return false;
        }else {
            binding.rePassword.setError(null);
            return true;
        }
    }

}
