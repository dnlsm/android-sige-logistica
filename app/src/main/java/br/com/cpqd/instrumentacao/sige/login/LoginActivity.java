package br.com.cpqd.instrumentacao.sige.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;

import br.com.cpqd.instrumentacao.sige.LoginBroadcastReceiver;
import br.com.cpqd.instrumentacao.sige.R;
import br.com.cpqd.instrumentacao.sige.home.HomeActivity;
import br.com.cpqd.instrumentacao.sige.login.service.SessionManager;
import br.com.cpqd.instrumentacao.sige.typedef.intents;

public class LoginActivity extends Activity {
    public static final String userDataFilename = "user_data";


    LoginActivityReceiver loginActivityReceiver;

    Intent loginServiceIntent;
    Intent homeActivityIntent;


    Button enterButton;
    EditText usernameField, passwordField;

    private String get_SHA_512_SecurePassword(String passwordToHash) {
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // load components references
        enterButton = (Button) findViewById(R.id.enterButton);
        usernameField = (EditText) findViewById(R.id.loginField);
        passwordField = (EditText) findViewById(R.id.passwordField);


        // load intents
        homeActivityIntent = new Intent(this, HomeActivity.class);

        loginActivityReceiver = new LoginActivityReceiver();
        loginActivityReceiver.registerReceiver(this);


        loginServiceIntent = new Intent(this, SessionManager.class);
        loginServiceIntent.setAction(intents.login.ask.ACTION_LOGIN);
        loginServiceIntent.addCategory(intents.CATEGORY_SESSION);
    }


    public boolean setFileContent(String file, String content) throws FileNotFoundException, IOException {
        FileOutputStream outputStream = openFileOutput(file, MODE_PRIVATE);
        outputStream.write(content.getBytes());
        outputStream.close();
        return true;
    }

    public static String getFileContent(File file) throws FileNotFoundException, IOException {
        /**
         * Leitura de arquivo
         */
        final InputStream inputStream = new FileInputStream(file);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        final StringBuilder stringBuilder = new StringBuilder();

        boolean done = false;

        while (!done) {
            final String line = reader.readLine();
            done = (line == null);

            if (line != null) {
                stringBuilder.append(line);
            }
        }

        reader.close();
        inputStream.close();


        String value = stringBuilder.toString();
        return value;
    }

    /**
     * Recebe as mensagens referentes ao processo de Login vindas do Session Manager
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginActivityReceiver.unregisterReceiver(this);
    }

    public void btnLogin(View view) {
        EditText userInput = (EditText) findViewById(R.id.loginField);
        EditText pwdInput = (EditText) findViewById(R.id.passwordField);

        String usernameValue = userInput.getText().toString();
        String passwordValue = pwdInput.getText().toString();

        usernameValue = Base64.encodeToString(usernameValue.getBytes(), Base64.DEFAULT);
        passwordValue = Base64.encodeToString(get_SHA_512_SecurePassword(passwordValue).getBytes(), Base64.DEFAULT);

        performLogin(usernameValue, passwordValue);
    }

    private void performLogin(String username, String password){
        File userDataFile = new File(getApplicationContext().getFilesDir(), userDataFilename);
        try{

            setFileContent(userDataFilename, ("{'user':'" + username + "','pwd':'" + password + "'}"));

            loginServiceIntent.putExtra(intents.login.ask.PARAM_USERNAME,username);
            loginServiceIntent.putExtra(intents.login.ask.PARAM_PASSWORD,password);
            startService(loginServiceIntent);
            System.out.println("Broadcast sent");
        }
        catch (FileNotFoundException error){
            error.printStackTrace();
        }
        catch (IOException error){
            error.printStackTrace();
        }
    }






    public class LoginActivityReceiver extends LoginBroadcastReceiver {
        @Override
        public void onLoginSuccessful(Intent intent) {
            Bundle loginExtrasBundle = intent.getExtras();
            homeActivityIntent.putExtras(loginExtrasBundle);

            startActivity(homeActivityIntent);
            finish();
        }

        @Override
        public void onLoginFailed(Intent intent) {

        }
    }

}
