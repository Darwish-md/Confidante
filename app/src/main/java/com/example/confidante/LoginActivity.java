package com.example.confidante;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.confidante.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private ImageView compass;
    private TextView direction;
    private MaterialButton enter;
    private List<String> secretPassword;
    private List<String> enteredPassword;
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private Sensor sensorMagneticField;

    private float[] floatGravity = new float[3];
    private float[] floatGeoMagnetic = new float[3];

    private float[] floatOrientation = new float[3];
    private float[] floatRotationMatrix = new float[9];
    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        secretPassword = new ArrayList<>();
        secretPassword.add("W");
        secretPassword.add("NW");
        secretPassword.add("NE");

        enteredPassword = new ArrayList<>();

        compass = findViewById(R.id.compass);
        direction = findViewById(R.id.direction);

        enter = findViewById(R.id.enter);
        showButtonText(i);
        i++;
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredPassword.add((String) direction.getText());
                showButtonText(i);
                i++;
                if (i > 3) i=1;
                System.out.println(enteredPassword);
                System.out.println(i);
                if(enteredPassword.size() == 3){
                    if(secretPassword.equals(enteredPassword)){
                        enteredPassword.clear();
                        Intent intent = new Intent(LoginActivity.this, CategoriesActivity.class);
                        startActivity(intent);
                    }
                    else{
                        enteredPassword.clear();
                        Toast.makeText(LoginActivity.this,"Wrong coordinates!",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        SensorEventListener sensorEventListenerAccelrometer = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                floatGravity = event.values;

                SensorManager.getRotationMatrix(floatRotationMatrix, null, floatGravity, floatGeoMagnetic);
                SensorManager.getOrientation(floatRotationMatrix, floatOrientation);
                float coordinate = (float) (-floatOrientation[0]*180/3.14159);
                compass.setRotation(coordinate);
                setDirection(coordinate);
               // direction.setText(String.valueOf(coordinate));

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

        SensorEventListener sensorEventListenerMagneticField = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                floatGeoMagnetic = event.values;

                SensorManager.getRotationMatrix(floatRotationMatrix, null, floatGravity, floatGeoMagnetic);
                SensorManager.getOrientation(floatRotationMatrix, floatOrientation);

                compass.setRotation((float) (-floatOrientation[0]*180/3.14159));

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        sensorManager.registerListener(sensorEventListenerAccelrometer, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListenerMagneticField, sensorMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void setDirection(float coordinate){
        if(coordinate >-10 && coordinate<10) direction.setText("N");
        else if(coordinate >10 && coordinate<80 ) direction.setText("NW");
        else if(coordinate >80 && coordinate<100) direction.setText("W");
        else if(coordinate >100 && coordinate<170) direction.setText("SW");
        else if(coordinate >170 || coordinate<-170) direction.setText("S");
        else if(coordinate >-170 && coordinate<-100) direction.setText("SE");
        else if(coordinate >-100 && coordinate<-80) direction.setText("E");
        else if(coordinate >-80 && coordinate<-10) direction.setText("NE");
    }

    public void showButtonText(int i){
        switch (i){
            case 1 : enter.setText("Enter the 1st coordinate");break;
            case 2 : enter.setText("Enter the 2nd coordinate");break;
            case 3 : enter.setText("Enter the 3rd coordinate");break;
        }

    }
}