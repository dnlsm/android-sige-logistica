package br.com.cpqd.instrumentacao.sige.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

import br.com.cpqd.instrumentacao.sige.R;
import br.com.cpqd.instrumentacao.sige.sync.DataSync;

public class SplashActivity extends AppCompatActivity implements Serializable {
    private HomeActivity homeActivity = new HomeActivity();
    private LoginActivity loginActivity = new LoginActivity();
    private transient DataSync dataSync;
    private final transient Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        System.out.println("COMEÇANDOOOOOOOOOOOO");
        chooseActivity();

    }

    private void chooseActivity() {
        System.out.println("ESCOLHENDO ACTIVITY");
        dataSync = new DataSync(this, this);
        System.out.println("DECLAROU O DATASYNC");
        //   goToAnotherActivity(homeActivity);   //apenas para testes

        if (dataSync.isThereAToken()) {//testa se existe um token gravado no celular
            System.out.println("TEM TOKEN");
            System.out.println("INDO CONSULTAR SE ESTÁ ATIVO AINDA");
            dataSync.consultUserInfo();//consulta através de uma requisição se o token ainda está ativo
            handler.postDelayed(new Runnable() {//coloca um delayzinho de 3 seg na activity, para a requisição ser feita e respondida a tempo
                @Override
                public void run() {
                    int statusCode = dataSync.getStatus_code();
                    if (statusCode == 200) {//vai para tela home
                        System.out.println("TOKEN AINDA ATIVO");
                        goToAnotherActivity(homeActivity);
                    } else {//vai para tela de login
                        System.out.println("TOKEN NÃO ATIVO");
                        goToAnotherActivity(loginActivity);
                    }
                }
            }, 3000);
        } else {//vai para tela de login
            System.out.println("NÃO TEM TOKEN");
            goToAnotherActivity(loginActivity);
        }
    }

    private void goToAnotherActivity(Object activity) {
        System.out.println("INDO PARA OUTRA ACTIVITY");
        Intent intent = new Intent(this, activity.getClass());
        //passa a referência do objeto dataSync para a outra activity
        intent.putExtra("dataSync", dataSync);
        startActivity(intent);
        finish();
    }

}
