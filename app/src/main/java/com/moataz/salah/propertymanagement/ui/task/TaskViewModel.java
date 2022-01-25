package com.moataz.salah.propertymanagement.ui.task;

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
import com.moataz.salah.propertymanagement.adapter.ElectricAdapter;
import com.moataz.salah.propertymanagement.adapter.ExpensesAdapter;
import com.moataz.salah.propertymanagement.adapter.TaskAdapter;
import com.moataz.salah.propertymanagement.databinding.TasksFragmentBinding;
import com.moataz.salah.propertymanagement.model.addFlat.AddNewFlatResponse;
import com.moataz.salah.propertymanagement.model.electrical.ElectricalModel;
import com.moataz.salah.propertymanagement.model.expenses.GetExpensesResponse;
import com.moataz.salah.propertymanagement.model.task.TaskModel;
import com.moataz.salah.propertymanagement.model.task.TaskResponse;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import com.moataz.salah.propertymanagement.ui.generalExpenses.GeneralExpensesViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskViewModel extends ViewModel implements TaskAdapter.onEditClick , TaskAdapter.onDeleteClick , TaskAdapter.onItemClick{

    TasksFragmentBinding binding;
    Context context;
    NavController navController;
    String currentLang ;
    ApiInterface apiInterface ;
    UserPreference preference ;
    List<TaskModel> list ;
    TaskAdapter adapter ;

    public void intUi(TasksFragmentBinding binding , Context context , NavController navController , String currentLang , ApiInterface apiInterface ,
                      UserPreference preference , List<TaskModel> list){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.currentLang = currentLang ;
        this.apiInterface = apiInterface ;
        this.preference = preference ;
        this.list = list ;
        getData();
    }

    public void getData(){
        binding.loader.setVisibility(View.VISIBLE);
        String api_key = preference.getApiKey();
        String token = preference.getToken();
        String mod = "get-tasks" ;
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
        Call<TaskResponse> call = apiInterface.getTask("application/json" , gsonObject);
        call.enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        binding.loader.setVisibility(View.GONE);
                        list = response.body().getData();
                        Log.e("response" , response.body().getMessage());
                        binding.viewPager.setLayoutManager(new GridLayoutManager(context , 1));
                        adapter = new TaskAdapter(context , list);
                        adapter.setOnItemClick(TaskViewModel.this);
                        adapter.setOnEditClick(TaskViewModel.this);
                        adapter.setOnDeleteClick(TaskViewModel.this);
                        binding.viewPager.setAdapter(adapter);
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
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getScheduledTasks(){
        binding.loader.setVisibility(View.VISIBLE);
        String api_key = preference.getApiKey();
        String token = preference.getToken();
        String mod = "get-tasks" ;
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
        Call<TaskResponse> call = apiInterface.getTask("application/json" , gsonObject);
        call.enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        binding.loader.setVisibility(View.GONE);
                        list = response.body().getData();
                        Log.e("response" , response.body().getMessage());
                        binding.viewPager.setLayoutManager(new GridLayoutManager(context , 1));
                        adapter = new TaskAdapter(context , list);
                        adapter.setOnItemClick(TaskViewModel.this);
                        adapter.setOnEditClick(TaskViewModel.this);
                        adapter.setOnDeleteClick(TaskViewModel.this);
                        binding.viewPager.setAdapter(adapter);
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
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getDoneTasks(){
        binding.loader.setVisibility(View.VISIBLE);
        String api_key = preference.getApiKey();
        String token = preference.getToken();
        String mod = "get-tasks" ;
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
        Call<TaskResponse> call = apiInterface.getTask("application/json" , gsonObject);
        call.enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        binding.loader.setVisibility(View.GONE);
                        list = response.body().getData();
                        Log.e("response" , response.body().getMessage());
                        binding.viewPager.setLayoutManager(new GridLayoutManager(context , 1));
                        adapter = new TaskAdapter(context , list);
                        adapter.setOnItemClick(TaskViewModel.this);
                        adapter.setOnEditClick(TaskViewModel.this);
                        adapter.setOnDeleteClick(TaskViewModel.this);
                        binding.viewPager.setAdapter(adapter);
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
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                binding.loader.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void delete_task(int task_id){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.confirm_dialog);
        dialog.setCancelable(true);
        Button ok = dialog.findViewById(R.id.delete_bt);
        Button cancel = dialog.findViewById(R.id.cancel_bt);
        TextView title = dialog.findViewById(R.id.title_text);
        TextView message = dialog.findViewById(R.id.message_text);

        title.setText(R.string.delete);
        message.setText(R.string.are_you_want_delete_this_task);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String api_key = preference.getApiKey();
                String token = preference.getToken();
                String mod = "delete-task" ;
                JsonObject gsonObject = new JsonObject();
                try {
                    JSONObject jsonObj_ = new JSONObject();
                    jsonObj_.put("task_id", task_id);
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
    public void onClickListener(TaskModel item) {

    }

    @Override
    public void onDeleteClickListener(TaskModel item) {
        delete_task(item.getTaskId());
    }

    @Override
    public void onEditClickListener(TaskModel item) {
        Bundle b = new Bundle();
        b.putSerializable("data" , item);
        b.putString("action" , "update");
        navController.navigate(R.id.nav_add_task_fragment , b);
    }
}
