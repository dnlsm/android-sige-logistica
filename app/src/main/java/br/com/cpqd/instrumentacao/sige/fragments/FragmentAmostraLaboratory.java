package br.com.cpqd.instrumentacao.sige.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;

import br.com.cpqd.instrumentacao.sige.R;
import br.com.cpqd.instrumentacao.sige.activities.LoadingActivity;
import br.com.cpqd.instrumentacao.sige.models.Item;

public class FragmentAmostraLaboratory extends Fragment implements Serializable{

    private TextView itemTitle;
    private TextView itemProtocol;
    private TextView itemCode;
    private TextView itemLocation;
    private Spinner itemSpinner;
    private CheckBox itemCheckBox;
    private EditText itemDeliveryDate;
    private Button itemConfirmButton;
    private String itemDestinySelected = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_button_amostras_laboratory, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemTitle = (TextView) view.findViewById(R.id.itemTitleAmostraLaboratory);
        itemCode = (TextView) view.findViewById(R.id.itemCodeAmostraLaboratory);
        itemProtocol = (TextView) view.findViewById(R.id.itemProtocolAmostraLaboratory);
        itemLocation = (TextView) view.findViewById(R.id.itemAtualLocationAmostraLaboratory);

        itemSpinner = (Spinner) view.findViewById(R.id.spinnerAmostrasLaboratory);
        itemCheckBox = (CheckBox) view.findViewById(R.id.checkBoxForLogistic);
        itemDeliveryDate = (EditText) view.findViewById(R.id.amostraDataToDeliveryAmostraLaboratory);
        itemConfirmButton = (Button) view.findViewById(R.id.confirmButtonAmostrasLaboratory);

        Bundle parameters = getArguments();
        if(parameters == null){

        }else{
            fillAndSet(parameters);
        }
    }

    private void fillAndSet(Bundle parameters) {

        Item item = (Item) parameters.getSerializable("amostra");

        itemTitle.setText(item.getName());
        itemCode.setText(String.valueOf(item.getCode()));
        itemProtocol.setText(String.valueOf(item.getProtocol()));
        itemLocation.setText(item.getLast_place());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner_laboratory_amostras, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout_dropdown);
        itemSpinner.setAdapter(adapter);

        itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemDestinySelected = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                itemDestinySelected = (String) adapterView.getItemAtPosition(0);
            }
        });

        itemConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemCheckBox.isChecked()){
                    if(itemDestinySelected.isEmpty()){
                        System.out.println("não selecionado o destino");
                        return;
                    }else{
                        String amostraDeliveryDate = FragmentAmostraLaboratory.this.itemDeliveryDate.getText().toString();
                        System.out.println(amostraDeliveryDate + " - " + "vai usar logistica" + " - " + itemDestinySelected);

                        openDialog();
                    }
                }else{
                    if(itemDestinySelected.isEmpty()){
                        System.out.println("não selecionado o destino");
                        return;
                    }else{
                        String amostraDeliveryDate = FragmentAmostraLaboratory.this.itemDeliveryDate.getText().toString();
                        System.out.println(amostraDeliveryDate + " - " + "n vai usar logistica" + " - " + itemDestinySelected);
                    }
                }
            }
        });

    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage(R.string.dialog_message_liberar_lab_amostra).setTitle(R.string.dialog_title_confirmacao);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getContext(), LoadingActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //faz nada
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
