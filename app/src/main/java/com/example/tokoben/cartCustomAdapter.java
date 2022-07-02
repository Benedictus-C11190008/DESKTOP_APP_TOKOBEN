package com.example.tokoben;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class cartCustomAdapter extends BaseAdapter {
    Context c;
    ArrayList item_namelist_cart, item_idlist_cart;
    ArrayList item_pricelist_cart, item_amountlist_cart, totalpricelist;
    LayoutInflater inflater;

    public cartCustomAdapter(Context c, ArrayList item_namelist_cart, ArrayList item_pricelist_cart, ArrayList item_amountlist_cart, ArrayList item_idlist_cart, ArrayList totalpricelist) {
        Log.d("incca", "yes");
        this.item_namelist_cart = item_namelist_cart;
        this.item_pricelist_cart = item_pricelist_cart;
        this.item_idlist_cart = item_idlist_cart;
        this.item_amountlist_cart = item_amountlist_cart;
        this.totalpricelist = totalpricelist;
        inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return item_idlist_cart.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.cart_lvelement, null);
        TextView tvname_cart = (TextView) view.findViewById(R.id.text_cartitemname);
        TextView tvprice_cart = (TextView) view.findViewById(R.id.text_cartitemprice);
        TextView tvamount_cart = (TextView) view.findViewById(R.id.text_cartitemamount);
        TextView tvtotalitem_cart = (TextView) view.findViewById(R.id.text_carttotalitemprice);

        tvname_cart.setText(""+item_namelist_cart.get(i));
        tvprice_cart.setText("Rp. "+item_pricelist_cart.get(i)+".000");
        tvamount_cart.setText("x "+item_amountlist_cart.get(i));
        tvtotalitem_cart.setText("Rp. "+totalpricelist.get(i)+".000");

        return view;
    }

}
