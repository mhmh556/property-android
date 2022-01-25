package com.moataz.salah.propertymanagement.ui.expenseDetails;

import android.app.Dialog;
import android.content.Context;
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
import com.moataz.salah.propertymanagement.adapter.DetailsAdapter;
import com.moataz.salah.propertymanagement.databinding.ExpensesDetailsFragmentBinding;
import com.moataz.salah.propertymanagement.databinding.VacantDetailsFragmentBinding;
import com.moataz.salah.propertymanagement.model.addFlat.AddNewFlatResponse;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseDetailsViewModel extends ViewModel {

    ExpensesDetailsFragmentBinding binding;
    Context context;
    NavController navController;
    List<String> newsDataModelList;
    String currentLang ;
    UserPreference preference ;

    public void intUi(ExpensesDetailsFragmentBinding binding , Context context , NavController navController , List<String> newsDataModelList , String currentLang ,
                      UserPreference preference){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.newsDataModelList = newsDataModelList;
        this.currentLang = currentLang ;
        this.preference = preference ;
        getData();
    }

    public void getData(){

    }

    public void deleteBill(int expense_id){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.confirm_dialog);
        dialog.setCancelable(true);
        Button ok = dialog.findViewById(R.id.delete_bt);
        Button cancel = dialog.findViewById(R.id.cancel_bt);
        TextView title = dialog.findViewById(R.id.title_text);
        TextView message = dialog.findViewById(R.id.message_text);

        title.setText(R.string.delete);
        message.setText(R.string.are_you_want_delete_this_bill);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String api_key = preference.getApiKey();
                String token = preference.getToken();
                String mod = "delete-expense";
                JsonObject gsonObject = new JsonObject();
                try {
                    JSONObject jsonObj_ = new JSONObject();
                    jsonObj_.put("expense_id" , expense_id);
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
                Call<AddNewFlatResponse> call = apiInterface.updateProperty("application/json", gsonObject);
                call.enqueue(new Callback<AddNewFlatResponse>() {
                    @Override
                    public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                        if (response.isSuccessful()) {
                            Log.e("response_message" , response.message());
                            if (response.body() != null) {
//                                binding.loader.setVisibility(View.GONE);
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("message", response.body().getMessage());
                                dialog.dismiss();
                                navController.navigate(R.id.nav_general_expenses_fragment);
                            } else {
//                                binding.loader.setVisibility(View.GONE);
                                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
//                            binding.loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddNewFlatResponse> call, Throwable t) {
//                        binding.loader.setVisibility(View.GONE);
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
}
