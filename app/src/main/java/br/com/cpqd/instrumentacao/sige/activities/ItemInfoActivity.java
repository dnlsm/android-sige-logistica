package br.com.cpqd.instrumentacao.sige.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

import br.com.cpqd.instrumentacao.sige.R;
import br.com.cpqd.instrumentacao.sige.fragments.ItemInfoFragment;
import br.com.cpqd.instrumentacao.sige.models.Item;
import br.com.cpqd.instrumentacao.sige.models.Movements;
import br.com.cpqd.instrumentacao.sige.sync.DataSync;

public class ItemInfoActivity extends AppCompatActivity {

    private List<Movements> itemMovements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_input);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();

        final ItemInfoFragment fragment = new ItemInfoFragment();

        final Item item = (Item) getIntent().getSerializableExtra("item");

        final DataSync dataSync = new DataSync(this, this);
        dataSync.findItemMovements(item.getCode());

        final Bundle parameters = new Bundle();
        parameters.putSerializable("item", item);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(dataSync.getStatus_code() == 200){
                    System.out.println("PEGANDO A LISTAAAAAAAAAA");
                    itemMovements = dataSync.getMovements();
                    parameters.putSerializable("movements", (Serializable) itemMovements);
                    fragment.setArguments(parameters);

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction tx = fragmentManager.beginTransaction();
                    tx.replace(R.id.fragment, fragment);

                    tx.commit();
                }else{
                    Intent intent = new Intent(ItemInfoActivity.this, SplashActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1000);
/*
        final ItemInfoFragment fragment = new ItemInfoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();
        fragment.setUserVisibleHint(true);
        tx.add(fragment, "info_fragment");
        tx.commit();

        tx.replace(R.id.fragment, fragment);

        tx.commit();*/

    }
}
