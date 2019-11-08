package com.alvaro.onlineshopping.WebUser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alvaro.onlineshopping.Models.WebUser;
import com.alvaro.onlineshopping.Networking.NetworkService;
import com.alvaro.onlineshopping.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CrearWebuser extends AppCompatActivity {
Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newwebuser);
        context = this;
    }

    public void createGotoListarWebuser(View view) {
        // Spinner
        Spinner campoState = findViewById(R.id.state);

        if (campoState.getSelectedItemPosition() == 0) {
            Toast.makeText(getApplicationContext(), "Please select a state before saving", Toast.LENGTH_SHORT).show();
        } else {
            WebUser webuser = new WebUser();
            String textoPassword;

            EditText campoPassword = findViewById(R.id.password);
            textoPassword = campoPassword.getText().toString();

            String textoState = campoState.getSelectedItem().toString();

            webuser.setPassword(textoPassword);
            webuser.setState(textoState);
            NetworkService.getInstance().getJSONApi().addWebuser(webuser)
                    .enqueue(new Callback<WebUser>() {

                        @Override
                        public void onResponse(Call<WebUser> call, Response<WebUser> response) {

                            Log.e("WEBUSER", webuser.toString());
                            Intent intent = new Intent(context, ListarWebuser.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<WebUser> call, Throwable t) {

                            Log.e("WEBUSER", "Ha petao");
                            t.printStackTrace();
                        }
                    });
            }
        }
}