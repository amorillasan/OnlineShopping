package com.alvaro.onlineshopping.WebUser;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alvaro.onlineshopping.MainActivity;
import com.alvaro.onlineshopping.R;

import static com.alvaro.onlineshopping.MainActivity.MESSAGEid;

public class OptionsWebuser extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webuser);
    }

    public void gotoListarWebusers(View view) {
        Intent intent = new Intent(this, ListarWebuser.class);
        startActivity(intent);
    }

    public void gotoNewWebuser(View view) {
        Intent intent = new Intent(this, CrearWebuser.class);
        startActivity(intent);
    }

    public void gotoFindWebuser(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Find by ID");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        AlertDialog.Builder view_webuser = builder.setPositiveButton("View web user", new DialogInterface.OnClickListener() {
            String reqId = " ";

            @Override
            public void onClick(DialogInterface dialog, int which) {
                reqId = input.getText().toString();
                if (reqId.equals("")) {
                    Log.e("INPUT", "No id was inserted. Aborting.");
                    dialog.cancel();
                } else {
                    Log.e("INPUT", reqId);
                    Intent intent = new Intent(OptionsWebuser.this, ViewWebuser.class).putExtra(MESSAGEid, input.getText().toString());
                    startActivity(intent);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
