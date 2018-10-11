package br.com.cpqd.instrumentacao.sige.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.cpqd.instrumentacao.sige.R;
import br.com.cpqd.instrumentacao.sige.activities.TranspButtonActivity;
import br.com.cpqd.instrumentacao.sige.dto.ItemDtoCB;
import br.com.cpqd.instrumentacao.sige.models.Item;

public class TranspListCheckBoxAdapter extends BaseAdapter {

    private final Context context;
    private final List<ItemDtoCB> items;

    public TranspListCheckBoxAdapter(Context context, List<ItemDtoCB> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ItemDtoCB item = items.get(position);

        View view = convertView;

        LayoutInflater inflater = LayoutInflater.from(context);

        view = inflater.inflate(R.layout.transport_items_list, parent, false);

        TextView itemName = (TextView) view.findViewById(R.id.itemNameTranspListItem);
        TextView itemCode = (TextView) view.findViewById(R.id.itemCodeTranspListItem);
        TextView itemProtocol = (TextView) view.findViewById(R.id.itemProtocolTranspListItem);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBoxItemTransportationList);

        itemName.setText(item.getItem().getName());
        itemCode.setText(String.valueOf(item.getItem().getCode()));
        itemProtocol.setText(String.valueOf(item.getItem().getProtocol()));
        checkBox.setChecked(item.isCheck());

        return view;
    }

}
