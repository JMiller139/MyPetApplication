package com.example.jona1.mypet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bekal on 4/25/2017.
 */

public class PetListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataPet> petDetail;
    //ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public PetListAdapter (Activity activity, List<DataPet> petDetail){
        super();
        this.activity = activity;
        this.petDetail = petDetail;
    }
    @Override
    public int getCount() {
        return petDetail.size();
    }

    @Override
    public Object getItem(int position) {
        return petDetail.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null)
            inflater=(LayoutInflater)activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
            convertView=inflater.inflate(R.layout.lost_pet_list,parent,false);
//        if(imageLoader==null)
//            imageLoader = MySingleton.getInstance().getImageLoader();
//        NetworkImageView thumbNail = (NetworkImageView) convertView
//                .findViewById(R.id.thumbnail);
        TextView petName = (TextView) convertView.findViewById(R.id.petName);
        TextView breed = (TextView) convertView.findViewById(R.id.breed);
        TextView species = (TextView) convertView.findViewById(R.id.species);

        DataPet data = petDetail.get(position);
//        thumbNail.setImageUrl(m.getThumbnailUrl(),imageLoader);
        petName.setText(data.getPetName());
        breed.setText(data.getBreed());
        species.setText(data.getSpecies());
        return convertView;
    }
}
