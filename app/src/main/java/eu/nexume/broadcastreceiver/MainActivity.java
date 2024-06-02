package eu.nexume.broadcastreceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Създаваме обект "receiver" от клас MyBroadcastReceiver
    MyBroadcastReceiver receiver = new MyBroadcastReceiver();
    IntentFilter intentFilter, airPlaneIntentFilter;
    Button btShow;
    EditText edInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btShow = findViewById(R.id.btShow);
        edInput = findViewById(R.id.edInput);

        // Създаваме интент филтър
        intentFilter = new IntentFilter("com.example.CUSTOM_INTENT");

        airPlaneIntentFilter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);

        // Натискайки бутона - Добавяме действието/текста във филтъра и изпращаме Broadcast с Intent
        btShow.setOnClickListener(v -> {
            String textStr = edInput.getText().toString().trim();
            Intent intent = new Intent();
            intent.putExtra("msg", textStr);
            intent.setAction("com.example.CUSTOM_INTENT");
            sendBroadcast(intent);
        });
    }

    // Регистриране на приемника
    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, intentFilter, RECEIVER_NOT_EXPORTED);
            registerReceiver(receiver, airPlaneIntentFilter, RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(receiver, intentFilter);
            registerReceiver(receiver, airPlaneIntentFilter);
        }
    }

    // Отписване на приемника
    @Override
    protected void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }
}