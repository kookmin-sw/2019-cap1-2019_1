package com.example.bluetoothtest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;


public class MainActivity extends AppCompatActivity
{
    private final int REQUEST_BLUETOOTH_ENABLE = 100;

    private TextView mConnectionStatus;
    private EditText mInputEditText;

    ConnectedTask mConnectedTask = null;
    static BluetoothAdapter mBluetoothAdapter;
    private String mConnectedDeviceName = null;
    private ArrayAdapter<String> mConversationArrayAdapter;
    static boolean isConnectionError = false;
    private static final String TAG = "BluetoothClient";

    private class ConnectTask extends AsyncTask<Void, Void, Boolean> {

        private BluetoothSocket mBluetoothSocket = null;
        private BluetoothDevice mBluetoothDevice = null;

        ConnectTask(BluetoothDevice bluetoothDevice) {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
        }

        @Override
        protected void onPostExecute(Boolean isSucess) {
        }
    }


    public void connected( BluetoothSocket socket ) {
    }



//    private class ConnectedTask extends AsyncTask<Void, String, Boolean> {
//
//    }


    public void showPairedDevicesListDialog()
    {
    }



    public void showErrorDialog(String message)
    {
    }


    public void showQuitDialog(String message)
    {
    }

    void sendMessage(String msg){
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }


}