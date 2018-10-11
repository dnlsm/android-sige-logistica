package br.com.cpqd.instrumentacao.sige.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.cpqd.instrumentacao.sige.R;
import br.com.cpqd.instrumentacao.sige.adapters.ItemPastAdapter;
import br.com.cpqd.instrumentacao.sige.models.Item;
import br.com.cpqd.instrumentacao.sige.models.Movements;

public class ItemInfoFragment extends Fragment implements Serializable {

    private ListView itensPastList;
    private TextView itemLocation;
    private TextView itemProtocol;
    private TextView itemCode;
    private TextView itemName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item_infos, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemName = (TextView) view.findViewById(R.id.itemTitle);

        itemCode = (TextView) view.findViewById(R.id.itemCode);
        itemProtocol = (TextView) view.findViewById(R.id.itemProtocol);
        itemLocation = (TextView) view.findViewById(R.id.itemAtualLocation);

        itensPastList = (ListView) view.findViewById(R.id.list_itens_past);

        Bundle parameters = getArguments();
        if (parameters == null) {
        } else {
            fillFields(parameters);
        }


        System.out.println("VIEW CREATED DO FRAGMENT");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("ON RESUME DO FRAGMENT");
    }

    private void fillFields(Bundle parameters) {

        Item item = (Item) parameters.getSerializable("item");
        List<Movements> movements = (List<Movements>) parameters.getSerializable("movements");

        itemName.setText(item.getName());

        itemCode.setText(String.valueOf(item.getCode()));
        itemProtocol.setText(String.valueOf(item.getProtocol()));
        itemLocation.setText(item.getLast_place());

        Collections.sort(movements, new Comparator<Movements>() {
            @Override
            public int compare(Movements t1, Movements t2) {
                if (t1.getDelivery() == null || t2.getDelivery() == null) {
                    return 0;
                } else if (t1.getDelivery() == t2.getDelivery()) {
                    return 0;
                } else {
                    return t2.getDelivery().compareTo(t1.getDelivery());
                }
            }
        });

        ItemPastAdapter adapter = new ItemPastAdapter(getContext(), movements);
        itensPastList.setAdapter(adapter);
        System.out.println("Terminou de setar o adapter do histórico");

        itensPastList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("CLICOU NO ITEM DA LIST VIEW");
            }
        });

    }

}
