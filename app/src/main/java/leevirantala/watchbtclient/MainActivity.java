package leevirantala.watchbtclient;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{
    final String TAG = MainActivity.class.getSimpleName();
    private TextView mTextView;
    Button connectbutton, sendbutton;
    private BluetoothAdapter btAdapter = null;
    private BTClient btClient = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rect_activity_main);
        initialize();
        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }
    
    private void initialize() {
        Log.d(TAG, "initialize");
        connectbutton = (Button)findViewById(R.id.ConnectButton);
        connectbutton.setOnClickListener(this);
        sendbutton = (Button)findViewById(R.id.SendButton);
        sendbutton.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick");
        switch(v.getId()){
            case R.id.ConnectButton:
                if (btClient != null){
                    Toast.makeText(this, "Nulling BTClient", Toast.LENGTH_SHORT).show();
                    btClient = null;
                    break;
                }
                if (btClient == null){
                    Toast.makeText(this, "Creating BTClient", Toast.LENGTH_SHORT).show();
                    if(btAdapter != null && btAdapter.isDiscovering()){
                        btAdapter.cancelDiscovery();
                    }
                    btAdapter.startDiscovery();
                    btClient = new BTClient();
                    break;
                }
                break;
            
            case R.id.SendButton:
                if (btClient != null) {
                    btClient.SendMessage();
                }
                if (btClient == null){
                    Toast.makeText(this, "Create BTClient first", Toast.LENGTH_SHORT).show();
                }
                break;
            
        }
    }
}
