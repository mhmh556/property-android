package com.moataz.salah.propertymanagement.ui.addNewFlat;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.MediaLoader;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.adapter.AptTypeAdapter;
import com.moataz.salah.propertymanagement.adapter.ElectricityMeterAdapter;
import com.moataz.salah.propertymanagement.databinding.AddNewFlatFragmentBinding;
import com.moataz.salah.propertymanagement.model.MessageResponse;
import com.moataz.salah.propertymanagement.model.addFlat.AddNewFlatResponse;
import com.moataz.salah.propertymanagement.model.apt.AptTypeModel;
import com.moataz.salah.propertymanagement.model.apt.AptTypeResponse;
import com.moataz.salah.propertymanagement.model.property.PropertyModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewFlatViewModel extends ViewModel implements AptTypeAdapter.onClick , AptTypeAdapter.onEditClick ,
        AptTypeAdapter.onDeleteClick , ElectricityMeterAdapter.onClick{

    AddNewFlatFragmentBinding binding;
    Context context;
    NavController navController;
    List<AptTypeModel> list;
    ApiInterface apiInterface;
    String currentLang ;
    String token ;
    Dialog dialog2 , dialog , counter_dialog;
    ProgressBar loader ;
    String api_key ;
    int apt_type;
    String image_body = "";
    String path;
    String file_name ;

    public void intUi(AddNewFlatFragmentBinding binding , Context context , NavController navController , List<AptTypeModel> list , String currentLang
                        , String token , String api_key ){
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

    public void addNewFlat(String type){
        binding.loader.setVisibility(View.VISIBLE);
        if (!validationRoomNum() || !validationBathNum() || !validationHallNum() || !validationKitchenNum() || !validationAptNum() || !validationAreaNum()){
            binding.loader.setVisibility(View.GONE);
            return;
        }else {
            if (image_body.isEmpty()) {
                showDialog("choose pic....", context);
                binding.loader.setVisibility(View.GONE);
                return;
            } else {
                String apt_num = binding.apartmentNumberInput.getText().toString();
                String num_rooms = binding.roomsNumberInput.getText().toString();
                String num_bath = binding.bathNumberInput.getText().toString();
                String num_living_room = binding.hallNumberInput.getText().toString();
                String num_floor = binding.floorNumberInput.getText().toString();
                int customer_id = 101;
                int apt_type_id = 1;
                String apt_area = "casablanca";
                String image = "data:image/png;base64," + image_body;
                String mod = "add-property";
                Log.e("image", image);
                Log.e("file", file_name);
                JsonObject gsonObject = new JsonObject();
                try {
                    JSONObject jsonObj_ = new JSONObject();
                    jsonObj_.put("customer_id", customer_id);
                    jsonObj_.put("electrical_account_id", 32);
                    jsonObj_.put("apt_type_id", apt_type);
                    jsonObj_.put("app_api_key", api_key);
                    jsonObj_.put("apt_num", Integer.valueOf(apt_num));
                    jsonObj_.put("num_rooms", Integer.valueOf(num_rooms));
                    jsonObj_.put("num_bath", Integer.valueOf(num_bath));
                    jsonObj_.put("num_living_room", Integer.valueOf(num_living_room));
                    jsonObj_.put("num_floor", Integer.valueOf(num_floor));
                    jsonObj_.put("apt_area", apt_area);
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
                Call<AddNewFlatResponse> call = apiInterface.addNewFlat("application/json", gsonObject);
                call.enqueue(new Callback<AddNewFlatResponse>() {
                    @Override
                    public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                        if (response.isSuccessful()) {
                            Log.e("response_message", response.message());
                            if (response.body() != null) {
                                if (response.body().getData() != null) {
                                    binding.loader.setVisibility(View.GONE);
                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.e("message", response.body().getMessage());
                                    Bundle b = new Bundle();
                                    b.putSerializable("data", response.body().getData());
                                    b.putString("type", type);
                                    navController.navigate(R.id.nav_add_new_price_fragment, b);
                                } else {
                                    binding.loader.setVisibility(View.GONE);
                                    showDialog(response.body().getMessage(), context);
                                }
                            } else {
                                binding.loader.setVisibility(View.GONE);
                                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                                showDialog(response.message(), context);
                            }
                        } else {
                            binding.loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                            showDialog(response.message(), context);
                        }
                    }

                    @Override
                    public void onFailure(Call<AddNewFlatResponse> call, Throwable t) {
                        binding.loader.setVisibility(View.GONE);
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        showDialog(t.getMessage(), context);
                    }
                });
            }
        }
    }

    public void updateFlat(int property_id){
        binding.loader.setVisibility(View.VISIBLE);
        if (!validationRoomNum() || !validationBathNum() || !validationHallNum() || !validationKitchenNum() || !validationAptNum() || !validationAreaNum()){
            binding.loader.setVisibility(View.GONE);
            return;
        }else {
            String apt_num = binding.apartmentNumberInput.getText().toString();
            String num_rooms = binding.roomsNumberInput.getText().toString();
            String num_bath = binding.bathNumberInput.getText().toString();
            String num_living_room = binding.hallNumberInput.getText().toString();
            String num_floor = binding.floorNumberInput.getText().toString();
            int customer_id = 101;
            int apt_type_id = 1;
            String apt_area = "casablanca";
            String mod = "update-property";
            JsonObject gsonObject = new JsonObject();
            try {
                JSONObject jsonObj_ = new JSONObject();
                jsonObj_.put("apt_type_id", apt_type);
                jsonObj_.put("customer_id", customer_id);
                jsonObj_.put("electrical_account_id", 32);
                jsonObj_.put("apt_num", Integer.valueOf(apt_num));
                jsonObj_.put("num_rooms", Integer.valueOf(num_rooms));
                jsonObj_.put("num_bath" , Integer.valueOf(num_bath));
                jsonObj_.put("num_living_room", Integer.valueOf(num_living_room));
                jsonObj_.put("num_floor", Integer.valueOf(num_floor));
                jsonObj_.put("reserve_until", 1);
                jsonObj_.put("available", "available");
                jsonObj_.put("apt_area", apt_area);
                jsonObj_.put("property_id", property_id);
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
                public void onFailure(Call<AddNewFlatResponse> call, Throwable t) {
                    binding.loader.setVisibility(View.GONE);
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void getAllAptType(){
        loader = dialog2.findViewById(R.id.loader);
        String mod = "list-all-apt-type";
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
        Call<AptTypeResponse> call = apiInterface.getAptType("application/json" , gsonObject);
        call.enqueue(new Callback<AptTypeResponse>() {
            @Override
            public void onResponse(Call<AptTypeResponse> call, Response<AptTypeResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                            loader.setVisibility(View.GONE);
                            list = response.body().getData();
                            RecyclerView recyclerView = dialog2.findViewById(R.id.recyclerView3);
                            recyclerView.setLayoutManager(new GridLayoutManager(context , 1));
                            AptTypeAdapter adapter = new AptTypeAdapter(context , list);
                            adapter.setOnClick(AddNewFlatViewModel.this);
                            adapter.setOnDeleteClick(AddNewFlatViewModel.this);
                            adapter.setOnEditClick(AddNewFlatViewModel.this);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                    }else {
                        loader.setVisibility(View.GONE);
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    loader.setVisibility(View.GONE);
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AptTypeResponse> call, Throwable t) {
                loader.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getAllElectricityMeters(){
        loader = counter_dialog.findViewById(R.id.loader);
        loader.setVisibility(View.GONE);
        List<String> stringList = new ArrayList<>();
        stringList.add("meter 1");
        stringList.add("meter 2");
        stringList.add("meter 3");
        stringList.add("meter 4");
        stringList.add("meter 5");

        RecyclerView recyclerView = counter_dialog.findViewById(R.id.recyclerView3);
        recyclerView.setLayoutManager(new GridLayoutManager(context , 1));
        ElectricityMeterAdapter adapter = new ElectricityMeterAdapter(context , stringList);
        adapter.setOnClick(AddNewFlatViewModel.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public void addNewType(EditText editText){
        loader = dialog.findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);
        String apt_type_name = editText.getText().toString();
        String mod = "add-apt-type";
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("apt_type_name" , apt_type_name);
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
        Call<AddNewFlatResponse> call = apiInterface.addNewAptType("application/json", gsonObject);
        call.enqueue(new Callback<AddNewFlatResponse>() {
            @Override
            public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            getAllAptType();
                            dialog.dismiss();
                    }else {
                        loader.setVisibility(View.GONE);
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    loader.setVisibility(View.GONE);
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddNewFlatResponse> call, Throwable t) {
                loader.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateNewType(EditText editText , AptTypeModel item){
        loader = dialog.findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);
        String apt_type_name = editText.getText().toString();
        String mod = "update-apt-type";
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("apt_type_id" , item.getAptTypeId());
            jsonObj_.put("app_api_key", api_key);
            jsonObj_.put("apt_type_name" , apt_type_name);
            jsonObj_.put("access_token", token);
            jsonObj_.put("mod", mod);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiInterface = RetrofitClient2.getInstance().getApi();
        Call<MessageResponse> call = apiInterface.updateAptType("application/json", gsonObject);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        loader.setVisibility(View.GONE);
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        getAllAptType();
                        dialog.dismiss();
                    }else {
                        loader.setVisibility(View.GONE);
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    loader.setVisibility(View.GONE);
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                loader.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getAptType(){
        dialog2 = new Dialog(context);
        dialog2.setContentView(R.layout.customer_dialog);
        dialog2.setCancelable(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView textView = dialog2.findViewById(R.id.title_text);
        textView.setText(R.string.apartment_rating);
        loader = dialog2.findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);
        getAllAptType();
        ImageView close = dialog2.findViewById(R.id.close_bt);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        Button add = dialog2.findViewById(R.id.add_bt);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });

        dialog2.show();
    }

    public void getCounter(){
        counter_dialog = new Dialog(context);
        counter_dialog.setContentView(R.layout.customer_dialog);
        counter_dialog.setCancelable(false);
        counter_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView textView = counter_dialog.findViewById(R.id.title_text);
        textView.setText(R.string.electric_meter);
        loader = counter_dialog.findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);
        getAllElectricityMeters();
        ImageView close = counter_dialog.findViewById(R.id.close_bt);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter_dialog.dismiss();
            }
        });
        Button add = counter_dialog.findViewById(R.id.add_bt);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showAddDialog();
            }
        });

        counter_dialog.show();
    }

    public void showAddDialog(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView title_text = dialog.findViewById(R.id.title_text);
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
                    addNewType(name_input);
            }
        });

        dialog.show();
    }

    public void showUpdateDialog(AptTypeModel item){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView title_text = dialog.findViewById(R.id.title_text);
        title_text.setText(context.getResources().getString(R.string.update_rating));
        ImageView close = dialog.findViewById(R.id.close_bt);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        EditText name_input = dialog.findViewById(R.id.name_text);
        name_input.setText(item.getAptTypeName());
        Button add = dialog.findViewById(R.id.add_bt);
        add.setText(context.getResources().getString(R.string.update));
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNewType(name_input , item);
            }
        });

        dialog.show();
    }

    private Boolean validationRoomNum(){
        String roomNumInput = binding.roomsNumberInput.getText().toString().trim();
        if (roomNumInput.isEmpty()){
            binding.roomsNumberInput.setError("Field can't be empty");
            return false;
        }else {
            binding.roomsNumberInput.setError(null);
            return true;
        }
    }
    private Boolean validationBathNum(){
        String bathNumInput = binding.bathNumberInput.getText().toString().trim();
        if (bathNumInput.isEmpty()){
            binding.bathNumberInput.setError("Field can't be empty");
            return false;
        }else {
            binding.bathNumberInput.setError(null);
            return true;
        }
    }
    private Boolean validationHallNum(){
        String hallNumInput = binding.hallNumberInput.getText().toString().trim();
        if (hallNumInput.isEmpty()){
            binding.hallNumberInput.setError("Field can't be empty");
            return false;
        }else {
            binding.hallNumberInput.setError(null);
            return true;
        }
    }
    private Boolean validationKitchenNum(){
        String kitchenNumInput = binding.kitchenNumberInput.getText().toString().trim();
        if (kitchenNumInput.isEmpty()){
            binding.kitchenNumberInput.setError("Field can't be empty");
            return false;
        }else {
            binding.kitchenNumberInput.setError(null);
            return true;
        }
    }
    private Boolean validationAptNum(){
        String aptNumInput = binding.apartmentNumberInput.getText().toString().trim();
        if (aptNumInput.isEmpty()){
            binding.apartmentNumberInput.setError("Field can't be empty");
            return false;
        }else {
            binding.apartmentNumberInput.setError(null);
            return true;
        }
    }
    private Boolean validationAreaNum(){
        String areaNumInput = binding.apartmentAreaInput.getText().toString().trim();
        if (areaNumInput.isEmpty()){
            binding.apartmentAreaInput.setError("Field can't be empty");
            return false;
        }else {
            binding.apartmentAreaInput.setError(null);
            return true;
        }
    }

    @Override
    public void onClickListener(AptTypeModel aptTypeModel) {
        binding.spinner.setText(aptTypeModel.getAptTypeName());
        apt_type = aptTypeModel.getAptTypeId();
        dialog2.dismiss();
    }

    @Override
    public void onDeleteClickListener(AptTypeModel aptTypeModel) {
            delete_item(aptTypeModel.getAptTypeId());
    }

    @Override
    public void onEditClickListener(AptTypeModel aptTypeModel) {
        showUpdateDialog(aptTypeModel);
    }

    public void delete_item(int item_id){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.confirm_dialog);
        dialog.setCancelable(true);
        Button ok = dialog.findViewById(R.id.delete_bt);
        Button cancel = dialog.findViewById(R.id.cancel_bt);
        TextView title = dialog.findViewById(R.id.title_text);
        TextView message = dialog.findViewById(R.id.message_text);
        ProgressBar loader = dialog.findViewById(R.id.loader);

        title.setText(R.string.delete);
        message.setText(R.string.are_you_want_delete_this_item);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.setVisibility(View.VISIBLE);
                String mod = "delete-apt-type" ;
                JsonObject gsonObject = new JsonObject();
                try {
                    JSONObject jsonObj_ = new JSONObject();
                    jsonObj_.put("apt_type_id", item_id);
                    jsonObj_.put("app_api_key" , api_key);
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
                                loader.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                                getAllAptType();
                            }else {
                                loader.setVisibility(View.VISIBLE);
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            loader.setVisibility(View.VISIBLE);
                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        loader.setVisibility(View.VISIBLE);
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
    public void onClickListener(String item) {
        counter_dialog.dismiss();
        binding.spinner2.setText(item);
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

    public void updateImage(PropertyModel propertyModel){
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

    public void onUpdateImageFromUrl(ImageView imageView, String URl, Context context , PropertyModel propertyModel) {
        Glide.with(context)
                .load(URl)
                .into(imageView);
        updatePic(propertyModel);
    }

    public void updatePic(PropertyModel propertyModel){
        binding.imageLoader.setVisibility(View.VISIBLE);
        String mod = "update-property-image" ;
        String image = "data:image/png;base64," + image_body;
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("property_id", propertyModel.getPropertyId());
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
