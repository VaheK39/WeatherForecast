package com.example.lesson48.connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class ConnectionState extends ConnectivityManager.NetworkCallback {
    private static final String TAG = "ConnectivityMonitor";

    final NetworkRequest networkRequest;
    Context context;

    public ConnectionState(Context context) {
        networkRequest = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build();
        this.context = context;
    }

    public void enable(Context context) {
        ConnectivityManager cm  = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        cm.registerNetworkCallback(networkRequest, this);
    }

    public void disable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        cm.unregisterNetworkCallback(this);
    }


    @Override
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
        Log.d(TAG, "onAvailable: Network is available");
        NetworkConnection.isConnected = true;
    }


    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
        Log.d(TAG, "onAvailable: Connection Lost");
        NetworkConnection.isConnected = false;
    }


}
