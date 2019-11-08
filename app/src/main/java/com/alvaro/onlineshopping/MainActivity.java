package com.alvaro.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alvaro.onlineshopping.Account.OptionsAccount;
import com.alvaro.onlineshopping.Customer.OptionsCustomer;
import com.alvaro.onlineshopping.WebUser.OptionsWebuser;


public class MainActivity extends AppCompatActivity {
    public final static String MESSAGEid = "com.alvaro.onlineshopping.MESSAGE";

    int i = 0;
    @Override
    public void onBackPressed() {
        i++;
        if (i >= 2 && i < 11) Toast.makeText(this, "Can't go back", Toast.LENGTH_SHORT).show();
        else if (i == 11) Toast.makeText(this, "You are pretty bored, aren't you?", Toast.LENGTH_SHORT).show();
        else if (i >= 20 && i < 27 && i % 2 == 0) Toast.makeText(this, "STOP", Toast.LENGTH_SHORT).show();
        else if (i == 27) Toast.makeText(this, "STAAAHP", Toast.LENGTH_SHORT).show();
        else if (i == 35) Toast.makeText(this, "STAAAAAAAAAAAAAAAAAAAAAAAHP", Toast.LENGTH_SHORT).show();
        else if (i == 50) Toast.makeText(this, "Really? Stop it.", Toast.LENGTH_SHORT).show();
        else if (i == 70) Toast.makeText(this, "If you keep trying, you could get a world record or something", Toast.LENGTH_LONG).show();
        else if (i == 90) i = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoCustomer(View view) {
        Intent intent = new Intent(this, OptionsCustomer.class);
        startActivity(intent);
    }

    public void gotoWebuser(View view) {
        Intent intent = new Intent(this, OptionsWebuser.class);
        startActivity(intent);
    }

    public void gotoAccount(View view) {
        Intent intent = new Intent(this, OptionsAccount.class);
        startActivity(intent);
    }

}
