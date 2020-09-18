package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;

public class NewsDetails extends AppCompatActivity {
    TextView details,description;
    Button save;
    ImageButton share;
    private long downloadID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        details=findViewById(R.id.details);
        description=findViewById(R.id.description);
        save=findViewById(R.id.save);
        share=findViewById(R.id.share);
        registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            share();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        haveStoragePermission();
            }
        });
       if(getIntent().hasExtra("description")){
           details.setText( getIntent().getStringExtra("description"));
           description.setText( getIntent().getStringExtra("description"));
        }
    }
    public  boolean haveStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission error","You have permission");
                addImageToGallery();
                return true;
            } else {

                Log.e("Permission error","You have asked for permission");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //you dont need to worry about these stuff below api level 23
            Log.e("Permission error","You already have the permission");
            addImageToGallery();
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            //you have the permission now.
            addImageToGallery();
        }
    }
    public void addImageToGallery() {
        String filename = "filename.jpg";
        String downloadUrlOfImage = "https://homepages.cae.wisc.edu/~ece533/images/airplane.png"; // in responce there is image path so dummy image is been used
        File direct =
                new File(Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .getAbsolutePath() + "/" + "demo" + "/");


        if (!direct.exists()) {
            direct.mkdir();
            Log.d("demo", "dir created for first time");
        }
        DownloadManager dm = (DownloadManager)  getSystemService(Context.DOWNLOAD_SERVICE);
        Uri downloadUri = Uri.parse(downloadUrlOfImage);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType("image/jpeg")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                        File.separator + "demo" + File.separator + filename);

        dm.enqueue(request);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onDownloadComplete);
    }
    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                Toast.makeText(context,"Image is saved successfully to your device",Toast.LENGTH_LONG).show();
                }
            }
    };
    public void share(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, details.getText().toString());
        startActivity(Intent.createChooser(shareIntent, "share the News info:"));

    }
}