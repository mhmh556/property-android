package com.moataz.salah.propertymanagement.ui.addContactUs;

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
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moataz.salah.propertymanagement.R;
import com.moataz.salah.propertymanagement.UserPreference;
import com.moataz.salah.propertymanagement.adapter.TypeAdapter;
import com.moataz.salah.propertymanagement.databinding.AddContactUsFragmentBinding;
import com.moataz.salah.propertymanagement.model.addFlat.AddNewFlatResponse;
import com.moataz.salah.propertymanagement.model.contactType.ContactTypeModel;
import com.moataz.salah.propertymanagement.model.contactType.ContactTypeResponse;
import com.moataz.salah.propertymanagement.network.ApiInterface;
import com.moataz.salah.propertymanagement.network.RetrofitClient2;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddContactUsViewModel extends ViewModel implements TypeAdapter.onItemClick , TypeAdapter.onDeleteClick , TypeAdapter.onEditClick{

    AddContactUsFragmentBinding binding;
    Context context;
    NavController navController;
    List<ContactTypeModel> list;
    ApiInterface apiInterface;
    UserPreference preference ;
    String currentLang ;
    Dialog dialog2 ;
    int contact_type_id = 0;

    public void intUi(AddContactUsFragmentBinding binding , Context context , NavController navController , List<ContactTypeModel> list , String currentLang
                        , UserPreference preference){
        this.binding = binding;
        this.context = context ;
        this.navController = navController;
        this.list = list;
        this.currentLang = currentLang ;
        this.preference = preference ;
    }

    public void addNewContactUs(){
        binding.loader.setVisibility(View.VISIBLE);
        if (!validation(binding.notesInput) || !validation(binding.durationInput) || !validation(binding.phoneInput)){
            binding.loader.setVisibility(View.GONE);
            return;
        }else {
            if (contact_type_id == 0) {
                return;
            } else {
                int user_id = preference.getUser().getUserId();
                String api_key = preference.getApiKey();
                String name = preference.getUser().getUserFullName();
                String phone_number = binding.phoneInput.getText().toString();
                String title = binding.durationInput.getText().toString();
                String note = binding.notesInput.getText().toString();
                String token = preference.getToken();
                String mod = "add-contact-us";
                JsonObject gsonObject = new JsonObject();
                try {
                    JSONObject jsonObj_ = new JSONObject();
                    jsonObj_.put("contact_type_id", contact_type_id);
                    jsonObj_.put("contact_user_id", user_id);
                    jsonObj_.put("app_api_key", api_key);
                    jsonObj_.put("name", name);
                    jsonObj_.put("phone_number", phone_number);
                    jsonObj_.put("title", title);
                    jsonObj_.put("contact_text", note);
                    jsonObj_.put("access_token", token);
                    jsonObj_.put("mod", mod);

                    JsonParser jsonParser = new JsonParser();
                    gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
                    Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                apiInterface = RetrofitClient2.getInstance().getApi();
                Call<AddNewFlatResponse> call = apiInterface.addContactUs("application/json", gsonObject);
                call.enqueue(new Callback<AddNewFlatResponse>() {
                    @Override
                    public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                        if (response.isSuccessful()) {
                            Log.e("response_message", response.message());
                            if (response.body() != null) {
                                binding.loader.setVisibility(View.GONE);
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("message", response.body().getMessage());
                                navController.navigate(R.id.nav_contact_us_fragment);
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

    public void update_item(int contact_type_id , int contact_user_id ,int contact_us_id , String name){
        binding.loader.setVisibility(View.VISIBLE);
        if (!validation(binding.durationInput) || !validation(binding.notesInput)){
            binding.loader.setVisibility(View.GONE);
            return;
        }else {
            String title = binding.durationInput.getText().toString();
            String dec = binding.notesInput.getText().toString();
            String phone = binding.phoneInput.getText().toString();
            String api_key = preference.getApiKey();
            String token = preference.getToken();
            String mod = "update-contact-us";
            JsonObject gsonObject = new JsonObject();
            try {
                JSONObject jsonObj_ = new JSONObject();
                jsonObj_.put("contact_type_id", contact_type_id);
                jsonObj_.put("contact_user_id", contact_user_id);
                jsonObj_.put("name", name);
                jsonObj_.put("phone_number", phone);
                jsonObj_.put("title", title);
                jsonObj_.put("contact_text", dec);
                jsonObj_.put("contact_us_id", contact_us_id);
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
            Call<AddNewFlatResponse> call = apiInterface.deleteProperty("application/json", gsonObject);
            call.enqueue(new Callback<AddNewFlatResponse>() {
                @Override
                public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            binding.loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            navController.navigate(R.id.nav_contact_us_fragment);
                            binding.loader.setVisibility(View.GONE);
                        } else {
                            binding.loader.setVisibility(View.GONE);
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

    public void getContactType(){
        dialog2 = new Dialog(context);
        dialog2.setContentView(R.layout.customer_dialog);
        dialog2.setCancelable(false);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView title = dialog2.findViewById(R.id.title_text);
        title.setText(R.string.contact_type);
        getData(dialog2);
        Button add = dialog2.findViewById(R.id.add_bt);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });
        dialog2.show();
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
        bar.setVisibility(View.VISIBLE);
        String api_key = preference.getApiKey();
        String token = preference.getToken();
        String mod = "get-contact-types" ;
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
        Call<ContactTypeResponse> call = apiInterface.getContactType("application/json" , gsonObject);
        call.enqueue(new Callback<ContactTypeResponse>() {
            @Override
            public void onResponse(Call<ContactTypeResponse> call, Response<ContactTypeResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        bar.setVisibility(View.GONE);
                        list = response.body().getData();
                        recyclerView.setLayoutManager(new GridLayoutManager(context , 1));
                        TypeAdapter adapter = new TypeAdapter(context , list);
                        adapter.setOnItemClick(AddContactUsViewModel.this);
                        adapter.setOnDeleteClick(AddContactUsViewModel.this);
                        adapter.setOnEditClick(AddContactUsViewModel.this);
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
            public void onFailure(Call<ContactTypeResponse> call, Throwable t) {
                bar.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showAddDialog(){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView textView = dialog.findViewById(R.id.title_text);
        textView.setText(R.string.add_type);
        TextView header = dialog.findViewById(R.id.text);
        header.setText(R.string.type_name);
        EditText name_text = dialog.findViewById(R.id.name_text);
        ProgressBar loader = dialog.findViewById(R.id.loader);
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
                loader.setVisibility(View.VISIBLE);
                    if (!validation(name_text)){
                        loader.setVisibility(View.GONE);
                        return;
                    }else {
                        String name = name_text.getText().toString();
                        String api_key = preference.getApiKey();
                        String token = preference.getToken();
                        String mod = "add-contact-type";
                        JsonObject gsonObject = new JsonObject();
                        try {
                            JSONObject jsonObj_ = new JSONObject();
                            jsonObj_.put("contact_type_name", name);
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
                        Call<AddNewFlatResponse> call = apiInterface.addContactUs("application/json", gsonObject);
                        call.enqueue(new Callback<AddNewFlatResponse>() {
                            @Override
                            public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                                if (response.isSuccessful()) {
                                    Log.e("response_message", response.message());
                                    if (response.body() != null) {
                                        loader.setVisibility(View.GONE);
                                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        getData(dialog2);
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
        });
        dialog.show();
    }

    public void showUpdateDialog(ContactTypeModel item){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ProgressBar loader = dialog.findViewById(R.id.loader);
        loader.setVisibility(View.GONE);
        TextView textView = dialog.findViewById(R.id.title_text);
        textView.setText(R.string.update_type);
        TextView header = dialog.findViewById(R.id.text);
        header.setText(R.string.type_name);
        EditText name_text = dialog.findViewById(R.id.name_text);
        name_text.setText(item.getContactTypeName());
        ImageView close = dialog.findViewById(R.id.close_bt);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button add = dialog.findViewById(R.id.add_bt);
        add.setText(R.string.update);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loader.setVisibility(View.VISIBLE);
                if (!validation(name_text)){
                    return;
                }else {
                    String name = name_text.getText().toString();
                    String api_key = preference.getApiKey();
                    String token = preference.getToken();
                    String mod = "update-contact-type";
                    JsonObject gsonObject = new JsonObject();
                    try {
                        JSONObject jsonObj_ = new JSONObject();
                        jsonObj_.put("contact_type_name", name);
                        jsonObj_.put("contact_type_id", item.getContactTypeId());
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
                    Call<AddNewFlatResponse> call = apiInterface.addContactUs("application/json", gsonObject);
                    call.enqueue(new Callback<AddNewFlatResponse>() {
                        @Override
                        public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                            if (response.isSuccessful()) {
                                Log.e("response_message", response.message());
                                if (response.body() != null) {
                                    loader.setVisibility(View.GONE);
                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    getData(dialog2);
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
        });
        dialog.show();
    }

    public void showDeleteDialog(ContactTypeModel item){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.confirm_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ProgressBar loader = dialog.findViewById(R.id.loader);
        loader.setVisibility(View.GONE);
        TextView message = dialog.findViewById(R.id.message_text);
        message.setText(R.string.delete_type_message);
        ImageView close = dialog.findViewById(R.id.close_bt);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button delete = dialog.findViewById(R.id.delete_bt);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loader.setVisibility(View.VISIBLE);
                String api_key = preference.getApiKey();
                String token = preference.getToken();
                String mod = "delete-contact-type";
                JsonObject gsonObject = new JsonObject();
                try {
                    JSONObject jsonObj_ = new JSONObject();
                    jsonObj_.put("contact_type_id", item.getContactTypeId());
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
                Call<AddNewFlatResponse> call = apiInterface.addContactUs("application/json", gsonObject);
                call.enqueue(new Callback<AddNewFlatResponse>() {
                    @Override
                    public void onResponse(Call<AddNewFlatResponse> call, Response<AddNewFlatResponse> response) {
                        if (response.isSuccessful()) {
                            Log.e("response_message", response.message());
                            if (response.body() != null) {
                                loader.setVisibility(View.GONE);
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                getData(dialog2);
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
        });

        Button cancel = dialog.findViewById(R.id.cancel_bt);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
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
    public void onClickListener(ContactTypeModel item) {
        contact_type_id = item.getContactTypeId();
        binding.administratorSpinner.setText(item.getContactTypeName());
        dialog2.dismiss();
    }

    @Override
    public void onDeleteClickListener(ContactTypeModel item) {
            showDeleteDialog(item);
    }

    @Override
    public void onEditClickListener(ContactTypeModel item) {
        showUpdateDialog(item);
    }
}
