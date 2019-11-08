package com.alvaro.onlineshopping.Customer;
import com.alvaro.onlineshopping.MainActivity;
import com.alvaro.onlineshopping.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import static com.alvaro.onlineshopping.MainActivity.MESSAGEid;

public class OptionsCustomer extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer);
    }

    public void gotoListarCustomer(View view) {
        Intent intent = new Intent(this, ListarCustomer.class);
        startActivity(intent);
    }

    public void gotoNewCustomer(View view) {
        Intent intent = new Intent(this, CrearCustomer.class);
        startActivity(intent);
    }

    public void gotoFindCustomer(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Find by ID");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        AlertDialog.Builder view_customer = builder.setPositiveButton("View customer", new DialogInterface.OnClickListener() {
            String reqId = " ";

            @Override
            public void onClick(DialogInterface dialog, int which) {
                reqId = input.getText().toString();
                if (reqId.equals("")) {
                    Log.e("INPUT", "No id was inserted. Aborting.");
                    dialog.cancel();
                } else {
                    Log.e("INPUT", reqId);
                    Intent intent = new Intent(OptionsCustomer.this, ViewCustomer.class).putExtra(MESSAGEid, input.getText().toString());
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
