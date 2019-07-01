package com.example.tracker;

import java.util.List;

public class ItemList {
    private List lista;
    private static  ItemList miItemList;

    public  static ItemList getItemList() {
        if (miItemList==null) {
            miItemList= new ItemList();
        }
        return miItemList;
    }

    private ItemList(){
        this.lista= null;

    }

    public List getLista() {
        return lista;
    }

    public void setLista(List lista) {
        this.lista = lista;
    }
}
