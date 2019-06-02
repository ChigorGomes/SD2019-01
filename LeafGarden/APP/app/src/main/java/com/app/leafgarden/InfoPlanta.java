package com.app.leafgarden;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InfoPlanta extends AppCompatActivity {
    TextView infoPlanta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_planta);

        infoPlanta = (TextView) findViewById(R.id.textViewTemperaturaPlanta);

        infoPlanta.setText("Mantenha a temperatura em sua casa\n" +
                "(ou pelo menos do local onde a\n" +
                "samambaia está) próxima a 21° C.");
    }
}
