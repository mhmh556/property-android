package com.moataz.salah.propertymanagement.ui.addProduct;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.moataz.salah.propertymanagement.databinding.AddProductFragmentBinding;
import com.moataz.salah.propertymanagement.model.MessageResponse;
import com.moataz.salah.propertymanagement.model.addFlat.AddNewFlatResponse;
import com.moataz.salah.propertymanagement.model.productsResponse.ProductsModel;
import com.moataz.salah.propertymanagement.model.property.PropertyModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
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

public class AddProductViewModel extends ViewModel {

    AddProductFragmentBinding binding;
    Context context;
    NavController navController;
    List<String> newsDataModelList;
    String currentLang ;
    UserPreference userPreference;
    ApiInterface apiInterface ;
    String api_key ;
    String image_body = "" ;
    String path;
    String file_name ;

    public void intUi(AddProductFragmentBinding binding , Context context , NavController navController , List<String> newsDataModelList , String currentLang ,
                      UserPreference userPreference , ApiInterface apiInterface , String api_key){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.newsDataModelList = newsDataModelList;
        this.currentLang = currentLang ;
        this.userPreference = userPreference ;
        this.apiInterface = apiInterface ;
        this.api_key = api_key ;
    }

    public void addCustomer(){
        binding.loader.setVisibility(View.VISIBLE);
        if (!validationInput(binding.nameInput) || !validationInput(binding.priceInput) || !validationInput(binding.salePriceInput)
            || !validationInput(binding.quantityInput)){
            binding.loader.setVisibility(View.GONE);
            return;
        }else {
            if (image_body.isEmpty()) {
                showDialog("choose pic....", context);
                binding.loader.setVisibility(View.GONE);
                return;
            } else {
                String api_key = userPreference.getApiKey();
                String name = binding.nameInput.getText().toString();
                int quantity = Integer.parseInt(binding.quantityInput.getText().toString());
                int price = Integer.parseInt(binding.priceInput.getText().toString());
                int sale_price = Integer.parseInt(binding.salePriceInput.getText().toString());
                String image = "data:image/png;base64," + image_body;
                Log.e("file" , file_name);
                Log.e("image" , image);
                String token = userPreference.getToken();
                String mod = "add-product";

                JsonObject gsonObject = new JsonObject();
                try {
                    JSONObject jsonObj_ = new JSONObject();
                    jsonObj_.put("app_api_key", api_key);
                    jsonObj_.put("name", name);
                    jsonObj_.put("qty", quantity);
                    jsonObj_.put("price", price);
                    jsonObj_.put("sale_price", sale_price);
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

                apiInterface = RetrofitClient2.getInstance().getApi();
                Call<AddNewFlatResponse> call = apiInterface.addProduct("application/json", gsonObject);
                call.enqueue(new Callback<AddNewFlatResponse>() {
                    @Override
                    public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                binding.loader.setVisibility(View.GONE);
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                navController.navigate(R.id.nav_products_fragment);
                            } else {
                                binding.loader.setVisibility(View.GONE);
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                showDialog(response.body().getMessage() , context);
                            }
                        } else {
                            binding.loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                            showDialog(response.message() , context);
                        }
                    }

                    @Override
                    public void onFailure(Call<AddNewFlatResponse> call, Throwable t) {
                        binding.loader.setVisibility(View.GONE);
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        showDialog(t.getMessage() , context);
                    }
                });
            }
        }
    }

    public void editProduct(int product_id){
        binding.loader.setVisibility(View.VISIBLE);
        if (!validationInput(binding.nameInput) || !validationInput(binding.priceInput) || !validationInput(binding.salePriceInput)
                || !validationInput(binding.quantityInput)){
            binding.loader.setVisibility(View.GONE);
            return;
        }else {
            String api_key = userPreference.getApiKey();
            String name = binding.nameInput.getText().toString();
            int quantity = Integer.parseInt(binding.quantityInput.getText().toString());
            int price = Integer.parseInt(binding.priceInput.getText().toString());
            int sale_price = Integer.parseInt(binding.salePriceInput.getText().toString());
            String token = userPreference.getToken();
            String mod = "update-product";

            JsonObject gsonObject = new JsonObject();
            try {
                JSONObject jsonObj_ = new JSONObject();
                jsonObj_.put("app_api_key", api_key);
                jsonObj_.put("name", name);
                jsonObj_.put("qty", quantity);
                jsonObj_.put("price", price);
                jsonObj_.put("sale_price", sale_price);
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
            Call<AddNewFlatResponse> call = apiInterface.updateProduct("application/json", gsonObject);
            call.enqueue(new Callback<AddNewFlatResponse>() {
                @Override
                public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                    if (response.isSuccessful()){
                        if (response.code() == 200){
                            binding.loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            navController.navigate(R.id.nav_products_fragment);
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
                    onLoadImageFromUrl(binding.photoId, path, context);
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

    public void updateImage(ProductsModel propertyModel){
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
                    onUpdateImageFromUrl(binding.photoId, path, context, propertyModel);
                })
                .onCancel(result -> {
                })
                .start();
    }

    public void onUpdateImageFromUrl(ImageView imageView, String URl, Context context , ProductsModel propertyModel) {
        Glide.with(context)
                .load(URl)
                .into(imageView);
        updatePic(propertyModel);
    }

    public void updatePic(ProductsModel propertyModel){
        binding.imageLoader.setVisibility(View.VISIBLE);
        String mod = "update-product-image" ;
        String image = "data:image/png;base64," + image_body;
        String token = userPreference.getToken();
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("product_id", propertyModel.getProductId());
            jsonObj_.put("image_file_name", file_name);
            jsonObj_.put("image", image);
            jsonObj_.put("app_api_key", api_key);
            jsonObj_.put("access_token", token);
            jsonObj_.put("mod", mod);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiInterface apiInterface = RetrofitClient2.getInstance().getApi();
        Call<MessageResponse> call = apiInterface.deleteAptType("application/json" , gsonObject);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        binding.imageLoader.setVisibility(View.GONE);
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        binding.imageLoader.setVisibility(View.GONE);
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    binding.imageLoader.setVisibility(View.GONE);
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                binding.imageLoader.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
