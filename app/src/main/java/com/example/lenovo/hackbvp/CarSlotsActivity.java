package com.example.lenovo.hackbvp;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarSlotsActivity extends AppCompatActivity implements SensorEventListener {

    ArrayList<TrainingData> arrayList;

    private static final int DELAY = 3000;
    public String type = "";
    Boolean changed = false;
    private final Handler handler = new Handler();
    private final Timer timer = new Timer();
    private final TimerTask task = new TimerTask() {
        public void run() {
            handler.post(new Runnable() {
                public void run() {

                    Log.i("TAG", "run: ");
                    final String[] currentString = new String[1];
                    AzureBody a = new AzureBody();
                    AzureBody.Inputs i = new AzureBody.Inputs(arrayList);
                    a.setInputs(i);
                    APIService service = AzureClient.getService();
                    Call<ResponseAzure> call = service.postAzure(a);
                    call.enqueue(new Callback<ResponseAzure>() {
                        @Override
                        public void onResponse(Call<ResponseAzure> call, Response<ResponseAzure> response) {
                            Log.i("TAG", "onResponse: ");
                            if(response.isSuccessful()){
                                ResponseAzure responseAzure = response.body();
                                currentString[0] = responseAzure.getType();
                                if(currentString[0].compareTo("standing")==0 && type.compareTo("sitting") ==0){
                                    changed = true;
                                    Toast.makeText(CarSlotsActivity.this,"Parked",Toast.LENGTH_SHORT).show();
                                }
                                type = currentString[0];
                                arrayList.clear();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseAzure> call, Throwable t) {
                            Log.i("TAG", "onFailure: " + t.getMessage());
                        }
                    });


                }
            });
            if(changed) {
                timer.cancel();
            }
        }
    };
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_slots);

        arrayList = new ArrayList<>();
        LinearLayout l = (LinearLayout) findViewById(R.id.parentPanel);
        for (int i = 0; i < 50; i++) {
            final TextView f = new TextView(this);
            f.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {

                                     }
                                 });

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,10,10,10);
            f.setLayoutParams(params);
            f.setBackgroundColor(Color.WHITE);
            f.setTextColor(Color.WHITE);
            f.setText("Unparked");
            f.setTextSize(20);
            l.addView(f);
        }

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);

        timer.schedule(task, DELAY, DELAY);



    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            arrayList.add(new TrainingData(x,y,z,""));
        }

        }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
