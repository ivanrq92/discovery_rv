package com.example.android.discoverdevices;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.samsung.multiscreen.Search;
import com.samsung.multiscreen.Service;

import java.util.ArrayList;

public class DeviceSelect extends AppCompatActivity {
    private static final String TAG = "DeviceSelect";
    // Get an instance of Search
    Search search;
    private ArrayList<Service> devices;
    private RecyclerView.Adapter<DeviceAdapter.ViewHolder> adapter;
    private RecyclerView recyclerView;
    private ImageView refreshImg;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_select);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        refreshImg = (ImageView) findViewById(R.id.btnRefresh);
        refreshImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devices.clear();
                search.start();
            }
        });
        search = Service.search(this);
        // Add a listener for the onServiceFound event
        search.setOnServiceFoundListener(new Search.OnServiceFoundListener() {
            @Override
            public void onFound(Service service) {
                // Add service to a visual list where your user can select.
                // For display, we recommend that you show: service.getName()
                Log.d(TAG,"Search onFound() " + service.toString());
                devices.add(service);
                adapter.notifyDataSetChanged();
                Log.d(TAG,"items: " + adapter.getItemCount());
            }
        });

        // Add a listener for the onServiceLost event
        search.setOnServiceLostListener(
                new Search.OnServiceLostListener() {
                    @Override
                    public void onLost(Service service) {
                        Log.d(TAG, "Search onLost() " + service.toString());
                        // Remove this service from the display list
                        devices.remove(service);
                        adapter.notifyDataSetChanged();
                    }
                }
        );

        // Add a listener for the onStop event
        search.setOnStopListener(
                new Search.OnStopListener() {
                    @Override
                    public void onStop() {
                        Log.d(TAG, "Search onStop() services found: " + search.getServices().size());
                    }
                }
        );
        search.setOnStartListener(new Search.OnStartListener()
        {
            @Override
            public void onStart()
            {
                progressBar.setVisibility(View.VISIBLE);
                refreshImg.setVisibility(View.GONE);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        search.stop();
                    }
                }, 500);
            }
        });

        search.setOnStopListener(new Search.OnStopListener()
        {
            @Override
            public void onStop()
            {
               // Toast.makeText(getApplicationContext(), "Stop discoverying.",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                refreshImg.setVisibility(View.VISIBLE);

            }
        });

        devices = new ArrayList<>();
        adapter = new DeviceAdapter(this,devices);

        recyclerView = (RecyclerView)findViewById(R.id.devices_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Start the discovery process
        search.start();

    }
}
