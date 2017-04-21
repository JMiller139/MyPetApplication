package com.example.jona1.mypet;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bekal on 4/8/2017.
 */

public class AdapterPet extends RecyclerView.Adapter<AdapterPet.ViewHolder> {
    Context context;


    private List<DataPet> lostPet;
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView petName,breed, species;
        public ImageView petImage;

        public ViewHolder(View v){
            super(v);
            petName = (TextView) v.findViewById(R.id.petName);
            breed = (TextView) v.findViewById(R.id.breed);
            species = (TextView) v.findViewById(R.id.speices);
        }
    }
    public void add(int position, DataPet info){
        lostPet.add(position,info);
        notifyItemChanged(position);
    }
    public void remove(DataPet info){
        int position = lostPet.indexOf(info);
        lostPet.remove(position);
        notifyItemRemoved(position);
    }
    public AdapterPet(List<DataPet> mylostPets){
        lostPet= mylostPets;
    }
    @Override
    public AdapterPet.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_screen, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterPet.ViewHolder holder, int position) {
        holder.petName.setText(lostPet.get(position).getPetName());
        holder.species.setText(lostPet.get(position).getPetName());
        holder.breed.setText(lostPet.get(position).getPetName());
//        Glide.with(holder.imageView.getContext()).load(lostPet.get(position).getPetImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return lostPet.size();
    }
}