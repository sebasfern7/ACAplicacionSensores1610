package com.example.acaplicacionsensores1610;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView accelX, accelY, accelZ;  // Acelerómetro
    TextView gyroX, gyroY, gyroZ;     // Giroscopio
    TextView magX, magY, magZ;        // Magnetómetro

    TextView precision;
    SensorManager gestorSensores;
    Sensor acelerometro;
    Sensor magnetometro;
    Sensor giroscopio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accelX = findViewById(R.id.xcoor);
        accelY = findViewById(R.id.ycoor);
        accelZ = findViewById(R.id.zcoor);

        // Giroscopio
        gyroX = findViewById(R.id.xcoor_gyro);
        gyroY = findViewById(R.id.ycoor_gyro);
        gyroZ = findViewById(R.id.zcoor_gyro);

        // Magnetómetro
        magX = findViewById(R.id.xcoor_magnet);
        magY = findViewById(R.id.ycoor_magnet);
        magZ = findViewById(R.id.zcoor_magnet);

        precision = findViewById(R.id.precision);

        gestorSensores = (SensorManager) getSystemService(SENSOR_SERVICE);
        acelerometro = gestorSensores.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (acelerometro != null) {
            gestorSensores.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "Tu dispositivo no tiene acelerómetro", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                // Actualiza los TextViews del Acelerómetro
                accelX.setText(String.format("X: %.2f", x));
                accelY.setText(String.format("Y: %.2f", y));
                accelZ.setText(String.format("Z: %.2f", z));


            case Sensor.TYPE_MAGNETIC_FIELD:
                float xMag = event.values[0];
                float yMag = event.values[1];
                float zMag = event.values[2];

                // Actualiza los TextViews del Magnetómetro
                magX.setText(String.format("X: %.2f", xMag));
                magY.setText(String.format("Y: %.2f", yMag));
                magZ.setText(String.format("Z: %.2f", zMag));


            case Sensor.TYPE_GYROSCOPE:
                float xGyro = event.values[0];
                float yGyro = event.values[1];
                float zGyro = event.values[2];

                // Actualiza los TextViews del Giroscopio
                gyroX.setText(String.format("X: %.2f", xGyro));
                gyroY.setText(String.format("Y: %.2f", yGyro));
                gyroZ.setText(String.format("Z: %.2f", zGyro));

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        String mensaje;
        switch (accuracy) {
            case SensorManager.SENSOR_STATUS_UNRELIABLE:
                mensaje = "Precisión baja: el sensor está confundido.";
                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                mensaje = "Precisión baja: intenta no moverlo tanto.";
                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                mensaje = "Precisión media: ¡va mejorando!";
                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                mensaje = "Precisión alta: ¡perfecto!";
                break;
            default:
                mensaje = "Estado desconocido.";
        }
        precision.setText(mensaje);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gestorSensores.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        gestorSensores.registerListener(this, magnetometro, SensorManager.SENSOR_DELAY_NORMAL);
        gestorSensores.registerListener(this, giroscopio, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gestorSensores.unregisterListener(this);
    }
}