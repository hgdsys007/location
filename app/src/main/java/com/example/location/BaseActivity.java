package com.example.location;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.Service.SocketService;

import org.json.JSONException;

public abstract class BaseActivity extends AppCompatActivity {

    protected SocketService.SendMessageBinder sendMessageBinder; //发送消息的binder 对象

    protected ServiceConnection connection = new ServiceConnection() { //与服务建立连接
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sendMessageBinder = (SocketService.SendMessageBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private final static  String TAG = "BaseActivity";


    protected abstract void initService();


    String message;  //客户端接收的信息

    //
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            message = intent.getStringExtra("ServerMessage");
            if (message!=null) {
                try {
                    getMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onReceive: ");
                }
            }
        }
    };

    public abstract void getMessage(String msg) throws JSONException;

    public String getMsg() {
        return message;
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.location.serverMessage");
        bindService();
        registerReceiver(receiver,filter);  //活动启动时注册广播
        initService();  //绑定服务


    }

    private void bindService() {
        Intent bindIntent = new Intent(BaseActivity.this, SocketService.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        unregisterReceiver(receiver);  //活动停止时解绑

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}