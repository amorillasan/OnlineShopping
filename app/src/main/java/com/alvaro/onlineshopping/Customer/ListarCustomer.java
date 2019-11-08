package com.alvaro.onlineshopping.Customer;

import com.alvaro.onlineshopping.Models.Customer;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alvaro.onlineshopping.Networking.NetworkService;
import com.alvaro.onlineshopping.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alvaro.onlineshopping.MainActivity.MESSAGEid;

public class ListarCustomer extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.alvaro.onlineshopping.extraMessage";

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, OptionsCustomer.class);
        startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NetworkService.getInstance()
                .getJSONApi()
                .getCustomers()
                .enqueue(new Callback<List<Customer>>(){
                    @Override
                    public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                        List<Customer> lista = response.body();
                        LinearLayout listaClientes = findViewById(R.id.listaClientes);

                        for (Customer c : lista) {

                            Button cButton = new Button(ListarCustomer.this);
                            View separator = new View(ListarCustomer.this);

                            // Creando separador: set 5dp to pixels
                            separator.setMinimumHeight((int)(15*(getResources().getDisplayMetrics().density) + 0.5f));

                            String idOnlyNumbers = c.get_id().replaceAll("[^0-9]", "");
                            int id = Integer.parseInt(idOnlyNumbers.substring( idOnlyNumbers.length() - 9 ));
                            String address = c.getAddress();
                            Number phone = c.getPhone();
                            String email = c.getEmail();

                            String clienteinfo = "\nID: " + c.get_id() + "\nAddress: " + address + "\nPhone: "
                                    + phone + "\nEmail: " + email + "\n";

                            cButton.setId(id);
                            cButton.setText(clienteinfo);
                            cButton.setBackground(getDrawable(R.drawable.layout_bg2));
                            cButton.setLongClickable(true);
                            final String extraMessage = c.get_id();

                            cButton.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    startActivity(new Intent(ListarCustomer.this,ViewCustomer.class).putExtra(MESSAGEid, extraMessage));
                                }
                            });

                            cButton.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData myClip = ClipData.newPlainText("id_clip", c.get_id());
                                    clipboard.setPrimaryClip(myClip);

                                    Toast.makeText(getApplicationContext(),
                                            "Customer ID saved to clipboard" ,
                                            Toast.LENGTH_SHORT).show();
                                    return true;
                                }});

                            listaClientes.addView(cButton);
                            listaClientes.addView(separator);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Customer>> call, Throwable t) {

                    }
                });

        setContentView(R.layout.listcustomers);

    }

    public void gotoNewCustomer(View view) {
        Intent intent = new Intent(this, CrearCustomer.class);
        startActivity(intent);
    }

    public void showInfo(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Info");

        final TextView text = new TextView(this);
        text.setText("Tip: you can long press any customer shown in the list to copy their ID to the clipboard.");
        text.setPaddingRelative(70,70,70,20);
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