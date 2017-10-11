package com.example.lenovo.hackbvp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainingActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    ArrayList<TrainingData> arrayList;
    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        arrayList = new ArrayList<>();
        edit = (EditText) findViewById(R.id.edit_query);
        final APIService service = Client.getService();
        Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(TrainingActivity.this,"Started",Toast.LENGTH_SHORT).show();
                senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                senSensorManager.registerListener(TrainingActivity.this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

            }
        });

        Button end = (Button) findViewById(R.id.stop);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ResponseBody> call = service.postData(arrayList);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful())
                            Toast.makeText(TrainingActivity.this,"Success!",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(TrainingActivity.this,"Failed!",Toast.LENGTH_SHORT).show();

                    }


                });
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            String type = edit.getText().toString();
            TrainingData d = new TrainingData(x,y,z,type);

            arrayList.add(d);
            Log.i("TAG", "onSensorChanged: Adding..");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
