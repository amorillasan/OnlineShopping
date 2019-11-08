package com.alvaro.onlineshopping.Networking;

import com.alvaro.onlineshopping.Models.Customer;
import com.alvaro.onlineshopping.Models.WebUser;
import com.alvaro.onlineshopping.Models.Account;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JSONPlaceHolderApi {

    // Customer -----------------------
    @POST("/customer")
    Call<Customer> addCustomer(@Body Customer customer);

    @GET("/customer")
    Call<List<Customer>> getCustomers();

    @GET("/customer/{id}")
    Call<Customer> getCustomer(@Path("id") String idcliente);

    @DELETE("/customer/{id}")
    Call<Customer> deleteCustomer(@Path("id") String idcliente);

    @PUT("/customer/{id}")
    Call<Customer> editCustomer(@Path("id") String idcliente, @Body Customer customerN);

    // WebUser -----------------------
    @POST("/webuser")
    Call<WebUser> addWebuser(@Body WebUser webuser);

    @GET("/webuser")
    Call<List<WebUser>> getWebUsers();

    @GET("/webuser/{id}")
    Call<WebUser> getWebUser(@Path("id") String idwebuser);

    @DELETE("/webuser/{id}")
    Call<WebUser> deleteWebUser(@Path("id") String idwebuser);

    @PUT("/webuser/{id}")
    Call<WebUser> editWebUser(@Path("id") String idwebuser, @Body WebUser webUserN);

    // Account -----------------------
    @POST("/account")
    Call<Account> addAccount(@Body Account account);

    @GET("/account")
    Call<List<Account>> getAccounts();

    @GET("/account/{id}")
    Call<Account> getAccount(@Path("id") String idaccount);

    @DELETE("/account/{id}")
    Call<Account> deleteAccount(@Path("id") String idaccount);

    @PUT("/account/{id}")
    Call<Account> editAccount(@Path("id") String idaccount, @Body Account accountN);

}
