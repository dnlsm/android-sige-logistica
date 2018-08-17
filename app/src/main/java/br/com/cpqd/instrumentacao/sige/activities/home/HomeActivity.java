package br.com.cpqd.instrumentacao.sige.activities.home;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import br.com.cpqd.instrumentacao.sige.R;
import br.com.cpqd.instrumentacao.sige.net.HTTPConnector;
import br.com.cpqd.instrumentacao.sige.typedef.intents;

public class HomeActivity extends Activity {

    Intent activityIntent;
    private RecyclerView recyclerView;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        System.out.println("LABORATÓRIO" + activityIntent.getStringExtra(intents.login.answer.PARAM_LABORATORY));
        switch(activityIntent.getStringExtra(intents.login.answer.PARAM_LABORATORY)){
            case "log":
                menu.findItem(R.id.logOption1).setVisible(true);
                break;
            default:
                menu.findItem(R.id.labOption1).setVisible(true);

        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        this.getIntent();
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager gridVertical = new GridLayoutManager(this,2, GridLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(gridVertical);

        activityIntent = this.getIntent();
        String userInfoString = activityIntent.getStringExtra(intents.login.answer.PARAM_USERNAME);
        String labInfoString  = activityIntent.getStringExtra(intents.login.answer.PARAM_LABORATORY);



        MainPageDownloadTask mainPageDownloadTask = new MainPageDownloadTask();
        mainPageDownloadTask.execute(labInfoString);


        /*recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });*/

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);


        NavigationView navigationView1 = (NavigationView) findViewById(R.id.navigationView);
        LinearLayout header = (LinearLayout) navigationView1.getHeaderView(0);
        TextView userInfo = (TextView) header.findViewById(R.id.userInfoView);
        TextView labInfo  = (TextView) header.findViewById(R.id.labInfoView);


        userInfo.setText(userInfoString);
        labInfo.setText(labInfoString);

        Menu menu = navigationView1.getMenu();

        MenuItem menuItem;
        switch(labInfoString){
            case "log":
                menuItem = (MenuItem)menu.findItem(R.id.logOption1);
                menuItem.setVisible(true);
                break;
            default:
                menuItem = (MenuItem) menu.findItem(R.id.labOption1);
                menuItem.setVisible(true);

        }
    }

    public void sairBTN_OnClicked(View view) {
    }

    public void verBTN_OnClicked(View view) {
    }

    public void cadastBTN_OnClicked(View view) {
    }

    public void transpBTN_OnClicked(View view) {
    }


    private class MainPageDownloadTask extends AsyncTask<String, Void, MenuAdapter>{

        @Override
        protected MenuAdapter doInBackground(String... strings) {
            try {
                HTTPConnector connectionManager = new HTTPConnector();
                String sampleListString;
                sampleListString = connectionManager
                        .postToServer(
                                "https://sige.cpqd.com.br/integration/list_samples.php",
                                "lab=" + strings[0]);
                System.out.println(sampleListString);
                JSONArray jsonArray = new JSONArray(sampleListString);


                ArrayList<JSONObject> dataSet = new ArrayList<>();
                for (int i=0; i < jsonArray.length(); i++){
                    JSONObject jsonObject =  (JSONObject) jsonArray.get(i);

                    dataSet.add(jsonObject);
                }
                MenuAdapter menuAdapter = new MenuAdapter(dataSet);
                return menuAdapter;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(MenuAdapter menuAdapter) {
            super.onPostExecute(menuAdapter);
            recyclerView.setAdapter(menuAdapter);

        }
    }


}
