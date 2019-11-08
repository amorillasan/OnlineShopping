package com.alvaro.onlineshopping.Customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.alvaro.onlineshopping.Models.Customer;
import com.alvaro.onlineshopping.Networking.NetworkService;
import com.alvaro.onlineshopping.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CrearCustomer extends AppCompatActivity {
Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newcustomer);
        context = this;
    }

    public void createGotoListarCustomer(View view) {
        Customer customer = new Customer();
        String textoAddress;
        Number textoPhone;
        String textoEmail;

        EditText campoAddress = findViewById(R.id.address);
        textoAddress = campoAddress.getText().toString();

        EditText campoPhone = findViewById(R.id.phone);
        textoPhone = Integer.parseInt(campoPhone.getText().toString());

        EditText campoEmail = findViewById(R.id.email);
        textoEmail = campoEmail.getText().toString();
        customer.setAddress(textoAddress);
        customer.setPhone(textoPhone);
        customer.setEmail(textoEmail);
        NetworkService.getInstance().getJSONApi().addCustomer(customer)
                .enqueue(new Callback<Customer>() {

                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {

                        Log.e("CUSTOMER", customer.toString());
                        Intent intent = new Intent(context, ListarCustomer.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {

                        Log.e("CUSTOMER", "Ha petao");
                        t.printStackTrace();
                    }
                });


    }

}