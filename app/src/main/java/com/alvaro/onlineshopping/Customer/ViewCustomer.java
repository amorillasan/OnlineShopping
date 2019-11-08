package com.alvaro.onlineshopping.Customer;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alvaro.onlineshopping.Models.Customer;
import com.alvaro.onlineshopping.Networking.NetworkService;
import com.alvaro.onlineshopping.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alvaro.onlineshopping.MainActivity.MESSAGEid;


public class ViewCustomer extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ListarCustomer.class);
        startActivity(intent);
    }

    Customer customer = new Customer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String idcliente = getIntent().getStringExtra(MESSAGEid);

        super.onCreate(savedInstanceState);

        NetworkService.getInstance().getJSONApi().getCustomer(idcliente)
                .enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {

                        Log.e("RESPONSE CODE",(((Number)response.code()).toString()));

                        if (response.code() != 200 ) {
                            Button buttonEdit = findViewById(R.id.editCustomer);
                            Button buttonDelete = findViewById(R.id.deleteCustomer);

                            buttonEdit.setVisibility(Button.GONE);
                            buttonDelete.setVisibility(Button.GONE);
                        }
                        else
                        {
                            Customer cliente = response.body();
                            Log.e("CLIENTE", cliente.get_id());

                            TextView infoCliente = findViewById(R.id.infoCliente);
                            TextView infoClienteID = findViewById(R.id.infoClienteID);

                            String id = cliente.get_id();
                            String address = cliente.getAddress();
                            Number phone = cliente.getPhone();
                            String email = cliente.getEmail();

                            String clienteinfo = "Address: " + address + "\nPhone: "
                                    + phone + "\nEmail: " + email;

                            infoCliente.setText(clienteinfo);
                            infoClienteID.setText("ID " + id);

                            infoClienteID.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData myClip = ClipData.newPlainText("id_clip", cliente.get_id());
                                    clipboard.setPrimaryClip(myClip);

                                    Toast.makeText(getApplicationContext(),
                                            "Customer ID saved to clipboard" ,
                                            Toast.LENGTH_SHORT).show();
                                    return true;
                                }});
                        }
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {

                    }
                });
        setContentView(R.layout.viewcustomer);
    }

    public void deleteCustomer(View view) {
        String idcliente = getIntent().getStringExtra(MESSAGEid);
        Log.e("INFO","Deleting - Customer ID " + idcliente);

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String clip0 = clipboard.getPrimaryClip().getItemAt(0).getText().toString();
        Log.e("CLIPBOARD",clip0);

        if (clip0.equals(idcliente))
        {
            Log.e("INFO","Clipboard and deleted ID MATCHES! Clearing clipboard");
            ClipData emptyClip = ClipData.newPlainText("", "");
            clipboard.setPrimaryClip(emptyClip);
        }

        Intent intent = new Intent(ViewCustomer.this, ListarCustomer.class);
        startActivity(intent);

        NetworkService.getInstance().getJSONApi().deleteCustomer(idcliente)
                .enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {
                    }
                });
    }

    public void gotoEditCustomer(View view) {
        String idcliente = getIntent().getStringExtra(MESSAGEid);

        Intent intent = new Intent(getApplicationContext(), EditarCustomer.class).putExtra(MESSAGEid, idcliente);
        startActivity(intent);
    }

    public void showInfo(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Info");

        final TextView text = new TextView(this);
        text.setText("Tip: you can long press the customer ID shown here to copy it to the clipboard.");
        text.setPaddingRelative(70,20,70,20);
        builder.setView(text);

        AlertDialog.Builder showInfoOk = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
            }
        });

        AlertDialog.Builder showInfoNice = builder.setNeutralButton("NICE!", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Thank you!",
                        Toast.LENGTH_LONG).show();
            }
        });

        builder.show();


    }
}