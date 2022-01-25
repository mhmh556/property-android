package com.moataz.salah.propertymanagement;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moataz.salah.propertymanagement.model.UserInfo;
import com.moataz.salah.propertymanagement.model.application.ApplicationModel;
import com.moataz.salah.propertymanagement.model.electrical.ElectricalModel;
import com.moataz.salah.propertymanagement.model.login.User;
import com.moataz.salah.propertymanagement.model.printer.PrinterModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserPreference {

    public Context context;
    private SharedPreferences preferences;
    private SharedPreferences pref;
    private SharedPreferences color_pref ;

    public UserPreference(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("u",Context.MODE_PRIVATE);
        pref = context.getSharedPreferences("i" , Context.MODE_PRIVATE);
        color_pref = context.getSharedPreferences("color_pref" , Context.MODE_PRIVATE);
    }

    public void saveUserInfo(String user_name , String pass){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user_name", user_name);
        editor.putString("user_pass", pass);
        editor.apply();
    }

    public UserInfo getUserInfo(){
        String user_name;
        String pass;
        user_name = pref.getString("user_name","");
        pass = pref.getString("user_pass" , "");
        UserInfo userInfo = new UserInfo();
        userInfo.setName(user_name);
        userInfo.setPass(pass);
        return userInfo;
    }

    public void savePrinter(PrinterModel printer){
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(printer);
        editor.putString("printer", json);
        editor.apply();

    }

    public PrinterModel loadPrinter(){
        Gson gson = new Gson();
        String json = pref.getString("printer", "");
        PrinterModel printerModel = gson.fromJson(json, PrinterModel.class);
        return printerModel ;
    }

    public void writeListPrinter(List<PrinterModel> list){
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("list_key" , jsonString);
        editor.apply();
    }

    public List<PrinterModel> readListPrinter(){
        String jsonString = preferences.getString("list_key" , "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<PrinterModel>>() {}.getType();
        List<PrinterModel> list = gson.fromJson(jsonString,type);
        return list;
    }
    public void saveItem(ElectricalModel item){
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(item);
        editor.putString("MyObject", json);
        editor.apply();
    }

    public ElectricalModel getItem(){
        Gson gson = new Gson();
        String json = pref.getString("MyObject", "");
        ElectricalModel obj = gson.fromJson(json, ElectricalModel.class);
        return obj ;
    }

    public void saveApiKey(String api_key){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("api_key" , api_key);
        editor.apply();
    }

    public String getApiKey(){
        String api_key ;
        api_key= preferences.getString("api_key" , "");
        return api_key;
    }

    public void saveToken(String token){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token" , token);
        editor.apply();
    }

    public String getToken(){
        String token ;
        token= preferences.getString("token" , "");
        return token;
    }

    public void saveUserData(User user){
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("user", json);
        editor.apply();
    }

    public User getUser(){
        Gson gson = new Gson();
        String json = pref.getString("user", "");
        User user = gson.fromJson(json, User.class);
        return user ;
    }

    public void saveUser(User user){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("user_id" , user.getUserId());
        editor.putString("user_full_name" ,user.getUserFullName());
        editor.putString("user_job" , user.getUserJob());
        editor.putLong("user_phone" , user.getUserPhone());
        editor.putString("city" , user.getCity());
        editor.putString("user_Image" ,user.getImage());
//        editor.putString("user_entry_date" , user.());
        editor.putString("user_email" , user.getUserEmail());
        editor.putInt("user_national_id" , user.getUserNationalId());
        editor.putString("api_key" , user.getUserApiKey());
        editor.putString("user_activation" ,user.getUserActivation());
        editor.putString("access_token" , user.getAccessToken());
        editor.putInt("admin" , user.getAdmin());
        editor.apply();
    }

//    public User getUser(){
//        User user = new User();
//        user.setUserId(preferences.getInt("user_id" , 0));
//        user.setUserFullName(preferences.getString("user_full_name" , ""));
//        user.setUserJob(preferences.getString("user_job" , ""));
//        user.setUserPhone(preferences.getInt("user_phone" , 0));
//        user.setCity(preferences.getString("city" , ""));
//        user.setUserImage(preferences.getString("user_Image" , ""));
////        user.setUserEntryDate(preferences.getString("user_entry_date" , ""));
//        user.setUserEmail(preferences.getString("user_email" , ""));
//        user.setUserNationalId(preferences.getInt("user_national_id" , 0));
//        user.setUserApiKey(preferences.getString("api_key" , ""));
//        user.setUserActivation(preferences.getString("user_activation" , ""));
//        user.setAccessToken(preferences.getString("access_token" , ""));
//        user.setAdmin(preferences.getInt("admin" , 0));
//
//        return user;
//    }

    public void writeSizeStatue(Boolean statue){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("size",statue);
        editor.apply();
    }

    public Boolean readSizeStatue(){
        Boolean loginStatue = false;
        loginStatue = preferences.getBoolean("size",false);
        return loginStatue;
    }


    public void saveApp(ApplicationModel app){
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(app);
        editor.putString("app", json);
        editor.apply();
    }

    public ApplicationModel getApp(){
        Gson gson = new Gson();
        String json = pref.getString("app", "");
        ApplicationModel app = gson.fromJson(json, ApplicationModel.class);
        return app ;
    }

    public void clear(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
