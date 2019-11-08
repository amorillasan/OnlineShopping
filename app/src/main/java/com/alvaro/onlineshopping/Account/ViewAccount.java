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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alvaro.onlineshopping.Models.Account;
import com.alvaro.onlineshopping.Networking.NetworkService;
import com.alvaro.onlineshopping.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alvaro.onlineshopping.MainActivity.MESSAGEid;


public class ViewAccount extends AppCompatActivity {
    Account account = new Account();

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ListarAccount.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String idaccount = getIntent().getStringExtra(MESSAGEid);

        super.onCreate(savedInstanceState);

        NetworkService.getInstance().getJSONApi().getAccount(idaccount)
                .enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(Call<Account> call, Response<Account> response) {

                        Log.e("RESPONSE CODE",(((Number)response.code()).toString()));

                        if (response.code() != 200 ) {
                            Button buttonEdit = findViewById(R.id.editAccount);
                            Button buttonDelete = findViewById(R.id.deleteAccount);

                            buttonEdit.setVisibility(Button.GONE);
                            buttonDelete.setVisibility(Button.GONE);
                        }
                        else
                        {
                            Account account = response.body();
                            Log.e("ACCOUNT", account.get_id());

                            TextView infoAccount = findViewById(R.id.infoAccount);
                            TextView infoAccountID = findViewById(R.id.infoAccountID);

                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss Z", Locale.getDefault());

                            String id = account.get_id();
                            String billing_address = account.getBilling_address();
                            String open = sdf.format(account.getOpen());
                            String closed = sdf.format(account.getClosed());

                            String is_closed = account.getIs_closed() ? "\nClosed account" : "" ;

                            String accountinfo = "Billing Address: " + billing_address
                                    + is_closed + "\n\nOpen:\n" + open + "\n\nClosed:\n" + closed;

                            infoAccount.setText(accountinfo);
                            infoAccountID.setText("ID " + id);

                            infoAccountID.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData myClip = ClipData.newPlainText("id_clip", account.get_id());
                                    clipboard.setPrimaryClip(myClip);

                                    Toast.makeText(getApplicationContext(),
                                            "Account ID saved to clipboard" ,
                                            Toast.LENGTH_SHORT).show();
                                    return true;
                                }});
                        }
                    }

                    @Override
                    public void onFailure(Call<Account> call, Throwable t) {

                    }
                });
        setContentView(R.layout.viewaccount);
    }

    public void deleteAccount(View view) {
        String idaccount = getIntent().getStringExtra(MESSAGEid);
        Log.e("INFO","Deleting - Account ID " + idaccount);

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String clip0 = clipboard.getPrimaryClip().getItemAt(0).getText().toString();
        Log.e("CLIPBOARD",clip0);

        if (clip0.equals(idaccount))
        {
            Log.e("INFO","Clipboard and deleted ID MATCHES! Clearing clipboard");
            ClipData emptyClip = ClipData.newPlainText("", "");
            clipboard.setPrimaryClip(emptyClip);
        }

        Intent intent = new Intent(ViewAccount.this, ListarAccount.class);
        startActivity(intent);

        NetworkService.getInstance().getJSONApi().deleteAccount(idaccount)
                .enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(Call<Account> call, Response<Account> response) {
                    }

                    @Override
                    public void onFailure(Call<Account> call, Throwable t) {
                    }
                });
    }

    public void gotoEditAccount(View view) {
        String idaccount = getIntent().getStringExtra(MESSAGEid);

        Intent intent = new Intent(getApplicationContext(), EditarAccount.class).putExtra(MESSAGEid, idaccount);
        startActivity(intent);
    }

    public void showInfo(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Info");

        final TextView text = new TextView(this);
        text.setText("Tip: you can long press the account ID shown here to copy it to the clipboard.");
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