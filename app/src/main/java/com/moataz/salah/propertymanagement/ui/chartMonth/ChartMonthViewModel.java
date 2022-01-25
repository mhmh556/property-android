package com.moataz.salah.propertymanagement.ui.chartMonth;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.lifecycle.ViewModel;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.databinding.ChartMonthFragmentBinding;
import com.moataz.salah.propertymanagement.model.bill.BillModel;
import com.moataz.salah.propertymanagement.model.bill.BillResponse;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartMonthViewModel extends ViewModel {

    ChartMonthFragmentBinding binding;
    Context context;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList<BarEntry> barEntries;
    UserPreference userPreference ;
    List<BillModel> list ;

    public void intUi(ChartMonthFragmentBinding binding , Context context , UserPreference userPreference){
        this.binding = binding;
        this.context = context ;
        this.userPreference = userPreference ;
        list = new ArrayList<>();
        getList();
    }

    public void getList(){
        barEntries = new ArrayList<BarEntry>();
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
                            list = response.body().getData();
                            setData(list);
                        }else {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BillResponse> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    public void setData(List<BillModel> list){
        barEntries = new ArrayList<BarEntry>();
        for (int i = 0 ; i < list.size() ; i++) {
            barEntries.add(new BarEntry(i , list.get(i).getConsumption()));
        }
        barDataSet = new BarDataSet(barEntries , "");
        barData = new BarData(barDataSet);
        binding.barGraph.setData(barData);
        binding.barGraph.animateY(3000);
        binding.barGraph.animateX(1000);
        binding.barGraph.setMaxVisibleValueCount(60);
        barDataSet.setColor(context.getResources().getColor(R.color.ddd));
        barDataSet.setValueTextColor(context.getResources().getColor(R.color.ddd));
        barDataSet.setValueTextSize(10f);
        barDataSet.setLabel(context.getResources().getString(R.string.label));
    }
}
