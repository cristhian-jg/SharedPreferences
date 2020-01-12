package com.crisgon.sharedpreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvUser;
    private TextView tvPassword;
    private TextView tvService;
    private TextView tvEncrypted;
    private TextView tvEncryptedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        tvUser = findViewById(R.id.tvUser);
        tvPassword = findViewById(R.id.tvPassword);
        tvService = findViewById(R.id.tvService);
        tvEncrypted = findViewById(R.id.tvEncrypted);
        tvEncryptedType = findViewById(R.id.tvEncryptedType);

    }

    @Override
    protected void onPause() {
        super.onPause();

        boolean isService = false;
        boolean isEncrypted = false;

        if (tvService.getText().toString().equals("Sí")) isService = true;
        if (tvEncrypted.getText().toString().equals("Sí")) isEncrypted = true;
        if (tvService.getText().toString().equals("No")) isService = false;
        if (tvEncrypted.getText().toString().equals("No")) isEncrypted = false;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user-preference", tvUser.getText().toString());
        editor.putString("password-preference", tvPassword.getText().toString());
        editor.putBoolean("service-preference", isService);
        editor.putBoolean("encrypted-preference", isEncrypted);
        editor.putString("algorithm-preference", tvEncryptedType.getText().toString());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String user = preferences.getString("user-preference", "");
        String password = preferences.getString("password-preference", "");
        boolean serviceStatus = preferences.getBoolean("service-preference", false);
        boolean encryptedStatus = preferences.getBoolean("encrypted-preference", false);
        String algorithmSelected = preferences.getString("algorithm-preference", "");

        tvUser.setText(user);
        tvPassword.setText(password);
        if (serviceStatus) tvService.setText("Sí");
        if (!serviceStatus) tvService.setText("No");
        if (encryptedStatus) tvEncrypted.setText("Sí");
        if (!encryptedStatus) tvEncrypted.setText("No");
        tvEncryptedType.setText("("+ algorithmSelected + ")");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_preferences:
                startActivity(new Intent(MainActivity.this, PreferencesActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
