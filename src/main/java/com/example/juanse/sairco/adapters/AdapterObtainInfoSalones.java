package com.example.juanse.sairco.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.juanse.sairco.Equipo;
import com.example.juanse.sairco.R;
import com.example.juanse.sairco.Salon;

import java.util.List;

/**
 * Created by juanse on 6/05/2016.
 */
public class AdapterObtainInfoSalones extends BaseAdapter {

    private List<Salon> salonesList;
    private Context context;

    public AdapterObtainInfoSalones(List<Salon> salonesList, Context context) {
        this.salonesList = salonesList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return salonesList.size();
    }

    @Override
    public Object getItem(int position) {
        return salonesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.row_obtain_info_salones, null);

            Salon salon= salonesList.get(position);
            TextView nombre= (TextView) v.findViewById(R.id.obtainName);
            TextView ubicacion= (TextView) v.findViewById(R.id.obtainUbication);
            nombre.setText("Nombre: "+ salon.getNombre());
            ubicacion.setText("Ubicacion: "+salon.getUbicacion());
        }else{
            v=(View) convertView;
        }


        return v;
    }
}
