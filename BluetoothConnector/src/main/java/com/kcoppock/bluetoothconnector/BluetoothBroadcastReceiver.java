package com.kcoppock.bluetoothconnector;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 *
 * Copyright 2013 Kevin Coppock
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class BluetoothBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "BluetoothBroadcastReceiver";

    private Callback mCallback;

    private BluetoothBroadcastReceiver (Callback callback) {
        mCallback = callback;
    }

    /**
     * Convenience method to register a new instance of this receiver with the
     * necessary IntentFilter, which will notify the callback upon a successful
     * connection or error case.
     * @param callback the callback that should be notified on success or failure
     *                 of Bluetooth being enabled
     * @param c the context from which to register the receiver
     */
    public static void register (Callback callback, Context c) {
        c.registerReceiver(new BluetoothBroadcastReceiver(callback), getFilter());
    }

    private static IntentFilter getFilter () {
        return new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
    }

    @Override
    public void onReceive (Context context, Intent intent) {
        if (!BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction())) {
            Log.v(TAG, "Received irrelevant broadcast. Disregarding.");
            return;
        }

        //This is a State Change event, get the state extra, falling back to ERROR
        //if it isn't there (which shouldn't happen)
        int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

        switch (state) {
            case BluetoothAdapter.STATE_CONNECTED:
                safeUnregisterReceiver(context, this);
                fireOnBluetoothConnected();
                break;
            case BluetoothAdapter.ERROR:
                safeUnregisterReceiver(context, this);
                fireOnBluetoothError();
                break;
        }
    }

    /**
     * Convenience method to do a checked unregistration of a broadcast receiver. Occasionally
     * you can get into a state where the receiver was already unregistered, throwing an
     * IllegalArgumentException. This method just swallows that exception and logs the error.
     * @param c the context from which to unregister the receiver
     * @param receiver the receiver that should be unregistered if it is not already unregistered
     */
    private static void safeUnregisterReceiver (Context c, BroadcastReceiver receiver) {
        try {
            c.unregisterReceiver(receiver);
        } catch (IllegalArgumentException ex) {
            Log.w(TAG, "Tried to unregister BluetoothBroadcastReceiver that was not registered.");
        }
    }

    private void fireOnBluetoothConnected () {
        if (mCallback != null) {
            mCallback.onBluetoothConnected();
        }
    }

    private void fireOnBluetoothError () {
        if (mCallback != null) {
            mCallback.onBluetoothError();
        }
    }

    public static interface Callback {
        public void onBluetoothConnected();
        public void onBluetoothError();
    }
}
