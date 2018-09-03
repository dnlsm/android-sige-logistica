package br.com.cpqd.instrumentacao.sige.activities.home;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.cpqd.instrumentacao.sige.R;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

import static br.com.cpqd.instrumentacao.sige.R.*;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{

    private ArrayList<JSONObject>    mDataSet;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView layout;

        public ViewHolder(CardView itemView){
            super(itemView);
            this.layout = itemView;
        }

        public void loadSampleItem(String itemID,
                                   String itemName,
                                   String itemProcess,
                                   String itemLab){
            this.setViewType("SAMPLE");

            ((TextView) layout.findViewById(R.id.sampleItemName)).setText(itemName);
            ((TextView) layout.findViewById(R.id.sampleItemProcess)).setText(itemProcess);
            ((TextView) layout.findViewById(R.id.sampleItemLab)).setText(itemLab);
            ((TextView) layout.findViewById(R.id.sampleItemCode)).setText(itemID);
        }

        public void loadTranportItem(String from,
                                     String to,
                                     String date,
                                     String protocol){
            this.setViewType("TRANSPORT");

            ((TextView) layout.findViewById(id.transportItemFrom)).setText(from);
            ((TextView) layout.findViewById(id.transportItemTo)).setText(to);
            ((TextView) layout.findViewById(id.transportItemDate)).setText(date);
            ((TextView) layout.findViewById(id.transportItemProtocol)).setText(protocol);
        }

        public void setViewType(String type){
            switch(type){
                case "SAMPLE":
                    ((LinearLayout)(layout.findViewById(id.sampleView))).setVisibility(View.VISIBLE);
                    ((LinearLayout)(layout.findViewById(id.transportView))).setVisibility(View.GONE);
                    ((ImageView)(layout.findViewById(id.menuItemImage))).setImageResource(drawable.menu_ico_dut);
                    ((ImageView)(layout.findViewById(id.menuItemImage))).setBackgroundResource(color.analogous1_300);
                    break;
                case "TRANSPORT":
                    ((LinearLayout)(layout.findViewById(id.sampleView))).setVisibility(View.GONE);
                    ((LinearLayout)(layout.findViewById(id.transportView))).setVisibility(View.VISIBLE);
                    ((ImageView)(layout.findViewById(id.menuItemImage))).setImageResource(drawable.menu_ico_transportation);
                    ((ImageView)(layout.findViewById(id.menuItemImage))).setBackgroundResource(color.analogous2_300);
                    break;
                default:

            }
        }
    }

    public MenuAdapter(ArrayList<JSONObject> dataSet){
        this.mDataSet = dataSet;
    }


    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_screen_item, parent, false);
        ViewHolder  vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JSONObject obj = mDataSet.get(position);
        try {
            String type = obj.getString("type");
            System.out.println("'"+type+"'");
            if(type.equals("sample")){

                String sampleId = obj.getString("id");
                String process = obj.getString("process");
                String name = obj.getString("name");
                String lab = obj.getString("laboratory");

                holder.loadSampleItem(sampleId,name, process,lab);

            }
            else if(type.equals("transport")){

                String from = obj.getString("from");
                String to = obj.getString("to");
                String date = obj.getString("date");
                String protocol = obj.getString("protocol");

                holder.loadTranportItem(from,to,date,protocol);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


}
