package br.com.cpqd.instrumentacao.sige.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.cpqd.instrumentacao.sige.R;
import br.com.cpqd.instrumentacao.sige.activities.AmostraButtonLabActivity;
import br.com.cpqd.instrumentacao.sige.activities.HomeActivity;
import br.com.cpqd.instrumentacao.sige.activities.ItemInfoActivity;
import br.com.cpqd.instrumentacao.sige.activities.LoadingActivity;
import br.com.cpqd.instrumentacao.sige.activities.TranspButtonActivity;
import br.com.cpqd.instrumentacao.sige.activities.TranspInfoActivity;
import br.com.cpqd.instrumentacao.sige.models.Item;
import br.com.cpqd.instrumentacao.sige.models.Movements;
import br.com.cpqd.instrumentacao.sige.models.Transportation;
import br.com.cpqd.instrumentacao.sige.models.UserCategory;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import static br.com.cpqd.instrumentacao.sige.R.*;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private final HomeActivity homeActivity;
    private List<Object> objectsList;
    private Context context;
    private transient ImageButton amostraInfo;
    private transient List<Movements> itemMovements;
    private Item item;
    private Transportation transportation;

    public HomeAdapter(List<Object> objectsList, Context context, HomeActivity activity) {
        this.homeActivity = activity;
        this.objectsList = objectsList;
        this.context = context;


        //loop para retirar os transportes que o usuário não está envolvido e as amostras que não estão no local do usuário
        for (Iterator<Object> objects = objectsList.iterator(); objects.hasNext(); ) {
            Object object = objects.next();

            if (object.getClass() == Item.class) {
                Item item = (Item) object;
                if (!(item.getLast_place().equals(homeActivity.getLabInfoString()))) {
                    objects.remove();
                }
            } else if (object.getClass() == Transportation.class) {
                Transportation transp = (Transportation) object;
                if (!(transp.getTo().equals(homeActivity.getLabInfoString())) && !(transp.getFrom().equals(homeActivity.getLabInfoString()))) {
                    objects.remove();
                }
            }
        }
    }

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int i = 1;
        System.out.println("PASSOU AQUI " + i + " VEZ(ES)");
        i++;
        CardView v = (CardView) LayoutInflater.from(context)
                .inflate(R.layout.home_screen_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object object = objectsList.get(position);
        item = new Item();
        transportation = new Transportation();
        try {
            if (object.getClass() == Item.class) {
                item = (Item) object;
                String type = item.getType();
                System.out.println("'" + type + "'");

                String sampleId = String.valueOf(item.getCode());
                String process = String.valueOf(item.getProtocol());
                String name = item.getName();
                String lab = item.getLast_place();

                holder.loadAmostraItem(sampleId, name, process, lab, item);


            } else if (object.getClass() == Transportation.class) {
                transportation = (Transportation) object;

                String type = transportation.getType();
                System.out.println("'" + type + "'");

                String from = transportation.getFrom();
                String to = transportation.getTo();

                String date = convertDate(transportation);

                String protocol = String.valueOf(transportation.getProtocol());

                holder.loadTranportItem(from, to, date, protocol, transportation);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String convertDate(Transportation transportation) throws ParseException {
        Date date = transportation.getDate();

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy");
        sdf2.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
        System.out.println(sdf2.format(date));
        return sdf2.format(date);
    }

    @Override
    public int getItemCount() {
        if (objectsList == null) {
            return 0;
        } else {
            return objectsList.size();
        }
    }

    public List<Movements> getItemMovements() {
        return itemMovements;
    }

    public List<Object> getObjectsList() {
        return objectsList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private CardView layout;

        public ViewHolder(CardView itemView) {
            super(itemView);
            this.layout = itemView;
        }

        public void loadAmostraItem(String itemID,
                                    String itemName,
                                    String itemProcess,
                                    String itemLab, Item item) {
            this.setViewType("AMOSTRAS", item);

            verifyUserCategoryforAmostras(item);

            ((TextView) layout.findViewById(R.id.amostraName)).setText(itemName);
            ((TextView) layout.findViewById(R.id.amostraProcess)).setText(itemProcess);
            ((TextView) layout.findViewById(R.id.amotraLab)).setText(itemLab);
            ((TextView) layout.findViewById(R.id.amostraCode)).setText(itemID);
        }

        private void verifyUserCategoryforAmostras(final Item item) {
            if (homeActivity.getUserCategory() == UserCategory.ADMINISTRATIVE) {
                ((Button) layout.findViewById(id.amostraButton)).setVisibility(View.INVISIBLE);
            } else if (homeActivity.getUserCategory() == UserCategory.LOGISTIC) {
                Button amostraButton = (Button) layout.findViewById(id.amostraButton);
                amostraButton.setVisibility(View.VISIBLE);
                amostraButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openDialog();
                    }
                });
            } else if (homeActivity.getUserCategory() == UserCategory.LABORATORY) {
                Button amostraButton = (Button) layout.findViewById(id.amostraButton);
                amostraButton.setVisibility(View.VISIBLE);
                amostraButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(homeActivity, AmostraButtonLabActivity.class);
                        intent.putExtra("amostra", item);
                        homeActivity.startActivity(intent);
                    }
                });
            }
        }

        private void openDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity);

            builder.setMessage(R.string.dialog_message_liberar_log_amostra).setTitle(string.dialog_title_confirmacao);

            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(homeActivity, LoadingActivity.class);
                    homeActivity.startActivity(intent);
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

        public void loadTranportItem(String from,
                                     String to,
                                     String date,
                                     String protocol, Transportation transportation) {
            this.setViewType("TRANSPORT", transportation);

            changeTranspBackground(transportation);

            verifyUserCategoryForTransp(transportation);
            ((TextView) layout.findViewById(id.transportItemCode)).setText(String.valueOf(transportation.getCode()));
            ((TextView) layout.findViewById(id.transportItemFrom)).setText(from);
            ((TextView) layout.findViewById(id.transportItemTo)).setText(to);
            ((TextView) layout.findViewById(id.transportItemDate)).setText(date);
            ((TextView) layout.findViewById(id.transportItemProtocol)).setText(protocol);
        }

        private void verifyUserCategoryForTransp(Transportation transportation) {
            if (homeActivity.getUserCategory() == null) {

            } else if (homeActivity.getUserCategory() == UserCategory.LOGISTIC && transportation.getTo().toLowerCase().equals(homeActivity.getLabInfoString().toLowerCase())) {
                ((TextView) layout.findViewById(id.transportationButton)).setText("Receber");
            } else if (homeActivity.getUserCategory() == UserCategory.LOGISTIC && transportation.getFrom().toLowerCase().equals(homeActivity.getLabInfoString().toLowerCase())) {
                ((TextView) layout.findViewById(id.transportationButton)).setText("Entregar");
            } else if (homeActivity.getUserCategory() == UserCategory.LABORATORY && transportation.getTo().toLowerCase().equals(homeActivity.getLabInfoString().toLowerCase())) {
                ((TextView) layout.findViewById(id.transportationButton)).setText("Receber");
            } else if (homeActivity.getUserCategory() == UserCategory.LABORATORY && transportation.getFrom().toLowerCase().equals(homeActivity.getLabInfoString().toLowerCase())) {
                ((TextView) layout.findViewById(id.transportationButton)).setText("Entregar");
            } else if (homeActivity.getUserCategory() == UserCategory.ADMINISTRATIVE) {
                ((Button) layout.findViewById(id.transportationButton)).setVisibility(View.INVISIBLE);
            } else {
                System.out.println("USER CAT: " + homeActivity.getUserCategory() + " place: " + homeActivity.getLabInfoString());
                System.out.println("entrou no else");
            }
        }

        public void setViewType(String type, Object object) {
            switch (type) {
                case "AMOSTRAS":
                    final Item item = (Item) object;

                    amostraInfo = (ImageButton) layout.findViewById(id.amostraInfo);
                    amostraInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(homeActivity, ItemInfoActivity.class);
                            intent.putExtra("item", item);
                            homeActivity.startActivity(intent);
                        }
                    });
                    ((LinearLayout) (layout.findViewById(id.sampleView))).setVisibility(View.VISIBLE);
                    ((LinearLayout) (layout.findViewById(id.transportView))).setVisibility(View.GONE);
                    ((ImageView) (layout.findViewById(id.menuItemImage))).setImageResource(drawable.menu_ico_dut);
                    ((ImageView) (layout.findViewById(id.menuItemImage))).setVisibility(View.VISIBLE);
                    ((ImageView) (layout.findViewById(id.menuItemImage))).setBackgroundResource(color.analogous1_300);
                    break;
                case "TRANSPORT":

                    final Transportation transportation = (Transportation) object;

                    ImageButton transportInfo = (ImageButton) layout.findViewById(id.transportInfo);
                    transportInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(homeActivity, TranspInfoActivity.class);
                            intent.putExtra("transportation", transportation);
                            homeActivity.startActivity(intent);
                        }
                    });

                    Button transportButton = (Button) layout.findViewById(id.transportationButton);
                    transportButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(homeActivity, TranspButtonActivity.class);
                            intent.putExtra("transportation", transportation);
                            intent.putExtra("user_category", homeActivity.getUserCategory());
                            homeActivity.startActivity(intent);
                        }
                    });

                    ((LinearLayout) (layout.findViewById(id.sampleView))).setVisibility(View.GONE);
                    ((LinearLayout) (layout.findViewById(id.transportView))).setVisibility(View.VISIBLE);
                    ((ImageView) (layout.findViewById(id.menuItemImage))).setImageResource(drawable.menu_ico_transportation);
                    ((ImageView) (layout.findViewById(id.menuItemImage))).setVisibility(View.VISIBLE);
                    break;
                default:

            }
        }

        public void changeTranspBackground(Transportation transportation) {

            int cont = 0;

            for (Item it :
                    transportation.getItems()) {
                for (Object object :
                        objectsList) {
                    if (object.getClass() == Item.class) {
                        Item it2 = (Item) object;
                        if (it.getCode() == it2.getCode()) {
                            cont++;
                        }
                    }
                }
            }

            if(cont == 0){
                ((ImageView) (layout.findViewById(id.menuItemImage))).setBackgroundResource(color.red10);
            }else if(cont == transportation.getItems().size()){
                ((ImageView) (layout.findViewById(id.menuItemImage))).setBackgroundResource(color.green10);
            }else if(cont > 0 && cont < transportation.getItems().size()){
                ((ImageView) (layout.findViewById(id.menuItemImage))).setBackgroundResource(color.yellow10);
            }

        }
    }
}
