package com.example.tokoben;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList item_namelist, item_pricelist, item_stocklist, item_idlist;
    LayoutInflater inflater;

    public CustomAdapter(Context c , ArrayList item_namelist, ArrayList item_pricelist, ArrayList item_stocklist, ArrayList item_idlist) {

    this.item_namelist =item_namelist;
    this.item_pricelist = item_pricelist;
    this.item_stocklist = item_stocklist;
    this.item_idlist = item_idlist;
    inflater = LayoutInflater.from(c);
    }


    @Override
    public int getCount() {
        return item_idlist.size();
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
        view = inflater.inflate(R.layout.home_lvelement, null);
        TextView tvname = (TextView)view.findViewById(R.id.textnama);
        TextView tvharga =(TextView) view.findViewById(R.id.textharga);
        TextView tvstock =(TextView) view.findViewById(R.id.textstock);

        tvname.setText(""+item_namelist.get(i));
        tvharga.setText("Rp. "+item_pricelist.get(i)+".000");
        tvstock.setText("Remaining: "+item_stocklist.get(i));

        return view;
    }
}
