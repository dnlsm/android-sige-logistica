package br.com.cpqd.instrumentacao.sige.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;

import br.com.cpqd.instrumentacao.sige.net.HTTPConnector;
import br.com.cpqd.instrumentacao.sige.typedef.intents;


public class SessionManager extends IntentService {
    final static private String DATA_URL = "https://sige.cpqd.com.br/integration/get_user_data.php";

    private boolean isLogged;

    private HTTPConnector connectionManager;

    public SessionManager() {
        super("SIGE-SERVICE");
        isLogged = false;
        connectionManager = new HTTPConnector();
        System.out.println("SessionManager started");

    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();

        if(action == null)
            return;

        if(intent.getCategories().contains(intents.CATEGORY_SESSION)){
            switch (action){
                case intents.login.ask.ACTION_LOGIN:
                    loginActionExecuter(intent);
                    break;
                case intents.logout.ask.ACTION_LOGOUT:

                default:

            }
        }

    }

    private void logoutActionExecuter(Intent intent){

    }
    private void loginActionExecuter(Intent intent){
        String username = intent.getStringExtra(intents.login.ask.PARAM_USERNAME);
        String password = intent.getStringExtra(intents.login.ask.PARAM_PASSWORD);

        // TODO refatorar o código e deixar mais limpo

        Intent responseIntent = new Intent();

        if(performLogin(username, password, responseIntent)){
            responseIntent.setAction(intents.login.answer.ACTION_SUCCESSFUL_LOGIN);
            responseIntent.addCategory(intents.CATEGORY_ANSWER_SESSION);

            System.out.println("Laboratorio: " + responseIntent.getStringExtra(intents.login.answer.PARAM_LABORATORY));
            System.out.println("Usuario: " + responseIntent.getStringExtra(intents.login.answer.PARAM_USERNAME));

        }else{
            responseIntent.setAction(intents.login.answer.ACTION_FAILED_LOGIN);
            responseIntent.addCategory(intents.CATEGORY_ANSWER_SESSION);
        }

        sendBroadcast(responseIntent);
    }

    private boolean performLogin(String username, String password, Intent outIntent){
        String postData = "name="+username+"&code="+password;
        try {
            String response = connectionManager.postToServer(DATA_URL, postData);
            System.out.println(response);
            if (response == null || response.isEmpty() || response.contains("Error:")) {
                outIntent.putExtra(intents.login.answer.PARAM_LOGIN_ERROR,"WRONG_USER_OR_PASSWORD");
                return false;
            }
            else {
                JSONObject jsob = new JSONObject(response);

                String gotUser = jsob.getString("name");
                String gotLab = jsob.getString("lab");

                if (gotUser.contentEquals("") || gotLab.contentEquals("")){
                    outIntent.putExtra(intents.login.answer.PARAM_LOGIN_ERROR,"WRONG_USER_OR_PASSWORD");
                    return false;
                }
                else{
                    outIntent.putExtra(intents.login.answer.PARAM_USERNAME, gotUser);
                    outIntent.putExtra(intents.login.answer.PARAM_LABORATORY, gotLab);
                }

            }
            return true;
        }catch (UnknownHostException e){
            outIntent.putExtra(intents.login.answer.PARAM_LOGIN_ERROR,"UNKNOWN_HOST");
        }catch (IOException e){
            outIntent.putExtra(intents.login.answer.PARAM_LOGIN_ERROR,"UNKNOWN_IO_EXCEPTION");
        }catch (Exception e) {
            outIntent.putExtra(intents.login.answer.PARAM_LOGIN_ERROR,"UNKNOWN_EXCEPTION");
        }
        return false;
    }


}
