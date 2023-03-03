package com.example.cronometro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {



    //numero de segundos que aparecen en el cronometro y numero de vuelta
    private int seconds = 0,i=0;;

    // Saber si se encuentra ejecutando
    private boolean running;
    //Obtener las vistas para mostrar los resultados
    private TextView texto,lap;
    //Saber si se estaba ejecutando
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        texto= (TextView)findViewById(R.id.tv_resultado);
        lap=(TextView) findViewById(R.id.tv_laps);

        if (savedInstanceState != null) {

            // Obtener el anterior estado del cronometro si la actividad a sido destruida y creada
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    //Guardar el valor del cronometro si esta a punto de ser destruido
    @Override
    public void onSaveInstanceState(
            Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    // Si la actividad se pausa, se detiene el cronometro
    @Override
    protected void onPause()
    {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    // Si la actividad se reanuda se inicia de nuevo el cronometro con el valor que contenia
    @Override
    protected void onResume()
    {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    // Iniciar el cronometro
    public void onClickStart(View view)
    {
        running = true;
    }

    //Detener el cronometro
    public void onClickStop(View view)
    {
        running = false;
    }

    // Restablece el cronometroo y las vueltas
    public void onClickReset(View view)
    {
        running = false;
        seconds = 0;
        lap.setText("");
        i=0;
    }

    // Establece el numero de segundos del cronometro para poder incrementarlos y mostrarlos
    private void runTimer()
    {

        // Se obtiene el textview
        final TextView timeView= (TextView)findViewById(R.id.tv_resultado);

        // Se crea un handler
        final Handler handler
                = new Handler();

        // Se ejecuta el código para el formato del tiempo
        handler.post(new Runnable() {
            @Override

            public void run()
            {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                // Formatea los segundos en horas, minutos y segundos
                String time
                        = String
                        .format(Locale.getDefault(),
                                "%d:%02d:%02d", hours,
                                minutes, secs);

                // Enviar el resultado a la vista
                timeView.setText(time);

                // Si running es true, se incrementan los segundos
                if (running) {
                    seconds++;
                }

                // Mandar el codigo de nuevo con un retraso de 1 segundo
                handler.postDelayed(this, 1000);
            }
        });
    }

    //Establecer una vuelta, incrementandola y registrando el tiempo en el cual se obtiene el valor
    public void onClickLap(View view){
        String vuelta=texto.getText().toString();
        i++;
        lap.setText("Lap "+i+" in "+vuelta+"\n"+lap.getText().toString());
    }
}