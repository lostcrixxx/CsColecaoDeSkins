package br.com.envolvedesenvolve.cscolecaodeskins.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import br.com.envolvedesenvolve.cscolecaodeskins.R;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private SwitchCompat switchCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchCollection = findViewById(R.id.switch_settings_collection);

        prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);
        switchCollection.setText(prefs.getBoolean("modeCollection", false) ? "ON" : "OFF");
        switchCollection.setChecked(prefs.getBoolean("modeCollection", false));

        switchCollection.setOnClickListener(view -> {
            prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = prefs.edit();

            ed.putBoolean("modeCollection", switchCollection.isChecked());
            ed.apply();

            switchCollection.setText(switchCollection.isChecked() ? "ON" : "OFF");

            Toast.makeText(getBaseContext(), "Configuração salva!", Toast.LENGTH_LONG).show();
        });
    }
}