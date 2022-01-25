package com.moataz.salah.propertymanagement.ui.application;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.adapter.ApplicationAdapter;
import com.moataz.salah.propertymanagement.databinding.AppFragmentBinding;
import com.moataz.salah.propertymanagement.model.addFlat.AddNewFlatResponse;
import com.moataz.salah.propertymanagement.model.application.AddApplicationResponse;
import com.moataz.salah.propertymanagement.model.application.ApplicationModel;
import com.moataz.salah.propertymanagement.model.contactType.ContactTypeModel;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import com.moataz.salah.propertymanagement.network.RetrofitClientLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationViewModel extends ViewModel implements ApplicationAdapter.onSaleItemClick , ApplicationAdapter.onInfoClick{

    AppFragmentBinding binding;
    Context context;
    NavController navController;
    String currentLang ;
    BarData barData;
    BarDataSet barDataSet;
    UserPreference userPreference ;
    List<ApplicationModel> list ;

    public void intUi(AppFragmentBinding binding , Context context , NavController navController , UserPreference userPreference ,
                      List<ApplicationModel> list ){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.userPreference = userPreference ;
        this.list = list ;
    }

    public void addNewApp(Dialog dialog , int app_type_id){
        ProgressBar loader = dialog.findViewById(R.id.loader);
        EditText jobName  = dialog.findViewById(R.id.name_text);
        if (!validation(jobName)){
            loader.setVisibility(View.GONE);
            return;
        }else {
            String name = jobName.getText().toString();
            String token = userPreference.getToken();
            String mod = "property-add-new-application";
            String x_api = "0F3zf54Nut73EaXsfGvykEkn6CfKFVT57494kJ10";
            JsonObject gsonObject = new JsonObject();
            try {
                JSONObject jsonObj_ = new JSONObject();
                jsonObj_.put("app_type_id", app_type_id);
                jsonObj_.put("app_name", name);
                jsonObj_.put("access_token", token);
                jsonObj_.put("mod", mod);

                JsonParser jsonParser = new JsonParser();
                gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
                Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ApiInterface apiInterface = RetrofitClientLogin.getInstance().getApi();
            Call<AddApplicationResponse> call = apiInterface.addApplication("application/json", x_api ,gsonObject);
            call.enqueue(new Callback<AddApplicationResponse>() {
                @Override
                public void onResponse(Call<AddApplicationResponse> call, Response<AddApplicationResponse> response) {
                    if (response.isSuccessful()) {
                        Log.e("response_message" , response.message());
                        if (response.body() != null) {
                            loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            list = response.body().getData();
                            userPreference.saveToken(response.body().getAccessToken());
                            ApplicationAdapter adapter  = new ApplicationAdapter(context , list);
                            if(context.getResources().getBoolean(R.bool.only_landscape)){
                                binding.recyclerView2.setLayoutManager(new GridLayoutManager(context , 5));
                            }else if(context.getResources().getBoolean(R.bool.only_portarite)){
                                binding.recyclerView2.setLayoutManager(new GridLayoutManager(context , 2));
                            }
                            binding.recyclerView2.setAdapter(adapter);
                            adapter.setOnSaleItemClick(ApplicationViewModel.this);
                            adapter.setOnInfoClick(ApplicationViewModel.this);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            loader.setVisibility(View.GONE);
                            showErrorDialog(response.body().getMessage());
                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        loader.setVisibility(View.GONE);
                        showErrorDialog(response.message());
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AddApplicationResponse> call, Throwable t) {
                    loader.setVisibility(View.GONE);
                    showErrorDialog(t.getMessage());
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private Boolean validation(EditText editText){
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
    public void onClickListener(ApplicationModel item) {
        userPreference.saveApiKey(item.getApplicationAppApiKey());
        userPreference.saveApp(item);
        navController.navigate(R.id.nav_home_fragment);
    }

    public void showErrorDialog(String message){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView message_text = dialog.findViewById(R.id.message_text);
        message_text.setText(message);
        Button ok = dialog.findViewById(R.id.ok_bt);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onInfoClickListener(ApplicationModel item) {

    }
}
