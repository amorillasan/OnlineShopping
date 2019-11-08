package com.alvaro.onlineshopping.WebUser;

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

import com.alvaro.onlineshopping.Models.WebUser;
import com.alvaro.onlineshopping.Networking.NetworkService;
import com.alvaro.onlineshopping.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alvaro.onlineshopping.MainActivity.MESSAGEid;

public class ListarWebuser extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.alvaro.onlineshopping.extraMessage";

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, OptionsWebuser.class);
        startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        NetworkService.getInstance()
                .getJSONApi()
                .getWebUsers()
                .enqueue(new Callback<List<WebUser>>(){
                    @Override
                    public void onResponse(Call<List<WebUser>> call, Response<List<WebUser>> response) {
                        List<WebUser> lista = response.body();
                        LinearLayout listaWebusers = findViewById(R.id.listaWebusers);

                        for (WebUser w : lista) {

                            Button wButton = new Button(ListarWebuser.this);
                            View separator = new View(ListarWebuser.this);

                            // Creando separador: set 5dp to pixels
                            separator.setMinimumHeight((int)(15*(getResources().getDisplayMetrics().density) + 0.5f));

                            String idOnlyNumbers = w.get_id().replaceAll("[^0-9]", "");
                            int id = Integer.parseInt(idOnlyNumbers.substring( idOnlyNumbers.length() - 9 ));
                            String password = w.getPassword();
                            String state = w.getState();

                            String webuserinfo = "\nID: " + w.get_id() + "\nPassword: " + password + "\nState: "
                                    + state + "\n";

                            wButton.setId(id);
                            wButton.setText(webuserinfo);
                            wButton.setBackground(getDrawable(R.drawable.layout_bg2));
                            wButton.setLongClickable(true);
                            final String extraMessage = w.get_id();

                            wButton.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    startActivity(new Intent(ListarWebuser.this,ViewWebuser.class).putExtra(MESSAGEid, extraMessage));
                                }
                            });

                            wButton.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData myClip = ClipData.newPlainText("id_clip", w.get_id());
                                    clipboard.setPrimaryClip(myClip);

                                    Toast.makeText(getApplicationContext(),
                                            "Web User ID saved to clipboard" ,
                                            Toast.LENGTH_SHORT).show();
                                    return true;
                                }});

                            listaWebusers.addView(wButton);
                            listaWebusers.addView(separator);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<WebUser>> call, Throwable t) {

                    }
                });

        setContentView(R.layout.listwebusers);

    }

    public void gotoNewWebuser(View view) {
        Intent intent = new Intent(this, CrearWebuser.class);
        startActivity(intent);
    }

    public void showInfo(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Info");

        final TextView text = new TextView(this);
        text.setText("Tip: you can long press any web user shown in the list to copy their ID to the clipboard.");
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