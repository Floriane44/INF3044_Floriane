package com.example.floriane.monapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by floriane on 15/11/16.
 */

public class RecyclerActivity extends AppCompatActivity {
    private RecyclerView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_activity);
        view = (RecyclerView) findViewById(R.id.rv_biere);
        view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        BiersAdapter biersAdapter = new BiersAdapter(getBiersFromFile());
        view.setAdapter(biersAdapter);
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
