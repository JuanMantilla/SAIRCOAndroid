package com.example.juanse.sairco.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.juanse.sairco.Equipo;
import com.example.juanse.sairco.R;
import com.example.juanse.sairco.adapters.AdapterObtainInfo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by juanse on 6/05/2016.
 */
public class InfoEquipo extends Fragment {

    public View v;
    public ListView listView;
    public List<Equipo> equiposList =  new ArrayList<>();
    private AdapterObtainInfo adapterObtainInfo;
    public InfoEquipo() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_info_equipo, container,false);
        listView=(ListView)v.findViewById(R.id.infoEquipos);

        adapterObtainInfo = new AdapterObtainInfo(equiposList,getActivity());
        listView.setAdapter(adapterObtainInfo);

        obtainInfo();
        return v;

    }

    private void obtainInfo() {
        String urlParameters= "http://labsoftware03.unitecnologica.edu.co/obtenerInformacion";
        new AsyncHttpTaskGET().execute(urlParameters);

    }
    private void parseResult(String result) {
       equiposList= new ArrayList<>();
        try {

            JSONArray response = new JSONArray(result);
            Log.e("dfdsdf",response.toString());

//            JSONArray nombre = new JSONArray(response.optString(0));
            for (int i=0;i<response.length();i++){
                Equipo equipo1= new Equipo();
                equipo1.setID(response.getJSONObject(i).getString("id"));
                equipo1.setNombre(response.getJSONObject(i).getString("name"));
                equipo1.setUbicacion(response.getJSONObject(i).getString("ubicacion"));
                equipo1.setNroReservas(response.getJSONObject(i).getString("nroReservas"));
                equiposList.add(equipo1);

            }



        } catch (JSONException e) {
           Log.e("error",e.toString());
        }

        adapterObtainInfo = new AdapterObtainInfo(equiposList,getActivity());
        listView.setAdapter(adapterObtainInfo);
    }
    public class AsyncHttpTaskGET extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            InputStream inputStream = null;
            String result =null ;
            HttpURLConnection urlConnection = null;

            try {
                /* forming th java.net.URL object */
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                /* for Get request */
                urlConnection.setRequestMethod("GET");
                int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    System.out.println("En la tercera: " + response);

                    //System.out.println("En la cuarta: "+equipo.nombre);
                    result = response.toString(); // Successful
                } else {
                    result = null; //"Failed to fetch data!";
                }

            } catch (Exception e) {

                e.printStackTrace();;
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(String result) {
            /* Download complete. Lets update UI */

            parseResult(result);

        }
    }
}
