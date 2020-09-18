package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {

    ArrayList<String> myListData;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle(R.string.app_name);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(HomeActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(null);
        myListData = new ArrayList<String>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(HomeActivity.this,myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        apicall();
        return;
    }

    public void apicall(){
        String url = "https://newsapi.org/v2/sources?apiKey=d29d58aab88d4ea0b04ddb245a230068";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                progressDialog.dismiss();
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String Apirespone = response.body().string();
                try {
                    JSONObject apiresponeObject = new JSONObject(Apirespone);
                    String responceStatus= apiresponeObject.getString("status");
                    JSONArray sourcesDetails = apiresponeObject.getJSONArray("sources");

                    for (int sources=0;sources<sourcesDetails.length();sources++){
                        JSONObject sourcesDetailsobject = sourcesDetails.getJSONObject(sources);
                        myListData.add(sourcesDetailsobject.getString("description"));
                    }
                    LoadData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void LoadData(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                MyListAdapter adapter = new MyListAdapter(HomeActivity.this,myListData);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
                // Stuff that updates the UI
            }
        });

    }
}