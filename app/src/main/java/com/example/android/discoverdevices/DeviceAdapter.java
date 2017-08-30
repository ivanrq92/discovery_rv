package com.example.android.discoverdevices;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.samsung.multiscreen.Service;

import java.util.ArrayList;

/**
 * Created by SRT on 8/21/2017.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    private ArrayList<Service> devicesList;
    private Context context;

    public DeviceAdapter(Context context,ArrayList<Service> devices){
        this.devicesList = devices;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(devicesList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return devicesList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.deviceName);
        }
    }
}
