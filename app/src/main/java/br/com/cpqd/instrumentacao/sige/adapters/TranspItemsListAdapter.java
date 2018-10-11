package br.com.cpqd.instrumentacao.sige.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.com.cpqd.instrumentacao.sige.R;
import br.com.cpqd.instrumentacao.sige.models.Item;

public class TranspItemsListAdapter extends BaseAdapter {
    private Context context;
    private List<Item> items;

    public TranspItemsListAdapter(Context context, List<Item> items) {
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
        View view = convertView;

        Item item = items.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.list_transport_itens, parent, false);

        TextView transpItemName = (TextView) view.findViewById(R.id.transpItemName);
        TextView transpItemCode = (TextView) view.findViewById(R.id.transpItemCod);

        transpItemName.setText(item.getName());
        transpItemCode.setText(String.valueOf(item.getCode()));

        return view;
    }
}
