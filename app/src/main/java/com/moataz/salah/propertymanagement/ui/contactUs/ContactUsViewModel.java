package com.moataz.salah.propertymanagement.ui.contactUs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.adapter.ContactUsAdapter;
import com.moataz.salah.propertymanagement.databinding.ContactUsFragmentBinding;
import com.moataz.salah.propertymanagement.model.addFlat.AddNewFlatResponse;
import com.moataz.salah.propertymanagement.model.contactUs.ContactUsModel;
import com.moataz.salah.propertymanagement.model.contactUs.ContactUsResponse;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsViewModel extends ViewModel implements ContactUsAdapter.onDeleteClick , ContactUsAdapter.onEditClick , ContactUsAdapter.onItemClick{

    ContactUsFragmentBinding binding;
    Context context;
    NavController navController;
    String currentLang ;
    List<ContactUsModel> list ;
    UserPreference preference ;

    public void intUi(ContactUsFragmentBinding binding , Context context , NavController navController , String currentLang , List<ContactUsModel> list ,
                      UserPreference preference){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.currentLang = currentLang ;
        this.list = list ;
        this.preference = preference ;
        getData();
    }

    public void getData(){
        binding.loader.setVisibility(View.VISIBLE);
        String api_key = preference.getApiKey();
        String token = preference.getToken();
        String mod = "get-contact-us" ;
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

        ApiInterface apiInterface = RetrofitClient2.getInstance().getApi();
        Call<ContactUsResponse> call = apiInterface.getContactUs("application/json" , gsonObject);
        call.enqueue(new Callback<ContactUsResponse>() {
            @Override
            public void onResponse(Call<ContactUsResponse> call, Response<ContactUsResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        binding.loader.setVisibility(View.GONE);
                        list = response.body().getData();
                        Log.e("response" , response.body().getMessage());
                        binding.contactUsRecycler.setLayoutManager(new GridLayoutManager(context , 1));
                        ContactUsAdapter adapter = new ContactUsAdapter(context , list);
                        binding.contactUsRecycler.setAdapter(adapter);
                        adapter.setOnDeleteClick(ContactUsViewModel.this);
                        adapter.setOnEditClick(ContactUsViewModel.this);
                        adapter.setOnItemClick(ContactUsViewModel.this);
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
            public void onFailure(Call<ContactUsResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
                String api_key = preference.getApiKey();
                String token = preference.getToken();
                String mod = "delete-contact-us" ;
                JsonObject gsonObject = new JsonObject();
                try {
                    JSONObject jsonObj_ = new JSONObject();
                    jsonObj_.put("contact_us_id", item_id);
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
                Call<AddNewFlatResponse> call = apiInterface.deleteProperty("application/json" , gsonObject);
                call.enqueue(new Callback<AddNewFlatResponse>() {
                    @Override
                    public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null){
                                loader.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                                getData();
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
                    public void onFailure(Call<AddNewFlatResponse> call, Throwable t) {
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
    public void onDeleteClickListener(ContactUsModel item) {
        delete_item(item.getContactUsId());
    }

    @Override
    public void onEditClickListener(ContactUsModel item) {
        Bundle b = new Bundle();
        b.putSerializable("data" , item);
        b.putString("action" , "update");
        navController.navigate(R.id.nav_add_contact_us_fragment , b);
    }

    @Override
    public void onClickListener(ContactUsModel item) {
        Bundle b = new Bundle();
        b.putSerializable("data" , item);
        navController.navigate(R.id.nav_contact_us_details_fragment , b);
    }
}
