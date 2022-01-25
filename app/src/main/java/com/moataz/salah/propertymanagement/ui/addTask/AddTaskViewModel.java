package com.moataz.salah.propertymanagement.ui.addTask;

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
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.adapter.CustomerAdapter;
import com.moataz.salah.propertymanagement.adapter.TaskAdapter;
import com.moataz.salah.propertymanagement.adapter.TaskJobAdapter;
import com.moataz.salah.propertymanagement.databinding.AddTaskFragmentBinding;
import com.moataz.salah.propertymanagement.model.addFlat.AddNewFlatResponse;
import com.moataz.salah.propertymanagement.model.apt.AptTypeModel;
import com.moataz.salah.propertymanagement.model.customer.CustomerModel;
import com.moataz.salah.propertymanagement.model.customer.ResponseCustomer;
import com.moataz.salah.propertymanagement.model.task.TaskModel;
import com.moataz.salah.propertymanagement.model.task.TaskResponse;
import com.moataz.salah.propertymanagement.model.taskJob.TaskJobModel;
import com.moataz.salah.propertymanagement.model.taskJob.TaskJobResponse;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import com.moataz.salah.propertymanagement.ui.reserve.ReservationFragment;
import com.moataz.salah.propertymanagement.ui.task.TaskViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTaskViewModel extends ViewModel implements TaskJobAdapter.onClick , CustomerAdapter.onClick{

    AddTaskFragmentBinding binding;
    Context context;
    NavController navController;
    List<TaskJobModel> list;
    ApiInterface apiInterface;
    UserPreference preference ;
    String currentLang ;
    Dialog dialog2 , dialog , dialog3;
    ProgressBar loader ;
    int task_user_id = 0 ;
    int task_job_id = 0 ;

    public void intUi(AddTaskFragmentBinding binding , Context context , NavController navController , List<TaskJobModel> list , String currentLang
                        , UserPreference preference){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.list = list;
        this.currentLang = currentLang ;
        this.preference = preference ;
    }

    public void getData(Dialog dialog){
        ImageView close = dialog.findViewById(R.id.close_bt);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView3);
        ProgressBar bar = dialog.findViewById(R.id.loader);
        String api_key = preference.getApiKey();
        String token = preference.getToken();
        String mod = "get-tasks-jobs" ;
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
        Call<TaskJobResponse> call = apiInterface.getTaskJob("application/json" , gsonObject);
        call.enqueue(new Callback<TaskJobResponse>() {
            @Override
            public void onResponse(Call<TaskJobResponse> call, Response<TaskJobResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        bar.setVisibility(View.GONE);
                        list = response.body().getData();
                        Log.e("response" , response.body().getMessage());
                        recyclerView.setLayoutManager(new GridLayoutManager(context , 1));
                        TaskJobAdapter adapter = new TaskJobAdapter(context , list);
                        adapter.setOnClick(AddTaskViewModel.this);
//                        adapter.setOnEditClick(TaskViewModel.this);
//                        adapter.setOnDeleteClick(TaskViewModel.this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else {
                        bar.setVisibility(View.GONE);
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    bar.setVisibility(View.GONE);
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TaskJobResponse> call, Throwable t) {
                bar.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getTasks(){
        dialog2 = new Dialog(context);
        dialog2.setContentView(R.layout.customer_dialog);
        dialog2.setCancelable(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loader = dialog2.findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);
        TextView title = dialog2.findViewById(R.id.title_text);
        title.setText(R.string.tasks);
        getData(dialog2);
//        ImageView close = dialog2.findViewById(R.id.close_bt);
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog2.dismiss();
//            }
//        });
        Button add = dialog2.findViewById(R.id.add_bt);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });
        dialog2.show();
    }

    public void addNewTask(){
        binding.loader.setVisibility(View.VISIBLE);
        if (!validation(binding.notesInput)){
            binding.loader.setVisibility(View.GONE);
            return;
        }else {
            if (task_job_id == 0 || task_user_id == 0) {
                binding.loader.setVisibility(View.GONE);
                return;
            } else {
                String api_key = preference.getApiKey();
                String note = binding.notesInput.getText().toString();
                String token = preference.getToken();
                String mod = "add-task";
                JsonObject gsonObject = new JsonObject();
                try {
                    JSONObject jsonObj_ = new JSONObject();
                    jsonObj_.put("task_user_id", task_user_id);
                    jsonObj_.put("task_job_id", task_job_id);
                    jsonObj_.put("app_api_key", api_key);
                    jsonObj_.put("note", note);
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
                                binding.loader.setVisibility(View.GONE);
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("message", response.body().getMessage());
                                navController.navigate(R.id.nav_task_fragment);
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

    public void showUserDialog(TaskModel item){
        dialog3 = new Dialog(context);
        dialog3.setContentView(R.layout.customer_dialog);
        dialog3.setCancelable(false);
        dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ProgressBar loader = dialog3.findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);
        ImageView close = dialog3.findViewById(R.id.close_bt);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog3.dismiss();
            }
        });
        Button add = dialog3.findViewById(R.id.add_bt);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("from" , "task");
                b.putString("type" , "add");
                b.putSerializable("data" , item);
                navController.navigate(R.id.nav_add_customer_fragment , b);
                dialog3.dismiss();
            }
        });
        String api_key = preference.getApiKey();
        String token = preference.getToken();
        String mod = "get-customers" ;
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
        Call<ResponseCustomer> call = apiInterface.getCustomer("application/json" , gsonObject);
        call.enqueue(new Callback<ResponseCustomer>() {
            @Override
            public void onResponse(Call<ResponseCustomer> call, Response<ResponseCustomer> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        loader.setVisibility(View.GONE);
                        List<CustomerModel> list ;
                        list = response.body().getData();
                        RecyclerView recyclerView = dialog3.findViewById(R.id.recyclerView3);
                        recyclerView.setLayoutManager(new GridLayoutManager(context , 1));
                        CustomerAdapter adapter = new CustomerAdapter(context , list);
                        adapter.setOnClick(AddTaskViewModel.this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
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
            public void onFailure(Call<ResponseCustomer> call, Throwable t) {
                loader.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        dialog3.show();
    }

    public void updateTask(int task_id){
        loader.setVisibility(View.VISIBLE);
        if (!validation(binding.notesInput)){
            binding.loader.setVisibility(View.GONE);
            return;
        }else {
            if (task_job_id == 0 || task_user_id == 0) {
                binding.loader.setVisibility(View.GONE);
                return;
            } else {
                String api_key = preference.getApiKey();
                String note = binding.notesInput.getText().toString();
                String token = preference.getToken();
                String mod = "add-property";
                JsonObject gsonObject = new JsonObject();
                try {
                    JSONObject jsonObj_ = new JSONObject();
                    jsonObj_.put("task_user_id", task_user_id);
                    jsonObj_.put("task_job_id", task_job_id);
                    jsonObj_.put("app_api_key", api_key);
                    jsonObj_.put("note", note);
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
                                binding.loader.setVisibility(View.GONE);
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("message", response.body().getMessage());
                                navController.navigate(R.id.nav_task_fragment);
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

    public void showAddDialog(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView title = dialog.findViewById(R.id.title_text);
        title.setText(R.string.add_task);
        TextView header = dialog.findViewById(R.id.text);
        header.setText(R.string.task_name);
        ImageView close = dialog.findViewById(R.id.close_bt);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button add = dialog.findViewById(R.id.add_bt);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTaskJob(dialog);
            }
        });

        dialog.show();
    }

    public void addNewTaskJob(Dialog dialog){
        ProgressBar loader = dialog.findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);
        EditText jobName  = dialog.findViewById(R.id.name_text);
        if (!validation(jobName)){
            loader.setVisibility(View.GONE);
            return;
        }else {
            String name = jobName.getText().toString();
            String api_key = preference.getApiKey();
            String token = preference.getToken();
            String mod = "add-task-job";
            JsonObject gsonObject = new JsonObject();
            try {
                JSONObject jsonObj_ = new JSONObject();
                jsonObj_.put("app_api_key", api_key);
                jsonObj_.put("task_job_name", name);
                jsonObj_.put("access_token", token);
                jsonObj_.put("mod", mod);

                JsonParser jsonParser = new JsonParser();
                gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
                Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            apiInterface = RetrofitClient2.getInstance().getApi();
            Call<AddNewFlatResponse> call = apiInterface.addExpense("application/json", gsonObject);
            call.enqueue(new Callback<AddNewFlatResponse>() {
                @Override
                public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                    if (response.isSuccessful()) {
                        Log.e("response_message" , response.message());
                        if (response.body() != null) {
                            loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            getTasks();
                            dialog.dismiss();
                        } else {
                            loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
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
    public void onClickListener(TaskJobModel item) {
        binding.taskSpinner.setText(item.getTaskJobName());
        task_job_id = item.getTaskJobId();
        dialog2.dismiss();
    }

    @Override
    public void onClickListener(CustomerModel item) {
        binding.administratorSpinner.setText(item.getName());
        task_user_id = item.getCreatedUserId();
        dialog3.dismiss();
    }
}
