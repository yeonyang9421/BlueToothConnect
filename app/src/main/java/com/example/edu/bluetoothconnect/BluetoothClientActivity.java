package com.example.edu.bluetoothconnect;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.UUID;

public class BluetoothClientActivity extends AppCompatActivity implements View.OnClickListener{
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private Button mbuttonUP, mbuttonDOWN, mbuttonLEFT, mbuttonRIGHT, mbuttonA,mbuttonB,mbuttonC,mbuttonD,mbuttonE,mbuttonF;
    private ProgressBar mProgressBar;
    private BluetoothSocket bluetoothSocket;
    private BluetoothAdapter bluetoothAdapter;
    String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_client);
        mbuttonUP = findViewById(R.id.buttonUP);
        mbuttonDOWN = findViewById(R.id.buttonDOWN);
        mbuttonLEFT = findViewById(R.id.buttonLEFT);
        mbuttonRIGHT = findViewById(R.id.buttonRIGHT);
        mbuttonA=findViewById(R.id.buttonA);
        mbuttonB=findViewById(R.id.buttonB);
        mbuttonC=findViewById(R.id.buttonC);
        mbuttonD=findViewById(R.id.buttonD);
        mbuttonE=findViewById(R.id.buttonE);
        mbuttonF=findViewById(R.id.buttonF);
        mProgressBar = findViewById(R.id.progressBar);
        mbuttonUP.setOnClickListener(this);
        mbuttonDOWN.setOnClickListener(this);
        mbuttonLEFT.setOnClickListener(this);
        mbuttonRIGHT.setOnClickListener(this);
        mbuttonA.setOnClickListener(this);
        mbuttonB.setOnClickListener(this);
        mbuttonC.setOnClickListener(this);
        mbuttonD.setOnClickListener(this);
        mbuttonE.setOnClickListener(this);
        mbuttonF.setOnClickListener(this);



        Intent newint = getIntent();
        address = newint.getStringExtra("device_address");
        new ConnectBluetoothTask().execute();
    }

    @Override
    public void onClick(View v) {
        String message="";
        switch (v.getId()){
            case R.id.buttonUP:
                message = "U";
            break;
            case R.id.buttonDOWN:
               message = "D";
                break;
            case R.id.buttonLEFT:
                message = "C";
                break;
            case R.id.buttonRIGHT:
                message = "L";
                break;
            case R.id.buttonA:
                message = "R";
                break;
            case R.id.buttonB:
                message = "a";
                break;
            case R.id.buttonC:
                message = "b";
                break;
            case R.id.buttonD:
                message = "c";
                break;
            case R.id.buttonE:
                message = "d";
                break;
            case R.id.buttonF:
                message = "e";
                break;
        }
        try {
            bluetoothSocket.getOutputStream().write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ConnectBluetoothTask extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected Void doInBackground(Void... devices) {
            if (bluetoothSocket == null) {
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                BluetoothDevice dispositivo = bluetoothAdapter.getRemoteDevice(address);
                try {
                    bluetoothSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                try {
                    bluetoothSocket.connect();
//                    mProgressBar.setVisibility(View.INVISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}

