package br.com.cpqd.instrumentacao.sige.home;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.cpqd.instrumentacao.sige.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


import java.util.ArrayList;

import static br.com.cpqd.instrumentacao.sige.R.*;
import static br.com.cpqd.instrumentacao.sige.R.layout.*;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{

    private ArrayList<JSONObject>    mDataSet;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CardView layout;
        public TextView name;
        public TextView process;
        public TextView lab;
        public TextView sampleId;


        public ViewHolder(CardView itemView){
            super(itemView);
            this.layout = itemView;
            name = (TextView) itemView.findViewById(R.id.menuItemName);
            process = (TextView) itemView.findViewById(R.id.menuItemProcess);
            lab = (TextView) itemView.findViewById(id.menuItemLab);
            sampleId = (TextView) itemView.findViewById(id.menuItemCode);
        }
    }

    public MenuAdapter(ArrayList<JSONObject> dataSet){
        this.mDataSet = dataSet;
    }


    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);
        ViewHolder  vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JSONObject obj = mDataSet.get(position);
        try {

            String sampleId = obj.getString("id");
            holder.sampleId.setText(sampleId);
            String process = obj.getString("process");
            holder.process.setText(process);
            String name = obj.getString("name");
            holder.name.setText(name);
            String lab = obj.getString("laboratory");
            holder.lab.setText(lab);
            System.out.println("NAME:"+name+"\nPROCESS:"+process);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


}
