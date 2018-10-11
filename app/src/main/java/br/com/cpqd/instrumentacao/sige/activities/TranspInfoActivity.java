package br.com.cpqd.instrumentacao.sige.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

import br.com.cpqd.instrumentacao.sige.R;
import br.com.cpqd.instrumentacao.sige.fragments.TranspInfoFragment;
import br.com.cpqd.instrumentacao.sige.models.Transportation;

public class TranspInfoActivity extends AppCompatActivity implements Serializable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_input);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();

        TranspInfoFragment fragment = new TranspInfoFragment();

        Transportation transportation = (Transportation) getIntent().getSerializableExtra("transportation");

        Bundle parameters = new Bundle();
        parameters.putSerializable("transportation", transportation);

        fragment.setArguments(parameters);

        tx.replace(R.id.fragment, fragment);

        tx.commit();
    }
}
