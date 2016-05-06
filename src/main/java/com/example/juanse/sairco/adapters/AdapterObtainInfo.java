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

import java.util.List;

/**
 * Created by juanse on 6/05/2016.
 */
public class AdapterObtainInfo extends BaseAdapter {

    private List<Equipo> equiposList;
    private Context context;

    public AdapterObtainInfo(List<Equipo> equiposList, Context context) {
        this.equiposList = equiposList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return equiposList.size();
    }

    @Override
    public Object getItem(int position) {
        return equiposList.get(position);
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
            v = inf.inflate(R.layout.row_obtain_info, null);

            Equipo equipo = equiposList.get(position);
            TextView nombre= (TextView) v.findViewById(R.id.obtainName);
            TextView ubicacion= (TextView) v.findViewById(R.id.obtainUbication);
            TextView nroReservas= (TextView) v.findViewById(R.id.obtainNroReservas);
            nombre.setText("Nombre: "+ equipo.getNombre());
            ubicacion.setText("Ubicacion: "+equipo.getUbicacion());
            nroReservas.setText("Numero de reserfvas: "+equipo.getNroReservas());
        }else{
            v=(View) convertView;
        }


        return v;
    }
}
