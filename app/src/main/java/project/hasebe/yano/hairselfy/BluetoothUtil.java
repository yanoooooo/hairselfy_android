package project.hasebe.yano.hairselfy;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.Set;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by aynishim on 2016/09/18.
 */
public class BluetoothUtil implements Runnable{
    private static final String TAG = "BluetoothUtil";

    private BluetoothAdapter mAdapter; //Bluetooth Adapter
    private BluetoothDevice mDevice; //Bluetoothデバイス
    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Bluetooth UUID
    private final String DEVICE_NAME = "eriBT"; //device name
    private BluetoothSocket mSocket; //Socket
    private Thread mThread; //Thread
    private boolean isRunning; //thread status
    private boolean connectFlg = false;
    OutputStream mmOutputStream = null; //bluetooth's output stream

    public void serchDevice() {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        Set< BluetoothDevice > devices = mAdapter.getBondedDevices();
        for (BluetoothDevice device : devices) {
            Log.i(TAG, "searching bluetooth");
            if (device.getName().equals(DEVICE_NAME)) {
                Log.i(TAG, "★finding bluetooth★");
                mDevice = device;
            }
        }
    }

    public void pause(){
        isRunning = false;
        try{
            mSocket.close();
        }
        catch(Exception e){}
    }

    public void write(String data) {
        // 接続中のみ書込みを行う
        if (connectFlg) {
            try {
                mmOutputStream.write(Integer.parseInt(data));
                Log.i(TAG,"writing bluetooth");
            } catch (IOException e) {
                Log.i(TAG,"text sending error!");
            }
        } else {
            Log.i(TAG,"Please connect bluetooth");
        }
    }

    @Override
    public void run() {
        InputStream mmInStream = null;

        Log.i(TAG,"connecting...");

        try{
            // 取得したデバイス名を使ってBluetoothでSocket接続
            mSocket = mDevice.createRfcommSocketToServiceRecord(MY_UUID);
            mSocket.connect();
            mmInStream = mSocket.getInputStream();
            mmOutputStream = mSocket.getOutputStream();

            // InputStreamのバッファを格納
            byte[] buffer = new byte[1024];

            // 取得したバッファのサイズを格納
            int bytes;
            Log.i(TAG,"connected");

            connectFlg = true;

            while(isRunning){
                // InputStreamの読み込み
                bytes = mmInStream.read(buffer);
                Log.i(TAG,"bytes="+bytes);
                // String型に変換
                String readMsg = new String(buffer, 0, bytes);

                // null以外なら表示
                if(readMsg.trim() != null && !readMsg.trim().equals("")){
                    Log.i(TAG,"value="+readMsg.trim());

                }
                else{
                    Log.i(TAG,"value=nodata");
                }

            }
        }catch(Exception e){
            Log.i(TAG,"Error:" + e);

            try{
                mSocket.close();
            }catch(Exception ee){}
            isRunning = false;
            connectFlg = false;
        }
    }

    public void connect() {
        if (!connectFlg) {
            Log.i(TAG,"try connecting bluetooth");

            mThread = new Thread(this);
            // Threadを起動し、Bluetooth接続
            isRunning = true;
            mThread.start();
        }
    }


}
