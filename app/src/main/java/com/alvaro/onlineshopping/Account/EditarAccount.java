package com.alvaro.onlineshopping.Account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alvaro.onlineshopping.Models.Account;
import com.alvaro.onlineshopping.Networking.NetworkService;
import com.alvaro.onlineshopping.R;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alvaro.onlineshopping.MainActivity.MESSAGEid;


public class EditarAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String idaccount = getIntent().getStringExtra(MESSAGEid);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editaccount);

        EditText campoBilling_address = findViewById(R.id.billing_address);
        CheckBox campoIs_closed = findViewById(R.id.checkBoxClosed);
        EditText campoOpen = findViewById(R.id.open);
        EditText campoClosed = findViewById(R.id.closed);

        TextView infoAccountID = findViewById(R.id.infoAccountID);

        NetworkService.getInstance().getJSONApi().getAccount(idaccount)
                .enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(Call<Account> call, Response<Account> response) {
                        Account account = response.body();
                        Log.e("GET ACCOUNT", account.get_id());

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                        campoBilling_address.setText(account.getBilling_address());
                        campoIs_closed.setChecked(account.getIs_closed());
                        campoOpen.setText(sdf.format(account.getOpen()));
                        campoClosed.setText(sdf.format(account.getClosed()));

                        infoAccountID.setText("ID " + account.get_id());
                    }

                    @Override
                    public void onFailure(Call<Account> call, Throwable t) {

                    }
                });
    }

    public void putGotoAccount(View view) {

        String idaccount = getIntent().getStringExtra(MESSAGEid);

        EditText campoBilling_Address = findViewById(R.id.billing_address);
        CheckBox campoIs_closed = findViewById(R.id.checkBoxClosed);
        EditText campoOpen = findViewById(R.id.open);
        EditText campoClosed = findViewById(R.id.closed);

        Account accountN = new Account();

        String textoBilling_Address = campoBilling_Address.getText().toString();
        accountN.setBilling_address(textoBilling_Address);

        accountN.setIs_closed(campoIs_closed.isChecked());

        Button save_Changes = findViewById(R.id.saveChanges);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy'T'HH:mm:ss.SSS");

        if (campoOpen.getText().toString().equals("") || campoClosed.getText().toString().equals("") || campoBilling_Address.getText().toString().equals("")) {
            Toast.makeText(this, "Some fields are empty, please fill them to continue", Toast.LENGTH_SHORT).show();
        } else {
            Date fechaOp = sdf.parse(campoOpen.getText().toString() + "T12:00:00.000", new ParsePosition(0));
            Date fechaCl = sdf.parse(campoClosed.getText().toString() + "T12:00:00.000", new ParsePosition(0));

            accountN.setOpen(fechaOp);
            accountN.setClosed(fechaCl);

            save_Changes.setClickable(false);
            save_Changes.setAlpha(0.5f);
            save_Changes.setText("Saving...");

            NetworkService.getInstance().getJSONApi().editAccount(idaccount, accountN)
                    .enqueue(new Callback<Account>() {

                        @Override
                        public void onResponse(Call<Account> call, Response<Account> response) {
                            if (response.code() == 200) {
                                Intent intent = new Intent(getApplicationContext(), ViewAccount.class).putExtra(MESSAGEid, idaccount);
                                startActivity(intent);
                            } else {
                                Log.e("E", "SA LIAO");
                                Toast.makeText(getApplicationContext(), "Please insert valid dates", Toast.LENGTH_SHORT).show();
                                save_Changes.setClickable(true);
                                save_Changes.setAlpha(1);
                                save_Changes.setText("Save changes");
                            }
                        }

                        @Override
                        public void onFailure(Call<Account> call, Throwable t) {
                            Log.e("E", "SA LIAO");
                            Toast.makeText(getApplicationContext(), "Please insert valid dates", Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                            save_Changes.setClickable(true);
                            save_Changes.setAlpha(1);
                            save_Changes.setText("Save changes");
                        }

                    });

        }
    }
}