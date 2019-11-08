package com.alvaro.onlineshopping.WebUser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alvaro.onlineshopping.Models.WebUser;
import com.alvaro.onlineshopping.Networking.NetworkService;
import com.alvaro.onlineshopping.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alvaro.onlineshopping.MainActivity.MESSAGEid;


public class EditarWebuser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String idwebuser = getIntent().getStringExtra(MESSAGEid);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editwebuser);

        EditText campoPassword = findViewById(R.id.password);
        Spinner campoState = findViewById(R.id.state);

        TextView infoWebUserID = findViewById(R.id.infoWebuserID);

        NetworkService.getInstance().getJSONApi().getWebUser(idwebuser)
                .enqueue(new Callback<WebUser>() {
                    @Override
                    public void onResponse(Call<WebUser> call, Response<WebUser> response) {
                        WebUser webuser = response.body();
                        Log.e("GET WEB USER", webuser.get_id());

                        campoPassword.setText(webuser.getPassword());

                        String[] states = {"New","Active","Blocked","Banned"};
                        int sPosition = 0, i = 0;
                        for (String s : states) {
                            if (s.equals(webuser.getState()))
                                sPosition = i;
                            i++;
                        }

                        campoState.setSelection(sPosition);

                        infoWebUserID.setText("ID " + webuser.get_id());
                    }

                    @Override
                    public void onFailure(Call<WebUser> call, Throwable t) {

                    }
                });
    }

    public void putGotoWebuser(View view) {
        String idwebuser = getIntent().getStringExtra(MESSAGEid);

        EditText campoPassword = findViewById(R.id.password);
        Spinner campoState = findViewById(R.id.state);

        WebUser webuserN = new WebUser();

        String textoPassword = campoPassword.getText().toString();
        String textoState = campoState.getSelectedItem().toString();

        webuserN.setPassword(textoPassword);
        webuserN.setState(textoState);

        NetworkService.getInstance().getJSONApi().editWebUser(idwebuser, webuserN)
                .enqueue(new Callback<WebUser>() {

                    @Override
                    public void onResponse(Call<WebUser> call, Response<WebUser> response) { }

                    @Override
                    public void onFailure(Call<WebUser> call, Throwable t) {
                        t.printStackTrace();
                    }

                });

        Intent intent = new Intent(getApplicationContext(), ViewWebuser.class).putExtra(MESSAGEid, idwebuser);
        startActivity(intent);
    }
}