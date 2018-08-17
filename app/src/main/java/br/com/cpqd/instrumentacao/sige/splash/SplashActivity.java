package br.com.cpqd.instrumentacao.sige.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.os.Bundle;

import org.json.JSONObject;

import java.io.File;

import br.com.cpqd.instrumentacao.sige.LoginBroadcastReceiver;
import br.com.cpqd.instrumentacao.sige.R;
import br.com.cpqd.instrumentacao.sige.home.HomeActivity;
import br.com.cpqd.instrumentacao.sige.login.LoginActivity;
import br.com.cpqd.instrumentacao.sige.login.service.SessionManager;
import br.com.cpqd.instrumentacao.sige.typedef.intents;

public class SplashActivity extends Activity {
    Intent loginServiceIntent;

    Intent loginActivityIntent;
    Intent homeActivityIntent;

    LoginInitializerReceiver loginReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Inicia intents
        homeActivityIntent = new Intent(this, HomeActivity.class);
        loginActivityIntent = new Intent(this, LoginActivity.class);


        loginServiceIntent = new Intent(this, SessionManager.class);
        loginServiceIntent.setAction(intents.login.ask.ACTION_LOGIN);
        loginServiceIntent.addCategory(intents.CATEGORY_SESSION);

        // Registra receiver
        loginReceiver = new LoginInitializerReceiver();
        loginReceiver.registerReceiver(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginReceiver.unregisterReceiver(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


        File userDataFile = new File(getApplicationContext().getFilesDir(), LoginActivity.userDataFilename);

        if(userDataFile.exists()){
            try {
                String content = LoginActivity.getFileContent(userDataFile);

                JSONObject obj = new JSONObject(content);

                String username = obj.getString("user");
                String password = obj.getString("pwd");

                loginServiceIntent.putExtra(intents.login.ask.PARAM_USERNAME,username);
                loginServiceIntent.putExtra(intents.login.ask.PARAM_PASSWORD,password);

                startService(loginServiceIntent);
            } catch (Exception e) {
                startActivity(loginActivityIntent);
                finish();
            }
        }
        else{
            startActivity(loginActivityIntent);
            finish();
        }
    }


    public class LoginInitializerReceiver extends LoginBroadcastReceiver{
        @Override
        public void onLoginSuccessful(Intent intent) {
            Bundle loginExtrasBundle = intent.getExtras();
            homeActivityIntent.putExtras(loginExtrasBundle);

            startActivity(homeActivityIntent);
            finish();
        }

        @Override
        public void onLoginFailed(Intent intent) {
            String errorName = intent.getStringExtra(intents.login.answer.PARAM_LOGIN_ERROR);

            switch (errorName){
                case "UNKNOWN_HOST":
                case "UNKNOWN_EXCEPTION":
                case "UNKNOWN_IO_EXCEPTION":
                    finish();
                    break;
                case "WRONG_USER_OR_PASSWORD":
                    startActivity(loginActivityIntent);
                    finish();
                    break;
                default:

            }

        }
    }
}
