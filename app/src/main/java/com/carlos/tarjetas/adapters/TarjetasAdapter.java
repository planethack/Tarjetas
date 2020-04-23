package com.carlos.tarjetas.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carlos.tarjetas.NuevoActivity;
import com.carlos.tarjetas.R;
import com.carlos.tarjetas.models.*;

import java.util.ArrayList;

public class TarjetasAdapter extends BaseAdapter {
    private final Context context;
    private TarjetaModel model;
    private ArrayList<TarjetaModel> list;

    public TarjetasAdapter(Context context, ArrayList<TarjetaModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.item_tarjetas, viewGroup, false);
        }
        TextView txNumero = itemView.findViewById(R.id.txNumero);
        TextView txFVencimiento = itemView.findViewById(R.id.txFVencimiento);
        TextView txFranquicia = itemView.findViewById(R.id.txFranquicia);
        ImageView imgFranquicia = itemView.findViewById(R.id.imgFranquicia);
        model = list.get(i);
        txNumero.setText(model.getNumero().toString());
        txFVencimiento.setText(model.getMes().toString() + "/" + model.getAnio());
        txFranquicia.setText(model.getFranquicia().toString());
        String s;
        s = model.getNumero().toString();
        switch(s.substring(0, 1)) {
            case "3":
                if(s.substring(0, 2).equals("36"))
                    imgFranquicia.setImageResource(R.drawable.ic_dinners);
                else
                    imgFranquicia.setImageResource(R.drawable.ic_amex);
                break;
            case "4":
                imgFranquicia.setImageResource(R.drawable.ic_visa);
                break;
            case "5":
                imgFranquicia.setImageResource(R.drawable.ic_master);
                break;
            case "6":
                imgFranquicia.setImageResource(R.drawable.ic_discover);
                break;
            default:
                imgFranquicia.setImageResource(R.drawable.ic_discover);

        }
        return itemView;
    }
}
