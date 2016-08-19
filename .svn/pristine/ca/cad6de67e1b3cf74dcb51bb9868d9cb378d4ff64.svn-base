package com.capitalbio.oemv2.myapplication.Devices.Bracelete;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.capitalbio.oemv2.myapplication.BraceleteLib.BraceleteDevices;
import com.capitalbio.oemv2.myapplication.StateMachine.State;
import com.capitalbio.oemv2.myapplication.StateMachine.StateMachine;

/**
 * 手环设备状态机类
 */

public class BraceleteStateMachine extends StateMachine {

    public static final String TAG = "BraceleteStateMachine";
    
    private static BraceleteStateMachine mInstance;
    
    private Context mContext;
    private DisConnectState mDisconnectState = new DisConnectState();
    private ConnectedState mConnectedState = new ConnectedState();
    private DataTransferState mDataTransferState = new DataTransferState();
    private BraceleteDevices mBraceleteDevices;

    private int currentState;
    

    public BraceleteStateMachine(BraceleteDevices bleService) {
        super("BloodPressStateMachine");
        addState(mDisconnectState);
        addState(mConnectedState);
        addState(mDataTransferState);
        
        mBraceleteDevices = bleService;
        setInitialState(mDisconnectState);
    }

    public static BraceleteStateMachine make(Context context, BraceleteDevices bleService) {
        Log.d(TAG, "BloodPressStateMachine make.....");
        
        if (mInstance == null) {
            mInstance = new BraceleteStateMachine(bleService);
            mInstance.start();
        }
        
        return mInstance;
    }
    
    private class DisConnectState extends State {
        @Override
        public void enter() {
            // TODO Auto-generated method stub
            Log.d(TAG, "enter DisConnectState..");
        }
        
        @Override
        public boolean processMessage(Message msg) {
            // TODO Auto-generated method stub
            Log.d(TAG, "current state is DisConnectState msg is " + msg.what);
            switch (msg.what) {

            }
            
            return true;
        }
    }
    
    private class ConnectedState extends State {
        @Override
        public void enter() {
            // TODO Auto-generated method stub
            Log.d(TAG, "enter ConnectState...");
        }
        
        @Override
        public boolean processMessage(Message msg) {
            // TODO Auto-generated method stub
            Log.d(TAG, "current state is ConnectedState msg is " + msg.what);
            switch (msg.what) {

            }
            
            return true;
        }
    }
    
    
    private class DataTransferState extends State {
        @Override
        public void enter() {
            // TODO Auto-generated method stub
            Log.d(TAG, "enter DataTransferState...");
        }
        
        @Override
        public boolean processMessage(Message msg) {
            // TODO Auto-generated method stub
            Log.d(TAG, "current state is DataTransferState msg is " + msg.what);
            switch (msg.what) {

            }
            
            return true;
        }
    }
    
}


