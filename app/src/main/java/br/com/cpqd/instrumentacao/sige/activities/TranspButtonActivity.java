package br.com.cpqd.instrumentacao.sige.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.cpqd.instrumentacao.sige.R;
import br.com.cpqd.instrumentacao.sige.adapters.TranspListCheckBoxAdapter;
import br.com.cpqd.instrumentacao.sige.dto.ItemDtoCB;
import br.com.cpqd.instrumentacao.sige.models.Item;
import br.com.cpqd.instrumentacao.sige.models.Transportation;
import br.com.cpqd.instrumentacao.sige.models.UserCategory;

public class TranspButtonActivity extends AppCompatActivity {

    private Transportation transportation;
    private TextView from;
    private TextView to;
    private ImageButton confirmButton;
    private ImageButton selectAllButton;
    private ListView itemsList;
    private UserCategory userCategory;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fragment_button_transp);

        Intent intent = getIntent();

        transportation = (Transportation) intent.getSerializableExtra("transportation");
        userCategory = (UserCategory) intent.getSerializableExtra("user_category");

        from = (TextView) findViewById(R.id.transportFromTitleAfterButtonClick);
        to = (TextView) findViewById(R.id.transportToTitleAfterButtonClick);
        confirmButton = (ImageButton) findViewById(R.id.confirmTransportationButton);
        selectAllButton = (ImageButton) findViewById(R.id.selectAllItemsTranspList);
        itemsList = (ListView) findViewById(R.id.checkBoxList);

        fillLayout();

    }

    private void fillLayout() {

        final List<ItemDtoCB> itemsSelected = new ArrayList<>();


        from.setText(transportation.getFrom());
        to.setText(transportation.getTo());

        final List<ItemDtoCB> dto = new ArrayList<>();

        for (Item item :
                transportation.getItems()) {
            dto.add(new ItemDtoCB(item, false));
        }

        final TranspListCheckBoxAdapter adapter = new TranspListCheckBoxAdapter(this, dto);
        itemsList.setAdapter(adapter);

        itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ItemDtoCB item = (ItemDtoCB) adapterView.getAdapter().getItem(position);
                checkIfItemIsChecked(item, itemsSelected);
                adapter.notifyDataSetChanged();
                setConfirmImage(itemsSelected, dto);
            }
        });


        selectAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (ItemDtoCB item :
                        dto) {
                    checkIfItemIsChecked(item, itemsSelected);
                }
                adapter.notifyDataSetChanged();
                setConfirmImage(itemsSelected, dto);

            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (ItemDtoCB item :
                        itemsSelected) {
                    System.out.println(item.getItem().getName());
                }

                openDialog();
            }
        });

    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.dialog_message_finalizar_transp).setTitle(R.string.dialog_title_confirmacao);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(TranspButtonActivity.this, LoadingActivity.class);
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

    private void checkIfThereIsSomeChecked(List<ItemDtoCB> dto) {
        for (ItemDtoCB item :
                dto) {
            if (item.isCheck()) {
                confirmButton.setImageResource(R.drawable.confirm_partial_24dp);
                confirmButton.setVisibility(View.VISIBLE);
                break;
            } else {
                confirmButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void checkIfItemIsChecked(ItemDtoCB item, List<ItemDtoCB> itemsSelected) {
        if (item.isCheck()) {
            item.setCheck(false);
            itemsSelected.remove(item);
        } else {
            item.setCheck(true);
            itemsSelected.add(item);
        }
    }

    private void setConfirmImage(List<ItemDtoCB> itemsSelected, List<ItemDtoCB> dto) {
        if (itemsSelected.size() == dto.size()) {
            confirmButton.setImageResource(R.drawable.confirm_all_24dp);
            confirmButton.setVisibility(View.VISIBLE);
        } else {
            checkIfThereIsSomeChecked(dto);
        }
    }
}
