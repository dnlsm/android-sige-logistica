package br.com.cpqd.instrumentacao.sige.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

import br.com.cpqd.instrumentacao.sige.R;
import br.com.cpqd.instrumentacao.sige.sync.DataSync;

public class LoginActivity extends AppCompatActivity implements Serializable {
    public static final String userDataFilename = "user_data";
    private transient Button enterButton;
    private transient EditText usernameField;
    private transient EditText passwordField;

    //método de criptografia da senha
 /*   private String get_SHA_512_SecurePassword(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }  */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        System.out.println("COMEÇANDO A LOGIN ACTIVITY");

        // load component referenceela
/*        enterButton = (Button) findViewById(R.id.enterButton);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("aeeeeeeeeeeeeeeeeeee");
            }
        });*/

    }

    public void onLoginClick(View view) {
        usernameField = (EditText) findViewById(R.id.loginField);
        passwordField = (EditText) findViewById(R.id.passwordField);

        //pega dados digitados nos respectivos campos
        String usernameValue = usernameField.getText().toString();
        String passwordValue = passwordField.getText().toString();

        System.out.println("MANDANDO AUTENTICAR");
        DataSync dataSync = new DataSync(this, this);
        dataSync.authentication(usernameValue, passwordValue);

        Intent intent = new Intent(this, LoadingActivity.class);
        intent.putExtra("dataSync", dataSync);
        System.out.println("INICIANDO INTENT PARA OUTRA ACTIVITY");
        startActivity(intent);
        finish();

        //  usernameValue = Base64.encodeToString(usernameValue.getBytes(), Base64.DEFAULT);
        //passwordValue = Base64.encodeToString(get_SHA_512_SecurePassword(passwordValue).getBytes(), Base64.DEFAULT);

    }

}
