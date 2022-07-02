package com.example.tokoben;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;



public class transactionsCustomAdapter extends BaseAdapter {
    Context c;
    ArrayList transactionid_list, itemprice_list, item_amount_list, status_list;
    LayoutInflater inflater;

    public transactionsCustomAdapter(Context c, ArrayList transactionid_list, ArrayList itemprice_list, ArrayList item_amount_list, ArrayList status_list) {

        this.transactionid_list = transactionid_list;
        this.itemprice_list = itemprice_list;
        this.item_amount_list= item_amount_list;
        this.status_list = status_list;
        inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return transactionid_list.size();
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
        Log.d("tca", "yo");
        view = inflater.inflate(R.layout.transactions_lvelement, null);
        TextView tvprid = (TextView) view.findViewById(R.id.text_totalitemprice);
        TextView tvamid = (TextView) view.findViewById(R.id.text_totalitemamount);
        TextView tvstatus = (TextView) view.findViewById(R.id.text_status);

        tvprid.setText("Rp. "+itemprice_list.get(i)+".000");
        tvamid.setText("Total Items : "+item_amount_list.get(i));
        tvstatus.setText("Status: "+status_list.get(i));
        Log.d("tca", "yo");
        return view;
    }
}
