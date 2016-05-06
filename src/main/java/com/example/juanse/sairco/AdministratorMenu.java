package com.example.juanse.sairco;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juanse.sairco.fragments.ActualizarEquipo;

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
import java.util.ArrayList;

import com.example.juanse.sairco.Equipo;
import com.example.juanse.sairco.fragments.InfoEquipo;

public class AdministratorMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private EditText mPasswordView;
    private EditText mUserNameView;
    private View mProgressView;
    private View mLoginFormView;
    public int respuestaServidor;
    public String usuario;
    public String clave;
    public String tipoUsuario;
    public String urlParameters;
    ProgressDialog progressDialog;
    public Equipo equipo= new Equipo();
    public ActionBarDrawerToggle toggle;
    public  DrawerLayout drawer;
    public Toolbar toolbar;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_menu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



//        ActualizarEquipo actualizarEquipo = new ActualizarEquipo();
//        getSupportFragmentManager().beginTransaction().replace(R.id.container_app,actualizarEquipo).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Equipo>parseResult(String result) {
        ArrayList<Equipo> lista= new ArrayList<>();
        try {

            JSONArray response = new JSONArray(result);
            Equipo equipo1= new Equipo();
            JSONArray nombre = new JSONArray(response.optString(0));
            for (int i=0;i<response.length();i++){
                equipo1.setID(response.getString(0));
                equipo1.setNombre(nombre.toString(1));
                equipo1.setUbicacion(response.optString(3));
                lista.add(equipo1);

            }


            System.out.println("En la quinta: " + equipo1.nombre);
            return (lista);
        } catch (JSONException e) {
            e.printStackTrace();
        }return lista;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;
        if (id == R.id.enlazar) {
            urlParameters = "http://labsoftware03.unitecnologica.edu.co/agregarEquipo";
            new AsyncHttpTaskGET().execute(urlParameters);
        } else if (id == R.id.eliminarEquipos) {
            urlParameters="http://labsoftware03.unitecnologica.edu.co/eliminarEquipo";
            new AsyncHttpTaskGET().execute(urlParameters);
        } else if (id == R.id.obtenerInfo) {
//            System.out.println("En la primera: "+equipo.nombre);
//            urlParameters="http://labsoftware03.unitecnologica.edu.co/obtenerInformacion";
//            new AsyncHttpTaskGET().execute(urlParameters);
//            TextView tv1= (TextView) findViewById(R.id.obtenerInformacion);
//            System.out.println("En la segunda: "+equipo.nombre);
//            tv1.setText(equipo.nombre);
            fragment = new InfoEquipo();
            getSupportFragmentManager().beginTransaction().replace(R.id.container_app, fragment).commit();


        } else if (id == R.id.actualizarEquipos) {
            fragment = new ActualizarEquipo();
            getSupportFragmentManager().beginTransaction().replace(R.id.container_app, fragment).commit();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//    private void parseResult(String result) {
//        try {
//            JSONObject response = new JSONObject();
//
//            student.setStudentName(response.optString("names") + " " + response.optString("lastnames"));
//            student.setId(response.optString("id"));
//            student.setProgram(response.optString("program"));
//            student.setEmail(response.optString("email"));
//            JSONObject posts = new JSONObject(response.optString("links"));
//            student.setUri(posts.optString("attendance_uri"));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
    public class AsyncHttpTaskGET extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            InputStream inputStream = null;
            Integer result = 0;
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
                    System.out.println("En la tercera: "+response);
                    //equipo= parseResult(response.toString());
                    System.out.println("En la cuarta: "+equipo.nombre);
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                result=-1;
                e.printStackTrace();;
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressDialog.dismiss();
            /* Download complete. Lets update UI */
            if (result == 1) {
                Toast.makeText(AdministratorMenu.this,"Se hizo correctamente.",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(AdministratorMenu.this,"Problema interno",Toast.LENGTH_LONG).show();
            }
        }
    }

    public class AsyncHttpTaskPOST extends AsyncTask<String, Void, Integer> {

        Context context;
        @Override
        protected void onPreExecute() {
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            URL url;
            HttpURLConnection connection = null;

            try {
                String usuarioAdministrador = params[1].substring(0,4);
                //Create connection
                if ("t000".equals(usuarioAdministrador) || "T000".equals(usuarioAdministrador)){
                    usuario= "username=";
                    clave= "&password=";
                    urlParameters = usuario + URLEncoder.encode(params[1], "UTF-8") +
                            clave + URLEncoder.encode(params[2], "UTF-8");
                }else {
                    String soyAndroid="&Movil=";
                    urlParameters = "email=" + params[1] +
                            "&password="+params[2]+ soyAndroid +"True";
                    System.out.println("url que se envia: "+urlParameters);
                }



                url = new URL(params[0]);

                connection = (HttpURLConnection)url.openConnection();
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
                } catch (Exception e){
                    respuestaServidor=-2;
                    e.printStackTrace();
                }
                //return response.toString();
                respuestaServidor=connection.getResponseCode();
                Log.e("Respuesta", "ID = "+connection.getResponseCode());
                return 1;
            } catch (Exception e) {
                Log.e("onExecute", "Error de app");
                respuestaServidor=-1;
                e.printStackTrace();
                return null;

            } finally {

                if(connection != null) {
                    connection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(Integer result) {

            progressDialog.dismiss();

            System.out.println("estado="+respuestaServidor);
            if (respuestaServidor==200 && "usuario".equals(tipoUsuario)) {
                Log.e("onPostExecute", "on PostExec");

//                SharedPreferences settings = getSharedPreferences("TokenStorage", 0);
//                Log.e("onPostExecute", "TokenSaved:" + settings.getString("token", ""));
//                Log.e("onPostExecute", "IdSaved:" + settings.getString("id", ""));

                Intent intent_name = new Intent();
                intent_name.setClass(getApplicationContext(), MainActivity.class);
                startActivity(intent_name);
            }else if (respuestaServidor==200 && "administrador".equals(tipoUsuario)){
                Log.e("onPostExecute", "on PostExec");

                Intent intent_name = new Intent();
                intent_name.setClass(getApplicationContext(), AdministratorMenu.class);
                startActivity(intent_name);
            }else{
                switch (respuestaServidor){
                    case (401):
                        Toast.makeText(AdministratorMenu.this, "Credenciales invalidas, intente de nuevo.", Toast.LENGTH_LONG).show();
                        break;
                    case (500):
                        Toast.makeText(AdministratorMenu.this,"Servidor en reparación.",Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(AdministratorMenu.this,"Verifique que tenga conexión a internet",Toast.LENGTH_LONG).show();
                        break;
                }

            }
        }
    }
}
