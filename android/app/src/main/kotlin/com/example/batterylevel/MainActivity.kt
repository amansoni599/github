package com.example.batterylevel

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.BatteryManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.widget.Toast
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugins.GeneratedPluginRegistrant
import android.net.wifi.WifiInfo

import android.net.wifi.WifiManager




class MainActivity : FlutterActivity() {
    private  var CHANNEL = "samples.flutter.dev/battery"
    private  var CHANNEL1 = "samaple.flutter.dev/Wifi"
    var context = this


    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
                .setMethodCallHandler { call: MethodCall, result: MethodChannel.Result ->
                    if (call.method == "getBatteryLevel") {
                        val batteryLevel = batteryLevel
                        if (batteryLevel != -1) {
                            result.success(batteryLevel)
                        } else {
                            result.error("UNAVAILABLE", "Battery level not available.", null)
                        }
                    } else {
                        result.notImplemented()
                    }
                }
              MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL1)
                .setMethodCallHandler { call: MethodCall, result: MethodChannel.Result ->
                    if (call.method == "getWifiStatus") {
                        val wifiManager = context.getApplicationContext().getSystemService(WIFI_SERVICE) as WifiManager
                        val wifiInfo = wifiManager.connectionInfo
                        val ssid = wifiInfo.ssid

                        if ( wifiManager != null)
                        {
                            var info = wifiManager!!.wifiState
                            val wifiInfo = wifiManager.connectionInfo

                            if (info != null)
                            {
                                if()
                                {
                                    Toast.makeText(context, "CONNECTED", Toast.LENGTH_LONG).show()
                                }
                            }
                            else
                            {
                                Toast.makeText(context, "NOT CONNECTED", Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        result.notImplemented()
                    }
                }
    }

    private val batteryLevel: Int
        private get() {
            var batteryLevel = -1
            batteryLevel = if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                val batteryManager = getSystemService(BATTERY_SERVICE) as BatteryManager
                batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
            } else {
                val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
                intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 /
                        intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            }
            return batteryLevel
        }

}