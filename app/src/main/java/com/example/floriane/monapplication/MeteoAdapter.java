package com.example.floriane.monapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by floriane on 19/12/16.
 */

public class MeteoAdapter extends RecyclerView.Adapter<MeteoAdapter.MeteoHolder>{
    private JSONObject tps;

    public MeteoAdapter(JSONObject tps){
        this.tps = tps;
    }

    @Override
    public MeteoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater in = LayoutInflater.from(parent.getContext());
        View view = in.inflate(R.layout.rv_meteo_element, null, false);
        MeteoHolder bh = new MeteoHolder(view);
        return bh;
    }

    @Override
    public void onBindViewHolder(MeteoAdapter.MeteoHolder holder, int position) {

        try {
            JSONObject o = (JSONObject) tps.getJSONObject(String.valueOf(position));
            holder.name.setText(o.getString("2016-12-18 22:00:00"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setNewBier(JSONObject biers){
        this.tps = biers;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tps.length();
    }

    public class MeteoHolder extends RecyclerView.ViewHolder{
        public TextView name;

        public MeteoHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.rv_meteo_element_name);
        }
    }

}
