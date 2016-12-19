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
 * Created by floriane on 15/11/16.
 */

public class BiersAdapter extends RecyclerView.Adapter<BiersAdapter.BierHolder>{
    private JSONArray biers;

    public BiersAdapter(JSONArray biers){
        this.biers = biers;
    }

    @Override
    public BierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater in = LayoutInflater.from(parent.getContext());
        View view = in.inflate(R.layout.rv_bier_element, null, false);
        BierHolder bh = new BierHolder(view);
        return bh;
    }

    @Override
    public void onBindViewHolder(BiersAdapter.BierHolder holder, int position) {

        try {
            JSONObject o = (JSONObject) biers.get(position);
            holder.name.setText(o.getString("name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setNewBier(JSONArray biers){
        this.biers = biers;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return biers.length();
    }

    public class BierHolder extends RecyclerView.ViewHolder{
        public TextView name;

        public BierHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.rv_bier_element_name);
        }
    }

}
