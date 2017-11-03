package leevirantala.watchbtclient;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Set;
import java.util.UUID;

/**
 * Created by eewijet on 1.11.2017.
 */

public class BTClient {
    private static final String TAG = BTClient.class.getSimpleName();
    private BluetoothSocket btSocket;
    private BluetoothDevice btDevice;
    private final UUID uuid = UUID.fromString("ee2133aa-b8a2-11e7-abc4-cec278b6b50a");
    
    public void SendMessage() {
        //btDevice = getPairedDevice();
        btDevice = BluetoothDevice
        Log.d(TAG, btDevice.getName());
        Log.d(TAG, btDevice.getAddress());
        try {
            btSocket = btDevice.createRfcommSocketToServiceRecord(uuid);
            if (!btSocket.isConnected()){
            btSocket.connect();}
        } catch (IOException e) {
            e.printStackTrace();
            try {
                Log.d(TAG,"trying fallback...");
                btSocket =(BluetoothSocket) btDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(btDevice,2);
                Thread.sleep(500);
                btSocket.connect();
        
                Log.d(TAG,"Connected");
            }
            catch (Exception e2) {
                Log.d(TAG, "Couldn't establish Bluetooth connection!");
            }
        }

        try {
            DataOutputStream dOut = new DataOutputStream(btSocket.getOutputStream());
// Send first message
            Log.d(TAG, "Starting to send messages");
            dOut.writeUTF("This is the first type of message.");
            dOut.flush(); // Send off the data
// Send the second message
            dOut.writeUTF("This is the second type of message.");
            dOut.flush(); // Send off the data
// Send the third message
            dOut.writeUTF("This is the third type of message (Part 1).");
            dOut.writeUTF("This is the third type of message (Part 2).");
            dOut.flush(); // Send off the data
// Send the exit message
            //dOut.writeByte(-1);
            //dOut.flush();
            dOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private BluetoothDevice getPairedDevice() {
        Set<BluetoothDevice> pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        if (pairedDevices != null) {
            for (BluetoothDevice device : pairedDevices) {
                Log.d(TAG, "device name: " + device.getName());
                BluetoothDevice pairedDevice = device.getAddress(74:A5:28:70:B8:5D);
                return pairedDevice;
            }
        }
        return null;
    }
}



