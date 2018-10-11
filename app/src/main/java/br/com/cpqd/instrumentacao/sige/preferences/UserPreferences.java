package br.com.cpqd.instrumentacao.sige.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

public class UserPreferences implements Serializable{
    private static final String ALUNOS_PREFERENCES = "br.com.cpqd.instrumentacao.sige.preferences";
    private static final String USER_TOKEN = "user_token";
    public static final String TOKEN_NOT_FOUND = "token not found";
    private Context context;

    public UserPreferences(Context context) {
        this.context = context;
    }

    public void SaveToken(String token){
        SharedPreferences preferences = getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_TOKEN, token);
        editor.commit();

    }

    private SharedPreferences getSharedPreferences() {
        return this.context.getSharedPreferences(ALUNOS_PREFERENCES, this.context.MODE_PRIVATE);
    }

    public String getUserToken() {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getString(USER_TOKEN, TOKEN_NOT_FOUND);
    }
}
