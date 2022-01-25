package com.moataz.salah.propertymanagement.network;

import com.google.gson.JsonObject;
import com.moataz.salah.propertymanagement.model.MessageResponse;
import com.moataz.salah.propertymanagement.model.UpdateResponse;
import com.moataz.salah.propertymanagement.model.addFlat.AddNewFlatResponse;
import com.moataz.salah.propertymanagement.model.application.AddApplicationResponse;
import com.moataz.salah.propertymanagement.model.apt.AptTypeResponse;
import com.moataz.salah.propertymanagement.model.bill.BillResponse;
import com.moataz.salah.propertymanagement.model.contactType.ContactTypeResponse;
import com.moataz.salah.propertymanagement.model.contactUs.ContactUsResponse;
import com.moataz.salah.propertymanagement.model.customer.ResponseCustomer;
import com.moataz.salah.propertymanagement.model.electrical.ElectricalResponse;
import com.moataz.salah.propertymanagement.model.expenses.GetExpensesResponse;
import com.moataz.salah.propertymanagement.model.login.LoginResponse;
import com.moataz.salah.propertymanagement.model.price.AddPriceResponse;
import com.moataz.salah.propertymanagement.model.productsResponse.ProductsResponse;
import com.moataz.salah.propertymanagement.model.property.AddPropertyResponse;
import com.moataz.salah.propertymanagement.model.property.PropertiesPriceListResponse;
import com.moataz.salah.propertymanagement.model.property.PropertyResponse;
import com.moataz.salah.propertymanagement.model.reserve.ReservationListResponse;
import com.moataz.salah.propertymanagement.model.sign.RegisterResponse;
import com.moataz.salah.propertymanagement.model.sign.SignResponse;
import com.moataz.salah.propertymanagement.model.staff.AddUserResponse;
import com.moataz.salah.propertymanagement.model.staff.GetPermissionResponse;
import com.moataz.salah.propertymanagement.model.staff.StaffResponse;
import com.moataz.salah.propertymanagement.model.task.TaskResponse;
import com.moataz.salah.propertymanagement.model.taskJob.TaskJobResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @POST("family_api.php")
    @Multipart
    Call<SignResponse> sign(
            @Part("user_full_name") RequestBody full_name,
            @Part("user_name") RequestBody user_name,
            @Part("user_pass") RequestBody user_pass,
            @Part("user_phone") RequestBody user_phone,
            @Part("user_email") RequestBody user_email,
            @Part("mod") RequestBody mod, //register_new_user
            @Part("image") RequestBody image_key,
            @Part MultipartBody.Part image,
            @Part("city") RequestBody city
    );

    @POST("dev/")
    Call<LoginResponse> login(
            @Header("Content-Type") String token ,
            @Header("x-api-key") String api_key,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<RegisterResponse> registerAdmin(
            @Header("Content-Type") String token ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<AddNewFlatResponse> addNewFlat(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<AddNewFlatResponse> updateProperty(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/list-all-apt-type")
    Call<AptTypeResponse> getAptType(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/add-apt-type")
    Call<AddNewFlatResponse> addNewAptType(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<MessageResponse> updateAptType(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<MessageResponse> deleteAptType(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<MessageResponse> updatePropertyPrice(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<MessageResponse> deletePropertyPrice(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/list-all-property")
    Call<PropertyResponse> getAllProperty(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<AddNewFlatResponse> deleteProperty(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<AddPriceResponse> addPropertiesPrice(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<PropertiesPriceListResponse> getPropertiesPrice(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/get-all-reservation")
    Call<ReservationListResponse> getAllReservation(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/add-customer")
    Call<AddNewFlatResponse> addCustomer(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/add-reservation")
    Call<AddNewFlatResponse> addReservation(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/get-customers")
    Call<ResponseCustomer> getCustomer(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<ProductsResponse> getProducts(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<AddNewFlatResponse> addProduct(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<AddNewFlatResponse> deleteProduct(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<AddNewFlatResponse> updateProduct(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<ElectricalResponse> getElectricity(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<AddNewFlatResponse> addElectricCounter(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<AddNewFlatResponse> deleteElectric(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<AddNewFlatResponse> editElectricCounter(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<AddNewFlatResponse> addElectricityBill(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<BillResponse> getBill(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<GetPermissionResponse> getUserPermission(
            @Header("Content-Type") String content_type ,
            @Header("x-api-key") String api_key,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<StaffResponse> getUser(
            @Header("Content-Type") String content_type ,
            @Header("x-api-key") String x_api_key,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<AddUserResponse> addEmployee(
            @Header("Content-Type") String token ,
            @Header("x-api-key") String api_key,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<MessageResponse> updateEmployeeInfo(
            @Header("Content-Type") String token ,
            @Header("x-api-key") String api_key,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<AddNewFlatResponse> addPermission(
            @Header("Content-Type") String token ,
            @Header("x-api-key") String api_key,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<GetExpensesResponse> getExpenses(
            @Header("Content-Type") String token ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<AddNewFlatResponse> addExpense(
            @Header("Content-Type") String token ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<TaskResponse> getTask(
            @Header("Content-Type") String token ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<TaskJobResponse> getTaskJob(
            @Header("Content-Type") String token ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<ContactUsResponse> getContactUs(
            @Header("Content-Type") String token ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<AddNewFlatResponse> addContactUs(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<ContactTypeResponse> getContactType(
            @Header("Content-Type") String token ,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<AddApplicationResponse> addApplication(
            @Header("Content-Type") String token ,
            @Header("x-api-key") String api_key,
            @Body JsonObject testModel
    );

    @POST("dev/")
    Call<MessageResponse> updateCheckOut(
            @Header("Content-Type") String content_type ,
            @Body JsonObject testModel
    );

}