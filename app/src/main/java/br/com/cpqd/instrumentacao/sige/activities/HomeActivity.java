package br.com.cpqd.instrumentacao.sige.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;

import br.com.cpqd.instrumentacao.sige.R;
import br.com.cpqd.instrumentacao.sige.adapters.HomeAdapter;
import br.com.cpqd.instrumentacao.sige.models.User;
import br.com.cpqd.instrumentacao.sige.models.UserCategory;
import br.com.cpqd.instrumentacao.sige.preferences.UserPreferences;
import br.com.cpqd.instrumentacao.sige.sync.DataSync;

public class HomeActivity extends AppCompatActivity implements Serializable{

    private DataSync dataSync;
    private RecyclerView recyclerView;
    private String userInfoString;
    private String labInfoString;
    private transient TextView userInfo;
    private transient TextView labInfo;
    private transient Handler handler = new Handler();
    private UserCategory userCategory;

    public RecyclerView getRecyclerView(){
        return this.recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    /*    System.out.println("LABORATÓRIO" + activityIntent.getStringExtra(intents.login.answer.PARAM_LABORATORY));
        switch (activityIntent.getStringExtra(intents.login.answer.PARAM_LABORATORY)) {
            case "log":
                menu.findItem(R.id.logOption1).setVisible(true);
                break;
            default:
                menu.findItem(R.id.labOption1).setVisible(true);

        } */
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Intent intent = getIntent();
        this.dataSync = (DataSync) intent.getSerializableExtra("dataSync");
        this.userInfoString = dataSync.getUserLogin();
        this.labInfoString = dataSync.getUserPlace();
        this.userCategory = dataSync.getUserCategory();

        this.dataSync = new DataSync(this, this);
        this.dataSync.fillAndSetAdapter(); //carrega todas as listas

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager gridVertical = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridVertical);
        registerForContextMenu(recyclerView);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);

        NavigationView navigationView1 = (NavigationView) findViewById(R.id.navigationView);
        LinearLayout header = (LinearLayout) navigationView1.getHeaderView(0);
        userInfo = (TextView) header.findViewById(R.id.userInfoView);
        labInfo = (TextView) header.findViewById(R.id.labInfoView);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                userInfo.setText(userInfoString);
                labInfo.setText(labInfoString);
            }
        }, 1000);



        Menu menu = navigationView1.getMenu();
        MenuItem menuItem;
        menuItem = (MenuItem) menu.findItem(R.id.logOption1);
        menuItem.setVisible(true);

        MenuItem menuItemExit = menu.findItem(R.id.exitOption);
        menuItemExit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(HomeActivity.this, LoadingActivity.class);
                UserPreferences preferences = new UserPreferences(HomeActivity.this);
                preferences.SaveToken("");
                startActivity(intent);
                return true;
            }
        });

        //####OLD
    /*    switch(labInfoString){
            case "log":
                menuItem = (MenuItem)menu.findItem(R.id.logOption1);
                menuItem.setVisible(true);
                break;
            default:
                menuItem = (MenuItem) menu.findItem(R.id.labOption1);
                menuItem.setVisible(true);

        }*/

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void sairBTN_OnClicked(View view) {
    }

    public void verBTN_OnClicked(View view) {
    }

    public void cadastBTN_OnClicked(View view) {
    }

    public void transpBTN_OnClicked(View view) {
    }

    public void doTransport(View view){

    }

    public void doRetirada(View view){

    }

    public String getUserInfoString(){
        return this.userInfoString;
    }

    public String getLabInfoString(){
        return this.labInfoString;
    }

    public UserCategory getUserCategory() {
        return this.userCategory;
    }

    public void setSearchButton(final HomeAdapter adapter) {

        ImageButton searchButton = (ImageButton) findViewById(R.id.search_icon_home);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("clicou");
                getRecyclerView().setAdapter(adapter);
            }
        });

    }
}
