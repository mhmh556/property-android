package com.moataz.salah.propertymanagement.ui.monthCounter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.adapter.CounterAdapter;
import com.moataz.salah.propertymanagement.databinding.MonthConterDetailsFragmentBinding;
import com.moataz.salah.propertymanagement.model.addFlat.AddNewFlatResponse;
import com.moataz.salah.propertymanagement.model.bill.BillModel;
import com.moataz.salah.propertymanagement.model.bill.BillResponse;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonthCounterViewModel extends ViewModel implements CounterAdapter.onClick , CounterAdapter.onDeleteClick , CounterAdapter.onEditClick{

    MonthConterDetailsFragmentBinding binding;
    Context context;
    NavController navController;
    List<BillModel> list ;
    UserPreference userPreference ;
    Dialog dialog ;

    public void intUi(MonthConterDetailsFragmentBinding binding , Context context , NavController navController , List<BillModel> list , UserPreference userPreference){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.list = list ;
        this.userPreference = userPreference ;
        dialog = new Dialog(context);
        getData();
    }

    public void getData(){
        binding.loader.setVisibility(View.VISIBLE);
        String api_key = userPreference.getApiKey();
        String token = userPreference.getToken();
        String mod = "get-electicals-bills" ;
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
        Call<BillResponse> call = apiInterface.getBill("application/json" , gsonObject);
        call.enqueue(new Callback<BillResponse>() {
            @Override
            public void onResponse(Call<BillResponse> call, Response<BillResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        binding.loader.setVisibility(View.GONE);
                        list = response.body().getData();
                        binding.monthRecycler.setLayoutManager(new GridLayoutManager(context , 1));
                        CounterAdapter adapter = new CounterAdapter(context , list);
                        binding.monthRecycler.setAdapter(adapter);
                        adapter.setOnClick(MonthCounterViewModel.this);
                        adapter.setOnDeleteClick(MonthCounterViewModel.this);
                        adapter.setOnEditClick(MonthCounterViewModel.this);
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
            public void onFailure(Call<BillResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteBill(int id){
        String api_key = userPreference.getApiKey();
        String token = userPreference.getUser().getAccessToken();
        String mod = "delete-electical-bill" ;
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("electical_bill_id", id);
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
        Call<AddNewFlatResponse> call = apiInterface.deleteProduct("application/json" , gsonObject);
        call.enqueue(new Callback<AddNewFlatResponse>() {
            @Override
            public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        dialog.dismiss();
                        getData();
                    }else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddNewFlatResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClickListener(BillModel item) {

    }

    @Override
    public void onDeleteClickListener(BillModel item) {
        dialog.setContentView(R.layout.confirm_dialog);
        dialog.setCancelable(true);
        Button ok = dialog.findViewById(R.id.delete_bt);
        Button cancel = dialog.findViewById(R.id.cancel_bt);
        TextView title = dialog.findViewById(R.id.title_text);
        TextView message = dialog.findViewById(R.id.message_text);

        title.setText(R.string.delete);
        message.setText(R.string.are_you_want_delete_customer);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBill(item.getElecticalBillId());
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
    public void onEditClickListener(BillModel item) {
        Bundle b = new Bundle();
        b.putSerializable("item" , item);
        b.putString("from" , "edit");
        b.putString("type" , "electric");
        b.putSerializable("data" , userPreference.getItem());
        navController.navigate(R.id.nav_add_bill_fragment , b);
    }
}
