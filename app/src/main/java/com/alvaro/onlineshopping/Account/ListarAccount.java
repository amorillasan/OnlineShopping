package com.alvaro.onlineshopping.Account;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alvaro.onlineshopping.Models.Account;
import com.alvaro.onlineshopping.Networking.NetworkService;
import com.alvaro.onlineshopping.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alvaro.onlineshopping.MainActivity.MESSAGEid;

public class ListarAccount extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.alvaro.onlineshopping.extraMessage";

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, OptionsAccount.class);
        startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        NetworkService.getInstance()
                .getJSONApi()
                .getAccounts()
                .enqueue(new Callback<List<Account>>(){
                    @Override
                    public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                        List<Account> lista = response.body();
                        LinearLayout listaAccounts = findViewById(R.id.listaAccounts);

                        for (Account a : lista) {

                            Button aButton = new Button(ListarAccount.this);
                            View separator = new View(ListarAccount.this);

                            // Creando separador: set 5dp to pixels
                            separator.setMinimumHeight((int)(15*(getResources().getDisplayMetrics().density) + 0.5f));

                            String idOnlyNumbers = a.get_id().replaceAll("[^0-9]", "");
                            int id = Integer.parseInt(idOnlyNumbers.substring( idOnlyNumbers.length() - 9 ));
                            String billing_address = a.getBilling_address();
                            Boolean is_closed = a.getIs_closed();
                            Date open = a.getOpen();
                            Date closed = a.getClosed();

                            Log.e("OPENDATE",open.toString());
                            Log.e("CLOSEDDATE",closed.toString());

                            SimpleDateFormat sdfShort = new SimpleDateFormat("dd/MM/yyyy");

                            String isclosed = is_closed ? "\nClosed account" : "";

                            String accountinfo = "\nID: " + a.get_id() + "\nBilling Address: " + billing_address
                                    + isclosed + "\nOpen: " + sdfShort.format(open) + "\nClosed: " + sdfShort.format(closed) + "\n";

                            aButton.setId(id);
                            aButton.setText(accountinfo);
                            aButton.setBackground(getDrawable(R.drawable.layout_bg2));
                            aButton.setLongClickable(true);
                            final String extraMessage = a.get_id();

                            aButton.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    startActivity(new Intent(ListarAccount.this,ViewAccount.class).putExtra(MESSAGEid, extraMessage));
                                }
                            });

                            aButton.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData myClip = ClipData.newPlainText("id_clip", a.get_id());
                                    clipboard.setPrimaryClip(myClip);

                                    Toast.makeText(getApplicationContext(),
                                            "Account ID saved to clipboard" ,
                                            Toast.LENGTH_SHORT).show();
                                    return true;
                                }});

                            listaAccounts.addView(aButton);
                            listaAccounts.addView(separator);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Account>> call, Throwable t) {

                    }
                });

        setContentView(R.layout.listaccounts);

    }

    public void gotoNewAccount(View view) {
        Intent intent = new Intent(this, CrearAccount.class);
        startActivity(intent);
    }

    public void showInfo(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Info");

        final TextView text = new TextView(this);
        text.setText("Tip: you can long press any account shown in the list to copy their ID to the clipboard.");
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