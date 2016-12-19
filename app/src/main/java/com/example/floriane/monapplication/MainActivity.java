package com.example.floriane.monapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView view;
    private RecyclerView view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter intentFilter = new IntentFilter(BIERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(), intentFilter);
        GetBiersServices.startActionBiers(this);
        view = (RecyclerView) findViewById(R.id.rv_biere);
        view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        view.setAdapter(new BiersAdapter(getBiersFromFile()));
        IntentFilter intentFilter2 = new IntentFilter(METEO_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new MeteoUpdate(), intentFilter2);
        GetMeteoServices.startActionMeteo(this);
        view2 = (RecyclerView) findViewById(R.id.rv_meteo);
        view2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        view2.setAdapter(new MeteoAdapter(getMeteoFromFile()));
        Button btn_hw = (Button) findViewById(R.id.but);
        btn_hw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), getString(R.string.app_name), Toast.LENGTH_LONG).show();
            }
        });
        Button b = (Button) findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RecyclerActivity.class));
            }
        });
        Button b2 = (Button) findViewById(R.id.button3);
        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RecyclerActivity2.class));
            }
        });
    }

    public JSONArray getBiersFromFile(){
        try{
            InputStream is = new FileInputStream(getCacheDir() + "/" + "bieres.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            JSONArray tab = new JSONArray(new String(buffer, "UTF-8"));
            List<JSONObject> jsonValues = new ArrayList<JSONObject>();
            for(int i = 0; i < tab.length(); i++) jsonValues.add(tab.getJSONObject(i));
            Collections.sort(jsonValues, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject a, JSONObject b) {
                    String valA = new String();
                    String valB = new String();

                    try{
                        valA = (String) a.get("name");
                        valB = (String) b.get("name");
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                    return valA.compareTo(valB);
                }
            });
            JSONArray sortedTab = new JSONArray(jsonValues);
            return sortedTab;
        } catch (IOException e){
            e.printStackTrace();
            return new JSONArray();
        } catch (JSONException e){
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public JSONObject getMeteoFromFile(){
        try{
            InputStream is = new FileInputStream(getCacheDir() + "/" + "meteo.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONObject(new String(buffer, "UTF-8"));
        } catch (IOException e){
            e.printStackTrace();
            return new JSONObject();
        } catch (JSONException e){
            e.printStackTrace();
            return new JSONObject();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.it1:
                //Toast.makeText(getApplicationContext(), getString(R.string.en), Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.fra2).setTitle(R.string.fra3);
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.it2:
                //Toast.makeText(getApplicationContext(), getString(R.string.sc3), Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                builder3.setMessage(R.string.sc3).setTitle(R.string.fra3);
                AlertDialog dialog2 = builder3.create();
                dialog2.show();
                break;
            case R.id.it3:
                //Toast.makeText(getApplicationContext(), getString(R.string.sc1), Toast.LENGTH_LONG).show();
                /*Intent i = new Intent(this, MainActivity.class);
                PendingIntent j = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
                NotificationManager nm = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder builder2 = new Notification.Builder(this);
                builder2.setContentTitle("Titre").setContentText("Bonjour").setContentIntent(j);
                Notification n = builder2.build();
                int mNotificationId = 001;
                nm.notify(mNotificationId, n);*/

                NotificationCompat.Builder not = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext()).
                        setSmallIcon(R.mipmap.ic_launcher).
                        setContentText("Veux-tu une bière ?").
                        setContentTitle("Bonjour");
                NotificationManager notM = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                notM.notify(1, not.build());
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    public static final String BIERS_UPDATE = "com.example.floriane.monapplication.BIERS_UPDATE";

    public class BierUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            view = (RecyclerView) findViewById(R.id.rv_biere);
            Log.d(getString(R.string.sc1), getIntent().getAction());
            Toast.makeText(getApplicationContext(), "Downloaded biers", Toast.LENGTH_LONG).show();
            BiersAdapter b = (BiersAdapter)view.getAdapter();
            b.setNewBier(getBiersFromFile());
        }

        public JSONArray getBiersFromFile(){
            try{
                InputStream is = new FileInputStream(getCacheDir() + "/" + "bieres.json");
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                is.close();
                JSONArray tab = new JSONArray(new String(buffer, "UTF-8"));
                List<JSONObject> jsonValues = new ArrayList<JSONObject>();
                for(int i = 0; i < tab.length(); i++) jsonValues.add(tab.getJSONObject(i));
                Collections.sort(jsonValues, new Comparator<JSONObject>() {
                    @Override
                    public int compare(JSONObject a, JSONObject b) {
                        String valA = new String();
                        String valB = new String();

                        try{
                            valA = (String) a.get("name");
                            valB = (String) b.get("name");
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        return valA.compareTo(valB);
                    }
                });
                JSONArray sortedTab = new JSONArray(jsonValues);
                return sortedTab;
            } catch (IOException e){
                e.printStackTrace();
                return new JSONArray();
            } catch (JSONException e){
                e.printStackTrace();
                return new JSONArray();
            }
        }
    }

    public static final String METEO_UPDATE = "com.example.floriane.monapplication.METEO_UPDATE";

    public class MeteoUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            view2 = (RecyclerView) findViewById(R.id.rv_meteo);
            Log.d(getString(R.string.sc1), getIntent().getAction());
            NotificationCompat.Builder not = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext()).
                    setSmallIcon(R.mipmap.ic_launcher).
                    setContentText("Downloaded meteo").
                    setContentTitle("Info");
            NotificationManager notM = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notM.notify(1, not.build());
            //Toast.makeText(getApplicationContext(), "Downloaded meteo", Toast.LENGTH_LONG).show();
            MeteoAdapter b = (MeteoAdapter)view2.getAdapter();
            b.setNewBier(getMeteoFromFile());
        }

        public JSONObject getMeteoFromFile(){
            try{
                InputStream is = new FileInputStream(getCacheDir() + "/" + "meteo.json");
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                is.close();
                return new JSONObject(new String(buffer, "UTF-8"));
            } catch (IOException e){
                e.printStackTrace();
                return new JSONObject();
            } catch (JSONException e){
                e.printStackTrace();
                return new JSONObject();
            }
        }
    }
}
