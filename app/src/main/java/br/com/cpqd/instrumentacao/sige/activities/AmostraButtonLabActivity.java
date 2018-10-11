package br.com.cpqd.instrumentacao.sige.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

import br.com.cpqd.instrumentacao.sige.R;
import br.com.cpqd.instrumentacao.sige.fragments.FragmentAmostraLaboratory;
import br.com.cpqd.instrumentacao.sige.models.Item;

public class AmostraButtonLabActivity extends AppCompatActivity implements Serializable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_amostra_button_laboratory);

        Item item = (Item) getIntent().getSerializableExtra("amostra");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();

        FragmentAmostraLaboratory fragment = new FragmentAmostraLaboratory();

        Bundle parameters = new Bundle();
        parameters.putSerializable("amostra", item);

        fragment.setArguments(parameters);

        tx.replace(R.id.frameLayoutToFragment, fragment);
        tx.commit();
    }
}
