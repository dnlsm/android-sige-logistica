package br.com.cpqd.instrumentacao.sige.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

import br.com.cpqd.instrumentacao.sige.R;
import br.com.cpqd.instrumentacao.sige.sync.DataSync;

public class LoadingActivity extends AppCompatActivity implements Serializable {

    private DataSync dataSync;
    private HomeActivity homeActivity = new HomeActivity();
    private LoginActivity loginActivity = new LoginActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        dataSync = new DataSync(this, this);
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dataSync.consultUserInfo();
            }
        }, 1500);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dataSync.getStatus_code() == 200) {
                    System.out.println("LOGIN APROVADO, INDO PRA HOME HAHA");
                    goToAnotherActivity(homeActivity);
                } else if (dataSync.getStatus_code() == 401) {
                    System.out.println("LOGIN REJEITADO, INDO PRA TELA DE LOGIN DE NOVO LOL");
                    goToAnotherActivity(loginActivity);
                } else {
                    System.out.println("N ENTROU NOS 2 IFS ANTERIORES");
                    System.out.println(dataSync.getStatus_code());
                    goToAnotherActivity(loginActivity);
                }
            }
        }, 3500);
    }

    private void goToAnotherActivity(Object object) {
        Intent intent = new Intent(this, object.getClass());
        intent.putExtra("dataSync", dataSync);
        startActivity(intent);
        finish();
    }
}
