package br.com.cpqd.instrumentacao.sige.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import br.com.cpqd.instrumentacao.sige.R;
import br.com.cpqd.instrumentacao.sige.adapters.TranspItemsListAdapter;
import br.com.cpqd.instrumentacao.sige.models.Transportation;

public class TranspInfoFragment extends Fragment implements Serializable{

    private TextView from;
    private TextView to;
    private TextView transpCode;
    private TextView transpProtocol;
    private TextView transpCreatedDate;
    private ListView transpItemsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transport_infos, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        from = (TextView) view.findViewById(R.id.transportFromTitle);
        to = (TextView) view.findViewById(R.id.transportToTitle);
        transpCode = (TextView) view.findViewById(R.id.transportCode);
        transpProtocol = (TextView) view.findViewById(R.id.transpProtocol);
        transpCreatedDate = (TextView) view.findViewById(R.id.transpCreationDate);

        transpItemsList = (ListView) view.findViewById(R.id.list_transport_itens);


        Bundle parameters = getArguments();
        if(parameters == null){}
        else{
            System.out.println("INDO FILLAR OS CAMPÕES");
            fillFields(parameters);
        }

    }

    private void fillFields(Bundle parameters) {
        Transportation transportation = (Transportation) parameters.getSerializable("transportation");

        from.setText(transportation.getFrom());
        to.setText(transportation.getTo());
        transpCode.setText(String.valueOf(transportation.getCode()));
        transpProtocol.setText(String.valueOf(transportation.getProtocol()));
        transpCreatedDate.setText(handleDate(transportation));

        transpItemsList.setAdapter(new TranspItemsListAdapter(getContext(), transportation.getItems()));

    }

    private String handleDate(Transportation transportation) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm - dd/MM/yy");

        return sdf.format(transportation.getCreated_date());
    }
}
