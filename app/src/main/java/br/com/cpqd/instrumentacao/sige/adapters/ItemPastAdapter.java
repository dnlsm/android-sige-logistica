package br.com.cpqd.instrumentacao.sige.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import br.com.cpqd.instrumentacao.sige.R;
import br.com.cpqd.instrumentacao.sige.models.Movements;

public class ItemPastAdapter extends BaseAdapter {

    private final Context context;
    private final List<Movements> movements;

    public ItemPastAdapter(Context context, List<Movements> movements) {
        this.context = context;
        this.movements = movements;
    }


    @Override
    public int getCount() {
        return movements.size();
    }

    @Override
    public Object getItem(int position) {
        return movements.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movements movements = this.movements.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;

        if (view == null) {
            System.out.println("VIEW NULL");
        } else {
            System.out.println("VIEW NOT NULL");
        }

        view = inflater.inflate(R.layout.list_item_past, parent, false);
        System.out.println("Inflei o list_item_past.xml");

        TextView itemFrom = (TextView) view.findViewById(R.id.itemFrom);
        TextView itemTo = (TextView) view.findViewById(R.id.itemTo);
        TextView itemDataFrom = (TextView) view.findViewById(R.id.dataFrom);
        TextView itemDataTo = (TextView) view.findViewById(R.id.dataTo);

        itemFrom.setText(movements.getFrom());
        itemTo.setText(movements.getTo());

        if(movements.getDelivery() == null){

        }else{
            itemDataFrom.setText(formatDate(movements.getDelivery()));
        }

        if(movements.getReceipt() == null){

        }else{
            itemDataTo.setText(formatDate(movements.getReceipt()));
        }

        return view;
    }

    private String formatDate(Date date) {

        try {
            /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
            String dateFormated = sdf.format(date);
            Date parsedDate = sdf.parse(dateFormated);  */
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm dd/MM/yy");
            System.out.println(sdf2.format(date));
            sdf2.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
            return sdf2.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
