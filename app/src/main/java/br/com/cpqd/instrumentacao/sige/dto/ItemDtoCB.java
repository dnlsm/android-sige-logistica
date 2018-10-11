package br.com.cpqd.instrumentacao.sige.dto;

import java.io.Serializable;

import br.com.cpqd.instrumentacao.sige.models.Item;

public class ItemDtoCB implements Serializable{

    private Item item;
    private boolean check;

    public ItemDtoCB(Item item, boolean check){
        this.item = item;
        this.check = check;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
