package com.moataz.salah.propertymanagement.ui.reserve;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.adapter.PropertyPriceAdapter;
import com.moataz.salah.propertymanagement.databinding.ReservationFragment2Binding;
import com.moataz.salah.propertymanagement.model.MessageResponse;
import com.moataz.salah.propertymanagement.model.addFlat.AddNewFlatResponse;
import com.moataz.salah.propertymanagement.model.price.AddPriceResponse;
import com.moataz.salah.propertymanagement.model.property.AddPropertyResponse;
import com.moataz.salah.propertymanagement.model.property.PropertiesPriceListResponse;
import com.moataz.salah.propertymanagement.model.property.PropertyModel;
import com.moataz.salah.propertymanagement.model.property.PropertyPriceModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationViewModel extends ViewModel implements PropertyPriceAdapter.onClick , PropertyPriceAdapter.onEditClick , PropertyPriceAdapter.onDeleteClick{

    ReservationFragment2Binding binding;
    Context context;
    NavController navController;
    List<PropertyPriceModel> price_list;
    String currentLang ;
    String token ;
    ApiInterface apiInterface ;
    Dialog dialog ;
    int price_id;
    int price = 0;
    String api_key ;
    int property_id;

    public void intUi(ReservationFragment2Binding binding , Context context , NavController navController , List<PropertyPriceModel> price_list , String currentLang ,
                                String token , Dialog dialog , String api_key , int property_id){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.price_list = price_list;
        this.currentLang = currentLang ;
        this.token = token ;
        this.dialog = dialog ;
        this.api_key = api_key ;
        this.property_id = property_id ;
        getData();
    }

    public void getData(){

    }

    public void addReservation(PropertyModel propertyModel , int customer_id){
        if (!validationInput(binding.numberOfPeopleInput) || !validationInput(binding.bookingDateInput) || !validationInput(binding.leavingDateInput)){
            return;
        }else {
//            int customer_id = 3 ;
            if (price == 0){
                binding.loader.setVisibility(View.GONE);
                showDialog("choose price" , context);
                return;
            }else if (customer_id == 0){
                binding.loader.setVisibility(View.GONE);
                Log.e("customer_id" , String.valueOf(customer_id));
                showDialog("choose customer" , context);
                return;
            }else {
                int person_num = Integer.parseInt(binding.numberOfPeopleInput.getText().toString());
                Log.e("price_id" , String.valueOf(price_id));
                Log.e("customer_id" , String.valueOf(customer_id));
                String check_in_date = binding.bookingDateInput.getText().toString();
                String check_out_date = binding.leavingDateInput.getText().toString();
                String mod = "add-reservation";
                JsonObject gsonObject = new JsonObject();
                try {
                    JSONObject jsonObj_ = new JSONObject();
                    jsonObj_.put("property_id", propertyModel.getPropertyId());
                    jsonObj_.put("app_api_key", api_key);
                    jsonObj_.put("check_in_date", check_in_date);
                    jsonObj_.put("check_out_date", check_out_date);
                    jsonObj_.put("num_person", person_num);
                    jsonObj_.put("property_price_id", price_id);
                    jsonObj_.put("property_price", price);
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
                Call<AddNewFlatResponse> call = apiInterface.addReservation("application/json", gsonObject);
                call.enqueue(new Callback<AddNewFlatResponse>() {
                    @Override
                    public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                binding.loader.setVisibility(View.GONE);
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Bundle b = new Bundle();
                                b.putSerializable("data", propertyModel);
                                navController.navigate(R.id.nav_vacant_details_fragment, b);
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
    }

    public void showPriceDialog(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.customer_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView title = dialog.findViewById(R.id.title_text);
        title.setText(R.string.booking_price);
        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView3);
        ImageView close = dialog.findViewById(R.id.close_bt);
        ProgressBar loader = dialog.findViewById(R.id.loader);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        getPriceList(property_id , recyclerView , loader);
        Button add = dialog.findViewById(R.id.add_bt);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddNewReserveTypeDialog();
            }
        });

        dialog.show();
    }

    public void showAddNewReserveTypeDialog(){
        Dialog dialog2 = new Dialog(context);
        dialog2.setContentView(R.layout.new_reserve_type_dialog);
        dialog2.setCancelable(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText price_name = dialog2.findViewById(R.id.price_type_input);
        EditText price_value = dialog2.findViewById(R.id.price_value_input);
        ImageView close = dialog2.findViewById(R.id.close_bt);
        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView3);
        ProgressBar loader = dialog.findViewById(R.id.loader);
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
                if (!validationInput(price_name) || !validationInput(price_value)) {
                    return;
                } else {
                    String priceName = price_name.getText().toString();
                    String priceValue = price_value.getText().toString();
                    int active = 1;
                    String mod = "add-property-price";

                    Log.e("id" , String.valueOf(property_id));

                    JsonObject gsonObject = new JsonObject();
                    try {
                        JSONObject jsonObj_ = new JSONObject();
                        jsonObj_.put("property_id", property_id);
                        jsonObj_.put("price_name", priceName);
                        jsonObj_.put("price", Integer.valueOf(priceValue));
                        jsonObj_.put("active", active);
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
                    Call<AddPriceResponse> call = apiInterface.addPropertiesPrice("application/json", gsonObject);
                    call.enqueue(new Callback<AddPriceResponse>() {
                        @Override
                        public void onResponse(Call<AddPriceResponse> call, Response<AddPriceResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    Log.e("message" , response.body().getMessage());
                                    getPriceList(property_id , recyclerView , loader);
                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    dialog2.dismiss();
                                } else {
                                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddPriceResponse> call, Throwable t) {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        dialog2.show();
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

    public void showUpdatePropertyPriceDialog(PropertyPriceModel item){
        Dialog dialog2 = new Dialog(context);
        dialog2.setContentView(R.layout.new_reserve_type_dialog);
        dialog2.setCancelable(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView title = dialog2.findViewById(R.id.title_text);
        title.setText(context.getResources().getString(R.string.update_booking_type));
        EditText price_name = dialog2.findViewById(R.id.price_type_input);
        price_name.setText(item.getPriceName());
        EditText price_value = dialog2.findViewById(R.id.price_value_input);
        price_value.setText(String.valueOf(item.getPrice()));
        ImageView close = dialog2.findViewById(R.id.close_bt);
        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView3);
        ProgressBar loader = dialog.findViewById(R.id.loader);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        Button add = dialog2.findViewById(R.id.add_bt);
        add.setText(context.getResources().getString(R.string.update));
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validationInput(price_name) || !validationInput(price_value)) {
                    return;
                } else {
                    String priceName = price_name.getText().toString();
                    String priceValue = price_value.getText().toString();
                    int active = 1;
                    String mod = "update-property-price";

                    JsonObject gsonObject = new JsonObject();
                    try {
                        JSONObject jsonObj_ = new JSONObject();
                        jsonObj_.put("property_id", property_id);
                        jsonObj_.put("property_price_id", item.getPropertyPriceId());
                        jsonObj_.put("app_api_key", api_key);
                        jsonObj_.put("price", Integer.valueOf(priceValue));
                        jsonObj_.put("price_name", priceName);
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
                    Call<MessageResponse> call = apiInterface.updatePropertyPrice("application/json", gsonObject);
                    call.enqueue(new Callback<MessageResponse>() {
                        @Override
                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    Log.e("message" , response.body().getMessage());
                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    getPriceList(property_id , recyclerView , loader);
                                    dialog2.dismiss();
                                } else {
                                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        dialog2.show();
    }

    public void getPriceList(int properties_id , RecyclerView recyclerView , ProgressBar loader ){
        String mod = "get-property-price";
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("property_id", properties_id);
            jsonObj_.put("app_api_key", api_key);
            jsonObj_.put("access_token", token);
            jsonObj_.put("mod", mod);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loader.setVisibility(View.VISIBLE);
        apiInterface = RetrofitClient2.getInstance().getApi();
        Call<PropertiesPriceListResponse> call = apiInterface.getPropertiesPrice("application/json" , gsonObject);
        call.enqueue(new Callback<PropertiesPriceListResponse>() {
            @Override
            public void onResponse(Call<PropertiesPriceListResponse> call, Response<PropertiesPriceListResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        loader.setVisibility(View.GONE);
                        price_list = response.body().getData();
                        PropertyPriceAdapter adapter = new PropertyPriceAdapter(context , price_list);
                        adapter.setOnClick(ReservationViewModel.this);
                        adapter.setOnEditClick(ReservationViewModel.this);
                        adapter.setOnDeleteClick(ReservationViewModel.this);
                        recyclerView.setLayoutManager(new GridLayoutManager(context , 1));
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
            public void onFailure(Call<PropertiesPriceListResponse> call, Throwable t) {
                loader.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void delete_item(PropertyPriceModel item){
        final Dialog dialog3 = new Dialog(context);
        dialog3.setContentView(R.layout.confirm_dialog);
        dialog3.setCancelable(true);
        Button ok = dialog3.findViewById(R.id.delete_bt);
        Button cancel = dialog3.findViewById(R.id.cancel_bt);
        TextView title = dialog3.findViewById(R.id.title_text);
        TextView message = dialog3.findViewById(R.id.message_text);
        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView3);
        ProgressBar loader = dialog.findViewById(R.id.loader);
        title.setText(R.string.delete);
        message.setText(R.string.are_you_want_delete_this_item);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.setVisibility(View.VISIBLE);
                String mod = "delete-property-price" ;
                JsonObject gsonObject = new JsonObject();
                try {
                    JSONObject jsonObj_ = new JSONObject();
                    jsonObj_.put("property_price_id", item.getPropertyPriceId());
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
                Call<MessageResponse> call = apiInterface.deletePropertyPrice("application/json" , gsonObject);
                call.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null){
                                loader.setVisibility(View.GONE);
                                dialog3.dismiss();
                                getPriceList(property_id , recyclerView , loader);
                            }else {
                                loader.setVisibility(View.GONE);
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3.dismiss();
            }
        });

        dialog3.show();
    }

    @Override
    public void onClickListener(PropertyPriceModel propertyPriceModel) {
        binding.spinnerPrice.setText(String.valueOf(propertyPriceModel.getPrice()));
        price_id = propertyPriceModel.getPropertyPriceId();
        price = propertyPriceModel.getPrice();
        dialog.dismiss();
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

    @Override
    public void onEditClickListener(PropertyPriceModel item) {
        showUpdatePropertyPriceDialog(item);
    }

    @Override
    public void onDeleteClickListener(PropertyPriceModel item) {
        delete_item(item);
    }
}
