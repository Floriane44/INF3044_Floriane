package com.example.floriane.monapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by floriane on 19/12/16.
 */

public class RecyclerActivity2 extends AppCompatActivity {
    private RecyclerView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler2_activity);
        view = (RecyclerView) findViewById(R.id.rv_meteo);
        view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        MeteoAdapter meteoAdapter = new MeteoAdapter(getMeteoFromFile());
        view.setAdapter(meteoAdapter);
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
