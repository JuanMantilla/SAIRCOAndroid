package com.example.juanse.sairco.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juanse.sairco.AdministratorMenu;
import com.example.juanse.sairco.Equipo;
import com.example.juanse.sairco.MainActivity;
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
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by juanse on 6/05/2016.
 */
public class ActualizarSalon extends Fragment {

    public View v;
    public ListView listView;
    public List<Equipo> equiposList = new ArrayList<>();
    private AdapterObtainInfo adapterObtainInfo;
    ProgressDialog progressDialog;
    String postID;
    String postNombre;
    String postUbicacion;
    String urlParameters;
    public int respuestaServidor;

    EditText tv1,tv2, tv3;

    public ActualizarSalon() {

    }

    final String URL = "http://labsoftware03.unitecnologica.edu.co/salonActualizado";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_actualizar_salon, container, false);

        tv1 = (EditText) v.findViewById(R.id.postID);
        tv2 = (EditText) v.findViewById(R.id.postNuevoNombre);
        tv3 = (EditText) v.findViewById(R.id.postNuevaUbicacion);

        Button btn = (Button) v.findViewById(R.id.btnPost);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postID = tv1.getText().toString();
                postNombre = tv2.getText().toString();
                postUbicacion = tv3.getText().toString();

                new AsyncHttpTask().execute(URL);
            }
        });
        return v;

    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        Context context;

        @Override
        protected void onPreExecute() {
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            URL url;
            HttpURLConnection connection = null;

            try {
                //Create connection
                String id = postID;
                String nombre = postNombre;
                String ubicacion = postUbicacion;
                String soyAndroid = "&Movil=";
                urlParameters = "id=" + id +
                        "&name=" + postNombre + "&ubicacion=" + postUbicacion;
                System.out.println("url que se envia: " + urlParameters);

                url = new URL(params[0]);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");

                connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));

                connection.setRequestProperty("Content-Language", "en-US");

                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                PrintWriter out = new PrintWriter(connection.getOutputStream());
                out.print(urlParameters);
                out.close();

                //Leer la respuesta del servidor
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                    StringBuilder sb = new StringBuilder();
                    String output;
                    while ((output = br.readLine()) != null) {
                        sb.append(output);
                    }

                   /* guardar token y el usuario en la app
                    SharedPreferences.Editor editor = MainActivity.settings.edit();
                    editor.putString("token", sb.toString());
                    editor.putString("id", mTeacherID);*/

                    // Commit the edits!
                    //editor.commit();
                    Log.e("Respuesta", sb.toString());
                } catch (Exception e) {
                    respuestaServidor = -2;
                    e.printStackTrace();
                }
                //return response.toString();
                respuestaServidor = connection.getResponseCode();
                Log.e("Respuesta", "ID = " + connection.getResponseCode());
                return 1;
            } catch (Exception e) {
                Log.e("onExecute", "Error de app");
                respuestaServidor = -1;
                e.printStackTrace();
                return null;

            } finally {

                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(Integer result) {

            //progressDialog.dismiss();

            System.out.println("estado=" + respuestaServidor);

            switch (respuestaServidor) {
                case (401):
                    Toast.makeText(getActivity(), "Credenciales invalidas, intente de nuevo.", Toast.LENGTH_LONG).show();
                    System.out.println("error: No autorizado");
                    break;
                case (500):
                    Toast.makeText(getActivity(), "Servidor en reparación.", Toast.LENGTH_LONG).show();
                    System.out.println("Error interno de la api");
                    break;
                default:
                    Toast.makeText(getActivity(), "Verifique que tenga conexión a internet", Toast.LENGTH_LONG).show();
                    System.out.println("A vaina");
                    break;
            }

        }
    }
}

