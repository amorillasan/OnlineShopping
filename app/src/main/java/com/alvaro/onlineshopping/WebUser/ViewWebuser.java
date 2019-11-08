package com.alvaro.onlineshopping.WebUser;

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

import com.alvaro.onlineshopping.Models.WebUser;
import com.alvaro.onlineshopping.Networking.NetworkService;
import com.alvaro.onlineshopping.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alvaro.onlineshopping.MainActivity.MESSAGEid;


public class ViewWebuser extends AppCompatActivity {
    WebUser webuser = new WebUser();

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ListarWebuser.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String idwebuser = getIntent().getStringExtra(MESSAGEid);

        super.onCreate(savedInstanceState);

        NetworkService.getInstance().getJSONApi().getWebUser(idwebuser)
                .enqueue(new Callback<WebUser>() {
                    @Override
                    public void onResponse(Call<WebUser> call, Response<WebUser> response) {

                        Log.e("RESPONSE CODE",(((Number)response.code()).toString()));

                        if (response.code() != 200 ) {
                            Button buttonEdit = findViewById(R.id.editWebuser);
                            Button buttonDelete = findViewById(R.id.deleteWebuser);

                            buttonEdit.setVisibility(Button.GONE);
                            buttonDelete.setVisibility(Button.GONE);
                        }
                        else
                        {
                            WebUser webuser = response.body();
                            Log.e("WEBUSER", webuser.get_id());

                            TextView infoWebuser = findViewById(R.id.infoWebuser);
                            TextView infoWebuserID = findViewById(R.id.infoWebuserID);

                            String id = webuser.get_id();
                            String password = webuser.getPassword();
                            String state = webuser.getState();

                            String webuserinfo = "Password: " + password + "\nState: "
                                    + state;

                            infoWebuser.setText(webuserinfo);
                            infoWebuserID.setText("ID " + id);

                            infoWebuserID.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData myClip = ClipData.newPlainText("id_clip", webuser.get_id());
                                    clipboard.setPrimaryClip(myClip);

                                    Toast.makeText(getApplicationContext(),
                                            "Web User ID saved to clipboard" ,
                                            Toast.LENGTH_SHORT).show();
                                    return true;
                                }});
                        }
                    }

                    @Override
                    public void onFailure(Call<WebUser> call, Throwable t) {

                    }
                });
        setContentView(R.layout.viewwebuser);
    }

    public void deleteWebuser(View view) {
        String idwebuser = getIntent().getStringExtra(MESSAGEid);
        Log.e("INFO","Deleting - WebUser ID " + idwebuser);

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String clip0 = clipboard.getPrimaryClip().getItemAt(0).getText().toString();
        Log.e("CLIPBOARD",clip0);

        if (clip0.equals(idwebuser))
        {
            Log.e("INFO","Clipboard and deleted ID MATCHES! Clearing clipboard");
            ClipData emptyClip = ClipData.newPlainText("", "");
            clipboard.setPrimaryClip(emptyClip);
        }

        Intent intent = new Intent(ViewWebuser.this, ListarWebuser.class);
        startActivity(intent);

        NetworkService.getInstance().getJSONApi().deleteWebUser(idwebuser)
                .enqueue(new Callback<WebUser>() {
                    @Override
                    public void onResponse(Call<WebUser> call, Response<WebUser> response) {
                    }

                    @Override
                    public void onFailure(Call<WebUser> call, Throwable t) {
                    }
                });
    }

    public void gotoEditWebuser(View view) {
        String idwebuser = getIntent().getStringExtra(MESSAGEid);

        Intent intent = new Intent(getApplicationContext(), EditarWebuser.class).putExtra(MESSAGEid, idwebuser);
        startActivity(intent);
    }

    public void showInfo(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Info");

        final TextView text = new TextView(this);
        text.setText("Tip: you can long press the web user ID shown here to copy it to the clipboard.");
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