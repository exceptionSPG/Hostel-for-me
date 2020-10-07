package com.hfad.hostel.Helper;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utilities {
        public static boolean isConnectionAvailable(Activity activity) {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) activity.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
    }
