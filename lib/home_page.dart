import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  static const platform = const MethodChannel('samples.flutter.dev/battery');

  // Wifi Check

  static const platform1=const MethodChannel('samaple.flutter.dev/Wifi');
  String _wifiCheck='Unknown wifi';


  String _batteryLevel = 'Unknown battery level.';


  Future<void> _getWifi()async

  {
    String Wificheck;

    try {
      final int result1= await platform1.invokeMethod('getWifiStatus');
      Wificheck='Wifi check at $result1';
    }on PlatformException catch(e){
      Wificheck='Failed ${e.message}';
    }
    setState(() {
      _wifiCheck=Wificheck;
    });

  }

  Future<void> _getBatteryLevel() async {
    String batteryLevel;
    try {
      final int result = await platform.invokeMethod('getBatteryLevel');
      batteryLevel = 'Battery level at $result %';
    } on PlatformException catch (e) {
      batteryLevel = "Failed to get battery level: '${e.message}'.";
    }
    setState(() {
      _batteryLevel = batteryLevel;
    });
  }
  @override
  Widget build(BuildContext context) {
    return Material(
      child: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            ElevatedButton(
              child: Text('Get Battery Level'),
              onPressed: _getBatteryLevel,
            ),
            Text(_batteryLevel),
            Text(_wifiCheck),
            Container(
              margin:EdgeInsets.all(10),
              child: FlatButton(
                child: Text('Wifi Connection Check'),
                color: Colors.amberAccent,
                onPressed:_getWifi,
              ),
            )
          ],
        ),
      ),
    );
  }
}

