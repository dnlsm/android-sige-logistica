package br.com.cpqd.instrumentacao.sige;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import br.com.cpqd.instrumentacao.sige.home.HomeActivity;
import br.com.cpqd.instrumentacao.sige.splash.SplashActivity;
import br.com.cpqd.instrumentacao.sige.typedef.intents;

public class LoginBroadcastReceiver extends BroadcastReceiver {

    private IntentFilter intentFilter;


    // Constructors

    public LoginBroadcastReceiver(Context context) {
        this();
        this.registerReceiver(context);
        System.out.println("CREATEDDDDd");
    }

    public LoginBroadcastReceiver(){
        super();

        intentFilter = new IntentFilter();
        intentFilter.addAction(intents.login.answer.ACTION_SUCCESSFUL_LOGIN);
        intentFilter.addAction(intents.login.answer.ACTION_FAILED_LOGIN);
        intentFilter.addCategory(intents.CATEGORY_ANSWER_SESSION);
    }



    // Register and Unregister
    public void registerReceiver(Context context){
        context.registerReceiver(this, this.intentFilter);
    }

    public void unregisterReceiver(Context context){
        context.unregisterReceiver(this);
    }



    // getters and setters
    public IntentFilter getIntentFilter() {
        return intentFilter;
    }



    // Handles Broadcast Intents
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        switch(action){
            case intents.login.answer.ACTION_SUCCESSFUL_LOGIN:
                onLoginSuccessful(intent);
                break;
            case intents.login.answer.ACTION_FAILED_LOGIN:
                onLoginFailed(intent);
                break;
            default:
                onUnknownMessage(intent);
        }
    }


    // Event Functions
    public void onLoginSuccessful(Intent intent){
        System.out.println("LBR!");
    };

    public void onLoginFailed(Intent intent){
        System.out.println("LBR!");
    };

    public void onUnknownMessage(Intent intent){
        System.out.println("LBR!");
    }
}