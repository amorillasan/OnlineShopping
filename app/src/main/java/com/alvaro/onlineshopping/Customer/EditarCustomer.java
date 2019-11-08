package com.alvaro.onlineshopping.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alvaro.onlineshopping.Models.Customer;
import com.alvaro.onlineshopping.Networking.NetworkService;
import com.alvaro.onlineshopping.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alvaro.onlineshopping.MainActivity.MESSAGEid;


public class EditarCustomer extends AppCompatActivity {

//    Bundle extra = getIntent().getExtras();
//    String idcliente = extra.getString(MESSAGEid);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String idcliente = getIntent().getStringExtra(MESSAGEid);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editcustomer);

        EditText campoAddress = findViewById(R.id.address);
        EditText campoPhone = findViewById(R.id.phone);
        EditText campoEmail = findViewById(R.id.email);
        TextView infoClienteID = findViewById(R.id.infoClienteID);

        NetworkService.getInstance().getJSONApi().getCustomer(idcliente)
                .enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                        Customer cliente = response.body();
                        Log.e("GET CLIENTE", cliente.get_id());
                        campoAddress.setText(cliente.getAddress());
                        campoPhone.setText(cliente.getPhone().toString());
                        campoEmail.setText(cliente.getEmail());

                        infoClienteID.setText("ID " + cliente.get_id());
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {

                    }
                });
    }

    public void putGotoCustomer(View view) {
        String idcliente = getIntent().getStringExtra(MESSAGEid);

        EditText campoAddress = findViewById(R.id.address);
        EditText campoPhone = findViewById(R.id.phone);
        EditText campoEmail = findViewById(R.id.email);

        Customer customerN = new Customer();

        String textoAddress = campoAddress.getText().toString();
        Number textoPhone = Integer.parseInt(campoPhone.getText().toString());
        String textoEmail = campoEmail.getText().toString();

        customerN.setAddress(textoAddress);
        customerN.setPhone(textoPhone);
        customerN.setEmail(textoEmail);

        NetworkService.getInstance().getJSONApi().editCustomer(idcliente, customerN)
                .enqueue(new Callback<Customer>() {

                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) { }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {
                        t.printStackTrace();
                    }

                });

        Intent intent = new Intent(getApplicationContext(), ViewCustomer.class).putExtra(MESSAGEid, idcliente);
        startActivity(intent);
    }
}