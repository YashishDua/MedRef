package com.example.lenovo.hackbvp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private ListView listview;
    ArrayList<String> arrayList;
    Adapter adapter;
    private ArrayAdapter<String> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        listview = (ListView) findViewById(R.id.listview);
        arrayList = new ArrayList<>();
        arrayList.add("tesvnkd");
        arr = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listview.setAdapter(arr);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }


    float lastX = 0;
    float lastY = 0;
    float lastZ = 0;
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            //if(mod(x,lastX)) {
                Log.i("Sensor", "onSensorChanged: X " + x);
                arrayList.add("X "+x);
                lastX = x;
            //}
            //if(mod(y,lastY)) {
                Log.i("Sensor", "onSensorChanged: Y " + y);
                arrayList.add("Y "+y);
                lastY = y;
            //}
            //if(mod(z,lastZ)) {
                arrayList.add("Z "+z);
                lastZ = z;
            //}

            Log.i("TAG", "onSensorChanged: Size"+arrayList.size());

            arr.notifyDataSetChanged();

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    boolean mod(float x,float y){

        if(x - y > 1 || x - y < -1)
        return true;

        return false;
    }
}
